package gui.application.form.other.ticket;

import java.awt.Image;
import java.awt.MediaTracker;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import dao.ServiceDAO;
import entity.*;
import net.miginfocom.swing.MigLayout;

public class ServiceChoosingDialog extends JDialog {

	private TrainJourneyOptionItem trainJourneyOptionItem;
	private List<Seat> chosenSeatList;
	private JPanel container;
	private JPanel chonDichVuContainer;
	private JPanel gioDichVuContainer;
	private JPanel container1;
	private JButton goBackButton;
	private JLabel chonDichVuLabel;
	private JPanel container2;
	private List<String> danhSachLoaiDichVu;
	private ServiceDAO serviceDAO;
	private String currentServiceType;
	private List<Service> currentServiceList;
	private JPanel serviceListContainer;
	private JPanel serviceTypeContainer;
	private JPanel wrapper;
	private JPanel wrapper1;
	private JLabel gioDichVuLabel;
	private JPanel wrapper2;
	private ArrayList<ServiceDetail> chosenServiceDetailList;
	private JPanel wrapper3;
	private JPanel wrapper4;
	private JButton tiepTucButton;
	private ServiceDetail chosenServiceDetail;
	private CheckoutDialog checkoutDialog;
	private PassengerInfoAddingDialog passengerInfoAddingDialog;

	public ServiceChoosingDialog(TrainJourneyOptionItem trainJourneyOptionItem, ArrayList<Ticket> chosenTicketList,
								 Customer customer, Employee employee, PassengerInfoAddingDialog passengerInfoAddingDialog, TicketInfo ticketInfo) {

		this.passengerInfoAddingDialog = passengerInfoAddingDialog;
		serviceDAO = new ServiceDAO();

		danhSachLoaiDichVu = serviceDAO.getAllServiceTypes();

		// state
		currentServiceType = danhSachLoaiDichVu.get(0);
		currentServiceList = serviceDAO.getServiceByType(currentServiceType);
		chosenServiceDetailList = new ArrayList<ServiceDetail>();
		// state

		container = new JPanel(new MigLayout("wrap, fill, insets 16 32", "[grow, fill][400px]", "[fill]"));
		// chọn dịch vụ container ở góc bên tay trái
		chonDichVuContainer = new JPanel(new MigLayout("flowy, fill", "[fill]", "[][]"));

		container1 = new JPanel(new MigLayout("wrap", "[]16[]", ""));
		goBackButton = new JButton(new FlatSVGIcon("gui/icon/svg/gobackicon.svg", 0.15f));
		goBackButton.putClientProperty(FlatClientProperties.STYLE,
				"background: #fafafa; borderWidth: 0; focusWidth: 0");
		goBackButton.addActionListener(e -> {
			this.dispose();
		});
		chonDichVuLabel = new JLabel("CHỌN DỊCH VỤ");
		chonDichVuLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +20");
		container1.add(goBackButton);
		container1.add(chonDichVuLabel);

		/***********************************************************************/
		container2 = new JPanel(new MigLayout("wrap, fill, flowy", "[]", "[][]"));
		reactToServiceTypeChanged();
		/***********************************************************************/

		chonDichVuContainer.add(container1, "dock north");
		chonDichVuContainer.add(container2, "grow");

		// chọn dịch vụ container ở góc bên tay trái
		gioDichVuContainer = new JPanel(new MigLayout("wrap, fill, insets 15", "[]", "[]"));
		wrapper = new JPanel(new MigLayout("wrap, fillx", "[fill]", "[][][][]"));
		wrapper1 = new JPanel(new MigLayout());
		gioDichVuLabel = new JLabel("Giỏ dịch vụ");
		gioDichVuLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +8");
		wrapper1.add(gioDichVuLabel);
		wrapper2 = new JPanel(new MigLayout("wrap, fillx", "[]"));
		wrapper3 = new JPanel(new MigLayout("wrap, fill", "[]push[]"));

		reactToChosenServiceDetailListChanged();

		wrapper4 = new JPanel(new MigLayout("wrap, fill", "[center]"));
		tiepTucButton = new JButton("Tiếp tục");
		tiepTucButton.putClientProperty(FlatClientProperties.STYLE, "background:$primary; foreground:$clr-white");
		wrapper4.add(tiepTucButton);

		wrapper.add(wrapper1, "dock north");
		wrapper.add(new JScrollPane(wrapper2), "dock center");
		wrapper.add(wrapper4, "dock south");
		wrapper.add(wrapper3, "dock south");
		gioDichVuContainer.add(wrapper, "dock center");

		container.add(chonDichVuContainer, "grow");
		container.add(gioDichVuContainer, "grow");
		this.add(container);
		this.setUndecorated(true);
		this.setSize(1400, 770);
		this.setLocationRelativeTo(null);

		JScrollPane scroll = (JScrollPane) wrapper2.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		tiepTucButton.addActionListener(e -> {
			checkoutDialog = new CheckoutDialog(trainJourneyOptionItem, chosenTicketList, chosenServiceDetailList,
					customer, employee, this, ticketInfo);
			checkoutDialog.setModal(true);
			checkoutDialog.setVisible(true);
		});
	}

	private void reactToChosenServiceDetailListChanged() {
		wrapper2.removeAll();
		if (chosenServiceDetailList.isEmpty()) {
			wrapper2.add(new JLabel("Chưa có dịch vụ nào được chọn"));
		}
		for (ServiceDetail chosenServiceDetail : chosenServiceDetailList) {
			JPanel productChosenCard = new JPanel();
			productChosenCard.setLayout(new MigLayout("wrap, fill", "[grow0][grow 0][fill]", "[][]"));
			ImageIcon chosenIcon = new ImageIcon(chosenServiceDetail.getService().getImageSource());
			Image chosenImg = chosenIcon.getImage();
			Image chosenResizedImg = chosenImg.getScaledInstance(80, -1, Image.SCALE_SMOOTH);
			ImageIcon chosenResizedIcon = new ImageIcon(chosenResizedImg);
			JLabel chosenImage = new JLabel(chosenResizedIcon);
			JLabel productChosenName = new JLabel(chosenServiceDetail.getService().getServiceName());
//			SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, chosenServiceDetail.getService().getQuantity(), 1);
			SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);

			JSpinner productChosenQuantitySpinner = new JSpinner(spinnerModel);
			productChosenQuantitySpinner.setValue(chosenServiceDetail.getQuantity());
			JButton removeButton = new JButton();
			removeButton.setIcon(new ImageIcon("images/delete.png"));
			JLabel priceLabel = new JLabel();
			DecimalFormat df = new DecimalFormat("#,###");
			priceLabel
					.setText(df.format(chosenServiceDetail.getService().getPrice() * chosenServiceDetail.getQuantity())
							+ " VND");
			productChosenCard.add(chosenImage, "span 1 2");
			productChosenCard.add(productChosenName);
			productChosenCard.add(removeButton, "gapleft push");
			productChosenCard.add(productChosenQuantitySpinner);
			productChosenCard.add(priceLabel, "gapleft push");
			wrapper2.add(productChosenCard, "aligny top, growx");

			productChosenCard.putClientProperty(FlatClientProperties.STYLE, "background: $clr-white");

			spinnerModel.addChangeListener(e -> {
				chosenServiceDetail.setQuantity((int) (spinnerModel.getValue()));
				reactToChosenServiceDetailListChanged();
			});

			removeButton.addActionListener(e -> {
				chosenServiceDetailList.remove(chosenServiceDetail);
				reactToChosenServiceDetailListChanged();
			});
		}
		wrapper2.repaint();
		wrapper2.revalidate();

		wrapper3.removeAll();
		JLabel tongCongLabel = new JLabel("Tổng cộng");
		double tongCong = 0;
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		for (ServiceDetail chosenServiceDetail : chosenServiceDetailList) {
			tongCong += chosenServiceDetail.getQuantity() * chosenServiceDetail.getService().getPrice();
		}
		JLabel tongCongValue = new JLabel(decimalFormat.format(tongCong) + " VND");
		tongCongValue.putClientProperty(FlatClientProperties.STYLE, "font:bold +8; foreground: $clr-red");
		wrapper3.add(tongCongLabel);
		wrapper3.add(tongCongValue);
		wrapper3.repaint();
		wrapper3.revalidate();

		wrapper.repaint();
		wrapper.revalidate();
	}

	private void reactToServiceTypeChanged() {
		container2.removeAll();

		serviceTypeContainer = new JPanel(new MigLayout("fill"));
		serviceTypeContainer.removeAll();
		for (String serviceType : danhSachLoaiDichVu) {
			JButton serviceTypeButon = new JButton(serviceType);
			if (serviceType.equals(currentServiceType)) {
				serviceTypeButon.putClientProperty(FlatClientProperties.STYLE,
						"background: $primary; foreground: $clr-white; margin: 8, 0, 8, 0");
			}
			serviceTypeContainer.add(serviceTypeButon, "growx, growy, push");
			serviceTypeButon.addActionListener(e -> {
				currentServiceType = serviceType;
				currentServiceList = serviceDAO.getServiceByType(currentServiceType);
				reactToServiceTypeChanged();
			});
		}
		serviceTypeContainer.repaint();
		serviceTypeContainer.revalidate();

		serviceListContainer = new JPanel(
				new MigLayout("fill, wrap", "[200px, fill, grow][200px, fill, grow][200px, fill, grow]"));
		serviceListContainer.removeAll();
		for (Service service : currentServiceList) {
			JButton productCard = new JButton();
			productCard.setLayout(new MigLayout("wrap, fill", "[][]", "[][][]"));
			ImageIcon icon = new ImageIcon(service.getImageSource());
			if (icon.getImageLoadStatus() == MediaTracker.ERRORED) {
				icon = new ImageIcon("images/movie-poster-not-found.jpg");
			}
			Image img = icon.getImage();
			Image resizedImg = img.getScaledInstance(200, -1, Image.SCALE_SMOOTH);
			ImageIcon resizedIcon = new ImageIcon(resizedImg);
			JLabel image = new JLabel(resizedIcon);
			JLabel productName = new JLabel(service.getServiceName());
			productName.putClientProperty(FlatClientProperties.STYLE, "" + "font:$h4.font;");
			JLabel quantity = new JLabel();
//			quantity.setText("Qty:" + service.getQuantity() + "");
			quantity.setText("");
			DecimalFormat decimalFormat = new DecimalFormat("#,###");
			JLabel price = new JLabel(decimalFormat.format(service.getPrice()) + " VND");
			price.putClientProperty(FlatClientProperties.STYLE, "" + "font:$h5.font;" + "foreground:$primary;");
			productCard.add(image, "span 2, al center");
			productCard.add(productName, "span 2, al center");
			productCard.add(quantity);
			productCard.add(price, "gapleft push");
			productCard.putClientProperty(FlatClientProperties.STYLE,
					"default.background:$white;default.background:$black");
			productCard.addActionListener(e -> {

				// add current product into the chosen list
				boolean exist = false;
				for (ServiceDetail chosenServiceDetail : chosenServiceDetailList) {
					if (chosenServiceDetail.getService().equals(service)) {
						exist = true;
						chosenServiceDetail.setQuantity(chosenServiceDetail.getQuantity() + 1);
						break;
					}
				}
				if (!exist) {
					chosenServiceDetailList.add(new ServiceDetail(service, null, 1));
				}
				reactToChosenServiceDetailListChanged();
			});
			serviceListContainer.add(productCard);
		}
		serviceListContainer.repaint();
		serviceListContainer.revalidate();

		container2.add(serviceTypeContainer, "dock north");
		container2.add(new JScrollPane(serviceListContainer), "dock north");

		JScrollPane scroll = (JScrollPane) serviceListContainer.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		container2.repaint();
		container2.revalidate();
	}

	public void dongDayChuyen() {
		this.dispose();
		passengerInfoAddingDialog.dongDayChuyen();
	}

}