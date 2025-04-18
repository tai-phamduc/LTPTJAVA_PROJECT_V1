package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ServiceRanking;
import entity.TrainJourneyRanking;

public class TrainJourneyRankingDAO {
	private ConnectDB connectDB;

	public TrainJourneyRankingDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public List<TrainJourneyRanking> getTop10TrainJourneyRanking(int month, int year) {
		Connection connection = connectDB.getConnection();
		List<TrainJourneyRanking> trainJourneyRankingList = new ArrayList<TrainJourneyRanking>();
		try {
			PreparedStatement s = connection.prepareStatement("SELECT * FROM dbo.GetTop10TrainJourneysByRevenue(?, ?)");
			s.setInt(1, month);
			s.setInt(2, year);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				String trainJourneyName = rs.getString("TrainJourneyName");
				String trainNumber = rs.getString("TrainNumber");
				long totalRevenue = rs.getLong("TotalRevenue");
				trainJourneyRankingList.add(new TrainJourneyRanking(trainJourneyName, trainNumber, totalRevenue));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trainJourneyRankingList;
	}
}
