package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Line;
import entity.LineDetails;
import entity.Station;
import entity.StationLine;
import entity.Stop;

public class LineDAO {
	private ConnectDB connectDB;

	public LineDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public List<Line> getAllLine() {
		Connection connection = connectDB.getConnection();
		List<Line> lineList = new ArrayList<Line>();
		try {
			PreparedStatement s = connection.prepareStatement("SELECT LineID, LineName FROM LINE");
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				String lineID = rs.getString("LineID");
				String lineName = rs.getString("LineName");
				lineList.add(new Line(lineID, lineName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lineList;
	}

	public List<LineDetails> getAllLineDetailsByName(String lineNameToFind) {
		Connection connection = connectDB.getConnection();
		List<LineDetails> lineDetailsList = new ArrayList<LineDetails>();
		try {
			PreparedStatement s = connection.prepareStatement("SELECT * FROM GetLineDetails() where LineName LIKE ?");
			s.setString(1, "%" + lineNameToFind + "%");
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				String lineID = rs.getString("LineID");
				String lineName = rs.getString("LineName");
				String departureStation = rs.getString("DepartureStation");
				String arrivalStation = rs.getString("ArrivalStation");
				int totalDistance = rs.getInt("TotalDistance");
				lineDetailsList.add(new LineDetails(lineID, lineName, departureStation, arrivalStation, totalDistance));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lineDetailsList;
	}

	public List<Stop> getLineStops(String lineID) {
		Connection connection = connectDB.getConnection();
		List<Stop> stopList = new ArrayList<Stop>();
		try {
			PreparedStatement s = connection.prepareStatement(
					"select stopOrder, s.stationID, s.stationName, distance from line l join LineStop ls on l.lineID = ls.lineID join station s on ls.stationID = s.stationID where l.lineID = ?");
			s.setString(1, lineID);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				int stopOrder = rs.getInt("stopOrder");
				String stationID = rs.getString("stationID");
				String stationName = rs.getString("stationName");
				int distance = rs.getInt("distance");
				LocalDate departureDate = LocalDate.now();
				LocalTime arrivalTime = LocalTime.now();
				LocalTime departureTime = LocalTime.now();
				stopList.add(new Stop(new Station(stationID, stationName), stopOrder, distance, departureDate,
						arrivalTime, departureTime));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stopList;
	}

	public List<LineDetails> getAllLineDetails() {
		Connection connection = connectDB.getConnection();
		List<LineDetails> lineDetailsList = new ArrayList<LineDetails>();
		try {
			PreparedStatement s = connection.prepareStatement("SELECT * FROM GetLineDetails()");
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				String lineID = rs.getString("LineID");
				String lineName = rs.getString("LineName");
				String departureStation = rs.getString("DepartureStation");
				String arrivalStation = rs.getString("ArrivalStation");
				int totalDistance = rs.getInt("TotalDistance");
				lineDetailsList.add(new LineDetails(lineID, lineName, departureStation, arrivalStation, totalDistance));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lineDetailsList;
	}

	public boolean removeLineByID(String lineID) {
		Connection connection = connectDB.getConnection();
		String deleteSQL = "DELETE FROM Line WHERE LineID = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, lineID);
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String addLine(String lineName) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String generatedStationID = null;

		try {
			connection = connectDB.getConnection();
			if (connection == null) {
				return null;
			}

			statement = connection.prepareStatement("INSERT INTO Line (LineName) OUTPUT inserted.LineID VALUES (?)");

			statement.setString(1, lineName);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				generatedStationID = resultSet.getString("LineID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return generatedStationID;
	}

	public boolean addLineStop(String lineID, StationLine stationLine) {
		Connection connection = connectDB.getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(
					"INSERT INTO LineStop (LineID, StationID, StopOrder, Distance) values (?, ?, ?, ?) ");
			statement.setString(1, lineID);
			statement.setString(2, stationLine.getStation().getStationID());
			statement.setInt(3, stationLine.getIndex());
			statement.setInt(4, stationLine.getDistance());
			int rowsAffected = statement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Line getLineByID(String lineID) {
		Connection connection = connectDB.getConnection();
		Line line = null;
		try {
			PreparedStatement s = connection.prepareStatement("SELECT LineID, LineName FROM LINE WHERE LineID = ?");
			s.setString(1, lineID);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				String lineName = rs.getString("LineName");
				line = new Line(lineID, lineName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return line;
	}

	public List<StationLine> getLineStopByLineID(String lineID) {
		StationDAO stationDAO = new StationDAO();
		Connection connection = connectDB.getConnection();
		List<StationLine> stationLineList = new ArrayList<StationLine>();
		try {
			PreparedStatement s = connection.prepareStatement("select * from LineStop where lineID = ?");
			s.setString(1, lineID);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				int stopOrder = rs.getInt("stopOrder");
				String stationID = rs.getString("stationID");
				int distance = rs.getInt("distance");
				stationLineList.add(new StationLine(stopOrder, stationDAO.getStationByID(stationID), distance));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stationLineList;
	}

	public boolean updateLine(Line line) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectDB.getConnection();
			if (connection == null) {
				System.err.println("Failed to establish a connection.");
				return false;
			}

			String sql = "UPDATE Line SET LineName = ? WHERE LineID = ?";
			statement = connection.prepareStatement(sql);

			statement.setString(1, line.getLineName());
			statement.setString(2, line.getLineID());

			int status = statement.executeUpdate();

			if (status == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean removeAllByLineID(String lineID) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectDB.getConnection();
			if (connection == null) {
				System.err.println("Failed to establish a connection.");
				return false;
			}

			String sql = "DELETE FROM LineStop WHERE LineID = ?";
			statement = connection.prepareStatement(sql);

			statement.setString(1, lineID);

			int status = statement.executeUpdate();

			if (status == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
