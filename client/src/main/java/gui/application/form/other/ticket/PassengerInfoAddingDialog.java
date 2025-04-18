package gui.application.form.other.ticket;

import java.awt.BorderLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import entity.*;
import net.miginfocom.swing.MigLayout;

public class PassengerInfoAddingDialog extends JDialog {

	private JPanel container;
	private JPanel container1;
	private JLabel thongTinKhachHangLabel;
	private JPanel container2;
	private JLabel hoVaTenLabel;
	private JTextField hoVaTenTextField;
	private JLabel emailLabel;
	private JTextField emailTextField;
	private JLabel soGiayToLabel;
	private JTextField soGiayToTextField;
	private JLabel sdtLabel;
	private JTextField sdtTextField;
	private JPanel container4;
	private JLabel thongTinGioVeLabel;
	private JPanel container5;
	private JLabel hoTenLabel;
	private JLabel thongTinChoLabel;
	private JLabel giaVeLabel;
	private JLabel giamDoiTuongLabel;
	private JLabel khuyenMaiLabel;
	private JLabel baoHiemLabel;
	private JLabel thanhTienLabel;
	private JLabel xoaLabel;
	private JPanel container6;
	private JPanel container7;
	private JButton xoaTatCaCacVeButton;
	private JPanel nhapMaGiamGiaContaienr;
	private JTextField nhapMaGiamGiaTextField;
	private JButton apDungButton;
	private JPanel tongTienContainer;
	private JLabel tongTienLabel;
	private JLabel tongTienValue;
	private JPanel container8;
	private JButton tiepTucButton;
	private JButton goBackButton;
	private ArrayList<Ticket> chosenTicketList;
	private TrainJourneyOptionItem trainJourneyOptionItem;
	private int renderTime = 1;
	private SeatsChoosingDialog seatsChoosingDialog;
	private ServiceChoosingDialog serviceChoosingDialog;
	private CheckoutDialog checkoutDialog;
	private String hoVaTen;
	private String email;
	private String soGiayTo;
	private String soDienThoai;
	private ArrayList<ComponentTicket> componentTicketList;
	private Employee employee;
	private Customer cus;
	private Customer customer;
	private TicketInfo ticketInfo;

	public PassengerInfoAddingDialog(TrainJourneyOptionItem trainJourneyOptionItem, ArrayList<Ticket> chosenTicketList,
									 SeatsChoosingDialog seatsChoosingDialog, Employee employee, TicketInfo ticket) {
		this.employee = employee;
		if (ticket != null) {
			this.cus = ticket.getTicket().getOrder().getCustomer();
			this.ticketInfo = ticket;
		}

		this.componentTicketList = new ArrayList<ComponentTicket>();
		this.trainJourneyOptionItem = trainJourneyOptionItem;
		this.chosenTicketList = chosenTicketList;
		this.seatsChoosingDialog = seatsChoosingDialog;
		this.setLayout(new BorderLayout());
		container = new JPanel(new MigLayout("wrap, fill, insets 32 32 8 32", "[fill]", "[][][][][][][][]"));

		// state
		hoVaTen = "";
		email = "";
		soGiayTo = "";
		soDienThoai = "";
		for (Ticket chosenTicket : chosenTicketList) {
			if (cus == null) {
				chosenTicket.setPassenger(new Passenger());
			} else {
				chosenTicket.setPassenger(ticket.getTicket().getPassenger());
			}
		}
		// state

		// render component
		render();

		this.add(container);
		this.setUndecorated(true);
		this.setSize(1400, 800);
		this.setLocationRelativeTo(null);
	}

	private void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
		render();
	}

	private void setEmail(String email) {
		this.email = email;
		render();
	}

	private void setSoGiayTo(String soGiayTo) {
		this.soGiayTo = soGiayTo;
		render();
	}

	private void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
		render();
	}

	private void render() {
		container.removeAll();

		container1 = new JPanel(new MigLayout("wrap, insets 0", "[]16[]", "[]"));
		goBackButton = new JButton(new FlatSVGIcon("gui/icon/svg/gobackicon.svg", 0.15f));
		goBackButton.putClientProperty(FlatClientProperties.STYLE,
				"background: #fafafa; borderWidth: 0; focusWidth: 0");
		goBackButton.addActionListener(e -> {
			this.dispose();
		});
		thongTinKhachHangLabel = new JLabel("THÔNG TIN NGƯỜI ĐẶT VÉ");
		thongTinKhachHangLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +20");
		container1.add(goBackButton);
		container1.add(thongTinKhachHangLabel);

		container2 = new JPanel(new MigLayout("wrap, insets 0", "[]8[]24[]8[]24[]8[]24[]8[]", "[]"));
		hoVaTenLabel = new JLabel("Họ và tên");
		hoVaTenLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		hoVaTenTextField = new JTextField(15);
		hoVaTenTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				hoVaTenTextField.requestFocusInWindow();
				hoVaTenTextField.setCaretPosition(hoVaTenTextField.getText().length()); // Move caret to the end
			}
		});
		hoVaTenTextField.setText(hoVaTen);
		if (cus != null) {
			hoVaTenTextField.setText(cus.getFullName());
			hoVaTenTextField.setEnabled(false);
		}
		hoVaTenTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				setHoVaTen(hoVaTenTextField.getText());
				hoVaTenTextField.requestFocusInWindow();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setHoVaTen(hoVaTenTextField.getText());
				hoVaTenTextField.requestFocusInWindow();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setHoVaTen(hoVaTenTextField.getText());
				hoVaTenTextField.requestFocusInWindow();
			}
		});
		emailLabel = new JLabel("Email");
		emailLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		emailTextField = new JTextField(20);
		emailTextField.setText(email);
		if (cus != null) {
			emailTextField.setText(cus.getEmail());
			emailTextField.setEnabled(false);
		}
		emailTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				emailTextField.requestFocusInWindow();
				emailTextField.setCaretPosition(emailTextField.getText().length()); // Move caret to the end
			}
		});
		emailTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				setEmail(emailTextField.getText());
				emailTextField.requestFocusInWindow();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setEmail(emailTextField.getText());
				emailTextField.requestFocusInWindow();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setEmail(emailTextField.getText());
				emailTextField.requestFocusInWindow();
			}
		});
		soGiayToLabel = new JLabel("Số giấy tờ");
		soGiayToLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		soGiayToTextField = new JTextField(10);
		soGiayToTextField.setText(soGiayTo);
		if (cus != null) {
			soGiayToTextField.setText(cus.getIdentificationNumber());
			soGiayToTextField.setEnabled(false);
		}
		soGiayToTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				soGiayToTextField.requestFocusInWindow();
				soGiayToTextField.setCaretPosition(soGiayToTextField.getText().length()); // Move caret to the end
			}
		});
		soGiayToTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				setSoGiayTo(soGiayToTextField.getText());
				soGiayToTextField.requestFocusInWindow();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setSoGiayTo(soGiayToTextField.getText());
				soGiayToTextField.requestFocusInWindow();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setSoGiayTo(soGiayToTextField.getText());
				soGiayToTextField.requestFocusInWindow();
			}
		});
		sdtLabel = new JLabel("Số điện thoại");
		sdtLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		sdtTextField = new JTextField(10);
		sdtTextField.setText(soDienThoai);
		if (cus != null) {
			sdtTextField.setText(cus.getPhoneNumber());
			sdtTextField.setEnabled(false);
		}
		sdtTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				sdtTextField.requestFocusInWindow();
				sdtTextField.setCaretPosition(sdtTextField.getText().length()); // Move caret to the end
			}
		});
		sdtTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				setSoDienThoai(sdtTextField.getText());
				sdtTextField.requestFocusInWindow();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setSoDienThoai(sdtTextField.getText());
				sdtTextField.requestFocusInWindow();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setSoDienThoai(sdtTextField.getText());
				sdtTextField.requestFocusInWindow();
			}
		});
		container2.add(hoVaTenLabel);
		container2.add(hoVaTenTextField);
		container2.add(emailLabel);
		container2.add(emailTextField);
		container2.add(soGiayToLabel);
		container2.add(soGiayToTextField);
		container2.add(sdtLabel);
		container2.add(sdtTextField);
		container2.add(soGiayToLabel);
		container2.add(soGiayToTextField);
		container2.add(sdtLabel);
		container2.add(sdtTextField);

		container4 = new JPanel(new MigLayout("wrap, fill, insets 16 0 0 0 0", "[]", "[][]"));
		thongTinGioVeLabel = new JLabel("THÔNG TIN GIỎ VÉ");
		thongTinGioVeLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +8");
		container4.add(thongTinGioVeLabel);

		container5 = new JPanel(new MigLayout("wrap, fill, insets 0",
				"[fill, grow, center][200px, center, shrink][125px, center, shrink][125px, center, shrink][125px, center, shrink][125px, center, shrink][200px, center, shrink][50px, center, shrink]",
				"[]"));

		hoTenLabel = new JLabel("Họ tên");
		hoTenLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		thongTinChoLabel = new JLabel("Thông tin chỗ");
		thongTinChoLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		giaVeLabel = new JLabel("Giá vé");
		giaVeLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		giamDoiTuongLabel = new JLabel("Giảm đối tượng");
		giamDoiTuongLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		khuyenMaiLabel = new JLabel("Khuyến mãi");
		khuyenMaiLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		baoHiemLabel = new JLabel("Bảo hiểm");
		baoHiemLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		thanhTienLabel = new JLabel("Thành tiền (VND)");
		thanhTienLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		xoaLabel = new JLabel("Xóa");
		xoaLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +0");
		container5.add(hoTenLabel);
		container5.add(thongTinChoLabel);
		container5.add(giaVeLabel);
		container5.add(giamDoiTuongLabel);
		container5.add(khuyenMaiLabel);
		container5.add(baoHiemLabel);
		container5.add(thanhTienLabel);
		container5.add(thanhTienLabel);
		container5.add(xoaLabel);

		container6 = new JPanel(new MigLayout("wrap, fillx, insets 0", "[fill]"));

		if (renderTime == 1) {
			for (Ticket chosenTicket : chosenTicketList) {
				ComponentTicket componentTicket = new ComponentTicket(chosenTicket, trainJourneyOptionItem, this, cus);
				componentTicketList.add(componentTicket);
				container6.add(componentTicket, "aligny top");
			}
		} else {
			for (ComponentTicket componentTicket : componentTicketList) {
				container6.add(componentTicket, "aligny top");
			}
		}

		container7 = new JPanel(new MigLayout("wrap, fill", "[]push[]push[]", "[]"));
		reactToChosenTicketChanged();

		container8 = new JPanel(new MigLayout("wrap, fill", "[]", "[]"));
		tiepTucButton = new JButton("Tiếp tục");
		tiepTucButton.putClientProperty(FlatClientProperties.STYLE, "background:$primary; foreground:$clr-white");
		container8.add(tiepTucButton, "al right");

		tiepTucButton.addActionListener(e -> {
			if (cus == null) {
				customer = new Customer(hoVaTen, soDienThoai, email, soGiayTo);
			} else {
				customer = cus;
			}
			serviceChoosingDialog = new ServiceChoosingDialog(trainJourneyOptionItem, chosenTicketList, customer,
					employee, this, ticketInfo);
			serviceChoosingDialog.setModal(true);
			serviceChoosingDialog.setVisible(true);
		});

		container.add(container1, "gapbottom 8");
		container.add(container2, "gapbottom 8");
		container.add(container4, "gapbottom 4");
		container.add(container5, "gapbottom 0");
		container.add(new JScrollPane(container6), "dock center");
		container.add(container7, "gaptop 0");
		container.add(container8, "gaptop 0");

		JScrollPane scroll = (JScrollPane) container6.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		renderTime++;

		container.repaint();
		container.revalidate();
	}

	public void reactToChosenTicketChanged() {
		container7.removeAll();

		xoaTatCaCacVeButton = new JButton("Xóa tất cả các vé");
		xoaTatCaCacVeButton.setIcon(new FlatSVGIcon("gui/icon/svg/removeicon.svg", 0.35f));
		xoaTatCaCacVeButton.addActionListener(e -> {
			chosenTicketList.removeAll(chosenTicketList);
			this.dispose();
			seatsChoosingDialog.reactToSelectedCoachChanged();
			seatsChoosingDialog.reactToChosenListChanged();
		});
		nhapMaGiamGiaContaienr = new JPanel(new MigLayout("wrap, fill, gap 0", "[][]", "[]"));
		nhapMaGiamGiaTextField = new JTextField(20);
		nhapMaGiamGiaTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã giảm giá tại đây");
		apDungButton = new JButton("Áp dụng");
		nhapMaGiamGiaContaienr.add(nhapMaGiamGiaTextField);
		nhapMaGiamGiaContaienr.add(apDungButton);
		tongTienContainer = new JPanel(new MigLayout("wrap, fill, gap 16", "[][]", "[]"));
		tongTienLabel = new JLabel("Tổng tiền");
		tongTienLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +0");

		int tongTien = 0;
		for (ComponentTicket componentTicket : componentTicketList) {
			String numberWithComma = componentTicket.getColumn7().getText().trim();
			tongTien += Integer.parseInt(numberWithComma.replace(",", ""));
		}

		DecimalFormat decimalFormat = new DecimalFormat("#,###");

		tongTienValue = new JLabel(decimalFormat.format(tongTien));
		tongTienValue.putClientProperty(FlatClientProperties.STYLE, "font:bold +8; foreground: $clr-red");
		tongTienContainer.add(tongTienLabel);
		tongTienContainer.add(tongTienValue);
		container7.add(xoaTatCaCacVeButton);
		container7.add(nhapMaGiamGiaContaienr);
		container7.add(tongTienContainer);

		container7.repaint();
		container7.revalidate();
	}

	public void removeTicketFromChosenTicketList(Ticket chosenTicket, ComponentTicket componentTicket) {
		chosenTicketList.remove(chosenTicket);
		if (chosenTicketList.isEmpty()) {
			this.dispose();
		}
		seatsChoosingDialog.reactToChosenListChanged();
		seatsChoosingDialog.reactToSelectedCoachChanged();
		componentTicketList.remove(componentTicket);
		render();
	}

	public void dongDayChuyen() {
		this.dispose();
		seatsChoosingDialog.dongDayChuyen();
	}

}