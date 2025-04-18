package gui.application.form.other.ticket;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import dao.TrainJourneyDAO;
import entity.Customer;
import entity.Ticket;
import entity.TrainJourneyOptionItem;
import net.miginfocom.swing.MigLayout;

public class ComponentTicket extends JPanel {

	public static final int TIEN_BAO_HIEM = 1000;

	private JPanel column1;
	private JLabel hoTenLabel;
	private JTextField hoTenTextField;
	private JLabel doiTuongLabel;
	private JLabel soGiayToLabel;
	private JTextField soGiayToTextField;
	private JPanel column2;
	private TrainJourneyDAO trainJourneyDAO;
	private JLabel column3;
	private JComboBox<String> doiTuongCombobox;
	private JLabel column4;
	private Ticket chosenTicket;
	private TrainJourneyOptionItem trainJourneyOptionItem;

	private JLabel column5;

	private JLabel column6;

	private JLabel column7;

	private JButton column8;

	private PassengerInfoAddingDialog parent;
	private Customer cus;

	public ComponentTicket(Ticket chosenTicket, TrainJourneyOptionItem trainJourneyOptionItem,
			PassengerInfoAddingDialog parent, Customer cus) {
		this.parent = parent;
		this.cus = cus;

		this.trainJourneyOptionItem = trainJourneyOptionItem;

		// state
		this.chosenTicket = chosenTicket;

		// component
		reactToPassengerTypeChanged();

	}

	private void setHoVaTen(String text) {
		chosenTicket.getPassenger().setFullName(text);
		reactToPassengerTypeChanged();
	}

	private void setSoGiayTo(String text) {
		chosenTicket.getPassenger().setIdentifier(text);
		reactToPassengerTypeChanged();
	}

	private void setDoiTuong(String text) {
		chosenTicket.getPassenger().setPassengerType(text);
		reactToPassengerTypeChanged();
	}

	private void reactToPassengerTypeChanged() {
		this.removeAll();

		trainJourneyDAO = new TrainJourneyDAO();

		this.setLayout(new MigLayout("wrap, fill, insets 0",
				"[fill, grow][200px, center, shrink][125px, center, shrink][125px, center, shrink][125px, center, shrink][125px, center, shrink][200px, center, shrink][50px, center, shrink]",
				"[]"));

		column1 = new JPanel(new MigLayout("wrap, fill", "[][]", "[][][]"));
		hoTenLabel = new JLabel("Họ tên");
		hoTenTextField = new JTextField(10);
		hoTenTextField.setText(chosenTicket.getPassenger().getFullName());
		hoTenTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				hoTenTextField.requestFocusInWindow();
				SwingUtilities.invokeLater(() -> hoTenTextField.setCaretPosition(hoTenTextField.getText().length()));
			}
		});
		hoTenTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				setHoVaTen(hoTenTextField.getText());
				hoTenTextField.requestFocusInWindow();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setHoVaTen(hoTenTextField.getText());
				hoTenTextField.requestFocusInWindow();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setHoVaTen(hoTenTextField.getText());
				hoTenTextField.requestFocusInWindow();
			}
		});

		doiTuongLabel = new JLabel("Đối tượng");
		doiTuongCombobox = new JComboBox<String>();
		doiTuongCombobox.addItem("Người lớn");
		doiTuongCombobox.addItem("Trẻ em");
		doiTuongCombobox.addItem("Sinh viên");
		doiTuongCombobox.addItem("Người cao tuổi");
		doiTuongCombobox.setSelectedItem(chosenTicket.getPassenger().getPassengerType());

		doiTuongCombobox.addActionListener(e -> {
			setDoiTuong((String) doiTuongCombobox.getSelectedItem());
			parent.reactToChosenTicketChanged();
		});
		soGiayToLabel = new JLabel("Số giấy tờ");
		soGiayToTextField = new JTextField(10);
		soGiayToTextField.setText(chosenTicket.getPassenger().getIdentifier());
		soGiayToTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				soGiayToTextField.requestFocusInWindow();
				SwingUtilities
						.invokeLater(() -> soGiayToTextField.setCaretPosition(soGiayToTextField.getText().length()));
			}
		});
		soGiayToTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				setSoGiayTo(soGiayToTextField.getText());
				soGiayToTextField.requestFocusInWindow();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setSoGiayTo(soGiayToTextField.getText());
				soGiayToTextField.requestFocusInWindow();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setSoGiayTo(soGiayToTextField.getText());
				soGiayToTextField.requestFocusInWindow();
			}
		});
		if (cus != null) {
			hoTenTextField.setEnabled(false);
			doiTuongCombobox.setEnabled(false);
			soGiayToTextField.setEnabled(false);
		}
		column1.add(hoTenLabel);
		column1.add(hoTenTextField);
		column1.add(doiTuongLabel);
		column1.add(doiTuongCombobox);
		column1.add(soGiayToLabel);
		column1.add(soGiayToTextField);

		String trainNumber = trainJourneyOptionItem.getTrain().getTrainNumber() + "";
		String departureStationName = trainJourneyOptionItem.getDepartureStation().getStationName();
		String arrivalStationName = trainJourneyOptionItem.getArrivalStation().getStationName();
		String departureDate = trainJourneyOptionItem.getDepartureDate()
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String departureTime = trainJourneyOptionItem.getDepartureTime().format(DateTimeFormatter.ofPattern("HH:mm"));
		String coachNumber = chosenTicket.getSeat().getCoach().getCoachNumber() + "";
		String seatNumber = chosenTicket.getSeat().getSeatNumber() + "";
		String coachType = chosenTicket.getSeat().getCoach().getCoachType();
		double heSoToa = 0;
		if (coachType.equals("Ngồi mềm điều hòa") || coachType.equals("Ngồi mềm đều hòa")) {
			coachType = "NMDH";
			heSoToa = 1;
		} else if (coachType.equals("Giường nằm khoang 6 điều hòa")) {
			coachType = "GNK6DH";
			heSoToa = 1.2;
		} else if (coachType.equals("Giường nằm khoang 4 điều hòa")) {
			coachType = "GNK4DH";
			heSoToa = 1.5;
		}
		double basePrice = trainJourneyDAO.getTrainJourneyByID(trainJourneyOptionItem.getTrainJourneyID())
				.getBasePrice();
		int distance = trainJourneyDAO.getDistanceBetweenTwoStopsOfATrainJourney(
				trainJourneyOptionItem.getTrainJourneyID(), trainJourneyOptionItem.getDepartureStation(),
				trainJourneyOptionItem.getArrivalStation());
		double ticketPrice = basePrice * distance * heSoToa;
		int giaVe = (int) (Math.ceil(ticketPrice / 1000) * 1000);

		column2 = new JPanel(new MigLayout("wrap, fill", "[fill]", "[][][][]"));

		JLabel tenChuyenTauLabel = new JLabel(trainNumber + " " + departureStationName + "-" + arrivalStationName);
		tenChuyenTauLabel.putClientProperty(FlatClientProperties.STYLE, "font:+italic -4");
		JLabel thoiGianDiLabel = new JLabel(departureDate + " " + departureTime);
		thoiGianDiLabel.putClientProperty(FlatClientProperties.STYLE, "font:+italic -4");
		JLabel choNgoiLabel = new JLabel("Toa " + coachNumber + "Chỗ " + seatNumber);
		choNgoiLabel.putClientProperty(FlatClientProperties.STYLE, "font:+italic -4");
		JLabel loaiToaLabel = new JLabel(coachType);
		loaiToaLabel.putClientProperty(FlatClientProperties.STYLE, "font:+italic -4");

		column2.add(tenChuyenTauLabel);
		column2.add(thoiGianDiLabel);
		column2.add(choNgoiLabel);
		column2.add(loaiToaLabel);

		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		column3 = new JLabel(decimalFormat.format(giaVe) + "");

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

		double giamDoiTuongDouble = giaVe * discountRate;
		int giamDoiTuong = (int) (Math.ceil(giamDoiTuongDouble / 1000) * 1000);
		column4 = new JLabel(decimalFormat.format(giamDoiTuong) + "");

		column5 = new JLabel("Không có");
		column6 = new JLabel(decimalFormat.format(TIEN_BAO_HIEM));

		int thanhTien = giaVe - giamDoiTuong + TIEN_BAO_HIEM;
		column7 = new JLabel(decimalFormat.format(thanhTien));

		column8 = new JButton(new FlatSVGIcon("gui/icon/svg/removeicon.svg", 0.25f));
		column8.addActionListener(e -> {
			parent.removeTicketFromChosenTicketList(chosenTicket, this);
		});

		this.add(column1);
		this.add(column2);
		this.add(column3);
		this.add(column4);
		this.add(column5);
		this.add(column6);
		this.add(column7);
		this.add(column8);

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

	public JComboBox<String> getDoiTuongCombobox() {
		return doiTuongCombobox;
	}

	public void setDoiTuongCombobox(JComboBox<String> doiTuongCombobox) {
		this.doiTuongCombobox = doiTuongCombobox;
	}

	public JLabel getColumn7() {
		return column7;
	}

	public void setColumn7(JLabel column7) {
		this.column7 = column7;
	}

}
