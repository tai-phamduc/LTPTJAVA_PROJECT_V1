package gui.application.form.other.station;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import dao.StationDAO;
import entity.Station;
import gui.application.Application;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

public class StationUpdateDialog extends JDialog {

	private JLabel title;
	private JPanel container;
	private JPanel container1;
	private JPanel container2;
	private JPanel soHieuTauContainer;
	private JLabel soHieuTauLabel;
	private JTextField txtStationName;
	private JPanel container4;
	private JButton updateButton;

	private JButton closeButton;
	private JPanel buttonContainer;

	private StationDAO stationDAO;
	private StationScreen stationScreen;

	public StationUpdateDialog(Station station) {
		stationDAO = new StationDAO();

		this.setUndecorated(true);
		this.setLayout(new BorderLayout());
		container = new JPanel(new MigLayout("wrap", "[fill]", "[][]push[]"));
		initUI(station);
		add(container);
		pack();
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JPanel defaultGlassPane = (JPanel) Application.getInstance().getGlassPane();
				defaultGlassPane.removeAll();
				Application.getInstance().setGlassPane(defaultGlassPane);
				defaultGlassPane.setVisible(false);
			}
		});
	}

	// Initializes the entire UI
	private void initUI(Station station) {
		container.removeAll();

		container1 = new JPanel(new MigLayout());
		title = new JLabel("CẬP NHẬT GA");
		title.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");
		container1.add(title);

		container2 = new JPanel(new MigLayout("wrap", "[]", "[]"));
		soHieuTauContainer = new JPanel(new MigLayout("wrap", "[]", "[]"));
		soHieuTauLabel = new JLabel("Tên Ga: ");
		txtStationName = new JTextField(10);
		txtStationName.setText(station.getStationName());
		soHieuTauContainer.add(soHieuTauLabel);
		soHieuTauContainer.add(txtStationName);
		container2.add(soHieuTauContainer, "alignx center");

		container4 = new JPanel(new MigLayout("wrap", "[grow]"));
		buttonContainer = new JPanel(new MigLayout("wrap", "[]4[]"));
		closeButton = new JButton("Đóng");
		closeButton.putClientProperty(FlatClientProperties.STYLE,
				"arc:5;background: $clr-red;foreground: $clr-white; hoverBackground: $clr-white; hoverForeground: $clr-red");
		closeButton.addActionListener(e -> {
			closeDialog();
		});
		updateButton = new JButton("Cập nhật Ga");
		updateButton.putClientProperty(FlatClientProperties.STYLE,
				"arc:5;background: $primary; foreground: $clr-white;hoverBackground:$clr-white;hoverForeground:$primary");
		buttonContainer.add(closeButton);
		buttonContainer.add(updateButton);
		container4.add(buttonContainer, "alignx right");

		container.add(container1);
		container.add(container2, "alignx center");
//        container.add(container3);
		container.add(container4, "alignx right");

		updateButton.addActionListener(e -> {
			if (stationDAO.updateStation(new Station(station.getStationID(), txtStationName.getText().trim()))) {

				Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
						"Cập nhật ga thành công!");
				stationScreen.search();
				this.closeDialog();
			} else {
				JOptionPane.showMessageDialog(this, "Không thể cập nhật!", "Thất bại", JOptionPane.ERROR_MESSAGE);
			}

		});

		revalidate();
		repaint();
	}

	public void setFormTrainManagement(StationScreen stationScreen) {
		this.stationScreen = stationScreen;
	}

	private void closeDialog() {
		JPanel defaultGlassPane = (JPanel) Application.getInstance().getGlassPane();
		defaultGlassPane.removeAll();
		Application.getInstance().setGlassPane(defaultGlassPane);
		defaultGlassPane.setVisible(false);
		dispose();
	}
}