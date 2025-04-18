package dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;

import connectDB.ConnectDB;
import entity.TotalIncome;

public class TotalIncomeDAO {
	private ConnectDB connectDB;

	public TotalIncomeDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public TotalIncome getTotalIncome(int month, int year) {
		Connection connection = connectDB.getConnection();
		TotalIncome totalIncome = null;
		try {
			PreparedStatement s = connection.prepareStatement("SELECT * FROM dbo.GetTotalIncomeByMonthYear(?, ?)");
			s.setInt(1, month);
			s.setInt(2, year);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				BigDecimal sumOfTicket = rs.getBigDecimal("sumOfTicket").setScale(2, RoundingMode.HALF_UP);
				BigDecimal sumOfSevice = rs.getBigDecimal("sumOfService").setScale(2, RoundingMode.HALF_UP);
				BigDecimal sumOfTicketAndService = rs.getBigDecimal("sumOfTicketAndService").setScale(2, RoundingMode.HALF_UP);
				totalIncome = new TotalIncome(sumOfTicket, sumOfSevice, sumOfTicketAndService);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalIncome;
	}
}
