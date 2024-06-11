package com.devworld.utils;

import java.util.Map;

import com.devworld.models.Transaction;
import com.devworld.services.TransactionService;

public class TransactionThread implements Runnable {
	TransactionService transactionService;
	Transaction transaction;
	int transferAccount;

	public TransactionThread(TransactionService transactionService, Transaction transaction, int transferAccount) {
		this.transactionService = transactionService;
		this.transaction = transaction;
		this.transferAccount = transferAccount;
	}

	@Override
	public void run() {
		Map<String, String> response = transactionService.processTransaction(transaction, transferAccount);
		System.out.println(String.format("%s - Transaction status: %s, message: %s", Thread.currentThread().getName(),
				response.get("status"), response.get("message")));
	}

}
