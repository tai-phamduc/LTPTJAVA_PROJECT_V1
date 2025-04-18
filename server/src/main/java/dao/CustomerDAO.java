package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectDB.ConnectDB;
import entity.Customer;

public class CustomerDAO {
	private ConnectDB connectDB;

	public CustomerDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public Customer getCustomerByID(String customerID) {
		String query = "SELECT * FROM Customer WHERE CustomerID = ?";
		Customer customer = null;
		Connection connection = connectDB.getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;

		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, customerID);

			rs = statement.executeQuery();

			if (rs.next()) {
				String fullName = rs.getString("fullName");
				String phoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				String identificationNumber = rs.getString("identificationNumber");

				customer = new Customer(customerID, fullName, phoneNumber, email, identificationNumber);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	public String addCustomer(Customer customer) {
		Connection connection = connectDB.getConnection();
		String insertSQL = "INSERT INTO customer (fullName, phoneNumber, email, identificationNumber) OUTPUT inserted.customerID "
				+ "VALUES (?, ?, ?, ?)";
		try {
			// Tạo câu lệnh PreparedStatement với truy vấn SQL
			PreparedStatement s = connection.prepareStatement(insertSQL);
			s.setString(1, customer.getFullName());
			s.setString(2, customer.getPhoneNumber());
			s.setString(3, customer.getEmail());
			s.setString(4, customer.getIdentificationNumber());

			// Thực thi câu truy vấn và lấy kết quả
			ResultSet rs = s.executeQuery();

			if (rs.next()) {
				// Trả về CustomerID (char(12)) dạng String
				return rs.getString(1);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Customer getCustomerByEmail(String email) {
		Connection connection = connectDB.getConnection();
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT CustomerID, fullName, phoneNumber, email, identificationNumber FROM Customer where email = ?");
			s.setString(1, email);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				String customerID = rs.getString("CustomerID");
				String fullName = rs.getString("fullName");
				String phoneNumber = rs.getString("phoneNumber");
				String identificationNUmber = rs.getString("identificationNumber");
				return new Customer(customerID, fullName, phoneNumber, email, identificationNUmber);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}