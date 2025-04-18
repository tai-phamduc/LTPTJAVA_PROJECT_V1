package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.CustomerRanking;
import entity.ServiceRanking;

public class CustomerRankingDAO {
	private ConnectDB connectDB;

	public CustomerRankingDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public List<CustomerRanking> getTop10CustomerRanking(int month, int year) {
		Connection connection = connectDB.getConnection();
		List<CustomerRanking> customerRankingList = new ArrayList<CustomerRanking>();
		try {
			PreparedStatement s = connection.prepareStatement("select * from dbo.GetTop10CustomersBySpending(?, ?)");
			s.setInt(1, month);
			s.setInt(2, year);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				String fullName = rs.getString("FullName");
				String phoneNumber = rs.getString("PhoneNumber");
				long totalSpending = rs.getLong("TotalSpending");
				customerRankingList.add(new CustomerRanking(fullName, phoneNumber, totalSpending));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerRankingList;
	}
}
