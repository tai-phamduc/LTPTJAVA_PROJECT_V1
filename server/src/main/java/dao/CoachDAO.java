package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Coach;
import entity.Train;

public class CoachDAO {

	private ConnectDB connectDB;

	public CoachDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public int addCoach(Coach coach) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;

		try {
			connection = connectDB.getConnection();
			if (connection == null) {
				System.err.println("Failed to establish a connection.");
				return -1;
			}

			String sql = "INSERT INTO Coach (CoachNumber, CoachType, Capacity, TrainID) VALUES (?, ?, ?, ?)";
			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			statement.setInt(1, coach.getCoachNumber());
			statement.setString(2, coach.getCoachType());
			statement.setInt(3, coach.getCapacity());
			statement.setString(4, coach.getTrain().getTrainID());

			int status = statement.executeUpdate();

			if (status == 1) {
				generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public List<Coach> getCoaches(Train train) {
		Connection connection = connectDB.getConnection();
		List<Coach> coachList = new ArrayList<Coach>();
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT CoachID, CoachNumber, CoachType, Capacity, TrainID FROM Coach WHERE TrainID = ?");
			s.setString(1, train.getTrainID());
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				int coachID = rs.getInt("CoachID");
				int coachNumber = rs.getInt("CoachNumber");
				String coachType = rs.getString("CoachType");
				int capacity = rs.getInt("Capacity");
				coachList.add(new Coach(coachID, coachNumber, coachType, capacity, train));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return coachList;
	}

	public boolean removeCoaches(Train train) {
		Connection connection = connectDB.getConnection();
		try {
			PreparedStatement s = connection.prepareStatement("DELETE FROM Coach WHERE TrainID = ?");
			s.setString(1, train.getTrainID());
			int status = s.executeUpdate();
			if (status == 1)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public Coach getCoachByID(int coachID) {
		Connection connection = connectDB.getConnection();
		try {
			PreparedStatement s = connection.prepareStatement(
					"SELECT CoachID, CoachNumber, CoachType, Capacity, TrainID FROM Coach WHERE CoachID = ?");
			s.setInt(1, coachID);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				int coachNumber = rs.getInt("CoachNumber");
				String coachType = rs.getString("CoachType");
				int capacity = rs.getInt("Capacity");
				return new Coach(coachID, coachNumber, coachType, capacity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}