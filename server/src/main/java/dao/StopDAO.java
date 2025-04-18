package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import connectDB.ConnectDB;
import entity.Stop;

public class StopDAO {

	private ConnectDB connectDB;

	public StopDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public int updateStop(Stop stop) {
		Connection connection = connectDB.getConnection();
		try {
			PreparedStatement s = connection.prepareStatement(
					"UPDATE Stop SET departureDate = ?, arrivalTime = ?, departureTime = ? WHERE stopID = ?");
			s.setDate(1, Date.valueOf(stop.getDepartureDate()));
			s.setTime(2, Time.valueOf(stop.getArrivalTime()));
			s.setTime(3, Time.valueOf(stop.getDepartureTime()));
			s.setString(4, stop.getStopID());
			return s.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}