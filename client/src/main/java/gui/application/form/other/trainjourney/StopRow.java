package gui.application.form.other.trainjourney;

import java.awt.Color;
import java.awt.Image;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.raven.datechooser.DateChooser;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import com.raven.swing.TimePicker;

import entity.Stop;
import net.miginfocom.swing.MigLayout;

public class StopRow extends JPanel {

	private JTextField departureDateTextField;
	private JTextField arrivalTimeTextField;
	private JTextField departureTimeTextField;
	private JButton departureDateDateChooserButton;
	private DateChooser departureDateDateChooser;
	private JPanel ngayDiContainer;
	private TimePicker arrivalTimeTimePicker;
	private JButton arrivalTimeTimePickerButton;
	private JPanel arrivalTimeContainer;
	private TimePicker departureTimeTimePicker;
	private JButton departureTimeTimePickerButton;
	private JPanel departureTimeContainer;

	@SuppressWarnings("deprecation")
	public StopRow(Stop stop) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm");

		this.setLayout(new MigLayout("wrap", "[grow][100px][grow][150px][100px][150px]"));

		JLabel stopOrderLabel = new JLabel(stop.getStopOrder() + "");
		JLabel stationLabel = new JLabel(stop.getStation().getStationName());
		JLabel distanceLabel = new JLabel(stop.getDistance() + "");
		departureDateTextField = new JTextField();
		arrivalTimeTextField = new JTextField();
		departureTimeTextField = new JTextField();

		// depatureDateTextField
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
		departureDateDateChooser.setTextRefernce(departureDateTextField);
		departureDateDateChooser.addEventDateChooser(new EventDateChooser() {
			@Override
			public void dateSelected(SelectedAction action, SelectedDate date) {
				if (action.getAction() == SelectedAction.DAY_SELECTED) {
					departureDateDateChooser.hidePopup();
				}
			}
		});

		ngayDiContainer = new JPanel();
		ngayDiContainer.add(departureDateTextField);
		ngayDiContainer.add(departureDateDateChooserButton);

		// depatureDateTextField

		// arrivalTimeTextField
		arrivalTimeTimePicker = new TimePicker();
		arrivalTimeTimePicker.set24hourMode(true);
		arrivalTimeTimePickerButton = new JButton();

		ImageIcon clockIcon = new ImageIcon("images/clock.png");
		Image clockImage = clockIcon.getImage();
		Image newClockImage = clockImage.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		clockIcon = new ImageIcon(newClockImage);

		arrivalTimeTimePicker.setForeground(new Color(138, 48, 191));
		arrivalTimeTimePicker.setDisplayText(arrivalTimeTextField);
		arrivalTimeTimePickerButton.setIcon(clockIcon);
		arrivalTimeTimePickerButton.addActionListener(e -> {
			arrivalTimeTimePicker.showPopup(this, (getWidth() - arrivalTimeTimePicker.getPreferredSize().width) / 2,
					(getHeight() - arrivalTimeTimePicker.getPreferredSize().height) / 2);
		});

		arrivalTimeContainer = new JPanel(new MigLayout("wrap"));
		arrivalTimeContainer.add(arrivalTimeTextField, "grow 0, split 3, gapright 0");
		arrivalTimeContainer.add(arrivalTimeTimePickerButton, "grow 0");
		// arrivalTimeTextField

		// arrivalTimeTextField
		departureTimeTimePicker = new TimePicker();
		departureTimeTimePicker.set24hourMode(true);
		departureTimeTimePickerButton = new JButton();

		departureTimeTimePicker.setForeground(new Color(138, 48, 191));
		departureTimeTimePicker.setDisplayText(departureTimeTextField);
		departureTimeTimePickerButton.setIcon(clockIcon);
		departureTimeTimePickerButton.addActionListener(e -> {
			departureTimeTimePicker.showPopup(this, (getWidth() - departureTimeTimePicker.getPreferredSize().width) / 2,
					(getHeight() - departureTimeTimePicker.getPreferredSize().height) / 2);
		});

		departureTimeContainer = new JPanel(new MigLayout("wrap"));
		departureTimeContainer.add(departureTimeTextField, "grow 0, split 3, gapright 0");
		departureTimeContainer.add(departureTimeTimePickerButton, "grow 0");
		// arrivalTimeTextField

		System.out.println(stop.getDepartureDate().getDayOfMonth());
		
	       // Convert LocalDateTime to ZonedDateTime with a specific time zone
        ZonedDateTime zonedDateTime = stop.getDepartureDate().atStartOfDay().atZone(ZoneId.systemDefault());
        
        // Convert ZonedDateTime to Date
        Date date = Date.from(zonedDateTime.toInstant());
        
		departureDateDateChooser.setSelectedDate(date);
		arrivalTimeTimePicker
				.setSelectedTime(new Date(1, 1, 1, stop.getArrivalTime().getHour(), stop.getArrivalTime().getMinute()));
		departureTimeTimePicker.setSelectedTime(
				new Date(1, 1, 1, stop.getDepartureTime().getHour(), stop.getDepartureTime().getMinute()));

		this.add(stopOrderLabel);
		this.add(stationLabel);
		this.add(distanceLabel);
		this.add(ngayDiContainer, "growx");
		this.add(arrivalTimeContainer, "growx");
		this.add(departureTimeContainer, "growx");

	}

	public JTextField getDepartureDateTexField() {
		return departureDateTextField;
	}

	public void setDepartureDateTexField(JTextField departureDateTexField) {
		this.departureDateTextField = departureDateTexField;
	}

	public JTextField getArrivalTimeTextField() {
		return arrivalTimeTextField;
	}

	public void setArrivalTimeTextField(JTextField arrivalTimeTextField) {
		this.arrivalTimeTextField = arrivalTimeTextField;
	}

	public JTextField getDepartureTimeTextField() {
		return departureTimeTextField;
	}

	public void setDepartureTimeTextField(JTextField departureTimeTextField) {
		this.departureTimeTextField = departureTimeTextField;
	}

}