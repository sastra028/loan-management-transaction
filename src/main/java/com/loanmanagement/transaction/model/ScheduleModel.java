package com.loanmanagement.transaction.model;

import java.util.Date;

public class ScheduleModel {

	private int payCycle;
	private Date dueDate;
	private Double dueAmount;

	public int getPayCycle() {
		return payCycle;
	}

	public void setPayCycle(int payCycle) {
		this.payCycle = payCycle;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(Double dueAmount) {
		this.dueAmount = dueAmount;
	}

}
