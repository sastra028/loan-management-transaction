package com.loanmanagement.transaction.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loanmanagement.transaction.constant.TransactionStatus;
import com.loanmanagement.transaction.entity.TransactionEntity;
import com.loanmanagement.transaction.entity.TransactionLogEntity;
import com.loanmanagement.transaction.model.CommonResponse;
import com.loanmanagement.transaction.model.TransactionRequest;
import com.loanmanagement.transaction.model.TransactionResponst;
import com.loanmanagement.transaction.repository.TransactionLogRepository;
import com.loanmanagement.transaction.repository.TransactionRepository;
import com.loanmanagement.transaction.util.ConvertUtils;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Autowired
	private TransactionDetailService transactionDetailService;

	public List<TransactionResponst> findAllTransaction() {
		return mapEntityToResponse(transactionRepository.findAll());
	}

	public List<TransactionResponst> findByNameSet(TransactionRequest transactionRequest) {
		return mapEntityToResponse(transactionRepository.findByFirstNameThaiOrLastNameThaiOrCitizenId(
				transactionRequest.getFirstNameThai(), transactionRequest.getLastNameThai(),
				transactionRequest.getCitizenId()));
	}

	public List<TransactionResponst> findPaidToday() {
		Date currentDate = Calendar.getInstance().getTime();
		Date refundDate = ConvertUtils.stringToDate(ConvertUtils.dateToString(currentDate, "dd/MM/yyyy"), "dd/MM/yyyy");
		return mapEntityToResponse(transactionRepository.findByRefundDate(refundDate));
	}

	public List<TransactionResponst> findOverdue() {
		Date currentDate = Calendar.getInstance().getTime();
		Date refundDate = ConvertUtils.stringToDate(ConvertUtils.dateToString(currentDate, "dd/MM/yyyy"), "dd/MM/yyyy");
		return mapEntityToResponse(transactionRepository.findByRefundDateBeforeAndStatus(refundDate, "process"));
	}

	public CommonResponse create(TransactionRequest transactionRequest) {

		CommonResponse commonResponse = new CommonResponse();
		if (StringUtils.isBlank(transactionRequest.getTransactionNo())) {
			TransactionEntity transactionEntity = mapRequestEntity(transactionRequest);
			transactionEntity.setTransactionNo(ConvertUtils.genKey("T"));
			transactionEntity.setCreateDate(new Date());
			transactionEntity.setUpdateDate(new Date());
			transactionEntity.setStatus(TransactionStatus.BORROWING.getCode());
			transactionRepository.save(transactionEntity);
			transactionLog(transactionEntity, "create");
			
			transactionDetailService.create(transactionEntity);
			
			commonResponse.setStatus("success");
		} else {
			commonResponse.setStatus("fail (request has transaction no)");
		}

		return commonResponse;
	}

	public CommonResponse update(TransactionRequest transactionRequest) {
		CommonResponse commonResponse = new CommonResponse();
		if (StringUtils.isNotBlank(transactionRequest.getTransactionNo())) {
			Optional<TransactionEntity> TransactionEntityOptional = transactionRepository
					.findById(transactionRequest.getTransactionNo());
			if (TransactionEntityOptional.isPresent()) {
				TransactionEntity transactionEntity = TransactionEntityOptional.get();
				transactionEntity.setTransactionNo(transactionRequest.getTransactionNo());
				transactionEntity.setAccountNo(transactionRequest.getAccountNo());
				transactionEntity.setTitle(transactionRequest.getTitle());
				transactionEntity.setFirstNameThai(transactionRequest.getFirstNameThai());
				transactionEntity.setLastNameThai(transactionRequest.getLastNameThai());
				transactionEntity.setCitizenId(transactionRequest.getCitizenId());
				transactionEntity
						.setLoanDate(ConvertUtils.stringToDate(transactionRequest.getLoanDate(), "dd/MM/yyyy"));
				transactionEntity
						.setRefundDate(ConvertUtils.stringToDate(transactionRequest.getRefundDate(), "dd/MM/yyyy"));
				transactionEntity.setLoanAmount(Double.parseDouble(transactionRequest.getLoanAmount()));
				transactionEntity.setStatus(transactionRequest.getStatus());
				transactionEntity.setNote(transactionRequest.getNote());
				transactionEntity.setUpdateDate(new Date());
				transactionEntity.setUpdateBy(transactionRequest.getUpdateBy());

				transactionRepository.save(transactionEntity);
				transactionLog(transactionEntity, "update");
				commonResponse.setStatus("success");
			} else {
				commonResponse.setStatus(
						"fail (transaction no: " + transactionRequest.getTransactionNo() + " not found on database)");
			}
		} else {
			commonResponse.setStatus("fail (request not have transaction no)");
		}

		return commonResponse;
	}

	public CommonResponse delete(TransactionRequest transactionRequest) {
		CommonResponse commonResponse = new CommonResponse();
		if (StringUtils.isNotBlank(transactionRequest.getTransactionNo())) {
			Optional<TransactionEntity> transactionEntity = transactionRepository
					.findById(transactionRequest.getTransactionNo());
			if (transactionEntity.isPresent()) {
				transactionRepository.deleteById(transactionRequest.getTransactionNo());
				commonResponse.setStatus("success");
				transactionLog(transactionEntity.get(), "delete");
			} else {
				commonResponse.setStatus(
						"fail (transaction no: " + transactionRequest.getTransactionNo() + " not found on database)");
			}
		} else {
			commonResponse.setStatus("fail (request not have transaction no)");
		}
		return commonResponse;
	}

	private List<TransactionResponst> mapEntityToResponse(List<TransactionEntity> transactionEntityList) {
		List<TransactionResponst> transactionResponstList = new ArrayList<>();
		for (TransactionEntity transactionEntity : transactionEntityList) {
			TransactionResponst transactionResponst = new TransactionResponst();
			transactionResponst.setTransactionNo(transactionEntity.getTransactionNo());
			transactionResponst.setAccountNo(transactionEntity.getAccountNo());
			transactionResponst.setTitle(transactionEntity.getTitle());
			transactionResponst.setFirstNameThai(transactionEntity.getFirstNameThai());
			transactionResponst.setLastNameThai(transactionEntity.getLastNameThai());
			transactionResponst.setCitizenId(transactionEntity.getCitizenId());
			transactionResponst.setLoanDate(ConvertUtils.dateToString(transactionEntity.getLoanDate(), "dd/MM/yyyy"));
			transactionResponst
					.setRefundDate(ConvertUtils.dateToString(transactionEntity.getRefundDate(), "dd/MM/yyyy"));
			transactionResponst.setLoanAmount(String.valueOf(transactionEntity.getLoanAmount()));
			transactionResponst.setStatus(transactionEntity.getStatus());
			transactionResponst.setNote(transactionEntity.getNote());
			transactionResponst
					.setUpdateDate(ConvertUtils.dateToString(transactionEntity.getUpdateDate(), "dd/MM/yyyy"));
			transactionResponst.setUpdateBy(transactionEntity.getUpdateBy());
			transactionResponst
					.setCreateDate(ConvertUtils.dateToString(transactionEntity.getCreateDate(), "dd/MM/yyyy"));
			transactionResponst.setCreateBy(transactionEntity.getCreateBy());

			transactionDetailService.calTransactionDetail(transactionResponst);

			transactionResponstList.add(transactionResponst);
		}
		return transactionResponstList;
	}

	private TransactionEntity mapRequestEntity(TransactionRequest transactionRequest) {

		TransactionEntity transactionEntity = new TransactionEntity();
		transactionEntity.setTransactionNo(transactionRequest.getTransactionNo());
		transactionEntity.setAccountNo(transactionRequest.getAccountNo());
		transactionEntity.setTitle(transactionRequest.getTitle());
		transactionEntity.setFirstNameThai(transactionRequest.getFirstNameThai());
		transactionEntity.setLastNameThai(transactionRequest.getLastNameThai());
		transactionEntity.setCitizenId(transactionRequest.getCitizenId());
		transactionEntity.setLoanDate(ConvertUtils.stringToDate(transactionRequest.getLoanDate(), "dd/MM/yyyy"));
		transactionEntity.setRefundDate(ConvertUtils.stringToDate(transactionRequest.getRefundDate(), "dd/MM/yyyy"));
		transactionEntity.setLoanAmount(ConvertUtils.stringToDouble(transactionRequest.getLoanAmount()));
		transactionEntity.setStatus(transactionRequest.getStatus());
		transactionEntity.setNote(transactionRequest.getNote());
		transactionEntity.setUpdateDate(new Date());
		transactionEntity.setUpdateBy(transactionRequest.getUpdateBy());
		transactionEntity.setCreateDate(new Date());
		transactionEntity.setCreateBy(transactionRequest.getCreateBy());

		return transactionEntity;
	}

	private void transactionLog(TransactionEntity transactionEntity, String action) {
		TransactionLogEntity transactionLogEntity = new TransactionLogEntity();
		transactionLogEntity.setId(UUID.randomUUID().toString());
		transactionLogEntity.setTransactionNo(transactionEntity.getTransactionNo());
		transactionLogEntity.setAccountNo(transactionEntity.getAccountNo());
		transactionLogEntity.setTitle(transactionEntity.getTitle());
		transactionLogEntity.setFirstNameThai(transactionEntity.getFirstNameThai());
		transactionLogEntity.setLastNameThai(transactionEntity.getLastNameThai());
		transactionLogEntity.setCitizenId(transactionEntity.getCitizenId());
		transactionLogEntity.setLoanDate(transactionEntity.getLoanDate());
		transactionLogEntity.setRefundDate(transactionEntity.getRefundDate());
		transactionLogEntity.setLoanAmount(transactionEntity.getLoanAmount());
		transactionLogEntity.setStatus(transactionEntity.getStatus());
		transactionLogEntity.setNote(transactionEntity.getNote());
		transactionLogEntity.setUpdateDate(transactionEntity.getUpdateDate());
		transactionLogEntity.setUpdateBy(transactionEntity.getUpdateBy());
		transactionLogEntity.setCreateDate(transactionEntity.getCreateDate());
		transactionLogEntity.setCreateBy(transactionEntity.getCreateBy());
		transactionLogEntity.setAction(action);

		transactionLogRepository.save(transactionLogEntity);
	}

}
