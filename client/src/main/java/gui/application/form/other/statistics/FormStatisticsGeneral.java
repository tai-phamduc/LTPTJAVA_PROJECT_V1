package gui.application.form.other.statistics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import dao.TotalIncomeDAO;
import entity.Employee;
import entity.Month;
import entity.TotalIncome;
import entity.Year;
import gui.other.SimpleForm;
import net.miginfocom.swing.MigLayout;
import raven.chart.data.pie.DefaultPieDataset;
import raven.chart.pie.PieChart;

public class FormStatisticsGeneral extends SimpleForm {

	private JPanel superContainer;
	private JPanel container;
	private JPanel container1;
	private JComboBox<Month> monthCombobox;
	private JComboBox<Year> yearComboBox;
	private JPanel container2;
	private JPanel contentContainer;
	private JPanel firstLine;
	private JLabel tongDoanhThuLabel;
	private JPanel secondLine;
	private JLabel tongDoanhThuValue;
	private JPanel thirdLine;
	private TotalIncomeDAO totalIncomeDAO;
	private TotalIncome totalIncome;
	private PieChart pieChart;
	private JPanel tongDoanhThuContainer;
	private DecimalFormat decimalFormat;

	public FormStatisticsGeneral(Employee emp) {

		totalIncomeDAO = new TotalIncomeDAO();

		// state
		totalIncome = totalIncomeDAO.getTotalIncome(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
		// state

		decimalFormat = new DecimalFormat("#,###");

		this.setLayout(new BorderLayout());
		superContainer = new JPanel(new MigLayout("wrap, fill, insets 20", "[fill]", "[fill]"));
		container = new JPanel(new MigLayout("wrap, fill", "[fill]", "[][]"));

		container1 = new JPanel(new MigLayout("wrap", "[][]", "[]"));

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

		monthCombobox.setSelectedItem(new Month(LocalDate.now().getMonthValue()));

		yearComboBox = new JComboBox<Year>();
		yearComboBox.addItem(new Year(2022, "Năm 2022"));
		yearComboBox.addItem(new Year(2023, "Năm 2023"));
		yearComboBox.addItem(new Year(2024, "Năm 2024"));

		yearComboBox.setSelectedItem(new Year(LocalDate.now().getYear()));

		monthCombobox.addActionListener(e -> {
			totalIncome = totalIncomeDAO.getTotalIncome(((Month) monthCombobox.getSelectedItem()).getValue(),
					((Year) yearComboBox.getSelectedItem()).getValue());
			reactToTotalIncomeChanged();
		});

		yearComboBox.addActionListener(e -> {
			totalIncome = totalIncomeDAO.getTotalIncome(((Month) monthCombobox.getSelectedItem()).getValue(),
					((Year) yearComboBox.getSelectedItem()).getValue());
			reactToTotalIncomeChanged();
		});

		container1.add(monthCombobox);
		container1.add(yearComboBox);

		container2 = new JPanel(new MigLayout("wrap, fillx, insets 32 0 32 0, gapy 16", "[center]"));

		reactToTotalIncomeChanged();

		container.add(container1, "dock north");
		container.add(container2, "dock center");
		superContainer.add(container);
		this.add(superContainer);
	}

	private void reactToTotalIncomeChanged() {

		container2.removeAll();

		contentContainer = new JPanel(new MigLayout("wrap, fill", "[fill]", "[][][][]"));
		firstLine = new JPanel(new MigLayout("wrap, fill", "[center]"));
		tongDoanhThuLabel = new JLabel("TỔNG DOANH THU");
		tongDoanhThuLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +8");
		firstLine.add(tongDoanhThuLabel);
		secondLine = new JPanel(new MigLayout("wrap, fill", "[center]"));
		tongDoanhThuContainer = new JPanel(new MigLayout("wrap, fill, insets 8 24 8 24", "[center]"));
		tongDoanhThuContainer.putClientProperty(FlatClientProperties.STYLE, "background: #24A94A; arc: 12");
		tongDoanhThuValue = new JLabel(decimalFormat.format(totalIncome.getSumOfTicketAndService()) + " VND");
		tongDoanhThuValue.putClientProperty(FlatClientProperties.STYLE, "font:bold +8; foreground: #FFFFFF");
		tongDoanhThuContainer.add(tongDoanhThuValue);
		secondLine.add(tongDoanhThuContainer);
		thirdLine = new JPanel();
		contentContainer.add(firstLine);
		contentContainer.add(secondLine);
		contentContainer.add(thirdLine);
		container2.add(contentContainer, "aligny top");
		if (totalIncome.getSumOfTicketAndService().compareTo(BigDecimal.valueOf(0)) == 0) {
			thirdLine.add(new JLabel("Không có hóa đơn nào được lập"));
		} else {
			pieChart = new PieChart();
			pieChart.setLayout(new MigLayout("wrap, fill", "[fill]", "[][]"));
			pieChart.getChartColor().addColor(Color.decode("#FB923C"), Color.decode("#F87171"));
			pieChart.setDataset(createPieChartForTotalIncome(totalIncome));
			thirdLine.add(pieChart);
		}
		repaint();
		revalidate();
		formRefresh();
	}

	private DefaultPieDataset<String> createPieChartForTotalIncome(TotalIncome totalIncome) {
		DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
		dataset.addValue("Tổng tiền dịch vự", totalIncome.getSumOfSevice());
		dataset.addValue("Tổng tiền vé tàu", totalIncome.getSumOfTicket());
		return dataset;
	}

	@Override
	public void formRefresh() {
		pieChart.startAnimation();
	}

}
