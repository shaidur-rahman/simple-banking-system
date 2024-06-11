package com.devworld.services;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.devworld.models.Account;
import com.devworld.models.Transaction;
import com.devworld.utils.BeansFactory;
import com.devworld.utils.JDBCUtils;

public class TransactionService extends BeansFactory {

	public synchronized Map<String, String> processTransaction(Transaction transaction, int transferAccount) {
		Map<String, String> response = new HashMap<String, String>();
		response.put("status", "success");
		response.put("message", "Transaction succeeded.");

		try {
			validateTransaction(transaction, transferAccount);
			JDBCUtils.getConnection().setAutoCommit(false);
			// start initiating the transaction
			initiateTransaction(transaction, transferAccount);
			JDBCUtils.getConnection().commit();
		} catch (Exception e) {
			try {
				JDBCUtils.getConnection().rollback();
			} catch (SQLException ignore) {
			}
			response.put("status", "error");
			response.put("message", String.format("Transaction failed, issue: %s", e.getMessage()));
		} finally {
			try {
				JDBCUtils.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return response;
	}
	
	private void validateTransaction (Transaction transaction, int transferAccount) throws Exception {
		if (transaction == null) throw new Exception("Transaction should not be null!");
		if (transaction.getTransactionType() == null) throw new Exception("Transaction type should not be null!");

		// check whether negative amount is provided
		if ((transaction.getAmount() * -1) < 0)  throw new Exception("Nagative amount transaction not allowed!");

		Account accountDetails = accountRepository.getAccountDetailsById(transaction.getAccountId());
		if (accountDetails == null) throw new Exception("Invalid account id: " + transaction.getAccountId());
		
		switch (transaction.getTransactionType()) {
		case WITHDRAWAL:
			checkAccountBalance(accountDetails.getBalance(), transaction.getAmount());
		case TRANSFER:
			checkAccountBalance(accountDetails.getBalance(), transaction.getAmount());
			Account transferAccountDetails = accountRepository.getAccountDetailsById(transferAccount);
			if (transferAccountDetails == null) throw new Exception("Invalid transfer account id: " + transferAccount);
			break;

		default:
			break;
		}
	}

	private void checkAccountBalance(double accountBalance, double transactionAmount) throws Exception {
		if ((accountBalance - transactionAmount) < 0) {
			throw new Exception("Insufficient account balance!");
		}
	}
	
	private void initiateTransaction(Transaction transaction, int transferAccount) throws SQLException {
		if (transaction == null) return;
		Account accountDetails = accountRepository.getAccountDetailsById(transaction.getAccountId());
		Account transferAccountDetails = accountRepository.getAccountDetailsById(transferAccount);
		
		switch (transaction.getTransactionType()) {
		case DEPOSIT:
			accountDetails.setBalance(accountDetails.getBalance() + transaction.getAmount());
			accountRepository.updateAccount(accountDetails);
			break;
		case WITHDRAWAL:
			accountDetails.setBalance(accountDetails.getBalance() - transaction.getAmount());
			accountRepository.updateAccount(accountDetails);
			break;
		case TRANSFER:
			accountDetails.setBalance(accountDetails.getBalance() - transaction.getAmount());
			accountRepository.updateAccount(accountDetails);
			
			transferAccountDetails.setBalance(transferAccountDetails.getBalance() + transaction.getAmount());
			accountRepository.updateAccount(transferAccountDetails);
			break;

		default:
			break;
		}
		transactionRepository.recordNewTransaction(transaction);
	}
}
