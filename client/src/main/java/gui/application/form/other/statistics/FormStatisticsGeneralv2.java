package gui.application.form.other.statistics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import dao.CoachTypeTotalIncomeDAO;
import dao.OrderDAO;
import dao.TotalIncomeDAO;
import entity.*;
import gui.other.SimpleForm;
import net.miginfocom.swing.MigLayout;
import raven.chart.bar.HorizontalBarChart;
import raven.chart.data.pie.DefaultPieDataset;
import raven.chart.pie.PieChart;

public class FormStatisticsGeneralv2 extends SimpleForm {

	private JPanel superContainer;
	private JPanel container;
	private JLabel tongDoanhThuLabel;
	private TotalIncome totalIncome;
	private TotalIncomeDAO totalIncomeDAO;
	private JPanel tongDoanhThuContainer;
	private JLabel tongDoanhThuValue;
	private DecimalFormat decimalFormat;
	private JPanel container1;
	private JPanel container1Left;
	private JComboBox<Month> monthCombobox;
	private JComboBox<Year> yearComboBox;
	private JPanel container2;
	private PieChart totalIncomePieChart;
	private CoachTypeTotalIncomeDAO coachTypeTotalIncomeDAO;
	private PieChart coachTypeTotalIncomePieChart;
	private List<CoachTypeTotalIncome> coachTypeTotalIncomeList;
	private int renderTime = 1;
	private Month monthSelected;
	private Year yearSelected;
	private JPanel container3;
	private HorizontalBarChart barChart;
	private OrderDAO orderDAO;
	private int numberOfOrdersWithService;
	private int numberOfOrdersWithoutService;
	private JPanel containerSuper2;

	public FormStatisticsGeneralv2(Employee employee) {

		totalIncomeDAO = new TotalIncomeDAO();
		coachTypeTotalIncomeDAO = new CoachTypeTotalIncomeDAO();
		orderDAO = new OrderDAO();
		decimalFormat = new DecimalFormat("#,###");

		this.setLayout(new BorderLayout());
		superContainer = new JPanel(new MigLayout("wrap, fill, insets 20", "[fill]", "[fill]"));
		container = new JPanel(new MigLayout("wrap, fill", "[fill]", "[][]"));

		// state
		monthSelected = new Month(LocalDate.now().getMonthValue());
		yearSelected = new Year(LocalDate.now().getYear());
		totalIncome = totalIncomeDAO.getTotalIncome(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
		coachTypeTotalIncomeList = coachTypeTotalIncomeDAO.getCoachTypeTotalIncome(LocalDate.now().getMonthValue(),
				LocalDate.now().getYear());
		numberOfOrdersWithService = orderDAO.getNumberOfOrderWithService(LocalDate.now().getMonthValue(),
				LocalDate.now().getYear());
		numberOfOrdersWithoutService = orderDAO.getNumberOfOrderWithoutService(LocalDate.now().getMonthValue(),
				LocalDate.now().getYear());
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
		container1 = new JPanel(new MigLayout("wrap, fill, insets 0", "[]push[][]", "[]"));
		container1Left = new JPanel(new MigLayout("wrap, fill, insets 0", "[]", "[][]"));
		tongDoanhThuLabel = new JLabel("Tổng doanh thu");
		tongDoanhThuLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +16");
		tongDoanhThuContainer = new JPanel(new MigLayout("wrap, fill, insets 16 40 16 40", "[center]"));
		tongDoanhThuContainer.putClientProperty(FlatClientProperties.STYLE, "background: #24A94A; arc: 12");
		tongDoanhThuValue = new JLabel(decimalFormat.format(totalIncome.getSumOfTicketAndService()) + " VND");
		tongDoanhThuValue.putClientProperty(FlatClientProperties.STYLE, "font:bold +8; foreground: #FFFFFF");
		tongDoanhThuContainer.add(tongDoanhThuValue);
		container1Left.add(tongDoanhThuLabel);
		container1Left.add(tongDoanhThuContainer);
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
			int selectedMonth = ((Month) monthCombobox.getSelectedItem()).getValue();
			int selectedYear = ((Year) yearComboBox.getSelectedItem()).getValue();
			totalIncome = totalIncomeDAO.getTotalIncome(selectedMonth, selectedYear);
			coachTypeTotalIncomeList = coachTypeTotalIncomeDAO.getCoachTypeTotalIncome(selectedMonth, selectedYear);
			numberOfOrdersWithService = orderDAO.getNumberOfOrderWithService(selectedMonth, selectedYear);
			numberOfOrdersWithoutService = orderDAO.getNumberOfOrderWithoutService(selectedMonth, selectedYear);
			render();
		});
		yearComboBox.addActionListener(e -> {
			yearSelected = (Year) yearComboBox.getSelectedItem();
			int selectedMonth = ((Month) monthCombobox.getSelectedItem()).getValue();
			int selectedYear = ((Year) yearComboBox.getSelectedItem()).getValue();
			totalIncome = totalIncomeDAO.getTotalIncome(selectedMonth, selectedYear);
			coachTypeTotalIncomeList = coachTypeTotalIncomeDAO.getCoachTypeTotalIncome(selectedMonth, selectedYear);
			numberOfOrdersWithService = orderDAO.getNumberOfOrderWithService(selectedMonth, selectedYear);
			numberOfOrdersWithoutService = orderDAO.getNumberOfOrderWithoutService(selectedMonth, selectedYear);
			render();
		});
		container1.add(container1Left);
		container1.add(monthCombobox, "aligny top");
		container1.add(yearComboBox, "aligny top");
		container.add(container1, "dock north");
		/*-----------------------------------------------------------------------------------*/

		/*-----------------------------------------------------------------------------------*/
		containerSuper2 = new JPanel(new MigLayout("wrap, fill", "[fill]", "[center]"));
		container2 = new JPanel(new MigLayout("wrap, fillx, insets 0 0 0 0", "[50%][50%]", "[fill]"));
		totalIncomePieChart = new PieChart();
		JLabel header1 = new JLabel("Theo sản phẩm");
		header1.putClientProperty(FlatClientProperties.STYLE, "font:$h5.font");
		totalIncomePieChart.setHeader(header1);
		totalIncomePieChart.putClientProperty(FlatClientProperties.STYLE,
				"" + "border:5,5,5,5,$Component.borderColor,,20");
		totalIncomePieChart.getChartColor().addColor(Color.decode("#FB923C"), Color.decode("#F87171"));
		totalIncomePieChart.setDataset(createPieChartForTotalIncome());

		coachTypeTotalIncomePieChart = new PieChart();
		JLabel header2 = new JLabel("Theo loại toa");
		header2.putClientProperty(FlatClientProperties.STYLE, "font:$h5.font");
		coachTypeTotalIncomePieChart.setHeader(header2);
		coachTypeTotalIncomePieChart.putClientProperty(FlatClientProperties.STYLE,
				"" + "border:5,5,5,5,$Component.borderColor,,20");
		coachTypeTotalIncomePieChart.getChartColor().addColor(Color.decode("#F87474"), Color.decode("#C084FC"),
				Color.decode("#22D3EE"));
		coachTypeTotalIncomePieChart.setDataset(createPieChartForCoachTypeTotalIncome());

		if (totalIncome.getSumOfTicketAndService().compareTo(BigDecimal.valueOf(0)) == 0) {
			container2.add(new JLabel("Không có hóa đơn nào được lập"), "align center");
		} else {
			container2.add(totalIncomePieChart, "aligny top");
		}
		if (coachTypeTotalIncomeList.isEmpty()) {
			container2.add(new JLabel("Không có dữ liệu"), "align center");
		} else {
			container2.add(coachTypeTotalIncomePieChart, "aligny top");
		}
		containerSuper2.add(container2);
		container.add(containerSuper2, "dock center");
		/*-----------------------------------------------------------------------------------*/

		/*-----------------------------------------------------------------------------------*/
		container3 = new JPanel(new MigLayout("wrap, fill", "[fill]", "[fill]"));
		barChart = new HorizontalBarChart();
		JLabel header = new JLabel("So sánh hóa đơn");
		barChart.setHeader(header);
		barChart.setBarColor(Color.decode("#f97316"));
		JPanel panel1 = new JPanel(new BorderLayout());
		header.putClientProperty(FlatClientProperties.STYLE, "font:$h4.font");
		panel1.add(barChart);
		barChart.setDataset(createBarChartForServiceInclusion());
		container3.add(panel1);
		container.add(container3, "dock south");

		/*-----------------------------------------------------------------------------------*/

		container.repaint();
		container.revalidate();
		formRefresh();

	}

	private DefaultPieDataset<String> createBarChartForServiceInclusion() {
		DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
		dataset.addValue("Có đặt dịch vụ", numberOfOrdersWithService);
		dataset.addValue("Không đặt dịch vụ", numberOfOrdersWithoutService);
		return dataset;
	}

	private DefaultPieDataset<String> createPieChartForTotalIncome() {
		DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
		dataset.addValue("Tổng tiền dịch vụ", totalIncome.getSumOfSevice());
		dataset.addValue("Tổng tiền vé tàu", totalIncome.getSumOfTicket());
		return dataset;
	}

	private DefaultPieDataset<String> createPieChartForCoachTypeTotalIncome() {
		DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
		for (CoachTypeTotalIncome coachTypeTotalIncome : coachTypeTotalIncomeList) {
			dataset.addValue(coachTypeTotalIncome.getCoachType(), coachTypeTotalIncome.getCoachTypeTotalIncome());
		}
		return dataset;
	}

	@Override
	public void formRefresh() {
		totalIncomePieChart.startAnimation();
		coachTypeTotalIncomePieChart.startAnimation();
	}

}
