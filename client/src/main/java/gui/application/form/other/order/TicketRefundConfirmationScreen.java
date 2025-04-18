package gui.application.form.other.order;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import dao.TicketDAO;
import entity.Employee;
import entity.TicketInfo;
import net.miginfocom.swing.MigLayout;
import raven.crazypanel.CrazyPanel;

public class TicketRefundConfirmationScreen extends JDialog {
	private JPanel container;
	private JButton goBackButton;
	private int renderTime = 1;
	private List<ComponentTicketRefund> componentTicketList;
	private JPanel superContainer;
	private int totalTicketPrice;
	private int totalFee;
	private int totalRefund;
	private int ticketCount;

	public TicketRefundConfirmationScreen(List<TicketInfo> refundTickets, Employee employee,
			ChoosenTicketsRefundDialog choosenTicketsRefundDialog) {
		this.setLayout(new BorderLayout());
		superContainer = new JPanel(new MigLayout("wrap, fill, insets 20", "[fill]", "[fill]"));
		container = new JPanel(new MigLayout("wrap, fill, insets 12", "[fill]", "[]8[grow, fill]8[]2[]2[]0[]"));
		componentTicketList = new ArrayList<ComponentTicketRefund>();
		totalTicketPrice = 0;
		totalFee = 0;
		totalRefund = 0;
		ticketCount = 0;

		initComponent(refundTickets, employee, choosenTicketsRefundDialog);

		superContainer.add(container);
		this.add(superContainer);
		this.setUndecorated(true);
		this.setSize(1450, 850);
		this.setLocationRelativeTo(null);
	}

	private void initComponent(List<TicketInfo> refundTickets, Employee employee,
			ChoosenTicketsRefundDialog choosenTicketsRefundDialog) {
		container.removeAll();

		JPanel firstContainer = new JPanel(new MigLayout("wrap, insets 0", "[]16[]", "[]"));

		goBackButton = new JButton(new FlatSVGIcon("gui/icon/svg/gobackicon.svg", 0.15f));
		goBackButton.putClientProperty(FlatClientProperties.STYLE,
				"background: #fafafa; borderWidth: 0; focusWidth: 0");
		goBackButton.addActionListener(e -> {
			this.dispose();
		});

		JLabel title = new JLabel("Xác nhận yêu cầu");
		title.putClientProperty(FlatClientProperties.STYLE, "font: bold +20");
		firstContainer.add(goBackButton);
		firstContainer.add(title);

		JPanel secondContainer = new JPanel(new MigLayout("wrap, fill, insets 16 0 0 0",
				"50[fill, grow][230px, center, shrink][230px, center, shrink][230px, center, shrink][230px, center, shrink]40",
				"[]"));

		JLabel lblPassenger = new JLabel("Thông tin hành khách");
		lblPassenger.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		JLabel lblTrainInfo = new JLabel("Thông tin tàu");
		lblTrainInfo.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		JLabel ticketPrice = new JLabel("Tiền vé");
		ticketPrice.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		JLabel lblFee = new JLabel("Lệ phí trả vé");
		lblFee.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		JLabel lblRefund = new JLabel("Tiền trả");
		lblRefund.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		secondContainer.add(lblPassenger);
		secondContainer.add(lblTrainInfo);
		secondContainer.add(ticketPrice);
		secondContainer.add(lblFee);
		secondContainer.add(lblRefund);

		JPanel thirdContainer = new JPanel(new MigLayout("wrap, fillx, insets 12, gapy 12", "[fill]"));

		if (renderTime == 1) {
			for (TicketInfo chosenticket : refundTickets) {
				ComponentTicketRefund componentTicket = new ComponentTicketRefund(chosenticket, this);
				componentTicketList.add(componentTicket);
				thirdContainer.add(componentTicket, "aligny top");
			}
		} else {
			for (ComponentTicketRefund componentTicket : componentTicketList) {
				thirdContainer.add(componentTicket, "aligny top");
			}
		}

		DecimalFormat formatter = new DecimalFormat("#,### VND");

		CrazyPanel fourthContainer = new CrazyPanel();
		fourthContainer
				.setLayout(new MigLayout("wrap 4, insets 0 16 0 16", "[][grow, fill]30[][grow, fill]", "[][][]"));
		fourthContainer.add(new JLabel("Tổng vé: "));
		JTextField nameField = new JTextField(20);
		nameField.setText(ticketCount + " Vé");
		fourthContainer.add(nameField);
		fourthContainer.add(new JLabel("Tổng tiền vé: "));
		JTextField emailField = new JTextField(20);
		emailField.setText(formatter.format(totalTicketPrice));
		fourthContainer.add(emailField, "growx");
		fourthContainer.add(new JLabel("Tổng lệ phí: "));
		JTextField idField = new JTextField(20);
		idField.setText(formatter.format(totalFee));
		fourthContainer.add(idField, "growx");
		fourthContainer.add(new JLabel("Tổng tiền trả: "));
		JTextField phoneField = new JTextField(20);
		phoneField.setText(formatter.format(totalRefund));
		fourthContainer.add(phoneField, "growx");

		JPanel fifthContainer = new JPanel(new MigLayout("wrap, insets 16", "[]", "[]"));
		JLabel lblMethodConfirm = new JLabel("Phương thức nhận mã xác thực trả vé");
		lblMethodConfirm.putClientProperty(FlatClientProperties.STYLE, "font:bold +8; foreground: $clr-red");
		fifthContainer.add(lblMethodConfirm);

		JPanel sixthContainer = new JPanel(new MigLayout("wrap, insets 0 16 0 16", "16[]", "[]6[]"));
		JRadioButton rbtnPhone = new JRadioButton(
				"Số điện thoại: " + refundTickets.getFirst().getTicket().getOrder().getCustomer().getPhoneNumber());
		JRadioButton rbtnEmail = new JRadioButton(
				"Email: " + refundTickets.getFirst().getTicket().getOrder().getCustomer().getEmail());
		ButtonGroup group = new ButtonGroup();
		group.add(rbtnPhone);
		group.add(rbtnEmail);
		sixthContainer.add(rbtnPhone);
		sixthContainer.add(rbtnEmail);

		JPanel seventhContainer = new JPanel(new MigLayout("wrap, fill", "[]", "[]"));
		JButton btnConfirm = new JButton("Xác nhận");
		btnConfirm.putClientProperty(FlatClientProperties.STYLE, "background:$primary; foreground:$clr-white");
		seventhContainer.add(btnConfirm, "al right");

		System.out.println(refundTickets.getFirst().getTicket().getTrainJourney());

		btnConfirm.addActionListener(e -> {
			boolean condition = true;
			for (TicketInfo ticket : refundTickets) {
				if (!(new TicketDAO()).updateTicketStatus("Đã Trả", ticket.getTicket().getTicketID())) {
					System.out.println("Error update Refund Ticket for TicketID: " + ticket.getTicket().getTicketID());
					condition = false;
				}
			}

			if (!condition) {
				JOptionPane.showMessageDialog(null, "Trả vé thất bại!", "Thất bại", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Trả vé thành công!", "Thành công",
						JOptionPane.INFORMATION_MESSAGE);
			}

			this.dispose();
			choosenTicketsRefundDialog.closeDialog();
		});

		container.add(firstContainer, "dock north");
		container.add(secondContainer, "dock north");
		container.add(new JScrollPane(thirdContainer), "dock center");
		container.add(seventhContainer, "dock south");
		container.add(sixthContainer, "dock south");
		container.add(fifthContainer, "dock south");
		container.add(fourthContainer, "dock south");

		JScrollPane scroll = (JScrollPane) thirdContainer.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		renderTime++;

		container.repaint();
		container.revalidate();
	}

	public int getTotalTicketPrice() {
		return totalTicketPrice;
	}

	public void setTotalTicketPrice(int totalTicketPrice) {
		this.totalTicketPrice = totalTicketPrice;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public int getTotalRefund() {
		return totalRefund;
	}

	public void setTotalRefund(int totalRefund) {
		this.totalRefund = totalRefund;
	}

	public int getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}
}
