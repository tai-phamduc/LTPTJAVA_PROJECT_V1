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

import javax.swing.AbstractButton;
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

public class TrainUpdateDialog extends JDialog implements ActionListener {

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

    private List<JComboBox<String>> coachTypeComboBoxes;
    private List<JTextField> capacityTextFields;
    private int n = 0;
	private JButton closeButton;
	private JPanel buttonContainer;
	private JPanel trangThaiContainer;
	private JLabel trangThaiLabel;
	private JComboBox<String> trangThaiCombobox;
	
	private SeatDAO seatDAO;
	private FormTrainManagement formTrainManagement;
	
	private Train train;
	private int soToa;
	private JButton capNhatTauButton;
	private List<Coach> coachList;
	
	private int renderTime = 0;

    public TrainUpdateDialog(Train train) {
    	this.train = train;
    	
    	seatDAO = new SeatDAO();

        coachList = new ArrayList<>();
        try {
            HashMap<String, String> payload = new HashMap<>();
            payload.put("trainID", train.getTrainID());
            coachList = (List<Coach>) ServerFetcher.fetch("coach", "getCoaches", payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	coachTypeComboBoxes = new ArrayList<JComboBox<String>>();
    	capacityTextFields = new ArrayList<JTextField>();	
    	    	
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
        title = new JLabel("CHỈNH SỬA THÔNG TIN TÀU");
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +16");
        container1.add(title);

        container2 = new JPanel(new MigLayout("wrap", "[][][]", "[]"));
        soHieuTauContainer = new JPanel(new MigLayout("wrap", "[]", "[][]"));
        soHieuTauLabel = new JLabel("Số hiệu tàu");
        soHieuTauTextField = new JTextField(10);
        soHieuTauTextField.setText(train.getTrainNumber());
        soHieuTauContainer.add(soHieuTauLabel);
        soHieuTauContainer.add(soHieuTauTextField);
        container2.add(soHieuTauContainer);

        soToaContainer = new JPanel(new MigLayout("wrap", "[]", "[][]"));
        soToaLabel = new JLabel("Số toa");
        soToaTextField = new JTextField(10);
        HashMap<String, String> payload = new HashMap<>();
        payload.put("trainID", train.getTrainID());
        soToa = (int) ServerFetcher.fetch("train", "getNumberOfCoaches", payload);
        soToaTextField.setText(soToa + "");
        n = soToa;
        System.out.println("So Toa: " + soToa);
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
        trangThaiCombobox.setSelectedItem(train.getStatus());
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
        capNhatTauButton = new JButton("Cập nhật");
        capNhatTauButton.putClientProperty(FlatClientProperties.STYLE, "arc:5;background: $primary; foreground: $clr-white;hoverBackground:$clr-white;hoverForeground:$primary");
        buttonContainer.add(closeButton);
        buttonContainer.add(capNhatTauButton);
        container4.add(buttonContainer, "alignx right");

        container.add(container1);
        container.add(container2);
        container.add(container3);
        container.add(container4);
        
        capNhatTauButton.addActionListener(this);

        revalidate();
        repaint();
    }

    // Adds the train cars dynamically based on the value of n
    private void addTrainCars() {
    	coachTypeComboBoxes.clear();
        capacityTextFields.clear();
        System.out.println(renderTime);
        System.out.println(n);
        System.out.println(coachList.size());
        for (int i = 0; i < n; i++) {
            JPanel toaContainer = new JPanel(new MigLayout("wrap", "[fill]"));
            JLabel toaLabel = new JLabel("Toa " + (i + 1));

            JPanel loaiToaContainer = new JPanel(new MigLayout("wrap, insets 0", "[][]"));
            JLabel loaiToaLabel = new JLabel("Loại toa");
            JComboBox<String> loaiToaCombobox = new JComboBox<>();
            loaiToaCombobox.addItem("Ngồi mềm điều hòa");
            loaiToaCombobox.addItem("Giường nằm khoang 4 điều hòa");
            loaiToaCombobox.addItem("Giường nằm khoang 6 điều hòa");
            if (renderTime == 0) {
            	loaiToaCombobox.setSelectedItem(coachList.get(i).getCoachType());
            }
            
            loaiToaContainer.add(loaiToaLabel);
            loaiToaContainer.add(loaiToaCombobox);
            coachTypeComboBoxes.add(loaiToaCombobox); 

            JPanel soChoContainer = new JPanel(new MigLayout("wrap, insets 0", "[]16[grow, fill]"));
            JLabel soChoLabel = new JLabel("Số chỗ");
            JTextField soChoTextField = new JTextField();
            if (renderTime == 0) {
            	soChoTextField.setText(coachList.get(i).getCapacity() + "");
            }
            
            soChoContainer.add(soChoLabel);
            soChoContainer.add(soChoTextField);
            capacityTextFields.add(soChoTextField);

            toaContainer.add(toaLabel);
            toaContainer.add(loaiToaContainer);
            toaContainer.add(soChoContainer);

            container3.add(toaContainer);
        }
    	renderTime++;
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
		if (e.getSource().equals(capNhatTauButton)) {
			String soHieuTau = soHieuTauTextField.getText().trim();
			String trangThai = (String) trangThaiCombobox.getSelectedItem();
            HashMap<String, String> payload = new HashMap<>();
            payload.put("trainID", train.getTrainID());
            payload.put("trainNumber", soHieuTau);
            payload.put("status", trangThai);
            boolean status = (boolean) ServerFetcher.fetch("train", "updateTrain", payload);

            payload = new HashMap<>();
            payload.put("trainID", train.getTrainID());
            ServerFetcher.fetch("coach", "removeCoaches", payload);

            if (status) {
                System.out.println("Update successful!");
            } else {
                System.out.println("Update failed!");
            }

            for (int i = 0; i < n; i++) {
	            String coachType = (String) coachTypeComboBoxes.get(i).getSelectedItem();
	            int capacity = Integer.parseInt(capacityTextFields.get(i).getText().trim());

                payload = new HashMap<>();
                payload.put("coachNumber", String.valueOf(i + 1));
                payload.put("coachType", coachType);
                payload.put("capacity", String.valueOf(capacity));
                payload.put("trainID", train.getTrainID());

                int coachID = (int) ServerFetcher.fetch("coach", "addCoach", payload);

	            for (int j = 0; j < capacity; j++) {
	            	seatDAO.addSeat(new Seat(j+1, new Coach(coachID)));
	            }
	        }
	        
	        // close the dialog
	        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Cập nhật thành công!");
	        formTrainManagement.search();
	        this.closeDialog();
	        // notify added successfully
	        // reload the whole table with new added train
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