package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.Service;

public class ServiceDAO {

	private ConnectDB connectDB;

	public ServiceDAO() {
		connectDB = ConnectDB.getInstance();
		connectDB.connect();
	}

	public List<Service> getServiceByType(String type) {
		List<Service> services = new ArrayList<>();
		Connection connection = connectDB.getConnection();
		String querySQL = "SELECT * FROM Service WHERE Type = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement(querySQL);
			statement.setString(1, type);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Service service = new Service(resultSet.getString("ServiceID"));
				service.setServiceName(resultSet.getString("ServiceName"));
				service.setPrice(resultSet.getDouble("Price"));
				service.setImageSource(resultSet.getString("ImageSource"));
				service.setType(type);

				services.add(service);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return services;
	}

	public List<Service> findFoodByName(String serviceName) {
		Connection connection = connectDB.getConnection();
		String querySQL = "SELECT * FROM Service WHERE ServiceName LIKE ? AND Type = N'Đồ ăn'";
		List<Service> foods = new ArrayList<Service>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
			preparedStatement.setString(1, "%" + serviceName + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String id = resultSet.getString("ServiceID");
				String name = resultSet.getString("ServiceName");
				double price = resultSet.getDouble("Price");
				String type = resultSet.getString("Type");
				String image = resultSet.getString("ImageSource");

				foods.add(new Service(id, name, price, type, image));
			}
			return foods;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Service> findDrinkByName(String serviceName) {
		Connection connection = connectDB.getConnection();
		String querySQL = "SELECT * FROM Service WHERE ServiceName LIKE ? AND ServiceType = 'Dồ uống'";
		List<Service> drinks = new ArrayList<Service>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(querySQL);
			preparedStatement.setString(1, "%" + serviceName + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String id = resultSet.getString("ServiceID");
				String name = resultSet.getString("ServiceName");
				double price = resultSet.getDouble("ServicePrice");
				String image = resultSet.getString("ImageSource");

				drinks.add(new Service(id, name, price, "Drink", image));
			}
			return drinks;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String addNewService(Service newService) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String generatedServiceID = null;

		try {
			connection = connectDB.getConnection();
			String insertServiceSQL = "INSERT INTO Service (ServiceName, Price, Type, ImageSource) OUTPUT inserted.ServiceID VALUES (?, ?, ?, ?)";
			preparedStatement = connection.prepareStatement(insertServiceSQL);

			preparedStatement.setString(1, newService.getServiceName());
			preparedStatement.setDouble(2, newService.getPrice());
			preparedStatement.setString(3, newService.getType());
			preparedStatement.setString(4, newService.getImageSource());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				generatedServiceID = resultSet.getString("ServiceID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return generatedServiceID;
	}

	public boolean removeServiceByID(String serviceID) {
		Connection connection = connectDB.getConnection();
		String deleteSQL = "DELETE FROM Service WHERE ServiceID = ?";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, serviceID);
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateNewProduct(Service newService) {
		Connection connection = connectDB.getConnection();
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE Service SET ServiceName = ?, Price = ?, [Type] = ?, ImageSource = ? WHERE ServiceID = ?";
		try {
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setString(1, newService.getServiceName());
			preparedStatement.setDouble(2, newService.getPrice());
			preparedStatement.setString(3, newService.getType());
			preparedStatement.setString(4, newService.getImageSource());
			preparedStatement.setString(5, newService.getServiceID());

			int rowsAffected = preparedStatement.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<String> getAllServiceTypes() {
		Connection connection = connectDB.getConnection();
		List<String> serviceTypes = new ArrayList<String>();
		try {
			PreparedStatement s = connection.prepareStatement("SELECT DISTINCT type FROM Service");
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				String type = rs.getString("type");
				serviceTypes.add(type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return serviceTypes;
	}

}