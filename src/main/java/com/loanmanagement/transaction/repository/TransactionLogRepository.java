package com.loanmanagement.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loanmanagement.transaction.entity.TransactionLogEntity;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLogEntity, String>{
}
