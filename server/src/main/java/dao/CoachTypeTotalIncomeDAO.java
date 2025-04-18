package dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.CoachTypeTotalIncome;

public class CoachTypeTotalIncomeDAO {
	private ConnectDB connectDB;

	public CoachTypeTotalIncomeDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public List<CoachTypeTotalIncome> getCoachTypeTotalIncome(int monthValue, int year) {
		Connection connection = connectDB.getConnection();
		List<CoachTypeTotalIncome> coachTypeTotalIncomeList = new ArrayList<CoachTypeTotalIncome>();
		try {
			PreparedStatement s = connection.prepareStatement("select * from dbo.GetTotalTicketIncomeByCoachType(?, ?)");
			s.setInt(1, monthValue);
			s.setInt(2, year);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				String coachType = rs.getString("CoachType");
				BigDecimal totalTicketIncome = rs.getBigDecimal("TotalTicketIncome").setScale(2, RoundingMode.HALF_UP);
				coachTypeTotalIncomeList.add(new CoachTypeTotalIncome(coachType, totalTicketIncome));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coachTypeTotalIncomeList;
	}
}
