package gui.application.form.other.statistics;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.ServiceRanking;
import entity.TrainJourneyRanking;

public class BangXepHangChuyenTauModel extends AbstractTableModel {

	private List<TrainJourneyRanking> trainJourneyRankingList;
	private String[] columnNames = {"STT", "Tên chuyến tàu", "Số hiệu tàu", "Doanh thu"};
	
	public BangXepHangChuyenTauModel(List<TrainJourneyRanking> trainJourneyRankingList) {
		this.trainJourneyRankingList = trainJourneyRankingList;
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
		return trainJourneyRankingList.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TrainJourneyRanking trainJourneyRanking = trainJourneyRankingList.get(rowIndex);
		DecimalFormat df = new DecimalFormat("#,###");
		switch(columnIndex) {
			case 0:
				return rowIndex + 1;
			case 1:
				return trainJourneyRanking.getTrainJourneyName();
			case 2:
				return trainJourneyRanking.getTrainNumber();
			case 3:
				return df.format(trainJourneyRanking.getTotalRevenue()) + " VND";
		}
		return null;
	}

}
