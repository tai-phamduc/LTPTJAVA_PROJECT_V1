package gui.application.form.other.customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;

import dao.PassengerDAO;
import entity.Passenger;
import net.miginfocom.swing.MigLayout;
import raven.crazypanel.CrazyPanel;
import raven.toast.Notifications;
import raven.toast.Notifications.Location;

public class CustomerUpdateDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Passenger passenger;
	private PassengerDAO passengerDAO;
	private CrazyPanel container;
	private JLabel lblTitle;
	private JLabel lblFullName;
	private JTextField txtFullName;
	private JLabel lblDateOfBirth;
	private JTextField txtDateOfBirth;
	private JLabel lblIdentifier;
	private JTextField txtIdentifier;
	private JLabel lblIdentifierType;
	private JComboBox<String> cboIdentifierType;
	private JLabel lblErrorMessage;
	private JButton btnUpdate;
	private DateChooser regDateDateChooser;
	private JButton btnRegDateChooser;
	private FormCustomerManagement formCustomerManagement;

	public CustomerUpdateDialog(Passenger passenger) {
		this.passenger = passenger;
		passengerDAO = new PassengerDAO();
		setLayout(new BorderLayout());
		initComponents();
	}

	private void initComponents() {
		this.setTitle("Cập Nhật Thông Tin Hành Khách");
		container = new CrazyPanel();
		lblTitle = new JLabel("Cập Nhật Thông Tin Hành Khách");
		lblFullName = new JLabel("Họ Tên: ");
		txtFullName = new JTextField(30);
		lblDateOfBirth = new JLabel("Ngày Sinh: ");
		txtDateOfBirth = new JTextField(30);
		lblIdentifierType = new JLabel("Loại Mã Số Định Danh: ");
		cboIdentifierType = new JComboBox<String>();
		lblIdentifier = new JLabel("Mã Số Định Danh: ");
		txtIdentifier = new JTextField(30);
		regDateDateChooser = new DateChooser();
		btnRegDateChooser = new JButton();
		lblErrorMessage = new JLabel();
		btnUpdate = new JButton("Cập Nhật");

		// fill text fields with existing data
		txtFullName.setText(passenger.getFullName());
		txtDateOfBirth.setText(passenger.getDateOfBirth().toString());
		cboIdentifierType.addItem("CCCD/CMND");
        cboIdentifierType.addItem("Hộ Chiếu");
        cboIdentifierType.addItem("GPLX");
        txtIdentifier.setText(passenger.getIdentifier());
		LocalDate dob = passenger.getDateOfBirth();
		regDateDateChooser.setSelectedDate(new SelectedDate(dob.getDayOfMonth(), dob.getMonthValue(), dob.getYear()));

		// styles
		lblTitle.setFont(new Font(lblTitle.getFont().getFontName(), Font.BOLD, 18));
		lblErrorMessage.setForeground(Color.RED);

		// set layout
		container.setLayout(new MigLayout("wrap 2,fillx,insets 8, gap 8", "[grow 0,trail]15[fill]"));

		container.add(lblTitle, "wrap, span, al center, gapbottom 8");

		container.add(lblFullName);
		container.add(txtFullName);

		container.add(lblDateOfBirth);
		container.add(txtDateOfBirth);
		container.add(txtDateOfBirth, "grow 0, split 3, gapright 0");
		container.add(new JLabel());
		container.add(btnRegDateChooser, "grow 0");

		container.add(lblIdentifierType);
		container.add(cboIdentifierType);

		container.add(lblIdentifier);
		container.add(txtIdentifier);

		container.add(lblErrorMessage, "span 2, al center");
		container.add(btnUpdate, "span 2, al trail");

		ImageIcon calendarIcon = new ImageIcon("images/calendar.png");
		Image image = calendarIcon.getImage();
		Image newimg = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH); // scale it the smooth way
		calendarIcon = new ImageIcon(newimg);
		btnRegDateChooser.setIcon(calendarIcon);
		btnRegDateChooser.addActionListener(e -> {
			regDateDateChooser.showPopup();
		});
		regDateDateChooser.setTextRefernce(txtDateOfBirth);
		regDateDateChooser.addEventDateChooser(new EventDateChooser() {
			@Override
			public void dateSelected(SelectedAction action, SelectedDate date) {
				System.out.println(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
				if (action.getAction() == SelectedAction.DAY_SELECTED) {
					regDateDateChooser.hidePopup();
				}
			}
		});

		// assign events
		btnUpdate.addActionListener(this);

		add(container);
		pack();
		setLocationRelativeTo(null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnUpdate)) {
			// get all the value
			String fullName = txtFullName.getText().trim();
			String dob = txtDateOfBirth.getText().trim();
			String identifier = txtIdentifier.getText().trim();
			String identifierType = (String) cboIdentifierType.getSelectedItem();
			String passengerType = passenger.getPassengerType();
			// validate those values
			if (fullName.equals("")) {
				lblErrorMessage.setText("Họ tên không được để trống");
				txtFullName.requestFocus();
				return;
			}
//			if (!fullName.matches("^[A-Z][a-zA-Z]*(\\s[A-Z][a-zA-Z]*)*$")) {
//				lblErrorMessage.setText("Full name must start with capital letters");
//				txtFullName.requestFocus();
//				return;
//			}
			if (dob.equals("")) {
				lblErrorMessage.setText("Ngày sinh không được để trống");
				txtDateOfBirth.requestFocus();
				return;
			}
//			if (identifier.equals("")) {
//				lblErrorMessage.setText("Email ");
//				txtIdentifier.requestFocus();
//				return;
//			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate dobDateLocalDate = LocalDate.parse(dob, formatter);
			if (!dobDateLocalDate.isBefore(LocalDate.now())) {
				lblErrorMessage.setText("Ngày sinh không hợp lệ!");
				txtIdentifier.requestFocus();
				return;
			}
			String id = passenger.getPassengerID();
			Passenger updatePassenger = new Passenger(id, fullName, dobDateLocalDate, identifier, identifierType,
					passengerType);
			System.out.println(updatePassenger);
			// write an update query to the database with the id of this customer
			boolean isSuccessful = passengerDAO.updatePassenger(updatePassenger);
			if (isSuccessful) {
				Notifications.getInstance().show(Notifications.Type.INFO, Location.BOTTOM_LEFT, "Cập nhật thành công!");
				formCustomerManagement.handleSearch();
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Cập nhật thất bại, vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);

			}
		}

	}

	public void setFormCustomerManagement(FormCustomerManagement formCustomerManagement) {
		this.formCustomerManagement = formCustomerManagement;
	}

}