package gui.application.form.other.order;

import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import dao.OrderDAO;
import dao.TicketDAO;
import entity.Customer;
import entity.Employee;
import entity.Order;
import entity.TicketInfo;
import net.miginfocom.swing.MigLayout;
import raven.crazypanel.CrazyPanel;

public class TicketRefundScreen extends JPanel {

	private CrazyPanel container;
	private JLabel lblTitle;
	private JLabel lblOrderID;
	private JTextField txtOrderID;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblPhoneNumber;
	private JTextField txtPhoneNumber;
	private JButton btnRefund;
	private JButton btnChange;
	private JLabel lblHeader;
	private CrazyPanel content;
	private ChoosenTicketsRefundDialog showTicketInfoScreen;
	private TicketChangeDetailInfo showChangeTicketDetailInfo;
	private OrderDAO orderDAO;

	public TicketRefundScreen(Employee employee) {
		orderDAO = new OrderDAO();
		container = new CrazyPanel();
		content = new CrazyPanel();
		lblHeader = new JLabel("Xử Lý Đổi Trả Vé");
		lblTitle = new JLabel("Thông Tin Cần Thiết: ");

		lblOrderID = new JLabel("Mã Hóa Đơn: ");
		txtOrderID = new JTextField(40);

		// test

		lblEmail = new JLabel("Email: ");
		txtEmail = new JTextField(15);

		lblPhoneNumber = new JLabel("Số Điện Thoại: ");
		txtPhoneNumber = new JTextField(15);

		btnRefund = new JButton("Trả Vé");
		btnChange = new JButton("Đổi Vé");

		lblTitle.setFont(new Font(lblTitle.getFont().getFontName(), Font.BOLD, 20));
		lblHeader.setFont(new Font(lblTitle.getFont().getFontName(), Font.BOLD, 32));
		btnRefund.putClientProperty(FlatClientProperties.STYLE,
				"arc:5;background: $primary; foreground: $clr-white;hoverBackground:$clr-white;hoverForeground:$primary");
		btnChange.putClientProperty(FlatClientProperties.STYLE,
				"arc:5;background: $primary; foreground: $clr-white;hoverBackground:$clr-white;hoverForeground:$primary");
		container.setLayout(new MigLayout("wrap 2, fillx, insets 15 20 15 20, gap 20", "[grow 0,trail]15[fill]"));
		content.setLayout(new MigLayout("wrap 3, fillx, insets 15 20 15 20, gap 20", "[]150[grow 0,trail]15[fill]"));

		container.add(new JLabel(), "wrap, span, al center, gaptop 10");
		container.add(lblHeader, "wrap, span, al left, gapbottom 30");
		content.add(new JLabel());
		content.add(lblTitle);
		content.add(new JLabel());
		content.add(new JLabel());
		content.add(lblOrderID);
		content.add(txtOrderID);
		content.add(new JLabel());
		content.add(lblEmail);
		content.add(txtEmail);
		content.add(new JLabel());
		content.add(lblPhoneNumber);
		content.add(txtPhoneNumber);
		JPanel btnContainer = new JPanel(new MigLayout("fill", "[]10[]", "[]"));
		btnContainer.add(btnChange);
		btnContainer.add(btnRefund);
		content.add(btnContainer, "wrap, span, al right, gapbottom 8");

		setLayout(new MigLayout("", ""));
		add(container, "wrap, span");
		add(content, "wrap, span");

		btnRefund.addActionListener(e -> callBtnRefundEvent(employee));
		btnChange.addActionListener(e -> callBtnChangeEvent(employee));

	}

	private void callBtnRefundEvent(Employee employee) {
		Thread thread = new Thread(() -> {
			TicketDAO ticketDAO = new TicketDAO();
			String orderID = txtOrderID.getText();
			String email = txtEmail.getText();
			String phone = txtPhoneNumber.getText();

			Order order = orderDAO.getOrderByID(orderID);

			Customer cus = order.getCustomer();

			boolean isValidOrder = order.getOrderStatus().equalsIgnoreCase("Đã thanh toán");

			boolean isSame = (cus.getEmail().equalsIgnoreCase(email) && cus.getPhoneNumber().equalsIgnoreCase(phone));

			if (!(isSame && isValidOrder)) {
				JOptionPane.showMessageDialog(null, "Thông tin không hợp lệ!");
				return;
			}

			List<TicketInfo> tickets = ticketDAO.fetchEligibleRefundTicketsForOrder(orderID, true);

			showTicketInfoScreen = new ChoosenTicketsRefundDialog(tickets, employee);
			showTicketInfoScreen.setModal(true);
			showTicketInfoScreen.setVisible(true);
		});
		thread.start();
	}

	private void callBtnChangeEvent(Employee employee) {
		Thread thread = new Thread(() -> {
			TicketDAO ticketDAO = new TicketDAO();
			String orderID = txtOrderID.getText();
			String email = txtEmail.getText();
			String phone = txtPhoneNumber.getText();

			Order order = orderDAO.getOrderByID(orderID);
			if (orderDAO.getTicketCountByOrderID(orderID) != 1) {
				JOptionPane.showMessageDialog(null, "Vé tập thể không được phép đổi!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			Customer cus = order.getCustomer();

			boolean isValidOrder = order.getOrderStatus().equalsIgnoreCase("Đã thanh toán");

			boolean isSame = (cus.getEmail().equalsIgnoreCase(email) && cus.getPhoneNumber().equalsIgnoreCase(phone));

			if (!(isSame && isValidOrder)) {
				JOptionPane.showMessageDialog(null, "Thông tin không hợp lệ!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
				return;
			}

			List<TicketInfo> tickets = ticketDAO.fetchEligibleRefundTicketsForOrder(orderID, false);

			if (tickets.getFirst().getRemainingHours() <= 24) {
				JOptionPane.showMessageDialog(null, "Vé phải được đổi trước khi tàu chạy tối thiểu 24h!", "Cảnh báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				showChangeTicketDetailInfo = new TicketChangeDetailInfo(tickets, employee);
				showChangeTicketDetailInfo.setModal(true);
				showChangeTicketDetailInfo.setVisible(true);
				
			}

		});
		thread.start();
	}

}
