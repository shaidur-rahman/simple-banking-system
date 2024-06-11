package com.devworld.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import com.devworld.models.Transaction;
import com.devworld.models.Transaction.TransactionType;
import com.devworld.utils.JDBCUtils;

public class TransactionRepository {
	// Every transactions must record to audit the integrity of the account balance
	// So, no update operation on transaction table
	private static final String INSERT_INTO_TRANSACTION_SQL = "INSERT INTO transactions (account_id, transaction_type, amount, timestamp) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_TRANSACTION_BY_ACCOUNT_ID_SQL = "SELECT * FROM transactions WHERE account_id = ?";

	    // Add new transaction
	    public int recordNewTransaction(Transaction transaction) throws SQLException {
	    	try (Connection connection = JDBCUtils.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_TRANSACTION_SQL)) {
	            preparedStatement.setInt(1, transaction.getAccountId());
	            preparedStatement.setString(2, transaction.getTransactionType().name());
	            preparedStatement.setDouble(3, transaction.getAmount());
	            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
	           return preparedStatement.executeUpdate();
	        }
	    }
	    
		public List<Transaction> getAllTransactionsByAaccountId(int accountId) throws SQLException {
			try (Connection connection = JDBCUtils.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_TRANSACTION_BY_ACCOUNT_ID_SQL)) {
				preparedStatement.setInt(1, accountId);
				return mapResultSetToTransaction(preparedStatement.executeQuery());
			}
		}

		private List<Transaction> mapResultSetToTransaction(ResultSet rs) throws SQLException {
			List<Transaction> transactions = new LinkedList<Transaction>();
			if (rs == null) return transactions;
			while (rs.next()) {
				Transaction transaction = new Transaction();
				transaction.setTransactionId(rs.getInt("transaction_id"));
				transaction.setTransactionType(TransactionType.valueOf(rs.getString("transaction_type")));
				transaction.setAmount(rs.getDouble("amount"));
				transaction.setTimestamp(rs.getTimestamp("timestamp"));
				transactions.add(transaction);
			}
			return transactions;
		}
}
