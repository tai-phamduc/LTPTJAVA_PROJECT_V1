package gui.application.form.other.ticket;

import java.awt.Color;
import java.awt.Image;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.formdev.flatlaf.FlatClientProperties;

import entity.Employee;
import entity.TicketInfo;
import entity.TrainJourneyOptionItem;
import net.miginfocom.swing.MigLayout;

public class TrainJourneyOptionItemCard extends JPanel {

	private TrainJourneyOptionItem trainJourneyOptionItem;
	private JPanel container1;
	private JPanel container2;
	private JPanel soHieuTauContainer;
	private JLabel soHieuTauLabel;
	private JLabel soHieuTauValue;
	private JPanel soChoConTrongContainer;
	private JLabel soChoConTrongLabel;
	private JLabel soChoConTrongValue;
	private JPanel thoiGianDiContainer;
	private JLabel thoiGianDiLabel;
	private JLabel thoiGianDiDateValue;
	private JLabel thoiGianDiTimeValue;
	private JPanel thoiGianDenContainer;
	private JLabel thoiGianDenLabel;
	private JLabel thoiGianDenTimeValue;
	private JLabel thoiGianDenDateValue;
	private JPanel thoiLuongChuyenTauContainer;
	private JLabel thoiLuongChuyenTauLabel;
	private JButton thoiLuongChuyenTauValue;

	public TrainJourneyOptionItemCard(TrainJourneyOptionItem trainJourneyOptionItem, Employee employee,
			TrainJourneyChoosingDialog trainJourneyChoosingDialog, TicketInfo ticket) {

		this.trainJourneyOptionItem = trainJourneyOptionItem;

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		this.setLayout(new MigLayout("wrap, fill, insets 0", "[fill]", "[][]"));

		container1 = new JPanel(new MigLayout("wrap, fill", "[]push[]", "fill, grow"));
		container2 = new JPanel(new MigLayout("wrap, fill", "[]push[]push[]", "fill, grow"));

		soHieuTauContainer = new JPanel(new MigLayout("wrap, fill", "[]", "[][]"));
		soHieuTauLabel = new JLabel("Số hiệu tàu");
		soHieuTauValue = new JLabel(trainJourneyOptionItem.getTrain().getTrainNumber());
		soHieuTauContainer.add(soHieuTauLabel);
		soHieuTauContainer.add(soHieuTauValue);

		soChoConTrongContainer = new JPanel(new MigLayout("wrap, fill", "[]", "[][]"));
		soChoConTrongLabel = new JLabel("Sô chỗ còn trống");
		soChoConTrongValue = new JLabel(trainJourneyOptionItem.getNumberOfAvailableSeatsLeft() + "");
		soChoConTrongContainer.add(soChoConTrongLabel);
		soChoConTrongContainer.add(soChoConTrongValue, "al right");

		container1.add(soHieuTauContainer);
		container1.add(soChoConTrongContainer);

		thoiGianDiContainer = new JPanel(new MigLayout("wrap, fill", "[fill]", "[][][]"));
		thoiGianDiLabel = new JLabel("Thời gian đi");
		thoiGianDiTimeValue = new JLabel(trainJourneyOptionItem.getDepartureTime().format(timeFormatter));
		thoiGianDiDateValue = new JLabel(trainJourneyOptionItem.getDepartureDate().format(dateFormatter));
		thoiGianDiContainer.add(thoiGianDiLabel);
		thoiGianDiContainer.add(thoiGianDiTimeValue);
		thoiGianDiContainer.add(thoiGianDiDateValue);

		thoiLuongChuyenTauContainer = new JPanel(new MigLayout("wrap, fill", "[center]", "[][]"));
		ImageIcon thoiLuongChuyenTauIcon = new ImageIcon("images/train.png");
		Image img = thoiLuongChuyenTauIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		thoiLuongChuyenTauLabel = new JLabel();
		thoiLuongChuyenTauLabel.setIcon(new ImageIcon(img));
		thoiLuongChuyenTauValue = new JButton(formatMinutesInVietnamese(trainJourneyOptionItem.getJourneyDuration()));
		thoiLuongChuyenTauContainer.add(thoiLuongChuyenTauLabel);
		thoiLuongChuyenTauContainer.add(thoiLuongChuyenTauValue);

		thoiGianDenContainer = new JPanel(new MigLayout("wrap, fill", "[]", "[][][]"));
		thoiGianDenLabel = new JLabel("Thời gian đến");
		thoiGianDenTimeValue = new JLabel(trainJourneyOptionItem.getArrivalTime().format(timeFormatter));
		thoiGianDenDateValue = new JLabel(trainJourneyOptionItem.getArrivalDate().format(dateFormatter));
		thoiGianDenContainer.add(thoiGianDenLabel);
		thoiGianDenContainer.add(thoiGianDenTimeValue, "al right");
		thoiGianDenContainer.add(thoiGianDenDateValue, "al right");

		container2.add(thoiGianDiContainer);
		container2.add(thoiLuongChuyenTauContainer);
		container2.add(thoiGianDenContainer);

		this.add(container1, "growx");
		this.add(container2, "growx");

		this.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLACK));

		soHieuTauValue.putClientProperty(FlatClientProperties.STYLE, "foreground: $primary; font: bold +0");
		soChoConTrongValue.putClientProperty(FlatClientProperties.STYLE, "foreground: $primary");
		thoiGianDiTimeValue.putClientProperty(FlatClientProperties.STYLE, "font: bold +8");
		thoiGianDenTimeValue.putClientProperty(FlatClientProperties.STYLE, "font: bold +8");
		container1.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
		thoiLuongChuyenTauValue.putClientProperty(FlatClientProperties.STYLE,
				"foreground: $clr-white; background: $primary");

		thoiLuongChuyenTauValue.addActionListener(e -> {
			SeatsChoosingDialog seatsChoosingDialog = new SeatsChoosingDialog(trainJourneyOptionItem, employee,
					trainJourneyChoosingDialog, ticket);
			seatsChoosingDialog.setModal(true);
			seatsChoosingDialog.setVisible(true);
		});
	}

	private String formatMinutesInVietnamese(int totalMinutes) {
		Duration duration = Duration.ofMinutes(totalMinutes);
		long hours = duration.toHours();
		long minutes = duration.toMinutesPart();

		return hours + " tiếng" + (minutes > 0 ? " " + minutes + " phút" : "");
	}

}
