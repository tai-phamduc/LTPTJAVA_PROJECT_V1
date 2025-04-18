package gui.application.form.other.customer;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.PassengerDAO;
import entity.Passenger;

public class CustomerTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Passenger> passengers;
	private String[] columnNames = { "Mã Hành Khách", "Họ Tên", "Ngày Sinh", "Mã Định Danh", "Loại Mã Định Danh",
			"Đối Tượng Khách Hàng" };
	private PassengerDAO passengerDAO;

	public CustomerTableModel() {
		passengerDAO = new PassengerDAO();
		passengers = passengerDAO.getAllPassengers();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return String.class;
		case 5:
			return String.class;
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		return passengers.size();
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Passenger passenger = passengers.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return passenger.getPassengerID();
		case 1:
			return passenger.getFullName();
		case 2:
			return passenger.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		case 3:
			return passenger.getIdentifier();
		case 4:
			return passenger.getIdentifierType();
		case 5:
			return passenger.getPassengerType();
		}
		return null;
	}

	public void setCustomerList(List<Passenger> customerList) {
		this.passengers = customerList;
	}

}