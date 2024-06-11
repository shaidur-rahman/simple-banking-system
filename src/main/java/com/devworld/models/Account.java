package com.devworld.models;

public class Account {
	private int accountId;
	private String accountHolderName;
	private double balance;

	public int getAccountId() {
		return accountId;
	}

	public void setAccount_id(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", accountHolderName=" + accountHolderName + ", balance=" + balance
				+ "]";
	}
}
