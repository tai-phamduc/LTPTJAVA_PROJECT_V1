package gui.application.form.other.order;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import entity.Customer;
import entity.Employee;
import entity.TicketInfo;
import gui.application.Application;
import net.miginfocom.swing.MigLayout;
import raven.crazypanel.CrazyPanel;;

public class ChoosenTicketsRefundDialog extends JDialog {

	private JPanel container;
	private JPanel container1;
	private JLabel thongTinKhachHangLabel;
	private JPanel container2;
	private JLabel hoVaTenLabel;
	private JLabel emailLabel;
	private JLabel soGiayToLabel;
	private JLabel sdtLabel;
	private JPanel container4;
	private JLabel thongTinGioVeLabel;
	private JPanel container5;
	private JLabel hoTenLabel;
	private JLabel thongTinChoLabel;
	private JLabel giaVeLabel;
	private JLabel giamDoiTuongLabel;
	private JLabel khuyenMaiLabel;
	private JLabel baoHiemLabel;
	private JLabel thanhTienLabel;
	private JLabel xoaLabel;
	private JPanel container6;
	private JPanel container7;
	private JPanel nhapMaGiamGiaContaienr;
	private JTextField nhapMaGiamGiaTextField;
	private JButton apDungButton;
	private JLabel tongTienLabel;
	private JPanel container8;
	private JButton tiepTucButton;
	private JButton goBackButton;
	private List<TicketInfo> choosenTickets;
	private List<TicketInfo> refundTickets;
	private ArrayList<ComponentTicketDetail> componentTicketList;
	private int renderTime = 1;
	private Customer cus;
	private TicketRefundConfirmationScreen showTicketInfoScreen;
	private JPanel superContainer;
	private CrazyPanel customerInfoContainer;

	public ChoosenTicketsRefundDialog(List<TicketInfo> chosenTickets, Employee employee) {
//		this.trainJourneyOptionItem = trainJourneyOptionItem;
		this.choosenTickets = chosenTickets;
//		this.seatsChoosingDialog = seatsChoosingDialog;
		this.setLayout(new BorderLayout());
		superContainer = new JPanel(new MigLayout("wrap, fill, insets 20", "[fill]", "[fill]"));
		container = new JPanel(new MigLayout("wrap, fill, insets 0", "[fill]", "[]0[]0[]6[grow, fill]6[]0[]0[]0[]"));
		cus = chosenTickets.getFirst().getTicket().getOrder().getCustomer();
		// state
		componentTicketList = new ArrayList<ComponentTicketDetail>();
		refundTickets = new ArrayList<TicketInfo>();

		// render component
		render(employee);

		superContainer.add(container);
		this.add(superContainer);
		this.setUndecorated(true);
		this.setSize(1450, 850);
		this.setLocationRelativeTo(null);
	}

	private void render(Employee employee) {
		container.removeAll();

		container1 = new JPanel(new MigLayout("wrap, insets 0", "[]16[]", "[]"));
		goBackButton = new JButton(new FlatSVGIcon("gui/icon/svg/gobackicon.svg", 0.15f));
		goBackButton.putClientProperty(FlatClientProperties.STYLE,
				"background: #fafafa; borderWidth: 0; focusWidth: 0");
		goBackButton.addActionListener(e -> {
			this.dispose();
		});
		thongTinKhachHangLabel = new JLabel("Thông tin hóa đơn");
		thongTinKhachHangLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +20");
		container1.add(goBackButton);

		// Form thông tin
		customerInfoContainer = new CrazyPanel();
		customerInfoContainer.setLayout(new MigLayout("wrap 4, insets 12", "[][grow, fill]30[][grow, fill]", "[][][]"));
		customerInfoContainer.add(new JLabel("Họ và tên"));
		JTextField nameField = new JTextField(20);
		nameField.setText(cus.getFullName());
		customerInfoContainer.add(nameField);

		customerInfoContainer.add(new JLabel("Số CMND/Hộ chiếu"));
		JTextField idField = new JTextField(20);
		idField.setText(cus.getIdentificationNumber());
		customerInfoContainer.add(idField, "growx");

		customerInfoContainer.add(new JLabel("Email"));
		JTextField emailField = new JTextField(20);
		emailField.setText(cus.getEmail());
		customerInfoContainer.add(emailField, "growx");

		customerInfoContainer.add(new JLabel("Số điện thoại"));
		JTextField phoneField = new JTextField(20);
		phoneField.setText(cus.getPhoneNumber());
		customerInfoContainer.add(phoneField, "growx");

		container1.add(thongTinKhachHangLabel);

		container2 = new JPanel(new MigLayout("wrap, insets 16", "[]8[]24[]8[]24[]8[]24[]8[]", "[]"));
		hoVaTenLabel = new JLabel("Họ và tên");
		hoVaTenLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		emailLabel = new JLabel("Email");
		emailLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		soGiayToLabel = new JLabel("Số giấy tờ");
		soGiayToLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		sdtLabel = new JLabel("Số điện thoại");
		sdtLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		container2.add(hoVaTenLabel);
		container2.add(emailLabel);
		container2.add(soGiayToLabel);
		container2.add(sdtLabel);
		container2.add(soGiayToLabel);
		container2.add(sdtLabel);

		container4 = new JPanel(new MigLayout("wrap, fill, insets 16", "[]", "[][]"));
		thongTinGioVeLabel = new JLabel("Các giao dịch thành công");
		thongTinGioVeLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +8; foreground: $clr-red");
		container4.add(thongTinGioVeLabel);

		container5 = new JPanel(new MigLayout("wrap, fill, insets 2",
				"40[fill, grow, center][180px, center, shrink][150px, center, shrink]0[150px, center, shrink]0[150px, center, shrink]0[150px, center, shrink]0[150px, center, shrink]0[50px, center, shrink]24",
				"[]"));

		hoTenLabel = new JLabel("Thông tin hành khách");
		hoTenLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		thongTinChoLabel = new JLabel("Thông tin vé");
		thongTinChoLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		giaVeLabel = new JLabel("Thành tiền");
		giaVeLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		giamDoiTuongLabel = new JLabel("Loại trả vé");
		giamDoiTuongLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		khuyenMaiLabel = new JLabel("Lệ phí");
		khuyenMaiLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		baoHiemLabel = new JLabel("Tiền trả lại");
		baoHiemLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		thanhTienLabel = new JLabel("Thông tin trả vé");
		thanhTienLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		xoaLabel = new JLabel("Chọn vé");
		xoaLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		container5.add(hoTenLabel);
		container5.add(thongTinChoLabel);
		container5.add(giaVeLabel);
		container5.add(giamDoiTuongLabel);
		container5.add(thanhTienLabel);
		container5.add(khuyenMaiLabel);
		container5.add(baoHiemLabel);
		container5.add(xoaLabel);

		container6 = new JPanel(new MigLayout("wrap, fill, insets 16, gapy 12", "[fill]"));

		if (renderTime == 1) {
			for (TicketInfo chosenticket : choosenTickets) {
				ComponentTicketDetail componentTicket = new ComponentTicketDetail(chosenticket, this);
				componentTicketList.add(componentTicket);
				container6.add(componentTicket);
			}
		} else {
			for (ComponentTicketDetail componentTicket : componentTicketList) {
				container6.add(componentTicket);
			}
		}

		container7 = new JPanel(new MigLayout("wrap, fill, insets 20 0 0 0", "[]push[]push[]", "[]"));
		nhapMaGiamGiaContaienr = new JPanel(new MigLayout("wrap, fill, gap 0", "[][]", "[]"));
		nhapMaGiamGiaTextField = new JTextField(20);
		nhapMaGiamGiaTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã giảm giá tại đây");
		apDungButton = new JButton("Áp dụng");
		nhapMaGiamGiaContaienr.add(nhapMaGiamGiaTextField);
		nhapMaGiamGiaContaienr.add(apDungButton);
		tongTienLabel = new JLabel("Thông tin người đặt vé");
		tongTienLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +8; foreground: $clr-red");

//		int tongTien = 0;
//		for (ComponentTicket componentTicket : componentTicketList) {
//			String numberWithComma = componentTicket.getColumn7().getText().trim();
//			tongTien += Integer.parseInt(numberWithComma.replace(",", ""));
//		}

//		tongTienValue = new JLabel(decimalFormat.format(tongTien));
//		tongTienValue = new JLabel("??????????????????????");

//		tongTienValue.putClientProperty(FlatClientProperties.STYLE, "font:bold +8; foreground: $clr-red");
		container7.add(tongTienLabel);

		container8 = new JPanel(new MigLayout("wrap, fill", "[]", "[]"));
		tiepTucButton = new JButton("Tiếp tục");
		tiepTucButton.putClientProperty(FlatClientProperties.STYLE, "background:$primary; foreground:$clr-white");
		container8.add(tiepTucButton, "al right");

		tiepTucButton.addActionListener(e -> {
			if (refundTickets.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn vé để tiếp tục");
				return;
			}
			Thread thread = new Thread(() -> {
				showTicketInfoScreen = new TicketRefundConfirmationScreen(refundTickets, employee, this);
				showTicketInfoScreen.setModal(true);
				showTicketInfoScreen.setVisible(true);
			});
			thread.start();

		});

		container.add(container1, "dock north");
		container.add(container4, "dock north");
		container.add(container5, "dock north");
		container.add(new JScrollPane(container6), "dock center");
		container.add(container8, "dock south");
		container.add(customerInfoContainer, "dock south");
		container.add(container7, "dock south");

		JScrollPane scroll = (JScrollPane) container6.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		renderTime++;

		container.repaint();
		container.revalidate();
	}

	public List<TicketInfo> getRefundTickets() {
		return refundTickets;
	}

	public void setRefundTickets(List<TicketInfo> refundTickets) {
		this.refundTickets = refundTickets;
	}

	public void closeDialog() {
		JPanel defaultGlassPane = (JPanel) Application.getInstance().getGlassPane();
		defaultGlassPane.removeAll();
		Application.getInstance().setGlassPane(defaultGlassPane);
		defaultGlassPane.setVisible(false);
		dispose();
	}

}
