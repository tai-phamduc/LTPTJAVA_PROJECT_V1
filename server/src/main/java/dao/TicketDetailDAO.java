package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connectDB.ConnectDB;
import entity.Stop;
import entity.Ticket;

public class TicketDetailDAO {
	
	private ConnectDB connectDB;
	
	public TicketDetailDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public void themChiTietVe(Stop stop, Ticket ticket) {
		Connection connection = connectDB.getConnection();
		try {
			PreparedStatement s = connection.prepareStatement("insert into TicketDetail (stopID, ticketID) values (?, ?)");
			s.setString(1, stop.getStopID());
			s.setString(2, ticket.getTicketID());
			s.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}