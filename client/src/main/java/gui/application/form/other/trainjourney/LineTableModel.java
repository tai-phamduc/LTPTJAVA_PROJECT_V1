package gui.application.form.other.trainjourney;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.LineDAO;
import entity.LineDetails;

public class LineTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<LineDetails> lineDetailsList;
	private String[] columnNames = { "Mã đường đi", "Tên đường đi", "Ga đi", "Ga đến", "Khoảng cách" };
	private LineDAO lineDAO;

	public LineTableModel() {
		lineDAO = new LineDAO();
		lineDetailsList = lineDAO.getAllLineDetails();
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
		return lineDetailsList.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		LineDetails lineDetails = lineDetailsList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return lineDetails.getLineID();
		case 1:
			return lineDetails.getLineName();
		case 2:
			return lineDetails.getDepartureStation();
		case 3:
			return lineDetails.getArrivalStation();
		case 4:
			return lineDetails.getDistance();
		}
		return null;
	}

	public void setLineDetailsList(List<LineDetails> lineDetailsList) {
		this.lineDetailsList = lineDetailsList;
	}

}