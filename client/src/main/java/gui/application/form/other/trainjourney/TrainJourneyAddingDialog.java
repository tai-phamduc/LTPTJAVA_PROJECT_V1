package gui.application.form.other.trainjourney;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

import com.formdev.flatlaf.FlatClientProperties;

import dao.SeatDAO;
import dao.TrainJourneyDAO;
import entity.Line;
import entity.Stop;
import entity.Train;
import entity.TrainJourney;
import gui.application.Application;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import utils.ServerFetcher;

public class TrainJourneyAddingDialog extends JDialog implements ActionListener {

    private JLabel title;
    private JPanel container;
    private JPanel container1;
    private JPanel container2;
    private JPanel container3;
    private JButton themTauButton;

	private JButton closeButton;
	
	private SeatDAO seatDAO;
	private FormTrainJourneyManagement formTrainJourneyManagement;
	private JPanel tenChuyenTauContainer;
	private JLabel tenChuyenTauLabel;
	private JTextField tenChuyenTauTextField;
	private JPanel tauContainer;
	private JLabel tauLabel;
	private JComboBox<Train> tauCombobox;
	private JPanel duongDiContainer;
	private JLabel duongDiLabel;
	private JComboBox<Line> duongDiCombobox;
	private JPanel giaGocContainer;
	private JTextField giaGocTextField;
	private JLabel giaGocLabel;
	private JPanel header;
	private JPanel container4;
	private JPanel buttonContainer;
	private TrainJourneyDAO trainJourneyDAO;
	private ArrayList<StopRow> stopRowList;
	private List<Stop> stopList;
	private JPanel superContainer;
	private HashMap<String, String> payload;

	public TrainJourneyAddingDialog() {
    	this.setLayout(new BorderLayout());
    	seatDAO = new SeatDAO();
    	trainJourneyDAO = new TrainJourneyDAO();
    	    	
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
    	title = new JLabel("Thêm chuyến tàu");
    	title.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");
    	container1.add(title);
    	
    	container2 = new JPanel(new MigLayout("wrap", "[][][][]", "[]"));
    	// this is a text field component
    	tenChuyenTauContainer = new JPanel(new MigLayout("wrap", "[]", "[][]"));
    	tenChuyenTauLabel = new JLabel("Tên chuyến tàu");
    	tenChuyenTauTextField = new JTextField(15);
    	tenChuyenTauContainer.add(tenChuyenTauLabel);
    	tenChuyenTauContainer.add(tenChuyenTauTextField);
    	//////////////////////////////////////////////
    	// this is a text field component
    	tauContainer = new JPanel(new MigLayout("wrap", "[]", "[][]"));
    	tauLabel = new JLabel("Tàu");
    	tauCombobox = new JComboBox<Train>();

		payload = new HashMap<>();
		List<Train> trainList = (List<Train>) ServerFetcher.fetch("train", "getAllTrain", payload);
    	trainList.forEach(train -> {
    		tauCombobox.addItem(train);
    	});
    	tauContainer.add(tauLabel);
    	tauContainer.add(tauCombobox);
    	//////////////////////////////////////////////
    	
    	// this is a text field component
    	duongDiContainer = new JPanel(new MigLayout("wrap", "[]", "[][]"));
    	duongDiLabel = new JLabel("Đường đi");
    	duongDiCombobox = new JComboBox<Line>();
		payload = new HashMap<>();
		Object response = ServerFetcher.fetch("line", "getAllLine", payload);
		List<Line> lineList = (List<Line>) response;
    	lineList.forEach(line -> {
    		duongDiCombobox.addItem(line);
    	});
    	duongDiContainer.add(duongDiLabel);
    	duongDiContainer.add(duongDiCombobox);
    	//////////////////////////////////////////////	
    	
    	// this is a text field component
    	giaGocContainer = new JPanel(new MigLayout("wrap", "[]", "[][]"));
    	giaGocLabel = new JLabel("Giá gốc");
    	giaGocTextField = new JTextField(10);
    	giaGocContainer.add(giaGocLabel);
    	giaGocContainer.add(giaGocTextField);
    	//////////////////////////////////////////////
    	
    	container2.add(tenChuyenTauContainer);
    	container2.add(tauContainer);
    	container2.add(duongDiContainer);
    	container2.add(giaGocContainer);
    	
    	container3 = new JPanel(new MigLayout("wrap", "[fill, grow]", ""));
   	
//    	List<Stop> stopList = lineDAO.getAllLineStop(); 
//    	for (Stop stop : stopList) {
//    		StopRow stopRow = new StopRow(stop);  		
//    		container3.add(stopRow);
//    		
//    	}
    	
        container4 = new JPanel(new MigLayout("wrap", "[grow]"));
        buttonContainer = new JPanel(new MigLayout("wrap", "[]4[]"));
        closeButton = new JButton("Đóng");
        closeButton.putClientProperty(FlatClientProperties.STYLE, "arc:5;background: $clr-red;foreground: $clr-white; hoverBackground: $clr-white; hoverForeground: $clr-red");
        closeButton.addActionListener(e -> {
        	closeDialog();
        });
        themTauButton = new JButton("Thêm");
        themTauButton.putClientProperty(FlatClientProperties.STYLE, "arc:5;background: $primary; foreground: $clr-white;hoverBackground:$clr-white;hoverForeground:$primary");
        buttonContainer.add(closeButton);
        buttonContainer.add(themTauButton);
        container4.add(buttonContainer, "alignx right");
    	
    	container.add(container1, "dock north");
    	container.add(container2, "dock north");
    	container.add(new JScrollPane(container3), "dock center");
		JScrollPane scroll = (JScrollPane) container3.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");
    	container.add(container4, "dock south,");
    	
    	duongDiCombobox.addActionListener(e -> reloadStops());  
    	themTauButton.addActionListener(this);
    	
    }
   

    private void reloadStops() {
        container3.removeAll();
        
    	header = new JPanel(new MigLayout("wrap", "[grow][100px][grow][150px][100px][150px]"));
    	header.add(new JLabel("STT"));
    	header.add(new JLabel("Ga đi"));
    	header.add(new JLabel("Cự ly"));
    	header.add(new JLabel("Ngày đi"));
    	header.add(new JLabel("Giờ đến"));
    	header.add(new JLabel("Giờ đi"));
    	container3.add(header);
    	
        Line selectedLine = (Line) duongDiCombobox.getSelectedItem();
        stopRowList = new ArrayList<StopRow>();
        if (selectedLine != null) {
			payload = new HashMap<>();
			payload.put("lineID", selectedLine.getLineID());
			List<Stop> stopList = (List<Stop>) ServerFetcher.fetch("line", "getLineStops", payload);

            for (Stop stop : stopList) {
                StopRow stopRow = new StopRow(stop);
                stopRowList.add(stopRow);
                container3.add(stopRow);
            }
        }

        container3.revalidate();
        container3.repaint();
        container.revalidate();
        container.repaint();
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(themTauButton)) {
			// get all the fields 
			String tenChuyenTau = tenChuyenTauTextField.getText().trim();
			Train tau = (Train) tauCombobox.getSelectedItem();
			Line duongDi = (Line) duongDiCombobox.getSelectedItem();
			double giaGoc = Double.parseDouble(giaGocTextField.getText().trim());
			
			String trainJourneyID = trainJourneyDAO.addTrainJourney(new TrainJourney(tenChuyenTau, tau, duongDi, giaGoc));
			
			// get all the stops and put them in the array
			
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			
			for (int i = 0; i < stopRowList.size(); i++) {
				stopList.get(i).setTrainJourney(new TrainJourney(trainJourneyID));
				LocalDate departureDate = LocalDate.parse(stopRowList.get(i).getDepartureDateTexField().getText().trim(), dateFormatter);
				LocalTime arrivalTime = LocalTime.parse(stopRowList.get(i).getArrivalTimeTextField().getText().trim(), timeFormatter);
				LocalTime departureTime = LocalTime.parse(stopRowList.get(i).getDepartureTimeTextField().getText().trim(), timeFormatter);
				stopList.get(i).setDepartureDate(departureDate);
				stopList.get(i).setArrivalTime(arrivalTime);
				stopList.get(i).setDepartureTime(departureTime);
			}
			// add all the stops of the train journey
			trainJourneyDAO.addStops(stopList);
			
			// update the table
			formTrainJourneyManagement.search();
			Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm chuyến tàu thành công!");
	        this.closeDialog();
		}
	}

	public void setFormTrainManagement(FormTrainJourneyManagement formTrainJourneyManagement) {
		this.formTrainJourneyManagement = formTrainJourneyManagement;		
	}
	
	private void closeDialog() {
		JPanel defaultGlassPane = (JPanel) Application.getInstance().getGlassPane();
		defaultGlassPane.removeAll();
		Application.getInstance().setGlassPane(defaultGlassPane);
		defaultGlassPane.setVisible(false);
    	dispose();
	}
}