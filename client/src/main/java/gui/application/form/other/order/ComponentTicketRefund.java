package gui.application.form.other.order;

import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import entity.TicketInfo;
import net.miginfocom.swing.MigLayout;

public class ComponentTicketRefund extends JPanel {

	public ComponentTicketRefund(TicketInfo ticket, TicketRefundConfirmationScreen form) {

		this.removeAll();

		this.setLayout(new MigLayout("wrap, fill, insets 12",
				"[grow][224px, center, shrink][224px, center, shrink][224px, center, shrink][224px, center, shrink]",
				"[]"));

		JPanel secondColumn = new JPanel(new MigLayout("wrap, fill", "[]24[]", "[]12[]"));
		JLabel lblName = new JLabel("Họ tên: ");
		JLabel lblIdentifier = new JLabel("Số giấy tờ: ");
		JLabel lblNameValue = new JLabel(ticket.getPassenger().getFullName());
		JLabel lblIdentifierValue = new JLabel(ticket.getPassenger().getIdentifier());
		secondColumn.add(lblName);
		secondColumn.add(lblNameValue);
		secondColumn.add(lblIdentifier);
		secondColumn.add(lblIdentifierValue);

		JPanel thirdColumn = new JPanel(new MigLayout("wrap, fill", "[]", "[]12[]"));
		JLabel lblTrainName = new JLabel(ticket.getTicket().getTrainJourney().getTrain().getTrainNumber());
		JLabel lblpossision = new JLabel(
				"Toa: " + ticket.getCoach().getCoachNumber() + ", Chỗ: " + ticket.getSeat().getSeatNumber());
		thirdColumn.add(lblTrainName);
		thirdColumn.add(lblpossision);

		DecimalFormat decimalFormat = new DecimalFormat("#,###");

		double ticketPrice = ticket.caculateTotal();
		int price = (int) (Math.ceil(ticketPrice / 1000) * 1000);

		JLabel fourthColumn = new JLabel(decimalFormat.format(price));

		double fee = ticket.caculateRefundFee();
		int refundFee = (int) (Math.ceil(fee / 1000) * 1000);

		JLabel fifthColumn = new JLabel(decimalFormat.format(refundFee));

		int refund = price - refundFee;

		JLabel sixthColumn = new JLabel(decimalFormat.format(refund));

		form.setTotalTicketPrice(form.getTotalTicketPrice() + price);
		form.setTotalFee(form.getTotalFee() + refundFee);
		form.setTotalRefund(form.getTotalRefund() + refund);
		form.setTicketCount(form.getTicketCount() + 1);

		this.add(secondColumn);
		this.add(thirdColumn);
		this.add(fourthColumn);
		this.add(fifthColumn);
		this.add(sixthColumn);

		secondColumn.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");
		thirdColumn.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");
		this.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");

		this.repaint();
		this.revalidate();
	}
}
