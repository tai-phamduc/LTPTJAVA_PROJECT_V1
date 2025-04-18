package gui.application.form.other.train;

import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.TrainDetails;
import utils.ServerFetcher;

public class TrainTableModel extends AbstractTableModel {
	
	private List<TrainDetails> trainDetailsList;
	private String[] columnNames = { "Mã tàu", "Số hiệu tàu", "Số toa", "Sức chứa", "Số loại toa", "Các loại", "Tình trạng" };

	public TrainTableModel() {
		this.trainDetailsList = fetchAllTrainDetails();
	}

	private List<TrainDetails> fetchAllTrainDetails() {
		HashMap<String, String> payload = new HashMap<>();
		return (List<TrainDetails>) ServerFetcher.fetch("train", "getAllTrainDetails", payload);
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
		return trainDetailsList != null ? trainDetailsList.size() : 0;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TrainDetails trainDetails = trainDetailsList.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return trainDetails.getTrainID();
			case 1:
				return trainDetails.getTrainNumber();
			case 2:
				return trainDetails.getNumberOfCoaches();
			case 3:
				return trainDetails.getCapacity();
			case 4:
				return trainDetails.getNumberOfCoachTypes();
			case 5:
				return trainDetails.getCoachTypes();
			case 6:
				return trainDetails.getStatus();
		}
		return null;
	}

	public void setTrainDetailsList(List<TrainDetails> trainDetailsLists) {
		this.trainDetailsList = trainDetailsLists;	
	}

}