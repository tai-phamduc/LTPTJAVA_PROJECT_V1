package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Order;
import entity.Passenger;
import entity.Seat;
import entity.Ticket;
import entity.TicketInfo;
import entity.TrainJourney;

public class TicketDAO {

	private ConnectDB connectDB;

	public TicketDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	private TicketInfo createTicketInfo(ResultSet resultSet) throws SQLException {
		String passengerID = resultSet.getString("PassengerID");
		LocalDateTime departureDateTime = resultSet.getTimestamp("DepartureDateTime").toLocalDateTime();
		int coachID = resultSet.getInt("CoachID");
		int seatID = resultSet.getInt("SeatID");
		String ticketType = resultSet.getString("OrderStatus");
		String ticketRefundInfo = resultSet.getString("OrderStatus");
		double baseTotal = resultSet.getDouble("distance");

		return new TicketInfo(getTicketByID(resultSet.getString("TicketID")),
				(new PassengerDAO()).getPassengerByID(passengerID), departureDateTime,
				(new SeatDAO()).getSeatByID(seatID), (new CoachDAO()).getCoachByID(coachID), ticketType,
				ticketRefundInfo, baseTotal);
	}

	public List<TicketInfo> fetchEligibleRefundTicketsForOrder(String orderID, boolean isRefund) {
		List<TicketInfo> tickets = new ArrayList<>();
		Connection connection = connectDB.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement("{CALL GetAllTicketsByOrderID(?)}");
			statement.setString(1, orderID);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Ticket ticket = getTicketByID(resultSet.getString("TicketID"));

				if (!ticket.getStatus().equalsIgnoreCase("Đã Trả") && isRefund) {
					tickets.add(createTicketInfo(resultSet));
				} else if (ticket.getStatus().equalsIgnoreCase("Bình thường") && !isRefund) {
					tickets.add(createTicketInfo(resultSet));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tickets;
	}

	public Ticket getTicketByID(String ticketID) {
		String query = "SELECT * FROM Ticket WHERE TicketID = ?";
		Ticket ticket = null;
		Connection connection = connectDB.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, ticketID);

			rs = statement.executeQuery();

			// public Ticket(String ticketID, String status, TrainJourney trainJourney, Seat
			// seat, Passenger passenger, Order order) {}
			if (rs.next()) {
				ticket = new Ticket(rs.getString("TicketID"), rs.getString("Status"),
						(new TrainJourneyDAO()).getTrainJourneyByID(rs.getString("TrainJourneyID")),
						(new SeatDAO()).getSeatByID(rs.getInt("SeatID")),
						(new PassengerDAO().getPassengerByID(rs.getString("PassengerID"))),
						(new OrderDAO()).getOrderByID(rs.getString("OrderID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ticket;
	}

	public String addTicket(TrainJourney trainJourney, Seat seat, Passenger passenger, Order order) {
		Connection connection = connectDB.getConnection();
		String insertSQL = "insert into ticket (trainJourneyID, seatID, passengerID, orderID) OUTPUT inserted.TicketID values (?, ?, ?, ?)";
//		String insertSQL = "insert into ticket (trainJourneyID, status, seatID, passengerID, orderID) OUTPUT inserted.TicketID values (?, ?, ?, ?)";
		try {
			PreparedStatement s = connection.prepareStatement(insertSQL);
			s.setString(1, trainJourney.getTrainJourneyID());
			// s.setString(2, "Đã Trả");
			s.setInt(2, seat.getSeatID());
			s.setString(3, passenger.getPassengerID());
			s.setString(4, order.getOrderID());

			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean reassignTicketToNewOrder(String newOrderID, String ticketID) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsAffected = 0;
		connection = connectDB.getConnection();
		String query = "UPDATE Ticket SET OrderID = ? WHERE TicketID = ?";
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, newOrderID);
			statement.setString(2, ticketID);

			rowsAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected > 0;
	}

	public boolean updateTicketStatus(String status, String ticketID) {
		Connection connection = null;
		PreparedStatement statement = null;
		int rowsAffected = 0;
		connection = connectDB.getConnection();
		String query = "UPDATE Ticket SET Status = ? WHERE TicketID = ?";
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, status);
			statement.setString(2, ticketID);

			rowsAffected = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected > 0;
	}

}
