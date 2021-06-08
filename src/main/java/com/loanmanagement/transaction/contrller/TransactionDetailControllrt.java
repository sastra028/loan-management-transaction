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
@RequestMapping("/transactionDetail")
public class TransactionDetailControllrt {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/find/transactionDetailNo")
	public List<TransactionResponst> findByTransactionDetailNo() {
		return transactionService.findAllTransaction();
	}

	@PostMapping("/find/transactionStatus")
	public List<TransactionResponst> findByTransactionStatus(@RequestBody TransactionRequest transactionRequest) {
		return transactionService.findByNameSet(transactionRequest);
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
