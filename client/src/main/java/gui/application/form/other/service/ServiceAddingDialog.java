package gui.application.form.other.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;

import dao.ServiceDAO;
import entity.Service;
import net.miginfocom.swing.MigLayout;
import raven.crazypanel.CrazyPanel;
import raven.toast.Notifications;
import raven.toast.Notifications.Location;

public class ServiceAddingDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FormFoodManagement formFoodManagement;
	private ServiceDAO serviceDAO;
	private CrazyPanel container;
	private JLabel imageSourceLabel;
	private JButton imageSourceButton;
	private JLabel displaypPosterLabel;
	private JButton saveButton;
	private JLabel errorNameMessageLabel;
	private JLabel errorPriceMessageLabel;
//	private JLabel errorQtyMessageLabel;
	private JLabel errorImageMessageLabel;
	private JLabel serviceNameLabel;
	private JTextField serviceNameTextField;
	private JLabel priceLabel;
	private JTextField priceTextField;
//	private JLabel qtyLabel;
//	private JTextField qtyTextField;
	private FileNameExtensionFilter filter;
	private File selectedFile;
	private FormDrinkManagement formDrinkManagement;

	public ServiceAddingDialog(String type) {
		serviceDAO = new ServiceDAO();
//		setLayout(new BorderLayout());
		initComponents(type);
	}

	private void initComponents(String type) {
		this.setTitle("HỘP THOẠI THÊM DỊCH VỤ");
		container = new CrazyPanel();
		JLabel title = new JLabel("Thêm Dịch Vụ");
		serviceNameLabel = new JLabel("Tên: ");
		serviceNameTextField = new JTextField(15);
		errorNameMessageLabel = new JLabel("");
		priceLabel = new JLabel("Giá: ");
		priceTextField = new JTextField(15);
		errorPriceMessageLabel = new JLabel("");
//		qtyLabel = new JLabel("Quantity: ");
//		qtyTextField = new JTextField(15);
//		errorQtyMessageLabel = new JLabel("");
		imageSourceLabel = new JLabel("Hình Ảnh: ");
		imageSourceButton = new JButton("Chọn Hình");
		displaypPosterLabel = new JLabel();
		errorImageMessageLabel = new JLabel("");
		saveButton = new JButton("Lưu");

		title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 24));
		errorNameMessageLabel.setForeground(Color.RED);
		errorNameMessageLabel.setFont(errorNameMessageLabel.getFont().deriveFont(Font.ITALIC));
		errorPriceMessageLabel.setForeground(Color.RED);
		errorPriceMessageLabel.setFont(errorPriceMessageLabel.getFont().deriveFont(Font.ITALIC));
//		errorQtyMessageLabel.setForeground(Color.RED);
//		errorQtyMessageLabel.setFont(errorQtyMessageLabel.getFont().deriveFont(Font.ITALIC));
		errorImageMessageLabel.setForeground(Color.RED);
		errorImageMessageLabel.setFont(errorImageMessageLabel.getFont().deriveFont(Font.ITALIC));

		container.setLayout(new MigLayout("wrap 2,fillx,insets 8, gap 8", "[grow 0,trail]15[fill]"));

		container.add(title, "wrap, span, al center, gapbottom 8");
		container.add(displaypPosterLabel, "wrap, span, al center, gapbottom 8");
		container.add(imageSourceLabel);
		container.add(imageSourceButton, "grow 0, split 2");
		container.add(new JLabel());
		container.add(new JLabel());
		container.add(errorImageMessageLabel);
		container.add(serviceNameLabel);
		container.add(serviceNameTextField);
		container.add(new JLabel());
		container.add(errorNameMessageLabel);
		container.add(priceLabel);
		container.add(priceTextField);
		container.add(new JLabel());
		container.add(errorPriceMessageLabel);
//		container.add(qtyLabel);
//		container.add(qtyTextField);
//		container.add(new JLabel());
//		container.add(errorQtyMessageLabel);
		container.add(saveButton, "span 2, al trail");

		add(container);
		setSize(700, 700);
		setLocationRelativeTo(null);

		imageSourceButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Choose Image File");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setAcceptAllFileFilterUsed(false);

			filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp");
			fileChooser.addChoosableFileFilter(filter);

			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileChooser.getSelectedFile();
				String path = String.format("images/%s", selectedFile.getName());
				Image icon = new ImageIcon(path).getImage();
				displaypPosterLabel.setIcon(new ImageIcon(icon.getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
			}
		});

		saveButton.addActionListener(e -> {
			// get all values from the input fields
			String name = serviceNameTextField.getText().trim();
			String purchasePrice = priceTextField.getText().trim();
//			String qty = qtyTextField.getText().trim();
			String imagePath = "";
			Icon icon = displaypPosterLabel.getIcon();
			if (icon == null) {
				System.out.println("icon is null");
			} else {
				System.out.println("icon is not null");
			}

			if (name.equals("")) {
				errorNameMessageLabel.setText("Tên không được rỗng");
				serviceNameTextField.requestFocus();
				return;
			}
			errorNameMessageLabel.setText("");

//			if (!name.matches("^[A-Z][a-zA-Z]*(\\s[A-Z][a-zA-Z]*)*$")) {
//				errorNameMessageLabel.setText("Tên phải bắt đầu băng");
//				productNameTextField.requestFocus();
//				return;
//			}
//			errorNameMessageLabel.setText("");

			if (purchasePrice.equals("")) {
				errorPriceMessageLabel.setText("Giá không được để trống");
				priceTextField.requestFocus();
				return;
			}
			errorPriceMessageLabel.setText("");

			if (!purchasePrice.matches("^[0-9]+(\\.[0-9]+)?$")) {
				errorPriceMessageLabel.setText("Giá bán phải là số!");
				priceTextField.requestFocus();
				return;
			}
			errorPriceMessageLabel.setText("");

//			if (qty.equals("")) {
//				errorQtyMessageLabel.setText("Quantity must not be empty");
//				qtyTextField.requestFocus();
//				return;
//			}
//			errorQtyMessageLabel.setText("");
//
//			if (!qty.matches("^\\d+$")) {
//				errorQtyMessageLabel.setText("Quantity must be number");
//				qtyTextField.requestFocus();
//				return;
//			}
//			errorQtyMessageLabel.setText("");

			if (icon == null) {
				errorImageMessageLabel.setText("Hình ảnh không khả dụng");
				imageSourceButton.requestFocus();
				return;
			}
			errorImageMessageLabel.setText("");

			try {
				BufferedImage image = ImageIO.read(selectedFile);
				imagePath = String.format("images/%s", selectedFile.getName());
				File destinationFile = new File(imagePath);
				String extension = FilenameUtils.getExtension(selectedFile.getName()).toLowerCase();
				boolean isValidExtension = Arrays.asList(filter.getExtensions()).contains(extension);
				if (isValidExtension) {
					try {
						ImageIO.write(image, extension, destinationFile);
						System.out.println("File has been written successfully with path: " + imagePath);
					} catch (IOException exc) {
						exc.printStackTrace();
					}
				} else {
					System.out.println("Invalid file extension!");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				System.out.println("Error saving image: " + ex.getMessage());
				return;
			}

			// create a new movie object
			Service newService = new Service(name, Double.parseDouble(purchasePrice), type, imagePath);
			String serviceID = serviceDAO.addNewService(newService);
			newService.setServiceID(serviceID);
			// add a record in the database
			if (serviceID != null) {
				Notifications.getInstance().show(Notifications.Type.INFO, Location.BOTTOM_LEFT,
						"Thêm dịch vụ thành công!");
				System.out.println(newService);
				if (newService.getType().equals("Đồ ăn")) {
					formFoodManagement.getFoods().add(newService);
					formFoodManagement.getFoods().sort(Comparator.comparing(Service::getServiceName));
					formFoodManagement.search();
				}

				if (newService.getType().equals("Đồ uống")) {
					formDrinkManagement.getDrinks().add(newService);
					formDrinkManagement.getDrinks().sort(Comparator.comparing(Service::getServiceName));
					formDrinkManagement.search();
				}
				this.dispose();
			} else {
				Notifications.getInstance().show(Notifications.Type.ERROR, Location.TOP_CENTER,
						"Thêm dịch vụ thất bại!");
			}
		});
	}

	public void setFormProductManagement(FormFoodManagement formFoodManagement) {
		this.formFoodManagement = formFoodManagement;
	}

	public void setFormProductManagement(FormDrinkManagement formDrinkManagement) {
		this.formDrinkManagement = formDrinkManagement;
	}

}