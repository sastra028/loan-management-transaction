package com.loanmanagement.transaction.constant;

public enum TransactionStatus {
	SUCCESS("S", "success", "รายการเสร็จสมบูรณ์"), BORROWING("B", "borrowing", "กำลังกู้"),
	OVERDUE("OD", "overdue", "ค้างชำระ"), OVER_CONTRACT("OC", "over contract", "เกินกำหนดสัญญา"),
	OVERDUE_INTEREST("OI", "over interest", "เกินกำหนดชำระดอกเบี้ย"),
	OVERDUE_PAID("OP", "over paid", "จ่ายเกิดเงินต้น");

	private final String code; // in kilograms
	private final String descTh; // in meters
	private final String descEng; // in meters

	TransactionStatus(String code, String descTh, String descEng) {
		this.code = code;
		this.descTh = descTh;
		this.descEng = descEng;
	}

	public String getCode() {
		return code;
	}

	public String getDescTh() {
		return descTh;
	}

	public String getDescEng() {
		return descEng;
	}

}
