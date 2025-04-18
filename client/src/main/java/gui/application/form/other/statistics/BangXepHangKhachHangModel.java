package gui.application.form.other.statistics;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.CustomerRanking;
import entity.ServiceRanking;

public class BangXepHangKhachHangModel extends AbstractTableModel {

	private List<CustomerRanking> customerRankingList;
	private String[] columnNames = {"STT", "Tên khách hàng", "Số điện thoại", "Tổng chi"};
	
	public BangXepHangKhachHangModel(List<CustomerRanking> customerRankingList) {
		this.customerRankingList = customerRankingList;
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
		return customerRankingList.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		CustomerRanking customerRanking = customerRankingList.get(rowIndex);
		DecimalFormat df = new DecimalFormat("#,###");
		switch(columnIndex) {
			case 0:
				return rowIndex + 1;
			case 1:
				return customerRanking.getCustomerName();
			case 2:
				return customerRanking.getPhoneNumber();
			case 3:
				return df.format(customerRanking.getTotalSpending()) + " VND";
		}
		return null;
	}

}
