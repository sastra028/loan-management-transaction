package com.loanmanagement.transaction.contrller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loanmanagement.transaction.model.CommonResponse;
import com.loanmanagement.transaction.model.TransactionRequest;
import com.loanmanagement.transaction.model.TransactionResponst;
import com.loanmanagement.transaction.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionControllrt {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/find/all")
	public List<TransactionResponst> findAll() {
		return transactionService.findAllTransaction();
	}

	@PostMapping("/find/byNameSet")
	public List<TransactionResponst> findByNameSet(@RequestBody TransactionRequest transactionRequest) {
		return transactionService.findByNameSet(transactionRequest);
	}

	@PostMapping("/find/paidToday")
	public List<TransactionResponst> findPaidToday() {
		return transactionService.findPaidToday();
	}

	@PostMapping("/find/overdue")
	public List<TransactionResponst> findOverdue() {
		return transactionService.findOverdue();
	}

	@PostMapping("/create")
	public CommonResponse create(@RequestBody TransactionRequest transactionRequest) {
		return transactionService.create(transactionRequest);
	}

	@PostMapping("/update")
	public CommonResponse update(@RequestBody TransactionRequest transactionRequest) {
		return transactionService.update(transactionRequest);
	}

	@PostMapping("/delete")
	public CommonResponse delete(@RequestBody TransactionRequest transactionRequest) {
		return transactionService.delete(transactionRequest);
	}
}
