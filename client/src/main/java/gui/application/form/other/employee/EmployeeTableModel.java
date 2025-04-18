package gui.application.form.other.employee;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.EmployeeDAO;
import entity.Employee;

public class EmployeeTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Employee> employeeList;
	private String[] columnNames = { "Mã Nhân Viên", "Họ Tên", "Giới Tính", "Ngày Sinh", "Email", "Số Điện Thoại",
			"Chức Vụ" };
	private EmployeeDAO employeeDAO;

	public EmployeeTableModel() {
		employeeDAO = new EmployeeDAO();
		employeeList = employeeDAO.getAllEmployees();
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
		case 6:
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
		return employeeList.size();
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Employee employee = employeeList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return employee.getEmployeeID();
		case 1:
			return employee.getFullName();
		case 2:
			return employee.isGender() ? "Nam" : "Nữ";
		case 3: 
			return employee.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		case 4:
			return employee.getEmail();
		case 5:
			return employee.getPhoneNumber();
		case 6:
			return employee.getRole();
		}
		return null;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

}
