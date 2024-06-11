package com.devworld.utils;

import com.devworld.repositories.AccountRepository;
import com.devworld.repositories.TransactionRepository;

public class BeansFactory {
	public AccountRepository accountRepository = new AccountRepository();
	public TransactionRepository transactionRepository  = new TransactionRepository();
}
