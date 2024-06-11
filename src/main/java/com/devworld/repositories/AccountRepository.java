package com.devworld.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.devworld.models.Account;
import com.devworld.utils.JDBCUtils;

public class AccountRepository {
    private static final String INSERT_ACCOUNT_SQL = "INSERT INTO accounts (account_holder_name, balance) VALUES (?, ?)";
    private static final String UPDATE_ACCOUNT_SQL = "UPDATE accounts SET account_holder_name = ?, balance = ? WHERE account_id = ?";
    private static final String SELECT_ACCOUNT_BY_ID_SQL = "SELECT * FROM accounts WHERE account_id = ?";

    // Create account
	public int createAccount(Account account) throws SQLException {
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_SQL)) {
			preparedStatement.setString(1, account.getAccountHolderName());
			preparedStatement.setDouble(2, account.getBalance());
			return preparedStatement.executeUpdate();
		}
	}

	// Update account
	public int updateAccount(Account account) throws SQLException {
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_SQL)) {
			preparedStatement.setString(1, account.getAccountHolderName());
			preparedStatement.setDouble(2, account.getBalance());
			preparedStatement.setInt(3, account.getAccountId());
			return preparedStatement.executeUpdate();
		}
	}
	
	//  Get account details
	public Account getAccountDetailsById(int accountId) throws SQLException {
		try (Connection connection = JDBCUtils.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID_SQL)) {
			preparedStatement.setInt(1, accountId);
			 List<Account> accounts = mapResultSetToAccount(preparedStatement.executeQuery());
			 
			 return accounts.stream().findFirst().orElse(null);
		}
	}

	private List<Account> mapResultSetToAccount(ResultSet rs) throws SQLException {
		List<Account> accounts = Collections.emptyList();
		if (rs == null) return accounts;
		while (rs.next()) {
			Account account = new Account();
			account.setAccount_id(rs.getInt("account_id"));
			account.setAccountHolderName(rs.getString("account_id"));
			account.setBalance(rs.getDouble("balance"));
			accounts.add(account);
		}
		return accounts;
	}
}
