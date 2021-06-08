package com.loanmanagement.transaction.service;

import java.util.Calendar;

import com.loanmanagement.transaction.util.ConvertUtils;

public class Bee {

	public static void main(String[] args) {
		
		Calendar calendarLoanDate = Calendar.getInstance();
		calendarLoanDate.setTime(ConvertUtils.stringToDate("31/01/2021", "dd/MM/yyyy"));
		System.out.println(calendarLoanDate.getTime());
		calendarLoanDate.add(Calendar.MONTH, 1);
		System.out.println(calendarLoanDate.getTime());
	}

}
