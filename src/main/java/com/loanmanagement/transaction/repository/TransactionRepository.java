package com.loanmanagement.transaction.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loanmanagement.transaction.entity.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String>{
	List<TransactionEntity> findByFirstNameThaiOrLastNameThaiOrCitizenId(String firstNameThai, String lastNameThai, String citizenId);
	List<TransactionEntity> findByRefundDate(Date refundDate);
	List<TransactionEntity> findByRefundDateBeforeAndStatus(Date refundDate, String status);
}
