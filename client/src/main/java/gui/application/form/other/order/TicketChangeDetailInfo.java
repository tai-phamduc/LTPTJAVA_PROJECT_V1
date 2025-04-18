package gui.application.form.other.order;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.raven.datechooser.DateChooser;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;

import dao.StationDAO;
import dao.TrainJourneyDAO;
import entity.Employee;
import entity.Station;
import entity.TicketInfo;
import entity.TrainJourneyOptionItem;
import gui.application.Application;
import gui.application.form.other.ticket.FormSearchTrainJourney;
import gui.application.form.other.ticket.TrainJourneyChoosingDialog;
import net.miginfocom.swing.MigLayout;
import raven.crazypanel.CrazyPanel;
import raven.toast.Notifications;;

public class TicketChangeDetailInfo extends JDialog {

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
	private TicketInfo ticket;
	private TicketRefundConfirmationScreen showTicketInfoScreen;
	private JPanel superContainer;
	private CrazyPanel customerInfoContainer;
	private JButton departureDateDateChooserButton;
	private DateChooser departureDateDateChooser;
	private TrainJourneyChoosingDialog trainJourneyChoosingDialog;

	public TicketChangeDetailInfo(List<TicketInfo> chosenTickets, Employee employee) {
//			this.trainJourneyOptionItem = trainJourneyOptionItem;
		this.choosenTickets = chosenTickets;
//			this.seatsChoosingDialog = seatsChoosingDialog;
		this.setLayout(new BorderLayout());
		superContainer = new JPanel(new MigLayout("wrap, fill, insets 20", "[fill]", "[fill]"));
		container = new JPanel(new MigLayout("wrap, fill, insets 0", "[fill]", "[]0[]0[]6[grow, fill]6[]0[]0[]0[]"));
		ticket = chosenTickets.getFirst();
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
		JPanel container9 = new JPanel(new MigLayout("wrap, fill", "[70%][30%]", "[][]"));
		JPanel dateChooserContainer = new JPanel(new MigLayout("wrap, fill", "[][]", "[][]"));
		customerInfoContainer = new CrazyPanel();
		customerInfoContainer.setLayout(new MigLayout("wrap, insets 12", "[][fill]30[][fill]", "[][]"));
		customerInfoContainer.add(new JLabel("Họ và tên"));
		JTextField nameField = new JTextField(20);
		nameField.setEnabled(false);
		nameField.setText(ticket.getTicket().getOrder().getCustomer().getFullName());
		customerInfoContainer.add(nameField);

		customerInfoContainer.add(new JLabel("Số CMND/Hộ chiếu"));
		JTextField idField = new JTextField(20);
		idField.setEnabled(false);
		idField.setText(ticket.getTicket().getOrder().getCustomer().getIdentificationNumber());
		customerInfoContainer.add(idField, "growx");

		customerInfoContainer.add(new JLabel("Email"));
		JTextField emailField = new JTextField(20);
		emailField.setEnabled(false);
		emailField.setText(ticket.getTicket().getOrder().getCustomer().getEmail());
		customerInfoContainer.add(emailField, "growx");

		customerInfoContainer.add(new JLabel("Số điện thoại"));
		JTextField phoneField = new JTextField(20);
		phoneField.setEnabled(false);
		phoneField.setText(ticket.getTicket().getOrder().getCustomer().getPhoneNumber());
		customerInfoContainer.add(phoneField, "growx");

		dateChooserContainer.add(new JLabel("Chọn ngày cần đổi"), "span 2");
		JTextField txtDateChooser = new JTextField(20);
		ImageIcon calendarIcon = new ImageIcon("images/calendar.png");
		Image image = calendarIcon.getImage();
		Image newimg = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH); // scale it the smooth way
		calendarIcon = new ImageIcon(newimg);
		departureDateDateChooserButton = new JButton();
		departureDateDateChooser = new DateChooser();
		departureDateDateChooserButton.setIcon(calendarIcon);
		departureDateDateChooserButton.addActionListener(e -> {
			departureDateDateChooser.showPopup();
		});
		departureDateDateChooser.setTextRefernce(txtDateChooser);
		departureDateDateChooser.addEventDateChooser(new EventDateChooser() {
			@Override
			public void dateSelected(SelectedAction action, SelectedDate date) {
				if (action.getAction() == SelectedAction.DAY_SELECTED) {
					departureDateDateChooser.hidePopup();
				}
			}
		});
		dateChooserContainer.add(txtDateChooser);
		dateChooserContainer.add(departureDateDateChooserButton);
		container9.add(customerInfoContainer);
		container9.add(dateChooserContainer);

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
//		container5.add(xoaLabel);

		container6 = new JPanel(new MigLayout("wrap, fill, insets 16, gapy 12", "[fill]"));

		if (renderTime == 1) {
			for (TicketInfo chosenticket : choosenTickets) {
				ComponentTicketDetail componentTicket = new ComponentTicketDetail(chosenticket, null);
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
		tongTienLabel = new JLabel("Thông tin đổi vé");
		tongTienLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +8; foreground: $clr-red");

		container7.add(tongTienLabel);

		container8 = new JPanel(new MigLayout("wrap, fill", "[]", "[]"));
		tiepTucButton = new JButton("Tiếp tục");
		tiepTucButton.putClientProperty(FlatClientProperties.STYLE, "background:$primary; foreground:$clr-white");
		container8.add(tiepTucButton, "al right");

		container.add(container1, "dock north");
		container.add(container4, "dock north");
		container.add(container5, "dock north");
		container.add(new JScrollPane(container6), "dock center");
		container.add(container8, "dock south");
		container.add(container9, "dock south");
		container.add(container7, "dock south");

		JScrollPane scroll = (JScrollPane) container6.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		tiepTucButton.addActionListener(e -> {
			List<Station> stations = (new StationDAO())
					.getStationsForTicket(choosenTickets.getFirst().getTicket().getTicketID());
			if (stations.size() == 2) {
				String departureStation = stations.getFirst().getStationName();
				String arrivalStation = stations.getLast().getStationName();
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate departureDate = LocalDate.parse(txtDateChooser.getText().trim(), dateFormatter);
				List<TrainJourneyOptionItem> trainJourneyOptionItemList = (new TrainJourneyDAO())
						.searchTrainJourney(departureStation, arrivalStation, departureDate);

				trainJourneyOptionItemList.forEach(i -> System.out.println(i));

				if (trainJourneyOptionItemList.isEmpty()) {
					Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
							"Không tìm thấy chyến tàu");
					return;
				}

				JPanel glassPane = new BlurGlassPane();
				Application.getInstance().setGlassPane(glassPane);
				glassPane.setVisible(true);

				trainJourneyChoosingDialog = new TrainJourneyChoosingDialog(trainJourneyOptionItemList, employee, ticket);
				trainJourneyChoosingDialog.setFormSearchTrainJourney(new FormSearchTrainJourney(employee));
				trainJourneyChoosingDialog.setModal(true);
				trainJourneyChoosingDialog.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "Cuts", "Cuts", JOptionPane.ERROR_MESSAGE);
				return;
			}

		});

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

class BlurGlassPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage blurredImage;

	public BlurGlassPane() {
		setOpaque(false); // Making the glass pane transparent
		// Create a blank translucent image
		blurredImage = new BufferedImage(Application.getInstance().getRootPane().getWidth(),
				Application.getInstance().getRootPane().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = blurredImage.createGraphics();
		g2d.setColor(new Color(0, 0, 0, 128)); // Set color with alpha for translucency
		g2d.fillRect(0, 0, Application.getInstance().getRootPane().getWidth(),
				Application.getInstance().getRootPane().getHeight()); // Fill the image with the translucent color
		g2d.dispose();

		// Apply blur effect
		blurredImage = blurImage(blurredImage);
	}

	// Method to blur an image
	private BufferedImage blurImage(BufferedImage image) {
		// You can implement your own image blurring algorithm or use libraries like
		// JavaFX or Apache Commons Imaging
		// Here, I'll use a simple averaging algorithm for demonstration purposes
		int blurRadius = 5;
		float weight = 1.0f / (blurRadius * blurRadius);
		float[] blurMatrix = new float[blurRadius * blurRadius];
		for (int i = 0; i < blurMatrix.length; i++) {
			blurMatrix[i] = weight;
		}
		Kernel kernel = new Kernel(blurRadius, blurRadius, blurMatrix);
		BufferedImageOp op = new ConvolveOp(kernel);
		return op.filter(image, null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw the blurred image onto the glass pane
		g.drawImage(blurredImage, 0, 0, Application.getInstance().getRootPane().getWidth(),
				Application.getInstance().getRootPane().getHeight(), null);
	}
}
