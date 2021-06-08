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
import com.loanmanagement.transaction.entity.TransactionDetailEntity;
import com.loanmanagement.transaction.entity.TransactionEntity;
import com.loanmanagement.transaction.model.CommonResponse;
import com.loanmanagement.transaction.model.TransactionDetailRequest;
import com.loanmanagement.transaction.model.TransactionDetailResponse;
import com.loanmanagement.transaction.model.TransactionResponst;
import com.loanmanagement.transaction.repository.TransactionDetailRepository;
import com.loanmanagement.transaction.util.ConvertUtils;

@Service
public class TransactionDetailService {

	@Autowired
	private TransactionDetailRepository transactionDetailRepository;

	public List<TransactionDetailResponse> findByTransactionDetailNo(
			TransactionDetailRequest transactionDetailRequest) {
		Optional<TransactionDetailEntity> transactionDetailEntityOptional = transactionDetailRepository
				.findById(transactionDetailRequest.getTransactionDetailNo());
		if (transactionDetailEntityOptional.isPresent()) {
			return mapEntityToResponse(transactionDetailEntityOptional.get());
		} else {
			return new ArrayList<>();
		}

	}

	public List<TransactionDetailResponse> findByTransactionNo(TransactionDetailRequest transactionDetailRequest) {
		List<TransactionDetailEntity> transactionDetailEntityList = transactionDetailRepository
				.findByTransactionNo(transactionDetailRequest.getTransactionNo());
		return mapEntityToResponseList(transactionDetailEntityList);

	}

	public CommonResponse create(TransactionEntity transactionEntity) {

		CommonResponse commonResponse = new CommonResponse();
		if (StringUtils.isNotBlank(transactionEntity.getTransactionNo())) {

			for (TransactionDetailEntity transactionDetailEntity : calSchedule(transactionEntity)) {
				transactionDetailRepository.save(transactionDetailEntity);
			}
			commonResponse.setStatus("success");
		} else {
			commonResponse.setStatus("fail (transaction no is empty)");
		}

		return commonResponse;
	}

	public List<TransactionDetailEntity> calSchedule(TransactionEntity transactionEntity) {
		List<TransactionDetailEntity> scheduleModelList = new ArrayList<>();

		Date loanDate = transactionEntity.getLoanDate();
		Date refundDate = transactionEntity.getRefundDate();
		Double loanAmount = transactionEntity.getLoanAmount();

		Calendar calendarLoanDate = Calendar.getInstance();
		calendarLoanDate.setTime(loanDate);

		Calendar calendarrefundDate = Calendar.getInstance();
		calendarrefundDate.setTime(refundDate);

		Double amountInterest = loanAmount * 0.15;

		int payCycle = 1;

		TransactionDetailEntity transactionDetailEntity = new TransactionDetailEntity();
		transactionDetailEntity.setTransactionDetailNo(UUID.randomUUID().toString());
		transactionDetailEntity.setCreateDate(new Date());
		transactionDetailEntity.setCreateBy(transactionEntity.getCreateBy());
		transactionDetailEntity.setUpdateDate(new Date());
		transactionDetailEntity.setUpdateBy(transactionEntity.getUpdateBy());
		transactionDetailEntity.setPayCycle(payCycle);
		transactionDetailEntity.setTransactionStatus("transferred");

		transactionDetailEntity.setAccountNo(transactionEntity.getAccountNo());
		transactionDetailEntity
				.setPrinciple(transactionEntity.getLoanAmount() - (transactionEntity.getLoanAmount() * 0.15));
		transactionDetailEntity
				.setAmount(transactionEntity.getLoanAmount() - (transactionEntity.getLoanAmount() * 0.15));
		transactionDetailEntity.setTransactionNo(transactionEntity.getTransactionNo());

		scheduleModelList.add(transactionDetailEntity);

		calendarLoanDate.add(Calendar.MONTH, 1);
		payCycle++;

		while (calendarLoanDate.before(calendarrefundDate)) {
			transactionDetailEntity = new TransactionDetailEntity();
			transactionDetailEntity.setAccountNo(transactionEntity.getAccountNo());
			transactionDetailEntity.setDueDate(calendarLoanDate.getTime());
			transactionDetailEntity.setPayCycle(payCycle);
			transactionDetailEntity.setDueAmount(amountInterest);
			transactionDetailEntity.setTransactionDetailNo(UUID.randomUUID().toString());
			transactionDetailEntity.setCreateDate(new Date());
			transactionDetailEntity.setCreateBy(transactionEntity.getCreateBy());
			transactionDetailEntity.setUpdateDate(new Date());
			transactionDetailEntity.setUpdateBy(transactionEntity.getUpdateBy());
			transactionDetailEntity.setTransactionStatus("waiting");
			transactionDetailEntity.setTransactionNo(transactionEntity.getTransactionNo());
			scheduleModelList.add(transactionDetailEntity);

			calendarLoanDate.add(Calendar.MONTH, 1);
			payCycle++;
		}

		if (calendarLoanDate.equals(calendarrefundDate) || calendarLoanDate.after(calendarrefundDate)) {
			transactionDetailEntity = new TransactionDetailEntity();
			transactionDetailEntity.setAccountNo(transactionEntity.getAccountNo());
			transactionDetailEntity.setDueDate(calendarLoanDate.getTime());
			transactionDetailEntity.setPayCycle(payCycle);
			transactionDetailEntity.setDueAmount(transactionEntity.getLoanAmount());
			transactionDetailEntity.setTransactionDetailNo(UUID.randomUUID().toString());
			transactionDetailEntity.setCreateDate(new Date());
			transactionDetailEntity.setCreateBy(transactionEntity.getCreateBy());
			transactionDetailEntity.setUpdateDate(new Date());
			transactionDetailEntity.setUpdateBy(transactionEntity.getUpdateBy());
			transactionDetailEntity.setTransactionStatus("waiting");
			transactionDetailEntity.setTransactionNo(transactionEntity.getTransactionNo());
			scheduleModelList.add(transactionDetailEntity);

		}

		if (scheduleModelList.isEmpty()) {
			transactionDetailEntity = new TransactionDetailEntity();
			transactionDetailEntity.setAccountNo(transactionEntity.getAccountNo());
			transactionDetailEntity.setDueDate(calendarLoanDate.getTime());
			transactionDetailEntity.setPayCycle(payCycle);
			transactionDetailEntity.setDueAmount(transactionEntity.getLoanAmount());
			transactionDetailEntity.setTransactionDetailNo(UUID.randomUUID().toString());
			transactionDetailEntity.setCreateDate(new Date());
			transactionDetailEntity.setCreateBy(transactionEntity.getCreateBy());
			transactionDetailEntity.setUpdateDate(new Date());
			transactionDetailEntity.setUpdateBy(transactionEntity.getUpdateBy());
			transactionDetailEntity.setTransactionStatus("waiting");
			transactionDetailEntity.setTransactionNo(transactionEntity.getTransactionNo());
			scheduleModelList.add(transactionDetailEntity);
		}

		return scheduleModelList;
	}

//	public CommonResponse create(TransactionDetailRequest transactionDetailRequest) {
//
//		CommonResponse commonResponse = new CommonResponse();
//		if (StringUtils.isBlank(transactionDetailRequest.getTransactionNo())) {
//			TransactionDetailEntity transactionDetailEntity = mapRequestEntity(transactionDetailRequest);
//			transactionDetailEntity.setTransactionNo(ConvertUtils.genKey("T"));
//			transactionDetailEntity.setCreateDate(new Date());
//			transactionDetailRepository.save(transactionDetailEntity);
//			commonResponse.setStatus("success");
//		} else {
//			commonResponse.setStatus("fail (request has transaction no)");
//		}
//
//		return commonResponse;
//	}

	private List<TransactionDetailResponse> mapEntityToResponseList(
			List<TransactionDetailEntity> transactionDetailEntityList) {
		List<TransactionDetailResponse> transactionDetailResponseList = new ArrayList<>();
		for (TransactionDetailEntity transactionDetailEntity : transactionDetailEntityList) {
			TransactionDetailResponse transactionDetailResponse = new TransactionDetailResponse();
			transactionDetailResponse.setTransactionDetailNo(transactionDetailEntity.getTransactionDetailNo());
			transactionDetailResponse.setTransactionNo(transactionDetailEntity.getTransactionNo());
			transactionDetailResponse.setAccountNo(transactionDetailEntity.getAccountNo());
			transactionDetailResponse.setTransactionStatus(transactionDetailEntity.getTransactionStatus());
			transactionDetailResponse.setAmount(String.valueOf(transactionDetailEntity.getAmount()));
			transactionDetailResponse.setPrinciple(String.valueOf(transactionDetailEntity.getPrinciple()));
			transactionDetailResponse.setInterest(String.valueOf(transactionDetailEntity.getInterest()));
			transactionDetailResponse.setFines(String.valueOf(transactionDetailEntity.getFines()));
			transactionDetailResponse.setNote(transactionDetailEntity.getNote());
			transactionDetailResponse
					.setCreateDate(ConvertUtils.dateToString(transactionDetailEntity.getCreateDate(), "dd/MM/yyyy"));
			transactionDetailResponse.setCreateBy(transactionDetailEntity.getCreateBy());

			transactionDetailResponseList.add(transactionDetailResponse);
		}
		return transactionDetailResponseList;
	}

	private List<TransactionDetailResponse> mapEntityToResponse(TransactionDetailEntity transactionDetailEntity) {
		List<TransactionDetailResponse> transactionDetailResponseList = new ArrayList<>();
		TransactionDetailResponse transactionDetailResponse = new TransactionDetailResponse();
		transactionDetailResponse.setTransactionDetailNo(transactionDetailEntity.getTransactionDetailNo());
		transactionDetailResponse.setTransactionNo(transactionDetailEntity.getTransactionNo());
		transactionDetailResponse.setAccountNo(transactionDetailEntity.getAccountNo());
		transactionDetailResponse.setTransactionStatus(transactionDetailEntity.getTransactionStatus());
		transactionDetailResponse.setAmount(String.valueOf(transactionDetailEntity.getAmount()));
		transactionDetailResponse.setPrinciple(String.valueOf(transactionDetailEntity.getPrinciple()));
		transactionDetailResponse.setInterest(String.valueOf(transactionDetailEntity.getInterest()));
		transactionDetailResponse.setFines(String.valueOf(transactionDetailEntity.getFines()));
		transactionDetailResponse.setNote(transactionDetailEntity.getNote());
		transactionDetailResponse
				.setCreateDate(ConvertUtils.dateToString(transactionDetailEntity.getCreateDate(), "dd/MM/yyyy"));
		transactionDetailResponse.setCreateBy(transactionDetailEntity.getCreateBy());

		transactionDetailResponseList.add(transactionDetailResponse);
		return transactionDetailResponseList;
	}

	private TransactionDetailEntity mapRequestEntity(TransactionDetailRequest transactionRequest) {

		TransactionDetailEntity transactionDetailEntity = new TransactionDetailEntity();
		transactionDetailEntity.setTransactionDetailNo(transactionDetailEntity.getTransactionDetailNo());
		transactionDetailEntity.setTransactionNo(transactionDetailEntity.getTransactionNo());
		transactionDetailEntity.setAccountNo(transactionDetailEntity.getAccountNo());
		transactionDetailEntity.setTransactionStatus(transactionDetailEntity.getTransactionStatus());
		transactionDetailEntity.setAmount(transactionDetailEntity.getAmount());
		transactionDetailEntity.setPrinciple(transactionDetailEntity.getPrinciple());
		transactionDetailEntity.setInterest(transactionDetailEntity.getInterest());
		transactionDetailEntity.setFines(transactionDetailEntity.getFines());
		transactionDetailEntity.setNote(transactionDetailEntity.getNote());
		transactionDetailEntity.setCreateDate(transactionDetailEntity.getCreateDate());
		transactionDetailEntity.setCreateBy(transactionDetailEntity.getCreateBy());

		return transactionDetailEntity;
	}

	public void calTransactionDetail(TransactionResponst transactionResponst) {
		List<TransactionDetailEntity> transactionDetailEntityList = transactionDetailRepository
				.findByTransactionNo(transactionResponst.getTransactionNo());

		Double paid = 0.0;
		Double fine = 0.0;
		Double interest = 0.0;
		Double principle = 0.0;
		for (TransactionDetailEntity transactionDetailEntity : transactionDetailEntityList) {
			paid = paid + transactionDetailEntity.getAmount();
			fine = fine + transactionDetailEntity.getFines();
			interest = interest + transactionDetailEntity.getInterest();
			principle = principle + transactionDetailEntity.getPrinciple();
		}
		transactionResponst.setPaid(String.valueOf(paid));
		transactionResponst.setFine(String.valueOf(fine));
		transactionResponst.setInterest(String.valueOf(interest));
		transactionResponst.setPrinciple(String.valueOf(principle));
	}

	public TransactionStatus getStatusTransaction(TransactionResponst transactionResponst) {
		List<TransactionDetailEntity> transactionDetailEntityList = transactionDetailRepository
				.findByTransactionNo(transactionResponst.getTransactionNo());

		Double paid = 0.0;
		Double fine = 0.0;
		Double interest = 0.0;
		Double principle = 0.0;

		for (TransactionDetailEntity transactionDetailEntity : transactionDetailEntityList) {
			paid = paid + transactionDetailEntity.getAmount();
			fine = fine + transactionDetailEntity.getFines();
			interest = interest + transactionDetailEntity.getInterest();
			principle = principle + transactionDetailEntity.getPrinciple();
		}

		// SUCCESS("S", "success", "รายการเสร็จสมบูรณ์")
		if (principle == paid) {
			return TransactionStatus.SUCCESS;
		}

		TransactionStatus transactionStatusResult = null;
		// OVERDUE_PAID("OP", "over paid", "จ่ายเกิดเงินต้น")
		if (principle < paid) {
			return TransactionStatus.OVERDUE_PAID;
		} else {

			// BORROWING("B", "borrowing", "กำลังกู้")
			transactionStatusResult = TransactionStatus.OVERDUE_PAID;

			// OVERDUE("OD", "overdue", "ค้างชำระ")

			// OVER_CONTRACT("OC", "over contract", "เกินกำหนดสัญญา")

			// OVERDUE_INTEREST("OI", "overdue interest", "เกินกำหนดชำระดอกเบี้ย");
		}

		return transactionStatusResult;

	}

}
