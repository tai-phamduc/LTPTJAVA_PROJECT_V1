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
import entity.Employee;

public class EmployeeDAO {

	private ConnectDB connectDB;

	public EmployeeDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public boolean updateAvatar(String imagePath, String employeeID) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean success = false;

		try {
			connection = connectDB.getConnection();
			String sql = "UPDATE Employee SET ImageSource = ? WHERE EmployeeID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, imagePath);
			preparedStatement.setString(2, employeeID);

			int rowsUpdated = preparedStatement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Avatar updated successfully.");
				success = true;
			} else {
				System.out.println("Failed to update avatar.");
			}
		} catch (SQLException e) {
			System.out.println("Error updating avatar: " + e.getMessage());
		}
		return success;
	}

	public List<Employee> getAllEmployees() {
		Connection connection = connectDB.getConnection();
		String querySQL = "SELECT * FROM Employee";
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String employeeID = resultSet.getString(1);
				String fullName = resultSet.getString(2);
				boolean gender = resultSet.getBoolean(3);
				LocalDate dob = resultSet.getDate(4).toLocalDate();
				String email = resultSet.getString(5);
				String phoneNumber = resultSet.getString(6);
				String role = resultSet.getString(7);
				employeeList.add(new Employee(employeeID, fullName, gender, dob, email, phoneNumber, role));
			}
			return employeeList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Employee> findEmployeByName(String nameToFind) {
		Connection connection = connectDB.getConnection();
		String querySQL = "SELECT EmployeeID, FullName, Gender, DateOfBirth, Email, PhoneNumber, Role FROM Employee WHERE FullName LIKE ?";
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
			preparedStatement.setString(1, "%" + nameToFind + "%"); 
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String employeeID = resultSet.getString(1);
				String fullName = resultSet.getString(2);
				boolean gender = resultSet.getBoolean(3);
				LocalDate dob = resultSet.getDate(4).toLocalDate();
				String email = resultSet.getString(5);
				String phoneNumber = resultSet.getString(6);
				String role = resultSet.getString(7);
				employeeList.add(new Employee(employeeID, fullName, gender, dob, email, phoneNumber, role));
			}
			return employeeList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean removeEmployeeByID(String employeeIDToDelete) {
		Connection connection = connectDB.getConnection();
		String deleteSQL = "DELETE FROM Employee WHERE EmployeeID = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, employeeIDToDelete);
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected > 0 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String addNewEmployee(Employee newEmployee) {
		Connection connection = connectDB.getConnection();
		String insertSQL = "INSERT INTO Employee (FullName, Gender, DateOfBirth, Email, PhoneNumber, Role, StartingDate) OUTPUT inserted.EmployeeID VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			// add a new employee
			PreparedStatement s = connection.prepareStatement(insertSQL);
			s.setString(1, newEmployee.getFullName());
			s.setBoolean(2, newEmployee.isGender());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			s.setString(3, newEmployee.getDateOfBirth().format(formatter));
			s.setString(4, newEmployee.getEmail());
			s.setString(5, newEmployee.getPhoneNumber());
			s.setString(6, newEmployee.getRole());
			s.setString(7, newEmployee.getStartingDate().format(formatter));
			ResultSet rs = s.executeQuery();

			if (rs.next()) {
				return rs.getString(1);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Employee getEmployeeByID(String employeeIDToFind) {
		Connection connection = connectDB.getConnection();
		String querySQL = "SELECT EmployeeID, FullName, Gender, DateOfBirth, Email, PhoneNumber, Role, StartingDate, ImageSource FROM Employee WHERE EmployeeID = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
			preparedStatement.setString(1, employeeIDToFind);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String employeeID = resultSet.getString(1);
				String fullName = resultSet.getString(2);
				boolean gender = resultSet.getBoolean(3);
				LocalDate dob = resultSet.getDate(4).toLocalDate();
				String email = resultSet.getString(5);
				String phoneNumber = resultSet.getString(6);
				String role = resultSet.getString(7);
				LocalDate startDate = resultSet.getDate(8).toLocalDate();
				String imageSource = resultSet.getString(9);
				return new Employee(employeeID, fullName, gender, dob, email, phoneNumber, role, startDate,
						imageSource);
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean updateEmployee(Employee newEmployee) {
		Connection connection = connectDB.getConnection();
		String updateSQL = "update Employee set FullName = ?, Gender = ?, DateOfBirth = ?, Email = ?, PhoneNumber = ?, Role = ?, StartingDate = ? where EmployeeID = ?";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			PreparedStatement s = connection.prepareStatement(updateSQL);
			s.setString(1, newEmployee.getFullName());
			s.setBoolean(2, newEmployee.isGender());
			s.setString(3, newEmployee.getDateOfBirth().format(formatter));
			s.setString(4, newEmployee.getEmail());
			s.setString(5, newEmployee.getPhoneNumber());
			s.setString(6, newEmployee.getRole());
			s.setString(7, newEmployee.getStartingDate().format(formatter));
			s.setString(8, newEmployee.getEmployeeID());
			int rowsAffected = s.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}