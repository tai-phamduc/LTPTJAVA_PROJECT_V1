package gui.application.form.other.ticket;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import dao.CustomerDAO;
import dao.OrderDAO;
import dao.PassengerDAO;
import dao.ServiceDetailDAO;
import dao.TicketDAO;
import dao.TicketDetailDAO;
import dao.TrainJourneyDAO;
import entity.*;
import gui.other.CreateTrainTickets;
import net.miginfocom.swing.MigLayout;

public class CheckoutDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private static final int TIEN_BAO_HIEM = 1000;
	private Customer customer;
	private JPanel container;
	private JPanel container1;
	private JButton goBackButton;
	private JLabel xacNhanThongTinLabel;
	private JPanel container2;
	private JPanel thongTinNguoiDatVeContainer;
	private JPanel thongTinVeMuaContainer;
	private JPanel thongTinDichVuContainer;
	private JPanel container3;
	private JLabel thongtinNguoiDatVeLabel;
	private JPanel thongTinNguoiDatVeValue;
	private JLabel hoTenLabel;
	private JLabel hoTenValue;
	private JLabel soCCCDLabel;
	private JLabel soCCCDValue;
	private JLabel soDiDongLabel;
	private JLabel soDiDongValue;
	private JLabel emailLabel;
	private JLabel emailValue;
	private JLabel thongTinVeMuaLabel;
	private JPanel thongTinVeMuaValue;
	private JLabel thongTinDichVuLabel;
	private JPanel thongTinDichVuValue;
	private JPanel ghiChuContainer;
	private JLabel ghiChuLabel;
	private JTextArea ghiChuTextArea;
	private JPanel donDatContainer;
	private JLabel donDatLabel;
	private JPanel thanhTienContainer;
	private JLabel thanhTienLabel;
	private JLabel thanhTienValue;
	private JButton thanhToanButton;
	private JPanel thongtinNguoiDatVeLabelContainer;
	private JPanel thongTinVeMuaLabelContainer;
	private JPanel thongTinDichVuLabelContainer;
	private JPanel donDatLabelContainer;
	private JPanel thanhToanButtonContainer;
	private TrainJourneyDAO trainJourneyDAO;
	private CustomerDAO customerDAO;
	private OrderDAO orderDAO;
	private TicketDAO ticketDAO;
	private PassengerDAO passengerDAO;
	private TicketDetailDAO ticketDetailDAO;
	private ServiceDetailDAO serviceDetailDAO;

	public CheckoutDialog(TrainJourneyOptionItem trainJourneyOptionItem, ArrayList<Ticket> chosenTicketList,
			ArrayList<ServiceDetail> chosenServiceDetailList, Customer customer, Employee employee,
			ServiceChoosingDialog serviceChoosingDialog, TicketInfo ticketInfo) {

		this.customer = customer;
		customerDAO = new CustomerDAO();
		orderDAO = new OrderDAO();
		passengerDAO = new PassengerDAO();
		ticketDAO = new TicketDAO();
		ticketDetailDAO = new TicketDetailDAO();
		serviceDetailDAO = new ServiceDetailDAO();

		this.setLayout(new BorderLayout());
		this.trainJourneyDAO = new TrainJourneyDAO();
		container = new JPanel(new MigLayout("wrap, fill", "[fill]", "[shrink 150][fill, grow][]"));

		container1 = new JPanel(new MigLayout("wrap", "[]16[]", ""));
		goBackButton = new JButton(new FlatSVGIcon("gui/icon/svg/gobackicon.svg", 0.15f));
		goBackButton.putClientProperty(FlatClientProperties.STYLE,
				"background: #fafafa; borderWidth: 0; focusWidth: 0");
		goBackButton.addActionListener(e -> {
			this.dispose();
		});
		xacNhanThongTinLabel = new JLabel("XÁC NHẬN THÔNG TIN");
		xacNhanThongTinLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +14");
		container1.add(goBackButton);
		container1.add(xacNhanThongTinLabel);

		container2 = new JPanel(new MigLayout("wrap, fill", "[350px][fill, grow][350px]", "[fill]"));

		// 2.1.
		thongTinNguoiDatVeContainer = new JPanel(new MigLayout("flowy, fill", "[fill]", "[]16[grow 0]"));
		thongTinNguoiDatVeContainer.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");
		thongtinNguoiDatVeLabelContainer = new JPanel(new MigLayout());
		thongtinNguoiDatVeLabel = new JLabel("THÔNG TIN NGƯỜI ĐẶT VÉ");
		thongtinNguoiDatVeLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0; foreground: $clr-white");
		thongtinNguoiDatVeLabelContainer.add(thongtinNguoiDatVeLabel);
		thongtinNguoiDatVeLabelContainer.putClientProperty(FlatClientProperties.STYLE, "background: $primary");
		thongTinNguoiDatVeValue = new JPanel(new MigLayout("wrap, gap 10, fillx", "[fill]", "[][]16[][]16[][]16[][]"));
		thongTinNguoiDatVeValue.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		thongTinNguoiDatVeValue.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");

		hoTenLabel = new JLabel("Họ và tên:");
		hoTenLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		hoTenValue = new JLabel(customer.getFullName());
		soCCCDLabel = new JLabel("Số CCCD/Hộ chiếu:");
		soCCCDLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		soCCCDValue = new JLabel(customer.getIdentificationNumber());
		soDiDongLabel = new JLabel("Số di động:");
		soDiDongLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		soDiDongValue = new JLabel(customer.getPhoneNumber());
		emailLabel = new JLabel("Email:");
		emailLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		emailValue = new JLabel(customer.getEmail());

		thongTinNguoiDatVeValue.add(hoTenLabel);
		thongTinNguoiDatVeValue.add(hoTenValue);
		thongTinNguoiDatVeValue.add(soCCCDLabel);
		thongTinNguoiDatVeValue.add(soCCCDValue);
		thongTinNguoiDatVeValue.add(soDiDongLabel);
		thongTinNguoiDatVeValue.add(soDiDongValue);
		thongTinNguoiDatVeValue.add(emailLabel);
		thongTinNguoiDatVeValue.add(emailValue);
		thongTinNguoiDatVeValue.add(emailValue);

		thongTinNguoiDatVeContainer.add(thongtinNguoiDatVeLabelContainer, "dock north");
		thongTinNguoiDatVeContainer.add(thongTinNguoiDatVeValue, "dock center");

		// 2.2.
		thongTinVeMuaContainer = new JPanel(new MigLayout("flowy, fill", "[fill]", "[][grow 0][]"));
		thongTinVeMuaLabelContainer = new JPanel(new MigLayout());
		thongTinVeMuaLabelContainer.putClientProperty(FlatClientProperties.STYLE, "background: $primary");
		thongTinVeMuaLabel = new JLabel("THÔNG TIN VÉ MUA");
		thongTinVeMuaLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +0; foreground: $clr-white");
		thongTinVeMuaLabelContainer.add(thongTinVeMuaLabel);
		thongTinVeMuaValue = new JPanel(new MigLayout("wrap, gap 10, fillx", "[fill]", ""));
		thongTinVeMuaValue.putClientProperty(FlatClientProperties.STYLE, "font: bold +0; background: $clr-white");

		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		int tongTienVe = 0;
		for (Ticket ticket : chosenTicketList) {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

			JPanel ticketItem = new JPanel(new MigLayout("wrap, fill", "[][]", "[]"));
			String trainNumber = trainJourneyOptionItem.getTrain().getTrainNumber();
			String departureStationName = trainJourneyOptionItem.getDepartureStation().getStationName();
			String arrivalStationName = trainJourneyOptionItem.getArrivalStation().getStationName();
			String departureDate = trainJourneyOptionItem.getDepartureDate().format(dateFormatter);
			String departureTime = trainJourneyOptionItem.getDepartureTime().format(timeFormatter);
			String coachNumber = ticket.getSeat().getCoach().getCoachNumber() + "";
			String seatNumber = ticket.getSeat().getSeatNumber() + "";
			String coachType = ticket.getSeat().getCoach().getCoachType();

			double basePrice = trainJourneyDAO.getTrainJourneyByID(trainJourneyOptionItem.getTrainJourneyID())
					.getBasePrice();
			int distance = trainJourneyDAO.getDistanceBetweenTwoStopsOfATrainJourney(
					trainJourneyOptionItem.getTrainJourneyID(), trainJourneyOptionItem.getDepartureStation(),
					trainJourneyOptionItem.getArrivalStation());
			double heSoToa = 0;
			if (ticket.getSeat().getCoach().getCoachType().equals("Ngồi mềm điều hòa")
					|| ticket.getSeat().getCoach().getCoachType().equals("Ngồi mềm đều hòa")) {
				heSoToa = 1;
			} else if (ticket.getSeat().getCoach().getCoachType().equals("Giường nằm khoang 6 điều hòa")) {
				heSoToa = 1.2;
			} else if (ticket.getSeat().getCoach().getCoachType().equals("Giường nằm khoang 4 điều hòa")) {
				heSoToa = 1.5;
			}
			double ticketPrice = basePrice * distance * heSoToa;

			int tienVe = (int) (Math.ceil(ticketPrice / 1000) * 1000);
			double discountRate;
			switch (ticket.getPassenger().getPassengerType()) {
			case "Người lớn":
				discountRate = 0.0;
				break;
			case "Trẻ em":
				discountRate = 0.5;
				break;
			case "Sinh viên":
				discountRate = 0.2;
				break;
			case "Người cao tuổi":
				discountRate = 0.3;
				break;
			default:
				throw new IllegalArgumentException("Loại đối tượng không hợp lệ");
			}

			double giamDoiTuongDouble = tienVe * discountRate;

			int giamDoiTuong = (int) (Math.ceil(giamDoiTuongDouble / 1000) * 1000);

			int tienVeCuoiCung = tienVe - giamDoiTuong + TIEN_BAO_HIEM;

			tongTienVe += tienVeCuoiCung;

			JPanel ticketItemThongTinChuyenTauContainer = new JPanel(
					new MigLayout("wrap, fill, insets 0", "[]", "[][][][]"));
			JPanel hanhTrinhContainer = new JPanel(new MigLayout("wrap, fill, insets 0, gap 0", "[][]"));
			JLabel hanhTrinhLabel = new JLabel("Hành trình: ");
			hanhTrinhLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +0");
			JLabel hanhTrinhValue = new JLabel(trainNumber + " " + departureStationName + "-" + arrivalStationName);
			hanhTrinhContainer.add(hanhTrinhLabel);
			hanhTrinhContainer.add(hanhTrinhValue);

			JPanel thoiGianKhoiHanhContainer = new JPanel(new MigLayout("wrap, fill, insets 0, gap 0", "[][]"));
			JLabel thoiGianKhoiHanhLabel = new JLabel("Thời gian khởi hành: ");
			thoiGianKhoiHanhLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +0");
			JLabel thoiGianKhoiHanhValue = new JLabel(departureDate + " " + departureTime);
			thoiGianKhoiHanhContainer.add(thoiGianKhoiHanhLabel);
			thoiGianKhoiHanhContainer.add(thoiGianKhoiHanhValue);

			JPanel viTriContainer = new JPanel(new MigLayout("wrap, fill, insets 0, gap 0", "[][]"));
			JLabel viTriLabel = new JLabel("Vị trí: ");
			viTriLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +0");
			JLabel viTriValue = new JLabel("Toa " + coachNumber + " ngồi " + seatNumber + ", " + coachType);
			viTriContainer.add(viTriLabel);
			viTriContainer.add(viTriValue);

			JPanel giaVeContainer = new JPanel(new MigLayout("wrap, fill, insets 0, gap 0", "[][][]"));
			JLabel giaVeLabel = new JLabel("Giá vé: ");
			giaVeLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +0");
			JLabel giaVeValue = new JLabel(decimalFormat.format(tienVeCuoiCung) + " ");
			giaVeValue.putClientProperty(FlatClientProperties.STYLE, "font: bold +0; foreground: $primary");
			giaVeContainer.add(giaVeLabel);
			giaVeContainer.add(giaVeValue);
			giaVeContainer.add(new JLabel("VND"));

			ticketItemThongTinChuyenTauContainer.add(hanhTrinhContainer);
			ticketItemThongTinChuyenTauContainer.add(thoiGianKhoiHanhContainer);
			ticketItemThongTinChuyenTauContainer.add(viTriContainer);
			ticketItemThongTinChuyenTauContainer.add(giaVeContainer);

			String fullName = ticket.getPassenger().getFullName();
			String passengerType = ticket.getPassenger().getPassengerType();
			String identificationNumber = ticket.getPassenger().getIdentifier();
			JPanel ticketItemThongTinHanhKhachContainer = new JPanel(
					new MigLayout("wrap, fill, insets 0", "[]", "[][][]"));
			JPanel hoVaTenContainer = new JPanel(new MigLayout("wrap, fill, insets 0, gap 0", "[][]"));
			JLabel hoVaTenLabel = new JLabel("Họ và tên: ");
			JLabel hoVaTenValue = new JLabel(fullName);
			hoVaTenContainer.add(hoVaTenLabel);
			hoVaTenContainer.add(hoVaTenValue);

			JPanel doiTuongContainer = new JPanel(new MigLayout("wrap, fill, insets 0, gap 0", "[][]"));
			JLabel doiTuongLabel = new JLabel("Đối tượng: ");
			JLabel doiTuongValue = new JLabel(passengerType);
			doiTuongContainer.add(doiTuongLabel);
			doiTuongContainer.add(doiTuongValue);

			JPanel soGiayToContainer = new JPanel(new MigLayout("wrap, fill, insets 0, gap 0", "[][]"));
			JLabel soGiayToLabel = new JLabel("Số giấy tờ: ");
			JLabel soGiayToValue = new JLabel(identificationNumber);
			soGiayToContainer.add(soGiayToLabel);
			soGiayToContainer.add(soGiayToValue);

			ticketItemThongTinHanhKhachContainer.add(hoVaTenContainer);
			ticketItemThongTinHanhKhachContainer.add(doiTuongContainer);
			ticketItemThongTinHanhKhachContainer.add(soGiayToContainer);

			ticketItem.add(ticketItemThongTinChuyenTauContainer, "growx");
			ticketItem.add(ticketItemThongTinHanhKhachContainer, "growx, aligny top");

			thongTinVeMuaValue.add(ticketItem, "");
		}

		JPanel tongCongVeContainer = new JPanel(new MigLayout("wrap, fill", "[]push[][]"));
		tongCongVeContainer.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");
		JLabel tongCongVeLabel = new JLabel("Tổng cộng");
		tongCongVeLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +0");
		JLabel tongCongVeValue;
		if (ticketInfo != null) {
			tongCongVeValue = new JLabel(
					decimalFormat.format(tongTienVe) + " - " + decimalFormat.format(ticketInfo.caculateTotal() + 2000)
							+ " = " + decimalFormat.format(tongTienVe - ticketInfo.caculateTotal() + 20000));
		} else {
			tongCongVeValue = new JLabel(decimalFormat.format(tongTienVe));
		}
		tongCongVeValue.putClientProperty(FlatClientProperties.STYLE, "font:bold +6; foreground: $clr-red");
		tongCongVeContainer.add(tongCongVeLabel);
		tongCongVeContainer.add(tongCongVeValue);
		tongCongVeContainer.add(new JLabel("VND"));

		thongTinVeMuaContainer.add(thongTinVeMuaLabelContainer, "dock north");
		thongTinVeMuaContainer.add(new JScrollPane(thongTinVeMuaValue), "dock center");
		thongTinVeMuaContainer.add(tongCongVeContainer, "dock south");

		JScrollPane scroll = (JScrollPane) thongTinVeMuaValue.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		thongTinDichVuContainer = new JPanel(new MigLayout("flowy, fill", "[fill]", "[][grow 0][]"));
		thongTinDichVuContainer.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");
		thongTinDichVuLabelContainer = new JPanel(new MigLayout());
		thongTinDichVuLabelContainer.putClientProperty(FlatClientProperties.STYLE, "background: $primary");
		thongTinDichVuLabel = new JLabel("THÔNG TIN DỊCH VỤ");
		thongTinDichVuLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +0; foreground: $clr-white");
		thongTinDichVuLabelContainer.add(thongTinDichVuLabel);
		thongTinDichVuValue = new JPanel(new MigLayout("wrap, gap 10, fillx", "[fill]", ""));
		thongTinDichVuValue.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");

		int tongTienDichVu = 0;
		for (ServiceDetail serviceDetail : chosenServiceDetailList) {
			JPanel serviceItemContainer = new JPanel(new MigLayout("wrap, fill", "[fill]", "[][]"));
			JLabel serviceItemNameLabel = new JLabel(serviceDetail.getService().getServiceName());
			JPanel tinhTienDichVuContainer = new JPanel(new MigLayout("wrap, fill", "[]push[]", "[fill]"));
			JLabel tinhTien = new JLabel(serviceDetail.getQuantity() + " x "
					+ decimalFormat.format(serviceDetail.getService().getPrice()) + " VND");
			int tongTienItem = (int) (serviceDetail.getQuantity() * serviceDetail.getService().getPrice());
			tongTienDichVu += tongTienItem;
			JLabel thanhTien = new JLabel(decimalFormat.format(tongTienItem));
			thanhTien.putClientProperty(FlatClientProperties.STYLE, "font: bold +0; foreground: $primary");
			tinhTienDichVuContainer.add(tinhTien);
			tinhTienDichVuContainer.add(thanhTien);
			serviceItemContainer.add(serviceItemNameLabel);
			serviceItemContainer.add(tinhTienDichVuContainer);
			thongTinDichVuValue.add(serviceItemContainer);
		}

		JPanel tongCongDichVuContainer = new JPanel(new MigLayout("wrap, fill", "[]push[][]"));
		tongCongDichVuContainer.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");
		JLabel tongCongDichVuLabel = new JLabel("Tổng cộng");
		JLabel tongCongDichVuValue = new JLabel(decimalFormat.format(tongTienDichVu));
		tongCongDichVuLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		tongCongDichVuValue.putClientProperty(FlatClientProperties.STYLE, "font: bold +6; foreground: $clr-red");
		tongCongDichVuContainer.add(tongCongDichVuLabel);
		tongCongDichVuContainer.add(tongCongDichVuValue);
		tongCongDichVuContainer.add(new JLabel("VND"));
		thongTinDichVuContainer.add(thongTinDichVuLabelContainer, "dock north");
		thongTinDichVuContainer.add(new JScrollPane(thongTinDichVuValue), "dock center");
		thongTinDichVuContainer.add(tongCongDichVuContainer, "dock south");

		JScrollPane dichVuScroll = (JScrollPane) thongTinDichVuValue.getParent().getParent();
		dichVuScroll.setBorder(BorderFactory.createEmptyBorder());
		dichVuScroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		container2.add(thongTinNguoiDatVeContainer, "growx");
		container2.add(thongTinVeMuaContainer);
		container2.add(thongTinDichVuContainer, "growx");

		container3 = new JPanel(new MigLayout("wrap, fill", "[fill, grow][350px]"));
		ghiChuContainer = new JPanel(new MigLayout("wrap, fill", "[fill]", "[][]"));
		ghiChuContainer.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");
		ghiChuLabel = new JLabel("Ghi chú");
		ghiChuLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +0");
		ghiChuTextArea = new JTextArea();
		ghiChuTextArea.setRows(6);
		ghiChuContainer.add(ghiChuLabel);
		ghiChuContainer.add(ghiChuTextArea, "growx, growy, push");

		donDatContainer = new JPanel(new MigLayout("flowy, fill", "[fill]", "[][grow 0][center]"));
		donDatContainer.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");
		donDatLabelContainer = new JPanel(new MigLayout());
		donDatLabel = new JLabel("ĐƠN ĐẶT");
		donDatLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +0; foreground: $clr-white");
		donDatLabelContainer.add(donDatLabel);
		donDatLabelContainer.putClientProperty(FlatClientProperties.STYLE, "background: $primary");
		thanhTienContainer = new JPanel(new MigLayout("wrap, fill, insets 16 8", "[]push[]0[]"));
		thanhTienContainer.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");
		thanhTienLabel = new JLabel("Thành tiền");
		int tongCong = tongTienVe + tongTienDichVu;
		if (ticketInfo == null) {
			thanhTienValue = new JLabel(decimalFormat.format(tongCong));
		} else {
			thanhTienValue = new JLabel(decimalFormat.format(tongCong - ticketInfo.caculateTotal() + 20000));
		}
		thanhTienValue.putClientProperty(FlatClientProperties.STYLE, "font: bold +12; foreground: $clr-red");
		thanhTienContainer.add(thanhTienLabel);
		thanhTienContainer.add(thanhTienValue);
		thanhTienContainer.add(new JLabel(" VND"));
		thanhToanButtonContainer = new JPanel(new MigLayout("wrap, fillx", "[center]"));
		thanhToanButton = new JButton("Thanh toán");
		thanhToanButtonContainer.add(thanhToanButton);
		thanhToanButtonContainer.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");
		thanhToanButton.putClientProperty(FlatClientProperties.STYLE, "background: $primary; foreground: $clr-white");
		donDatContainer.add(donDatLabelContainer, "dock north");
		donDatContainer.add(thanhTienContainer, "dock center");
		donDatContainer.add(thanhToanButtonContainer);

		container3.add(ghiChuContainer);
		container3.add(donDatContainer, "growx, growy, push");

		container.add(container1);
		container.add(container2);
		container.add(container3);
		this.add(container);
		this.setUndecorated(true);
		this.setSize(1400, 770);
		this.setLocationRelativeTo(null);

		thanhToanButton.addActionListener(e -> {

			if (ticketInfo != null) {
				String ticketID = ticketDAO.addTicket(new TrainJourney(trainJourneyOptionItem.getTrainJourneyID()),
						ticketInfo.getSeat(), ticketInfo.getPassenger(),
						new Order(ticketInfo.getTicket().getOrder().getOrderID()));
				List<Stop> stopList = trainJourneyDAO.getStops(
						new TrainJourney(trainJourneyOptionItem.getTrainJourneyID()),
						trainJourneyOptionItem.getDepartureStation(), trainJourneyOptionItem.getArrivalStation());
				for (Stop stop : stopList) {
					ticketDetailDAO.themChiTietVe(stop, new Ticket(ticketID));
				}
				
				

				Ticket chosenTicket = ticketInfo.getTicket();

				DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

				String gaDi = trainJourneyOptionItem.getDepartureStation().getStationName();
				String gaDen = trainJourneyOptionItem.getArrivalStation().getStationName();
				String tau = trainJourneyOptionItem.getTrain().getTrainNumber();
				String ngayDi = trainJourneyOptionItem.getDepartureDate().format(df);
//				String ngayDen = trainJourneyOptionItem.getArrivalDate().format(df);
				String gioDi = trainJourneyOptionItem.getArrivalTime().format(timeFormatter);
				String toa = chosenTicket.getSeat().getCoach().getCoachNumber() + "";
				String cho = chosenTicket.getSeat().getSeatNumber() + "";
				String loaiVe = "Toàn vé";
				String loaiCho = chosenTicket.getSeat().getCoach().getCoachType();

				double basePrice = trainJourneyDAO.getTrainJourneyByID(trainJourneyOptionItem.getTrainJourneyID())
						.getBasePrice();
				int distance = trainJourneyDAO.getDistanceBetweenTwoStopsOfATrainJourney(
						trainJourneyOptionItem.getTrainJourneyID(), trainJourneyOptionItem.getDepartureStation(),
						trainJourneyOptionItem.getArrivalStation());
				double heSoToa = 0;
				if (chosenTicket.getSeat().getCoach().getCoachType().equals("Ngồi mềm điều hòa")
						|| chosenTicket.getSeat().getCoach().getCoachType().equals("Ngồi mềm đều hòa")) {
					heSoToa = 1;
				} else if (chosenTicket.getSeat().getCoach().getCoachType().equals("Giường nằm khoang 6 điều hòa")) {
					heSoToa = 1.2;
				} else if (chosenTicket.getSeat().getCoach().getCoachType().equals("Giường nằm khoang 4 điều hòa")) {
					heSoToa = 1.5;
				}
				double ticketPrice = basePrice * distance * heSoToa;

				int tienVe = (int) (Math.ceil(ticketPrice / 1000) * 1000);
				double discountRate;
				switch (chosenTicket.getPassenger().getPassengerType()) {
				case "Người lớn":
					discountRate = 0.0;
					break;
				case "Trẻ em":
					discountRate = 0.5;
					break;
				case "Sinh viên":
					discountRate = 0.2;
					break;
				case "Người cao tuổi":
					discountRate = 0.3;
					break;
				default:
					throw new IllegalArgumentException("Loại đối tượng không hợp lệ");
				}

				double giamDoiTuongDouble = tienVe * discountRate;

				int giamDoiTuong = (int) (Math.ceil(giamDoiTuongDouble / 1000) * 1000);

				int gia = tienVe - giamDoiTuong + TIEN_BAO_HIEM;

				String ten = chosenTicket.getPassenger().getFullName();
				String soGiayTo = chosenTicket.getPassenger().getIdentifier();

				JOptionPane.showMessageDialog(null, "Đổi vé thành công!");

				CreateTrainTickets.createTicket(ticketID, gaDi, gaDen, tau, ngayDi, gioDi, toa, cho, loaiVe, loaiCho,
						decimalFormat.format(gia), ten, soGiayTo, ticketInfo.getTicket().getOrder().getOrderID());

				this.dispose();
				serviceChoosingDialog.dongDayChuyen();
			} else {
				String note = ghiChuTextArea.getText().trim();

				String orderID;
				if (customerDAO.getCustomerByEmail(customer.getEmail()) != null) {
					orderID = orderDAO.addOrder(LocalDate.now(), note, "Đã Thanh Toán",
							customerDAO.getCustomerByEmail(customer.getEmail()),
							new TrainJourney(trainJourneyOptionItem.getTrainJourneyID()), employee);
				} else {
					String customerID = customerDAO.addCustomer(customer);
					customer.setCustomerID(customerID);
					orderID = orderDAO.addOrder(LocalDate.now(), note, "Đã Thanh Toán", customer,
							new TrainJourney(trainJourneyOptionItem.getTrainJourneyID()), employee);
				}
//				System.out.println(orderID);
				for (Ticket chosenTicket : chosenTicketList) {
					// insert passenger first
					String passengerID = passengerDAO.addPassenger(chosenTicket.getPassenger());
					chosenTicket.getPassenger().setPassengerID(passengerID);

					// insert the ticket
					String ticketID = ticketDAO.addTicket(new TrainJourney(trainJourneyOptionItem.getTrainJourneyID()),
							chosenTicket.getSeat(), chosenTicket.getPassenger(), new Order(orderID));
					System.out.println(ticketID);

					// insert ticket detail
					// public TicketDetail(Stop stopID, Ticket ticketID) {}
					List<Stop> stopList = trainJourneyDAO.getStops(
							new TrainJourney(trainJourneyOptionItem.getTrainJourneyID()),
							trainJourneyOptionItem.getDepartureStation(), trainJourneyOptionItem.getArrivalStation());
					for (Stop stop : stopList) {
						ticketDetailDAO.themChiTietVe(stop, new Ticket(ticketID));
					}

					for (ServiceDetail serviceDetail : chosenServiceDetailList) {
						serviceDetailDAO.themChiTietDichVu(serviceDetail.getService(), new Order(orderID),
								serviceDetail.getQuantity());
					}

					DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

					String gaDi = trainJourneyOptionItem.getDepartureStation().getStationName();
					String gaDen = trainJourneyOptionItem.getArrivalStation().getStationName();
					String tau = trainJourneyOptionItem.getTrain().getTrainNumber();
					String ngayDi = trainJourneyOptionItem.getDepartureDate().format(df);
//					String ngayDen = trainJourneyOptionItem.getArrivalDate().format(df);
					String gioDi = trainJourneyOptionItem.getArrivalTime().format(timeFormatter);
					String toa = chosenTicket.getSeat().getCoach().getCoachNumber() + "";
					String cho = chosenTicket.getSeat().getSeatNumber() + "";
					String loaiVe = "Toàn vé";
					String loaiCho = chosenTicket.getSeat().getCoach().getCoachType();

					double basePrice = trainJourneyDAO.getTrainJourneyByID(trainJourneyOptionItem.getTrainJourneyID())
							.getBasePrice();
					int distance = trainJourneyDAO.getDistanceBetweenTwoStopsOfATrainJourney(
							trainJourneyOptionItem.getTrainJourneyID(), trainJourneyOptionItem.getDepartureStation(),
							trainJourneyOptionItem.getArrivalStation());
					double heSoToa = 0;
					if (chosenTicket.getSeat().getCoach().getCoachType().equals("Ngồi mềm điều hòa")
							|| chosenTicket.getSeat().getCoach().getCoachType().equals("Ngồi mềm đều hòa")) {
						heSoToa = 1;
					} else if (chosenTicket.getSeat().getCoach().getCoachType()
							.equals("Giường nằm khoang 6 điều hòa")) {
						heSoToa = 1.2;
					} else if (chosenTicket.getSeat().getCoach().getCoachType()
							.equals("Giường nằm khoang 4 điều hòa")) {
						heSoToa = 1.5;
					}
					double ticketPrice = basePrice * distance * heSoToa;

					int tienVe = (int) (Math.ceil(ticketPrice / 1000) * 1000);
					double discountRate;
					switch (chosenTicket.getPassenger().getPassengerType()) {
					case "Người lớn":
						discountRate = 0.0;
						break;
					case "Trẻ em":
						discountRate = 0.5;
						break;
					case "Sinh viên":
						discountRate = 0.2;
						break;
					case "Người cao tuổi":
						discountRate = 0.3;
						break;
					default:
						throw new IllegalArgumentException("Loại đối tượng không hợp lệ");
					}

					double giamDoiTuongDouble = tienVe * discountRate;

					int giamDoiTuong = (int) (Math.ceil(giamDoiTuongDouble / 1000) * 1000);

					int gia = tienVe - giamDoiTuong + TIEN_BAO_HIEM;

					String ten = chosenTicket.getPassenger().getFullName();
					String soGiayTo = chosenTicket.getPassenger().getIdentifier();

//					String ticketID, String gaDi, String gaDen, String tau, String ngayDi, String gioDi,
//					String toa, String cho, String loaiVe, String loaiCho, String gia, String hoTen, String giayTo
					JOptionPane.showMessageDialog(null, "Thanh toán thành công!");
					CreateTrainTickets.createTicket(ticketID, gaDi, gaDen, tau, ngayDi, gioDi, toa, cho, loaiVe,
							loaiCho, decimalFormat.format(gia), ten, soGiayTo, orderID);

//					CreateTrainTicketsV2.createTicket(ticketID, orderID, orderID, orderID, orderID, orderID, orderID, orderID, orderID, passengerID, ticketID, note, orderID)

				}
				//
//				// insert service detail
//				// public ServiceDetail(Service service, Order order, int quantity) {}

				this.dispose();
				serviceChoosingDialog.dongDayChuyen();
			}

		});
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}