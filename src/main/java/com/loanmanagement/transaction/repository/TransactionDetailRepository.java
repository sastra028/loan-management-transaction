package com.loanmanagement.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loanmanagement.transaction.entity.TransactionDetailEntity;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetailEntity, String> {
	List<TransactionDetailEntity> findByTransactionNo(String TransactionNo);
}
