package gui.application.form.other.service;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import dao.ServiceDAO;
import entity.Service;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import raven.toast.Notifications.Location;

public class FormFoodManagement extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField searchTextField;
	private JButton addButton;
	private JButton[] deleteButton;
	private JButton[] updateButton;
	private JButton[] addQuantityButton;
	private JPanel[] servicePanelList;
	private JPanel container;
	private List<Service> foods;
	private ServiceDAO foodDAO;
	private JScrollPane scroll;
	private ServiceAddingDialog serviceAddingDialog;
	private ServiceUpdateDialog productUpdateDialog;
//	private ProductAddQtyDialog productAddQtyDialog;

	public FormFoodManagement() {
		foods = new ArrayList<>();
		foodDAO = new ServiceDAO();
		foods = foodDAO.getServiceByType("Đồ ăn");
		foods.sort(Comparator.comparing(Service::getServiceName));
		init();
	}

	private void init() {

		setLayout(new MigLayout("fill, wrap", "[fill]", "[grow 0][fill]"));

		JPanel topPanel = new JPanel(new MigLayout("fill, wrap 2", "[left]push[right]", "[]"));

		add(topPanel);

		// Search text field
		searchTextField = new JTextField(15);
		searchTextField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON,
				new FlatSVGIcon("gui/icon/svg/search.svg", 0.35f));
		searchTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm Kiếm");
		topPanel.add(searchTextField);

		// Add button
		addButton = new JButton("Thêm Dịch Vụ");
		addButton.setIcon(new FlatSVGIcon("gui/icon/svg/add.svg", 0.35f));
		topPanel.add(addButton);

		deleteButton = new JButton[99];
		updateButton = new JButton[99];
		addQuantityButton = new JButton[99];
		servicePanelList = new JPanel[99];

		container = initPanel(foods);
		scroll = new JScrollPane(container);
		// Change scroll style
		JScrollPane scroll = (JScrollPane) container.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		add(scroll, "span 2, grow, al left");

		searchTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				search();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search();

			}

		});

		addButton.addActionListener(e -> {
			Thread thread = new Thread(() -> {
				serviceAddingDialog = new ServiceAddingDialog("Đồ ăn");
				serviceAddingDialog.setFormProductManagement(this);
				serviceAddingDialog.setModal(true);
				serviceAddingDialog.setVisible(true);
			});
			thread.start();
		});
	}

	public void search() {
		List<Service> foodListSearch = new ArrayList<Service>();
		foodListSearch = foodDAO.findFoodByName(searchTextField.getText().trim());
		if (foodListSearch.size() == 0) {
			remove(scroll);
			revalidate();
			repaint();

			container = initPanelEmpty();
			scroll = new JScrollPane(container);
			add(scroll, "span 2, grow");
		} else {
			if (!searchTextField.getText().trim().equalsIgnoreCase("")) {
				remove(scroll);
				revalidate();
				repaint();

				container = initPanel(foodListSearch);
				scroll = new JScrollPane(container);
				add(scroll, "span 2, grow");
			} else {
				remove(scroll);
				revalidate();
				repaint();

				container = initPanel(foods);
				scroll = new JScrollPane(container);
				add(scroll, "span 2, grow, al left");
			}
		}
	}

	public List<Service> getFoods() {
		return foods;
	}

	public void setFoods(List<Service> foods) {
		this.foods = foods;
	}

	private JPanel initPanel(List<Service> foods) {
		// Product list panel
		JPanel container = new JPanel(new MigLayout("wrap 4, insets 20 50 20 50, gap 12"));

		int i = 0;
		for (Service food : foods) {
			final int index = i;
			deleteButton[index] = new JButton("Xóa");
			updateButton[index] = new JButton("Cập Nhật");
			addQuantityButton[index] = new JButton("Add");
			deleteButton[index].setIcon(new FlatSVGIcon("gui/icon/svg/delete.svg", 0.35f));
			updateButton[index].setIcon(new FlatSVGIcon("gui/icon/svg/edit.svg", 0.35f));
			addQuantityButton[index].setIcon(new FlatSVGIcon("gui/icon/svg/add.svg", 0.35f));
			servicePanelList[index] = new JPanel(new MigLayout("wrap 6, fill"));
			servicePanelList[index].setPreferredSize(new Dimension(250, 500));
			servicePanelList[index].putClientProperty(FlatClientProperties.STYLE, "background:$white");
			// productPanelList[index].setBorder(BorderFactory.createLineBorder(Color.BLACK));

			JLabel servicePosterLabel = new JLabel("", SwingConstants.CENTER);
			JLabel serviceNameLabel = new JLabel();
//			JLabel productQuantityLabel = new JLabel("Quantity: " + product.getQuantity());
			JLabel serivcePriceLabel = new JLabel("Giá Bán: " + new DecimalFormat("#,### VND").format(food.getPrice()));

			boolean isValid = false;
			String path = food.getImageSource();
			if (path != null && !path.trim().isEmpty()) {
				File file = new File(path);
				isValid = file.exists();
			}

			if (!isValid) {
				servicePosterLabel.setIcon(null);
			} else {
				Image imageIcon = new ImageIcon(path).getImage();
				servicePosterLabel.setIcon(new ImageIcon(imageIcon.getScaledInstance(200, 300, Image.SCALE_SMOOTH)));
			}
//			productQuantityLabel.setHorizontalAlignment(SwingConstants.LEFT);
			serivcePriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			serviceNameLabel.setText(food.getServiceName());
			serviceNameLabel.putClientProperty(FlatClientProperties.STYLE, "font:$h3.font; border:0,0,16,0");
			// productNameLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			serviceNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			servicePosterLabel.setPreferredSize(new Dimension(250, 350));
			servicePanelList[index].add(servicePosterLabel, "grow, span 6");
			servicePanelList[index].add(serviceNameLabel, "grow, span 6");
//			productPanelList[index].add(productQuantityLabel, "grow, span 3");
			servicePanelList[index].add(serivcePriceLabel, "span 6, center");
			servicePanelList[index].add(deleteButton[index], "span 3");
			servicePanelList[index].add(updateButton[index], "span 3");
//			servicePanelList[index].add(addQuantityButton[index], "span 2");

			container.add(servicePanelList[index], "growx");

			deleteButton[index].addActionListener(e -> {
				int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắc muốn xóa dịch vụ này?", "Cảnh Báo",
						JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					String serviceID = food.getServiceID();
					if (foodDAO.removeServiceByID(serviceID)) {
						Notifications.getInstance().show(Notifications.Type.INFO, Location.TOP_CENTER,
								"Xóa thành công!");
						foods.remove(food);
						search();
					} else {
						Notifications.getInstance().show(Notifications.Type.ERROR, Location.TOP_CENTER,
								"Không thể xóa dịch vụ này!");
					}
				}
			});

			updateButton[index].addActionListener(e -> {
				Thread thread = new Thread(() -> {
					productUpdateDialog = new ServiceUpdateDialog(food);
					productUpdateDialog.setFormProductManagement(this);
					productUpdateDialog.setModal(true);
					productUpdateDialog.setVisible(true);
				});
				thread.start();
			});

//			addQuantityButton[index].addActionListener(e -> {
//				Thread thread = new Thread(() -> {
//					productAddQtyDialog = new ProductAddQtyDialog(product);
//					productAddQtyDialog.setFormProductManagement(this);
//					productAddQtyDialog.setModal(true);
//					productAddQtyDialog.setVisible(true);
//				});
//				thread.start();
//			});
			i++;
		}
		return container;

	}

	private JPanel initPanelEmpty() {
		JPanel container = new JPanel(new MigLayout("wrap 3, insets 20 50 20 50, gap 20"));
		int index = 0;
		servicePanelList[index] = new JPanel(new MigLayout("wrap 6, fill"));
		servicePanelList[index].setPreferredSize(new Dimension(250, 500));
		servicePanelList[index].setBorder(BorderFactory.createEmptyBorder());
		JLabel productPosterLabel = new JLabel("", SwingConstants.CENTER);
		JLabel productNameLabel = new JLabel();
		JLabel productQuantityLabel = new JLabel("");
		JLabel productPriceLabel = new JLabel("");
		productPosterLabel.setIcon(null);
		productQuantityLabel.setHorizontalAlignment(SwingConstants.LEFT);
		productPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		productNameLabel.setText("");
		productNameLabel.setBorder(BorderFactory.createEmptyBorder());
		productNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		productPosterLabel.setPreferredSize(new Dimension(250, 350));
		servicePanelList[index].add(productPosterLabel, "grow, span 6");
		servicePanelList[index].add(productNameLabel, "grow, span 6");
		servicePanelList[index].add(productQuantityLabel, "grow, span 3");
		servicePanelList[index].add(productPriceLabel, "grow, span 3");
		container.add(servicePanelList[index], "growx");
		return container;
	}
}