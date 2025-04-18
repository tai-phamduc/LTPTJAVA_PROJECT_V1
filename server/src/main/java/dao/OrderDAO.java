package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import connectDB.ConnectDB;
import entity.Customer;
import entity.Employee;
import entity.Order;
import entity.TrainJourney;

public class OrderDAO {

	private ConnectDB connectDB;

	public OrderDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public int getTicketCountByOrderID(String orderID) {
		String query = "SELECT COUNT(*) AS TicketCount FROM Ticket WHERE OrderID = ?";
		int ticketCount = 0;
		Connection connection = connectDB.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, orderID);

			rs = statement.executeQuery();

			if (rs.next()) {
				ticketCount = rs.getInt("TicketCount");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ticketCount;
	}

	public Order getOrderByID(String orderID) {
		String query = "SELECT * FROM [Order] WHERE OrderID = ?";
		Order order = null;
		Connection connection = connectDB.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, orderID);

			rs = statement.executeQuery();

			if (rs.next()) {
				LocalDateTime orderDate = rs.getTimestamp("OrderDate").toLocalDateTime();
				String note = rs.getString("Note");
				LocalTime timeRemaining = rs.getTime("TimeRemaining").toLocalTime();
				String orderStatus = rs.getString("OrderStatus");

				// Lấy thông tin từ các bảng liên quan
				Customer customer = (new CustomerDAO()).getCustomerByID(rs.getString("CustomerID"));
//				TrainJourney trainJourney = (new TrainJourneyDAO()).getTrainJourneyByID(rs.getString("TrainJourneyID"));
//				Employee employee = (new EmployeeDAO()).getEmployeeByID(rs.getString("EmployeeID"));

				// Khởi tạo đối tượng Order
				order = new Order(orderID);
				order.setOrderDate(orderDate);
				order.setNote(note);
				order.setTimeRemaining(timeRemaining);
				order.setOrderStatus(orderStatus);
				order.setCustomer(customer);
//				order.setTrainJourney(trainJourney);
//				order.setEmployee(employee);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	public String addOrder(LocalDate orderDate, String note, String orderStatus, Customer customer,
			TrainJourney trainJourney, Employee employee) {
		Connection connection = connectDB.getConnection();
		String insertSQL = "insert into [order] (OrderDate, Note, TimeRemaining, OrderStatus, TaxID, CustomerID, TrainJourneyID, EmployeeID) OUTPUT inserted.orderID values (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement s = connection.prepareStatement(insertSQL);
			s.setDate(1, Date.valueOf(orderDate));
			s.setString(2, note);
			s.setTime(3, Time.valueOf(LocalTime.of(12, 0)));
			s.setString(4, orderStatus);
			s.setString(5, "Tax001");
			s.setString(6, customer.getCustomerID());
			s.setString(7, trainJourney.getTrainJourneyID());
			s.setString(8, employee.getEmployeeID());

			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public int getNumberOfOrderWithService(int monthValue, int year) {
		Connection connection = connectDB.getConnection();
		try {
			PreparedStatement s = connection
					.prepareStatement("SELECT dbo.GetOrdersWithService(?, ?) AS OrdersWithService");
			s.setInt(1, monthValue);
			s.setInt(2, year);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				return rs.getInt("OrdersWithService");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int getNumberOfOrderWithoutService(int monthValue, int year) {
		Connection connection = connectDB.getConnection();
		try {
			PreparedStatement s = connection
					.prepareStatement("SELECT dbo.GetOrdersWithoutService(?, ?) AS OrdersWithoutService");
			s.setInt(1, monthValue);
			s.setInt(2, year);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				return rs.getInt("OrdersWithoutService");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
