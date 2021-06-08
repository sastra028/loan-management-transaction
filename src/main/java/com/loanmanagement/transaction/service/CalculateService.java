package com.loanmanagement.transaction.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loanmanagement.transaction.entity.TransactionDetailEntity;
import com.loanmanagement.transaction.model.CalculateModel;
import com.loanmanagement.transaction.model.ScheduleModel;
import com.loanmanagement.transaction.model.TransactionDetailRequest;
import com.loanmanagement.transaction.repository.TransactionDetailRepository;

@Service
public class CalculateService {

	@Autowired
	private TransactionDetailRepository transactionDetailRepository;

	public CalculateModel calForUpdateStatus(TransactionDetailRequest transactionDetailRequest) {
		List<TransactionDetailEntity> transactionDetailEntityList = transactionDetailRepository
				.findByTransactionNo(transactionDetailRequest.getTransactionNo());

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
		CalculateModel calculateModel = new CalculateModel();
		calculateModel.setPaid(String.valueOf(paid));
		calculateModel.setFine(String.valueOf(fine));
		calculateModel.setInterest(String.valueOf(interest));
		calculateModel.setPrinciple(String.valueOf(principle));
		return calculateModel;
	}

	public List<ScheduleModel> calSchedule(Date loanDate, Date refundDate, Double amount) {
		List<ScheduleModel> scheduleModelList = new ArrayList<>();

		Calendar calendarLoanDate = Calendar.getInstance();
		calendarLoanDate.setTime(loanDate);

		Calendar calendarrefundDate = Calendar.getInstance();
		calendarrefundDate.setTime(refundDate);

		Double amountInterest = amount * 0.15;

		int payCycle = 1;
		while (calendarLoanDate.before(calendarrefundDate)) {
			ScheduleModel scheduleModel = new ScheduleModel();
			scheduleModel.setDueDate(calendarLoanDate.getTime());
			scheduleModel.setPayCycle(payCycle);
			scheduleModel.setDueAmount(amountInterest);
			scheduleModelList.add(scheduleModel);
			calendarLoanDate.add(Calendar.MONTH, 1);
			payCycle++;

		}

		return scheduleModelList;
	}
}
