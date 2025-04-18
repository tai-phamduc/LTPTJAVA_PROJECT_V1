package gui.application.form.other.station;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import dao.StationDAO;
import gui.application.Application;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;

public class StationAddingDialog extends JDialog {

	private JLabel title;
	private JPanel container;
	private JPanel container1;
	private JPanel container2;
	private JPanel soHieuTauContainer;
	private JLabel soHieuTauLabel;
	private JTextField soHieuTauTextField;
	private JPanel soToaContainer;
	private JLabel soToaLabel;
	private JTextField soToaTextField;
	private JPanel container3;
	private JPanel container4;
	private JButton themTauButton;

	private List<JComboBox<String>> coachTypeComboBoxes = new ArrayList<>();
	private List<JTextField> capacityTextFields = new ArrayList<>();
	private int n = 0;
	private JButton closeButton;
	private JPanel buttonContainer;
	private JPanel trangThaiContainer;
	private JLabel trangThaiLabel;
	private JComboBox<String> trangThaiCombobox;

	private StationDAO stationDAO;
	private StationScreen stationScreen;

	public StationAddingDialog() {
		stationDAO = new StationDAO();

		this.setUndecorated(true);
		this.setLayout(new BorderLayout());
		container = new JPanel(new MigLayout("wrap", "[fill]", "[][]push[]"));
		initUI();
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
	private void initUI() {
		container.removeAll();

		container1 = new JPanel(new MigLayout());
		title = new JLabel("THÊM GA");
		title.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");
		container1.add(title);

		container2 = new JPanel(new MigLayout("wrap", "[]", "[]"));
		soHieuTauContainer = new JPanel(new MigLayout("wrap", "[]", "[]"));
		soHieuTauLabel = new JLabel("Tên Ga: ");
		soHieuTauTextField = new JTextField(10);
		soHieuTauContainer.add(soHieuTauLabel);
		soHieuTauContainer.add(soHieuTauTextField);
		container2.add(soHieuTauContainer, "alignx center");

		container4 = new JPanel(new MigLayout("wrap", "[grow]"));
		buttonContainer = new JPanel(new MigLayout("wrap", "[]4[]"));
		closeButton = new JButton("Đóng");
		closeButton.putClientProperty(FlatClientProperties.STYLE,
				"arc:5;background: $clr-red;foreground: $clr-white; hoverBackground: $clr-white; hoverForeground: $clr-red");
		closeButton.addActionListener(e -> {
			closeDialog();
		});
		themTauButton = new JButton("Thêm Ga");
		themTauButton.putClientProperty(FlatClientProperties.STYLE,
				"arc:5;background: $primary; foreground: $clr-white;hoverBackground:$clr-white;hoverForeground:$primary");
		buttonContainer.add(closeButton);
		buttonContainer.add(themTauButton);
		container4.add(buttonContainer, "alignx right");

		container.add(container1);
		container.add(container2, "alignx center");
//        container.add(container3);
		container.add(container4, "alignx right");

		themTauButton.addActionListener(e -> {
			String stationName = soHieuTauTextField.getText().trim();
			String stationID = stationDAO.addNewStation(stationName);
			System.out.println(stationID);
			
			Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm ga thành công!");
			stationScreen.search();
	        this.closeDialog();
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