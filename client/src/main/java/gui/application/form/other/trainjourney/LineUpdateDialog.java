package gui.application.form.other.trainjourney;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.FlatClientProperties;

import dao.StationDAO;
import dao.TrainJourneyDAO;
import entity.Line;
import entity.Station;
import entity.StationLine;
import entity.Stop;
import gui.application.Application;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import utils.ServerFetcher;

public class LineUpdateDialog extends JDialog implements ActionListener {

	private JLabel title;
	private JPanel container;
	private JPanel container1;
	private JPanel container2;
	private JPanel container3;
	private JButton updateButton;

	private JButton closeButton;

	private FormLineManagement formLineManagement;
	private JPanel lineNameContainer;
	private JLabel lblLineName;
	private JTextField txtLineName;
	private JPanel numberOfStationContainer;
	private JTextField txtNumberOfStation;
	private JLabel lblNumberOfStation;
	private JPanel header;
	private JPanel container4;
	private JPanel buttonContainer;
	private TrainJourneyDAO trainJourneyDAO;
	private ArrayList<StopRow> stopRowList;
	private List<Stop> stopList;
	private JPanel superContainer;
	private List<Station> stations;
	private ArrayList<JPanel> stationPanel;
	private ArrayList<LineItem> lineItemList;
	private Line line;
	private List<StationLine> stationLineList;

	public LineUpdateDialog(Line line, List<StationLine> stationLineList) {
		this.setLayout(new BorderLayout());

		this.line = line;
		this.stationLineList = stationLineList;

		trainJourneyDAO = new TrainJourneyDAO();

		stations = new StationDAO().getAllStation();

		this.setUndecorated(true);
		this.setLayout(new BorderLayout());
		superContainer = new JPanel(new MigLayout("wrap, fill, insets 24", "[fill]", "[fill]"));
		container = new JPanel(new MigLayout("wrap, fill", "[fill]", "[][][]"));
		superContainer.add(container);
		initUI();
		add(superContainer);
		setSize(1000, 700);
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

		container1 = new JPanel(new MigLayout("", ""));
		title = new JLabel("Thêm đường đi");
		title.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");
		container1.add(title);

		container2 = new JPanel(new MigLayout("wrap", "[]24[]", "[]"));
		// this is a text field component
		lineNameContainer = new JPanel(new MigLayout("wrap", "[]", "[][]"));
		lblLineName = new JLabel("Tên đường đi");
		txtLineName = new JTextField(15);
		txtLineName.setText(line.getLineName());
		lineNameContainer.add(lblLineName);
		lineNameContainer.add(txtLineName);

		// this is a text field component
		numberOfStationContainer = new JPanel(new MigLayout("wrap", "[]", "[][]"));
		lblNumberOfStation = new JLabel("Số ga");
		txtNumberOfStation = new JTextField(10);
		txtNumberOfStation.setText(stationLineList.size() + "");
		txtNumberOfStation.setEditable(false);
		txtNumberOfStation.setEnabled(false);
		numberOfStationContainer.add(lblNumberOfStation);
		numberOfStationContainer.add(txtNumberOfStation);
		//////////////////////////////////////////////

		container2.add(lineNameContainer);
		container2.add(numberOfStationContainer);

		container3 = new JPanel(new MigLayout("wrap", "[fill, grow]", ""));
		
		header = new JPanel(new MigLayout("wrap", "[100px][grow][150px]"));
		header.add(new JLabel("STT"));
		header.add(new JLabel("Ga đi"));
		header.add(new JLabel("Cự ly"));
		container3.add(header);

		lineItemList = new ArrayList<LineItem>();

		for (StationLine stationLine : stationLineList) {
			LineItem lineItem = new LineItem(stationLine.getIndex() - 1, stations);
			lineItem.getIndexColumn().setText(stationLine.getIndex() + "");
			lineItem.getStationCombobox().setSelectedItem(stationLine.getStation());
			lineItem.getDistanceTextField().setText(stationLine.getDistance() + "");
			lineItemList.add(lineItem);
			container3.add(lineItem);
		}

//    	List<Stop> stopList = lineDAO.getAllLineStop(); 
//    	for (Stop stop : stopList) {
//    		StopRow stopRow = new StopRow(stop);  		
//    		container3.add(stopRow);
//    		
//    	}

		container4 = new JPanel(new MigLayout("wrap", "[grow]"));
		buttonContainer = new JPanel(new MigLayout("wrap", "[]4[]"));
		closeButton = new JButton("Đóng");
		closeButton.putClientProperty(FlatClientProperties.STYLE,
				"arc:5;background: $clr-red;foreground: $clr-white; hoverBackground: $clr-white; hoverForeground: $clr-red");
		closeButton.addActionListener(e -> {
			closeDialog();
		});
		updateButton = new JButton("Cập nhật");
		updateButton.putClientProperty(FlatClientProperties.STYLE,
				"arc:5;background: $primary; foreground: $clr-white;hoverBackground:$clr-white;hoverForeground:$primary");
		buttonContainer.add(closeButton);
		buttonContainer.add(updateButton);
		container4.add(buttonContainer, "alignx right");

		container.add(container1, "dock north");
		container.add(container2, "dock north");
		container.add(new JScrollPane(container3), "dock center");
		JScrollPane scroll = (JScrollPane) container3.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");
		container.add(container4, "dock south,");

		updateButton.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(updateButton)) {
			// get all the fields
			String lineName = txtLineName.getText().trim();

			ArrayList<StationLine> stationInfoList = new ArrayList<StationLine>();

			for (LineItem lineItem : lineItemList) {
				int index = Integer.parseInt(lineItem.getIndexColumn().getText().trim());
				Station station = (Station) lineItem.getStationCombobox().getSelectedItem();
				int distance = Integer.parseInt(lineItem.getDistanceTextField().getText().trim());
				System.out.println(station);
				stationInfoList.add(new StationLine(index, station, distance));
			}

			HashMap<String, String> payload = new HashMap<>();
			payload.put("lineID", line.getLineID());
			payload.put("lineName", lineName);
			ServerFetcher.fetch("line", "updateLine", payload);

			payload = new HashMap<>();
			payload.put("lineID", line.getLineID());
			ServerFetcher.fetch("line", "removeAllByLineID", payload);
			
			for (StationLine stationLine : stationInfoList) {
				payload = new HashMap<>();
				payload.put("lineID", line.getLineID());
				payload.put("stationID", stationLine.getStation().getStationID());
				payload.put("index", String.valueOf(stationLine.getIndex()));
				payload.put("distance", String.valueOf(stationLine.getDistance()));
				boolean success = (boolean) ServerFetcher.fetch("line", "addLineStop", payload);
			}

			// update the table
			formLineManagement.handleSearch();
			Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER,
					"Thêm đường đi thành công!");
			this.closeDialog();
		}
	}

	public void setFormLineManagement(FormLineManagement formLineManagement) {
		this.formLineManagement = formLineManagement;
	}

	private void closeDialog() {
		JPanel defaultGlassPane = (JPanel) Application.getInstance().getGlassPane();
		defaultGlassPane.removeAll();
		Application.getInstance().setGlassPane(defaultGlassPane);
		defaultGlassPane.setVisible(false);
		dispose();
	}

}