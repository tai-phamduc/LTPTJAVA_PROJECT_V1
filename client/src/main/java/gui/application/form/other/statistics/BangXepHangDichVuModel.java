package gui.application.form.other.statistics;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.ServiceRanking;

public class BangXepHangDichVuModel extends AbstractTableModel {

	private List<ServiceRanking> serviceRankingList;
	private String[] columnNames = {"STT", "Tên dịch vụ", "Số lượng bán ra", "Doanh thu bán ra"};
	
	public BangXepHangDichVuModel(List<ServiceRanking> serviceRankingList) {
		this.serviceRankingList = serviceRankingList;
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
		return serviceRankingList.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ServiceRanking serviceRanking = serviceRankingList.get(rowIndex);
		DecimalFormat df = new DecimalFormat("#,###");
		switch(columnIndex) {
			case 0:
				return rowIndex + 1;
			case 1:
				return serviceRanking.getServiceName();
			case 2:
				return serviceRanking.getSalesQuantity();
			case 3:
				return df.format(serviceRanking.getRevenue()) + " VND";
		}
		return null;
	}

}
