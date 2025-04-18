package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectDB {
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=TrainTicketBookingSystem;encrypt=false;";
	private static final String USER = "sa";
	private static final String PASSWORD = "22639301";

	private static ConnectDB instance;
	private Connection connection;

	private ConnectDB() {
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ConnectDB getInstance() {
		if (instance == null) {
			synchronized (ConnectDB.class) {
				if (instance == null) {
					instance = new ConnectDB();
				}
			}
		}
		return instance;
	}

	public Connection getConnection() {
		return connection;
	}

	public Connection connect() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	public void disconnect() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	public void close(PreparedStatement statement, ResultSet resultSet) {
//		try {
//			if (resultSet != null) {
//				resultSet.close();
//			}
//			if (statement != null) {
//				statement.close();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}
