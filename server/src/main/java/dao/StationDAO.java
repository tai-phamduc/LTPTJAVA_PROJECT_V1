package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Station;

public class StationDAO {
	private ConnectDB connectDB;

	public StationDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public List<Station> getAllStation() {
		Connection connection = connectDB.getConnection();
		List<Station> stationList = new ArrayList<Station>();
		try {
			PreparedStatement s = connection.prepareStatement("SELECT StationID, StationName FROM Station");
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				String stationID = rs.getString("StationID");
				String stationName = rs.getString("StationName");
				stationList.add(new Station(stationID, stationName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stationList;
	}

	public String addNewStation(String stationName) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String generatedStationID = null;

		try {
			connection = connectDB.getConnection();
			if (connection == null) {
				return null;
			}

			statement = connection
					.prepareStatement("INSERT INTO Station (StationName) OUTPUT inserted.StationID VALUES (?)");

			statement.setString(1, stationName);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				generatedStationID = resultSet.getString("StationID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return generatedStationID;
	}

	public int deleteStationByID(String stationID) {
		Connection connection = connectDB.getConnection();
		String deleteSQL = "DELETE FROM Station WHERE StationID = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, stationID);
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<Station> findStationByName(String stationName) {
		Connection connection = connectDB.getConnection();
		String querySQL = "SELECT * FROM Station WHERE StationName LIKE ?";
		List<Station> stations = new ArrayList<Station>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
			preparedStatement.setString(1, "%" + stationName + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String id = resultSet.getString("StationID");
				String name = resultSet.getString("StationName");

				stations.add(new Station(id, name));
			}
			return stations;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Station getStationByID(String stationID) {
		Connection connection = connectDB.getConnection();
		String querySQL = "SELECT * FROM Station WHERE StationID = ?";
		Station station = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
			preparedStatement.setString(1, stationID);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String id = resultSet.getString("StationID");
				String name = resultSet.getString("StationName");

				station = new Station(id, name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return station;
	}

	public boolean updateStation(Station station) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectDB.getConnection();
			if (connection == null) {
				System.err.println("Failed to establish a connection.");
				return false;
			}

			String sql = "UPDATE Station SET StationName = ? WHERE StationID = ?";
			statement = connection.prepareStatement(sql);

			statement.setString(1, station.getStationName());
			statement.setString(2, station.getStationID());

			int status = statement.executeUpdate();

			if (status == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Station> getStationsForTicket(String ticketID) {
		Connection connection = connectDB.getConnection();
		List<Station> stations = new ArrayList<Station>();
		try {
			PreparedStatement s = connection.prepareStatement("{CALL GetStationsForTicket(?)}");
			s.setString(1, ticketID);

			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				String stationID = rs.getString("StationID");
				String stationName = rs.getString("StationName");
				stations.add(new Station(stationID, stationName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stations;
	}

}