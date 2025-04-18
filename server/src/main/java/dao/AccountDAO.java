package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.mindrot.jbcrypt.BCrypt;

import connectDB.ConnectDB;
import entity.Account;
import entity.Employee;

public class AccountDAO {
	private ConnectDB connectDB;
	private EmployeeDAO employeeDAO;

	public AccountDAO() {
		this.employeeDAO = new EmployeeDAO();
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public boolean checkCredentials(String username, String password) {
		Account account = getAccountByUsername(username);
		if (account == null || !BCrypt.checkpw(password, account.getPassword())) {
			return false;
		}
		return true;
	}

	public Employee getEmployeeByAccount(String username, String password) {
		return getEmployeeByUsername(username, checkCredentials(username, password));
	}

	public Account getAccountByUsername(String username) {
		Account account = null;
		Connection connection = connectDB.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement("SELECT * FROM Account WHERE Username = ?");
			statement.setString(1, username);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				account = new Account();
				account.setUsername(resultSet.getString(1));
				account.setPassword(resultSet.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return account;
	}

	public boolean createAccount(Account account) {
		PreparedStatement statement = null;
		int n = 0;

		try {
			String sql = "INSERT INTO Account (Username, Password, EmployeeID) VALUES (?, ?, ?)";
			statement = connectDB.getConnection().prepareStatement(sql);
			statement.setString(1, account.getUsername());
			statement.setString(2, BCrypt.hashpw(account.getPassword(), BCrypt.gensalt()));
			statement.setString(3, account.getEmployee().getEmployeeID());
			n = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return n > 0;
	}

	public Employee getEmployeeByUsername(String username, boolean authentication) {
		Employee employee = null;
		Connection connection = connectDB.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT * FROM getEmployeeByAccount(?)";

		if (getAccountByUsername(username) != null && authentication) {
			try {
				statement = connection.prepareStatement(sqlQuery);
				statement.setString(1, username);
				resultSet = statement.executeQuery();

				if (resultSet.next()) {
					String id = resultSet.getString("EmployeeID");
					String name = resultSet.getString("FullName");
					boolean gender = resultSet.getBoolean("Gender");
					LocalDate dob = resultSet.getDate("DateOfBirth").toLocalDate();
					String email = resultSet.getString("Email");
					String phone = resultSet.getString("PhoneNumber");
					String role = resultSet.getString("Role");
					LocalDate starting = resultSet.getDate("StartingDate").toLocalDate();
					String image = resultSet.getString("ImageSource");

					employee = new Employee(id, name, gender, dob, email, phone, role, starting, image);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return employee;
	}

	public boolean updatePassword(String employeeID, String newPassword) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean success = false;

		try {
			connection = connectDB.getConnection();
			String sql = "UPDATE Account SET Password = ? WHERE EmployeeID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
			preparedStatement.setString(2, employeeID);

			int rowsUpdated = preparedStatement.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Password updated successfully.");
				success = true;
			} else {
				System.out.println("Failed to update password.");
			}
		} catch (SQLException e) {
			System.out.println("Error updating password: " + e.getMessage());
		}

		return success;
	}

	public String getUserByEmployeeID(String employeeID) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String username = null;

		try {
			connection = connectDB.getConnection();
			String sql = "SELECT Username FROM Account WHERE EmployeeID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, employeeID);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				username = resultSet.getString("Username");
			}
		} catch (SQLException e) {
			System.out.println("Error fetching account information: " + e.getMessage());
		} 
		return username;
	}

	public boolean checkAvalibility(String username) {
		Connection connection = connectDB.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement("SELECT * FROM Account WHERE Username = ?");
			statement.setString(1, username);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Account getAccountByEmployeeID(String employeeIDToFind) {
		Account account = null;
		Connection connection = connectDB.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sqlQuery = "SELECT * FROM Account WHERE EmployeeID = ?";

		try {
			statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, employeeIDToFind);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String username = resultSet.getString(1);
				String password = resultSet.getString(2);
				String employeeID = resultSet.getString(3);

				return new Account(username, password, employeeDAO.getEmployeeByID(employeeID));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	// what's this ???
	public boolean updateAccount(String employeeID, String username, String password) {
		Connection connection = connectDB.getConnection();
		// update the account with employeeID, username, and new password
		String updateSQL = "UPDATE Account SET username = ?, password = ? WHERE employeeID = ?";
		try {
			PreparedStatement s = connection.prepareStatement(updateSQL);
			s.setString(1, username);
			s.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
			s.setString(3, employeeID);
			int rowsAffected = s.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
