package gui.application.form.other.train;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import dao.SeatDAO;
import entity.Coach;
import entity.Seat;
import entity.Train;
import gui.application.Application;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import raven.toast.Notifications.Location;
import raven.toast.Notifications.Type;
import utils.ServerFetcher;

public class TrainAddingDialog extends JDialog implements ActionListener {

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
	
	private SeatDAO seatDAO;
	private FormTrainManagement formTrainManagement;

    public TrainAddingDialog() {
    	seatDAO = new SeatDAO();
    	    	
    	this.setUndecorated(true);
        this.setLayout(new BorderLayout());
        container = new JPanel(new MigLayout("wrap", "[fill]", "[][][]push[]"));
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
        title = new JLabel("THÊM TÀU");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");
        container1.add(title);

        container2 = new JPanel(new MigLayout("wrap", "[][][]", "[]"));
        soHieuTauContainer = new JPanel(new MigLayout("wrap", "[]", "[][]"));
        soHieuTauLabel = new JLabel("Số hiệu tàu");
        soHieuTauTextField = new JTextField(10);
        soHieuTauContainer.add(soHieuTauLabel);
        soHieuTauContainer.add(soHieuTauTextField);
        container2.add(soHieuTauContainer);

        soToaContainer = new JPanel(new MigLayout("wrap", "[]", "[][]"));
        soToaLabel = new JLabel("Số toa");
        soToaTextField = new JTextField(10);
        soToaTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        n = Integer.parseInt(soToaTextField.getText());  // Update n
                        reloadTrainCars();  // Reload UI with new n
                    } catch (NumberFormatException ex) {
                        System.err.println("Invalid number: " + ex.getMessage());
                    }
                }
            }
        });
        soToaContainer.add(soToaLabel);
        soToaContainer.add(soToaTextField);
        container2.add(soToaContainer);
        
        trangThaiContainer = new JPanel(new MigLayout("wrap", "[]", "[][]"));
        trangThaiLabel = new JLabel("Trạng thái");
        trangThaiCombobox = new JComboBox<String>();
        trangThaiCombobox.addItem("Đang hoạt động");
        trangThaiCombobox.addItem("Ngừng hoạt động");
        trangThaiContainer.add(trangThaiLabel);
        trangThaiContainer.add(trangThaiCombobox);
        container2.add(trangThaiContainer);

        container3 = new JPanel(new MigLayout("wrap, gapy 16", "[][][]"));
        addTrainCars();

        container4 = new JPanel(new MigLayout("wrap", "[grow]"));
        buttonContainer = new JPanel(new MigLayout("wrap", "[]4[]"));
        closeButton = new JButton("Đóng");
        closeButton.putClientProperty(FlatClientProperties.STYLE, "arc:5;background: $clr-red;foreground: $clr-white; hoverBackground: $clr-white; hoverForeground: $clr-red");
        closeButton.addActionListener(e -> {
        	closeDialog();
        });
        themTauButton = new JButton("Thêm tàu");
        themTauButton.putClientProperty(FlatClientProperties.STYLE, "arc:5;background: $primary; foreground: $clr-white;hoverBackground:$clr-white;hoverForeground:$primary");
        buttonContainer.add(closeButton);
        buttonContainer.add(themTauButton);
        container4.add(buttonContainer, "alignx right");

        container.add(container1);
        container.add(container2);
        container.add(container3);
        container.add(container4);
        
        themTauButton.addActionListener(this);

        revalidate();
        repaint();
    }

    // Adds the train cars dynamically based on the value of n
    private void addTrainCars() {
    	coachTypeComboBoxes.clear();
        capacityTextFields.clear();
        for (int i = 0; i < n; i++) {
            JPanel toaContainer = new JPanel(new MigLayout("wrap", "[fill]"));
            JLabel toaLabel = new JLabel("Toa " + (i + 1));

            JPanel loaiToaContainer = new JPanel(new MigLayout("wrap, insets 0", "[][]"));
            JLabel loaiToaLabel = new JLabel("Loại toa");
            JComboBox<String> loaiToaCombobox = new JComboBox<>();
            loaiToaCombobox.addItem("Ngồi mềm điều hòa");
            loaiToaCombobox.addItem("Giường nằm khoang 4 điều hòa");
            loaiToaCombobox.addItem("Giường nằm khoang 6 điều hòa");
            loaiToaContainer.add(loaiToaLabel);
            loaiToaContainer.add(loaiToaCombobox);
            coachTypeComboBoxes.add(loaiToaCombobox); 

            JPanel soChoContainer = new JPanel(new MigLayout("wrap, insets 0", "[]16[grow, fill]"));
            JLabel soChoLabel = new JLabel("Số chỗ");
            JTextField soChoTextField = new JTextField();
            soChoContainer.add(soChoLabel);
            soChoContainer.add(soChoTextField);
            capacityTextFields.add(soChoTextField);

            toaContainer.add(toaLabel);
            toaContainer.add(loaiToaContainer);
            toaContainer.add(soChoContainer);

            container3.add(toaContainer);
        }
    }
    
    // Reloads the train cars section
    private void reloadTrainCars() {
        container3.removeAll(); 
        addTrainCars();
        revalidate();
        repaint();
        pack();
        setLocationRelativeTo(null);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(themTauButton)) {
			String soHieuTau = soHieuTauTextField.getText().trim();
			String trangThai = (String) trangThaiCombobox.getSelectedItem();
            HashMap<String, String> trainPayload = new HashMap<>();
            trainPayload.put("trainNumber", soHieuTau);
            trainPayload.put("status", trangThai);
            String trainID = (String) ServerFetcher.fetch("train", "addNewTrain", trainPayload);

	        for (int i = 0; i < n; i++) {
	            String coachType = (String) coachTypeComboBoxes.get(i).getSelectedItem();
	            int capacity = Integer.parseInt(capacityTextFields.get(i).getText().trim());

                HashMap<String, String> payload = new HashMap<>();
                payload.put("coachNumber", String.valueOf(i+1));
                payload.put("coachType", coachType);
                payload.put("capacity", String.valueOf(capacity));
                payload.put("trainID", trainID);

                int coachID = (int) ServerFetcher.fetch("coach", "addCoach", payload);

	            for (int j = 0; j < capacity; j++) {
	            	seatDAO.addSeat(new Seat(j+1, new Coach(coachID)));
	            }
	        }

	        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Thêm tàu thành công!");
	        formTrainManagement.search();
	        this.closeDialog();
		}
	}

	public void setFormTrainManagement(FormTrainManagement formTrainManagement) {
		this.formTrainManagement = formTrainManagement;		
	}
	
	private void closeDialog() {
		JPanel defaultGlassPane = (JPanel) Application.getInstance().getGlassPane();
		defaultGlassPane.removeAll();
		Application.getInstance().setGlassPane(defaultGlassPane);
		defaultGlassPane.setVisible(false);
    	dispose();
	}
}