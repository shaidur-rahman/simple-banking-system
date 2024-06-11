package com.devworld.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.devworld.models.Account;
import com.devworld.models.Transaction;
import com.devworld.models.Transaction.TransactionType;
import com.devworld.services.TransactionService;
import com.devworld.utils.TransactionThread;

public class TestTheSystem extends BaseTestConfig {
	TransactionService transactionService = new TransactionService();

	@Test
	void basicDataSetup() {
		try {
			List<Account> list = transactionService.accountRepository.getAllAccountDetails();
			if (list.size() < 2) {
				System.out.println("No account found! preparing some basic accounts...");
				prerpareAccounts();
			} else {
				list.forEach(e -> {
					System.out.println(e.toString());
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testTransaction1() {
		Transaction transaction = new Transaction();
		transaction.setAccountId(15);
		transaction.setAmount(10);
		transaction.setTransactionType(TransactionType.TRANSFER);

		Thread t1 = new Thread(new TransactionThread(transactionService, transaction, 0));
		t1.start();
	}
	
	private void prerpareAccounts() {
		Account ac1 = new Account();
		Account ac2 = new Account();
		
		ac1.setAccountHolderName("Shaidur Rahman");
		ac2.setAccountHolderName("Abdur Rahman");
		try {
			transactionService.accountRepository.createAccount(ac1);
			transactionService.accountRepository.createAccount(ac2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
