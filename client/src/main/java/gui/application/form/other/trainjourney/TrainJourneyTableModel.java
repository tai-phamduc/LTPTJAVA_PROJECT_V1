package gui.application.form.other.trainjourney;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.TrainJourneyDAO;
import entity.TrainDetails;
import entity.TrainJourneyDetails;

public class TrainJourneyTableModel extends AbstractTableModel {
	
	private List<TrainJourneyDetails> trainJourneyDetailsList;
	private String[] columnNames = { "Mã", "Tàu", "Tên", "Ga đi - Ga đến", "Ngày đi", "TG đi - TG đến", "Cự ly", "Tổng" };
	private TrainJourneyDAO trainJourneyDAO;
	
	public TrainJourneyTableModel() {
		trainJourneyDAO = new TrainJourneyDAO();
		trainJourneyDetailsList = trainJourneyDAO.getAllTrainJourneyDetails();
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
		return trainJourneyDetailsList.size();
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TrainJourneyDetails trainJourneyDetails = trainJourneyDetailsList.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return trainJourneyDetails.getMaChuyenTau();
			case 1:
				return trainJourneyDetails.getSoHieuTau();
			case 2:
				return trainJourneyDetails.getTenChuyenTau();
			case 3:
				return trainJourneyDetails.getHanhTrinh();
			case 4:
				return trainJourneyDetails.getNgayKhoiHanh();
			case 5:
				return trainJourneyDetails.getThoiGian();
			case 6:
				return trainJourneyDetails.getCuLy() + "km";
			case 7:
				return trainJourneyDetails.getTongHanhKhach();
		}
		return null;
	}

	public void setTrainDetailsList(List<TrainJourneyDetails> trainJourneyDetailsList) {
		this.trainJourneyDetailsList = trainJourneyDetailsList;	
	}

}
