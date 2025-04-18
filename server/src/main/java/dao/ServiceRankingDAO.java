package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ServiceRanking;

public class ServiceRankingDAO {
	private ConnectDB connectDB;

	public ServiceRankingDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}
	
	public List<ServiceRanking> getTop10ServiceRanking(int month, int year) {
		Connection connection = connectDB.getConnection();
		List<ServiceRanking> serviceRankingList = new ArrayList<ServiceRanking>();
		try {
			PreparedStatement s = connection.prepareStatement("SELECT * FROM dbo.GetTopServicesByIncome(?, ?)");
			s.setInt(1, month);
			s.setInt(2, year);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				String serviceName = rs.getString("ServiceName");
				int salesQuantity = rs.getInt("SalesQuantity");
				long revenue = rs.getLong("Revenue");
				serviceRankingList.add(new ServiceRanking(serviceName, salesQuantity, revenue));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return serviceRankingList;
	}
}
