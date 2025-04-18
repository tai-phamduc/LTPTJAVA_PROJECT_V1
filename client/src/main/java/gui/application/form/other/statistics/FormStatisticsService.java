package gui.application.form.other.statistics;

import java.awt.BorderLayout;
import java.awt.Component;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.formdev.flatlaf.FlatClientProperties;

import dao.ServiceRankingDAO;
import entity.Employee;
import entity.Month;
import entity.ServiceRanking;
import entity.Year;
import net.miginfocom.swing.MigLayout;

public class FormStatisticsService extends JPanel {

	private JPanel superContainer;
	private JPanel container1;
	private JLabel titleLabel;
	private JComboBox<Month> monthCombobox;
	private JComboBox<Year> yearComboBox;
	private Month monthSelected;
	private Year yearSelected;
	private JPanel container;
	private JPanel container2;
	private JTable bangXepHangDichVuTable;
	private BangXepHangDichVuModel bangXepHangDichVuTableModel;
	private List<ServiceRanking> serviceRankingList;
	private ServiceRankingDAO serviceRankingDAO;

	public FormStatisticsService(Employee employee) {
		serviceRankingDAO = new ServiceRankingDAO();
		this.setLayout(new BorderLayout());
		superContainer = new JPanel(new MigLayout("wrap, fill, insets 20", "[fill]", "[fill]"));
		container = new JPanel(new MigLayout("wrap, fill", "[fill]", "[][]"));
		
		// state
		monthSelected = new Month(LocalDate.now().getMonthValue());
		yearSelected = new Year(LocalDate.now().getYear());
		serviceRankingList = serviceRankingDAO.getTop10ServiceRanking(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
		// state
		
		// render
		render();
		// render
		
		superContainer.add(container);
		this.add(superContainer);		
	}

	private void render() {
		container.removeAll();
		
		/*-----------------------------------------------------------------------------------*/
		container1 = new JPanel(new MigLayout("wrap, fill, insets 0", "[]push[][]", "[fill]"));
		titleLabel = new JLabel("Bảng xếp hạng doanh thu dịch vụ");
		titleLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +4");
		monthCombobox = new JComboBox<Month>();
		monthCombobox.addItem(new Month(1, "Tháng 1"));
		monthCombobox.addItem(new Month(2, "Tháng 2"));
		monthCombobox.addItem(new Month(3, "Tháng 3"));
		monthCombobox.addItem(new Month(4, "Tháng 4"));
		monthCombobox.addItem(new Month(5, "Tháng 5"));
		monthCombobox.addItem(new Month(6, "Tháng 6"));
		monthCombobox.addItem(new Month(7, "Tháng 7"));
		monthCombobox.addItem(new Month(8, "Tháng 8"));
		monthCombobox.addItem(new Month(9, "Tháng 9"));
		monthCombobox.addItem(new Month(10, "Tháng 10"));
		monthCombobox.addItem(new Month(11, "Tháng 11"));
		monthCombobox.addItem(new Month(12, "Tháng 12"));
		monthCombobox.setSelectedItem(monthSelected);
		yearComboBox = new JComboBox<Year>();
		yearComboBox.addItem(new Year(2022, "Năm 2022"));
		yearComboBox.addItem(new Year(2023, "Năm 2023"));
		yearComboBox.addItem(new Year(2024, "Năm 2024"));
		yearComboBox.setSelectedItem(yearSelected);
		monthCombobox.addActionListener(e -> {
			monthSelected = (Month) monthCombobox.getSelectedItem();
			int selectedMonth = ((Month)monthCombobox.getSelectedItem()).getValue();
			int selectedYear = ((Year)yearComboBox.getSelectedItem()).getValue();
			serviceRankingList = serviceRankingDAO.getTop10ServiceRanking(selectedMonth, selectedYear);
			render();
		});
		yearComboBox.addActionListener(e -> {
			yearSelected = (Year) yearComboBox.getSelectedItem();
			int selectedMonth = ((Month)monthCombobox.getSelectedItem()).getValue();
			int selectedYear = ((Year)yearComboBox.getSelectedItem()).getValue();
			serviceRankingList = serviceRankingDAO.getTop10ServiceRanking(selectedMonth, selectedYear);
			render();
		});
		container1.add(titleLabel);
		container1.add(monthCombobox);
		container1.add(yearComboBox);
		container.add(container1, "dock north");
		/*-----------------------------------------------------------------------------------*/
		
		/*-----------------------------------------------------------------------------------*/
		container2 = new JPanel(new MigLayout("wrap, fill, insets 20 0 20 0", "[fill]", "[fill]"));
		bangXepHangDichVuTableModel = new BangXepHangDichVuModel(serviceRankingList);
		bangXepHangDichVuTable = new JTable(bangXepHangDichVuTableModel);
		container2.add(new JScrollPane(bangXepHangDichVuTable));
		
		JScrollPane scroll = (JScrollPane) bangXepHangDichVuTable.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");
		bangXepHangDichVuTable.getTableHeader().putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
		bangXepHangDichVuTable.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
		
		bangXepHangDichVuTable.getTableHeader().setDefaultRenderer(
				getAlignmentCellRender(bangXepHangDichVuTable.getTableHeader().getDefaultRenderer(), true));
		bangXepHangDichVuTable.setDefaultRenderer(Object.class,
				getAlignmentCellRender(bangXepHangDichVuTable.getDefaultRenderer(Object.class), false));

		if (bangXepHangDichVuTable.getColumnModel().getColumnCount() > 0) {
			bangXepHangDichVuTable.getColumnModel().getColumn(1).setPreferredWidth(300);
		}
		
		container.add(container2, "dock center");
		/*-----------------------------------------------------------------------------------*/
		
		/*-----------------------------------------------------------------------------------*/
		/*-----------------------------------------------------------------------------------*/
		
		container.repaint();
		container.revalidate();
	}
	
	private TableCellRenderer getAlignmentCellRender(TableCellRenderer oldRender, boolean header) {
		return new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component com = oldRender.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				if (com instanceof JLabel) {
					JLabel label = (JLabel) com;
					if (column == 2 || column == 3) {
						label.setHorizontalAlignment(SwingConstants.CENTER);
					} else {
						label.setHorizontalAlignment(SwingConstants.LEADING);
					}
					if (header == false) {
						if (isSelected) {
							com.setForeground(table.getSelectionForeground());
						} else {
							com.setForeground(table.getForeground());
						}
					}
				}
				return com;
			}
		};
	};
	
}
