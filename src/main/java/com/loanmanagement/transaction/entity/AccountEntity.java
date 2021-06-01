package com.loanmanagement.transaction.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class AccountEntity {
	@Id
	private String accountNo;
	private String title;
	private String firstNameThai;
	private String lastNameThai;
	private String firstNameEng;
	private String lastNameEng;
	private String citizenId;
	private String bankName;
	private String bankAccountName;
	private String bankAccountId;
	private String tel1;
	private String tel2;
	private String tel3;
	private String telRef1;
	private String telRef2;
	private String telRef3;
	private String lineId;
	private String career;
	private String income;
	private String accountNoRef;
	private String address1;
	private String address2;
	private String address3;
	private String note;
	private Date updateDate;
	private String updateBy;
	private Date createDate;
	private String createBy;
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstNameThai() {
		return firstNameThai;
	}
	public void setFirstNameThai(String firstNameThai) {
		this.firstNameThai = firstNameThai;
	}
	public String getLastNameThai() {
		return lastNameThai;
	}
	public void setLastNameThai(String lastNameThai) {
		this.lastNameThai = lastNameThai;
	}
	public String getFirstNameEng() {
		return firstNameEng;
	}
	public void setFirstNameEng(String firstNameEng) {
		this.firstNameEng = firstNameEng;
	}
	public String getLastNameEng() {
		return lastNameEng;
	}
	public void setLastNameEng(String lastNameEng) {
		this.lastNameEng = lastNameEng;
	}
	public String getCitizenId() {
		return citizenId;
	}
	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBankAccountId() {
		return bankAccountId;
	}
	public void setBankAccountId(String bankAccountId) {
		this.bankAccountId = bankAccountId;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getTel3() {
		return tel3;
	}
	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}
	public String getTelRef1() {
		return telRef1;
	}
	public void setTelRef1(String telRef1) {
		this.telRef1 = telRef1;
	}
	public String getTelRef2() {
		return telRef2;
	}
	public void setTelRef2(String telRef2) {
		this.telRef2 = telRef2;
	}
	public String getTelRef3() {
		return telRef3;
	}
	public void setTelRef3(String telRef3) {
		this.telRef3 = telRef3;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getAccountNoRef() {
		return accountNoRef;
	}
	public void setAccountNoRef(String accountNoRef) {
		this.accountNoRef = accountNoRef;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	
}
