package gui.application.form.other.order;

import java.awt.event.ItemEvent;
import java.text.DecimalFormat;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import dao.OrderDAO;
import entity.TicketInfo;
import net.miginfocom.swing.MigLayout;

public class ComponentTicketDetail extends JPanel {

	private JPanel column1;
	private JLabel hoTenLabel;
	private JTextField hoTenTextField;
	private JLabel doiTuongLabel;
	private JLabel soGiayToLabel;
	private JTextField soGiayToTextField;
	private JPanel column2;
	private JLabel column3;
	private JTextField doiTuongCombobox;
	private JLabel column4;
	private TicketInfo ticket;
	private JLabel column5;
	private JLabel column6;
	private JLabel column7;
	private JCheckBox column8;
//	private ChoosenTicketsRefundDialog choosenTicketsRefundDialog;
//	private TicketChangeDetailInfo ticketChangeDetailInfo;

	public ComponentTicketDetail(TicketInfo chosenTicket, ChoosenTicketsRefundDialog choosenTicketsRefundDialog) {
		this.ticket = chosenTicket;

		// component
		reactToPassengerTypeChanged(choosenTicketsRefundDialog);

	}

	private void reactToPassengerTypeChanged(ChoosenTicketsRefundDialog choosenTicketsRefundDialog) {
		this.removeAll();

		this.setLayout(new MigLayout("wrap, fill, insets 0",
				"[fill, grow]8[200px, center, shrink]0[135px, center, shrink]15[135px, center, shrink]10[135px, center, shrink]12[135px, center, shrink]12[135px, center, shrink]12[50px, center, shrink]4",
				"[]"));

		column1 = new JPanel(new MigLayout("wrap, fill", "[]8[grow, fill]", "[][][]"));
		hoTenLabel = new JLabel("Họ tên");
		hoTenTextField = new JTextField(10);
		hoTenTextField.setText(ticket.getPassenger().getFullName());
		doiTuongLabel = new JLabel("Đối tượng");
		doiTuongCombobox = new JTextField(10);
		doiTuongCombobox.setText(ticket.getPassenger().getPassengerType());

		soGiayToLabel = new JLabel("Số giấy tờ");
		soGiayToTextField = new JTextField(10);
		soGiayToTextField.setText(ticket.getPassenger().getIdentifier());

		column1.add(hoTenLabel);
		column1.add(hoTenTextField);
		hoTenTextField.setEditable(false);
		column1.add(doiTuongLabel);
		column1.add(doiTuongCombobox);
		doiTuongCombobox.setEditable(false);
		column1.add(soGiayToLabel);
		column1.add(soGiayToTextField);
		soGiayToTextField.setEditable(false);

		String trainNumber = ticket.getTicket().getTrainJourney().getTrain().getTrainNumber();
		String trainJourneyName = ticket.getTicket().getTrainJourney().getTraInJourneyName();
		String departureDateTime = ticket.getDepatureDateTimeToString();
		String coachNumber = ticket.getCoach().getCoachNumber() + "";
		String seatNumber = ticket.getSeat().getSeatNumber() + "";
		String coachType = ticket.getSeat().getCoach().getCoachType();

		double ticketPrice = ticket.caculateTotal();
		int giaVe = (int) (Math.ceil(ticketPrice / 1000) * 1000);

		column2 = new JPanel(new MigLayout("wrap, fill", "[fill]", "[][][][]"));

		JLabel tenChuyenTauLabel = new JLabel(trainNumber + " " + trainJourneyName);
		tenChuyenTauLabel.putClientProperty(FlatClientProperties.STYLE, "font:+italic -4");
		JLabel thoiGianDiLabel = new JLabel(departureDateTime);
		thoiGianDiLabel.putClientProperty(FlatClientProperties.STYLE, "font:+italic -4");
		JLabel choNgoiLabel = new JLabel("Toa: " + coachNumber + ", Chỗ số: " + seatNumber);
		choNgoiLabel.putClientProperty(FlatClientProperties.STYLE, "font:+italic -4");
		JLabel loaiToaLabel = new JLabel(coachType);
		loaiToaLabel.putClientProperty(FlatClientProperties.STYLE, "font:+italic -4");

		column2.add(tenChuyenTauLabel);
		column2.add(thoiGianDiLabel);
		column2.add(choNgoiLabel);
		column2.add(loaiToaLabel);

		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		column3 = new JLabel(decimalFormat.format(giaVe));

		double fee = ticket.caculateRefundFee();
		int refundFee = (int) (Math.ceil(fee / 1000) * 1000);

		column5 = new JLabel(decimalFormat.format(refundFee));

		int refund = giaVe - refundFee;

		column6 = new JLabel(decimalFormat.format(refund));
		column4 = new JLabel(
				(new OrderDAO()).getTicketCountByOrderID(ticket.getTicket().getOrder().getOrderID()) > 1 ? "Vé tập thể"
						: "Vé cá nhân");

		String seventhColumnValue = ticket.getTicket().getStatus().equalsIgnoreCase("Bình thường") ? "Vé chưa đổi"
				: "Vé đã đổi";
		column7 = new JLabel(seventhColumnValue);

		column8 = new JCheckBox();
		if (choosenTicketsRefundDialog != null) {
			column8.addItemListener(e -> {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					choosenTicketsRefundDialog.getRefundTickets().add(ticket);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					choosenTicketsRefundDialog.getRefundTickets().remove(ticket);
				}
			});
		} else {
			column8.setEnabled(false);
		}

		this.add(column1);
		this.add(column2);
		this.add(column3);
		this.add(column4);
		this.add(column7);
		this.add(column5);
		this.add(column6);
		if (choosenTicketsRefundDialog != null) {
			this.add(column8);
		}

		column1.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");
		column2.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");
		this.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");

		this.repaint();
		this.revalidate();

	}

	public JTextField getHoTenTextField() {
		return hoTenTextField;
	}

	public void setHoTenTextField(JTextField hoTenTextField) {
		this.hoTenTextField = hoTenTextField;
	}

	public JTextField getSoGiayToTextField() {
		return soGiayToTextField;
	}

	public void setSoGiayToTextField(JTextField soGiayToTextField) {
		this.soGiayToTextField = soGiayToTextField;
	}

	public JTextField getDoiTuongCombobox() {
		return doiTuongCombobox;
	}

	public void setDoiTuongCombobox(JTextField doiTuongCombobox) {
		this.doiTuongCombobox = doiTuongCombobox;
	}

	public JLabel getColumn7() {
		return column7;
	}

	public void setColumn7(JLabel column7) {
		this.column7 = column7;
	}

}
