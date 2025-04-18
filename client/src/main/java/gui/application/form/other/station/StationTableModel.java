package gui.application.form.other.station;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.StationDAO;
import entity.Station;

public class StationTableModel extends AbstractTableModel {

	private List<Station> stations;
	private String[] columnNames = { "Mã ga", "Tên ga" };
	private StationDAO stationDAO;

	public StationTableModel() {
		stationDAO = new StationDAO();
		stations = stationDAO.getAllStation();
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getRowCount() {
		return stations.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Station station = stations.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return station.getStationID();
		case 1:
			return station.getStationName();
		}
		return null;
	}

	public void setTrainDetailsList(List<Station> stations) {
		this.stations = stations;
	}

}