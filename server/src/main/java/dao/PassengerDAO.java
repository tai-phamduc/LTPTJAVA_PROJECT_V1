package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Passenger;

public class PassengerDAO {

	private ConnectDB connectDB;

	public PassengerDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public List<Passenger> getAllPassengers() {
		Connection connection = connectDB.getConnection();
		String querySQL = "SELECT PassengerID, FullName, DateOfBirth, Identifier, IdentifierType, PassengerType FROM Passenger";
		List<Passenger> passengers = new ArrayList<Passenger>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String id = resultSet.getString(1);
				String name = resultSet.getString(2);

				String dobString = resultSet.getString(3);
				LocalDate dob;
				if (dobString != null) {
					dob = LocalDate.parse(dobString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				} else {
					dob = LocalDate.now();
				}

				String identifier = resultSet.getString(4);
				String identifierType = resultSet.getString(5);
				String passengerType = resultSet.getString(6);

				passengers.add(new Passenger(id, name, dob, identifier, identifierType, passengerType));
			}
			return passengers;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Passenger> findPassengersByName(String nameToFind) {
		Connection connection = connectDB.getConnection();
		String querySQL = "SELECT * FROM Passenger WHERE FullName LIKE ?";
		List<Passenger> passengers = new ArrayList<Passenger>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
			preparedStatement.setString(1, "%" + nameToFind + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String id = resultSet.getString(1);
				String name = resultSet.getString(2);
				LocalDate dob = LocalDate.parse(resultSet.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				String identifier = resultSet.getString(4);
				String identifierType = resultSet.getString(5);
				String passengerType = resultSet.getString(6);
				passengers.add(new Passenger(id, name, dob, identifier, identifierType, passengerType));
			}
			return passengers;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Passenger> findPassengersByIdentifier(String identifierToFind) {
		Connection connection = connectDB.getConnection();
		String querySQL = "SELECT PassengerID, FullName, DateOfBirth, Identifier, IdentifierType, PassengerType FROM Passenger WHERE Identifier LIKE ?";
		List<Passenger> passengers = new ArrayList<Passenger>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
			preparedStatement.setString(1, "%" + identifierToFind + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String id = resultSet.getString(1);
				String fullName = resultSet.getString(2);
				LocalDate dob = LocalDate.parse(resultSet.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				String identifier = resultSet.getString(4);
				String identifierType = resultSet.getString(5);
				String passengerType = resultSet.getString(6);
				passengers.add(new Passenger(id, fullName, dob, identifier, identifierType, passengerType));
			}
			return passengers;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean removePassengerByID(String passengerIDToDelete) {
		Connection connection = connectDB.getConnection();
		String deleteSQL = "DELETE FROM Passenger WHERE PassengerID = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, passengerIDToDelete);
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Passenger getPassengerByID(String passengerIDToFind) {
		Connection connection = connectDB.getConnection();
		String querySQL = "SELECT * FROM Passenger WHERE PassengerID = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
			preparedStatement.setString(1, passengerIDToFind);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String id = resultSet.getString(1);
				String fullName = resultSet.getString(2);
				String dobStr = resultSet.getString(3);
				LocalDate dob = null;
				if (dobStr != null && !dobStr.isEmpty()) {
					dob = LocalDate.parse(dobStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				} else {
					dob = LocalDate.now();
				}
				String identifier = resultSet.getString(4);
				String identifierType = resultSet.getString(5);
				String passengerType = resultSet.getString(6);
				return new Passenger(id, fullName, dob, identifier, identifierType, passengerType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updatePassenger(Passenger updatedPassenger) {
		Connection connection = connectDB.getConnection();
		String updateSQL = "UPDATE Passenger SET FullName = ?, DateOfBirth = ?, Identifier = ?, IdentifierType = ?, PassengerType = ? WHERE PassengerID = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, updatedPassenger.getFullName());
			preparedStatement.setString(2,
					updatedPassenger.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			preparedStatement.setString(3, updatedPassenger.getIdentifier());
			preparedStatement.setString(4, updatedPassenger.getIdentifierType());
			preparedStatement.setString(5, updatedPassenger.getPassengerType());
			preparedStatement.setString(6, updatedPassenger.getPassengerID());
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String updateCustomerByPhoneNumber(String phoneNumber, String fullName, String email) {
		Connection connection = connectDB.getConnection();
		try {
			PreparedStatement s = connection.prepareStatement(
					"UPDATE customer SET fullname = N?, email = N? OUTPUT INSERTED.customerid WHERE phonenumber = N?");

			s.setString(1, fullName);
			s.setString(2, email);
			s.setString(3, phoneNumber);

			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String addPassenger(Passenger passenger) {
		Connection connection = connectDB.getConnection();
		String insertSQL = "insert into Passenger (FullName, Identifier, PassengerType) output inserted.PassengerID values (?, ?, ?)";
		try {
			PreparedStatement s = connection.prepareStatement(insertSQL);
			s.setString(1, passenger.getFullName());
			s.setString(2, passenger.getIdentifier());
			s.setString(3, passenger.getPassengerType());

			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}