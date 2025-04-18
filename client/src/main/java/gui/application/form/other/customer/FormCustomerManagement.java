package gui.application.form.other.customer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import dao.PassengerDAO;
import entity.Passenger;
import net.miginfocom.swing.MigLayout;

public class FormCustomerManagement extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField txtSearch;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JComboBox<String> cboFilter;
	private JPanel container0;
	private JPanel container1;

	private PassengerDAO passengerDAO;
	private CustomerTableModel customerTableModel;
	private JTable customerTable;
	private CustomerUpdateDialog customerUpdateDialog;

	public FormCustomerManagement() {

		passengerDAO = new PassengerDAO();

		setLayout(new BorderLayout());
		container0 = new JPanel();
		container1 = new JPanel();
		txtSearch = new JTextField();
		txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm Kiếm");
		btnUpdate = new JButton("Cập Nhật");
		btnDelete = new JButton("Xóa");
		cboFilter = new JComboBox<String>();
		cboFilter.addItem("Theo Tên");
		cboFilter.addItem("Theo Số Định Danh");
		container1.setLayout(new MigLayout("", "[][]push[][]", ""));
		container1.add(txtSearch, "w 200!");
		container1.add(cboFilter);
		container1.add(btnUpdate);
		container1.add(btnDelete);

		btnUpdate.setIcon(new FlatSVGIcon("gui/icon/svg/edit.svg", 0.35f));
		btnDelete.setIcon(new FlatSVGIcon("gui/icon/svg/delete.svg", 0.35f));
		txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON,
				new FlatSVGIcon("gui/icon/svg/search.svg", 0.35f));

		customerTableModel = new CustomerTableModel();
		customerTable = new JTable(customerTableModel);
		container0.setLayout(new MigLayout("wrap, fill, insets 15", "[fill]", "[grow 0][fill]"));
		container0.add(container1);
		container0.add(new JScrollPane(customerTable));

		if (customerTable.getColumnModel().getColumnCount() > 0) {
			customerTable.getColumnModel().getColumn(1).setPreferredWidth(160);
			customerTable.getColumnModel().getColumn(3).setPreferredWidth(10);
		}

		// Change scroll style
		JScrollPane scroll = (JScrollPane) customerTable.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		customerTable.getTableHeader().putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
		customerTable.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");

		// To Create table alignment
		customerTable.getTableHeader()
				.setDefaultRenderer(getAlignmentCellRender(customerTable.getTableHeader().getDefaultRenderer(), true));
		customerTable.setDefaultRenderer(Object.class,
				getAlignmentCellRender(customerTable.getDefaultRenderer(Object.class), false));

		cboFilter.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);

		txtSearch.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				handleSearch();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				handleSearch();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				handleSearch();
			}
		});

		add(container0);

	}

	@SuppressWarnings("serial")
	private TableCellRenderer getAlignmentCellRender(TableCellRenderer oldRender, boolean header) {
		return new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component com = oldRender.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				if (com instanceof JLabel) {
					JLabel label = (JLabel) com;
					if (column == 4) {
						label.setHorizontalAlignment(SwingConstants.CENTER);
					} else {
						label.setHorizontalAlignment(SwingConstants.LEADING);
					}
					if (header == false) {
						if (isSelected) {
							com.setForeground(table.getSelectionForeground());
						} else {
							com.setForeground(table.getForeground());
						}
					}
				}
				return com;
			}
		};
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnDelete)) {
			int selectedRow = customerTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa.");
			} else {
				int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa hành khách này?",
						"Warning", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					String customerID = (String) customerTable.getValueAt(selectedRow, 0);
					boolean isSuccessful = passengerDAO.removePassengerByID(customerID);
					if (isSuccessful) {
						handleSearch();
					} else {
						JOptionPane.showMessageDialog(this, "Không thể xóa!", "Thất bại", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		if (e.getSource().equals(btnUpdate)) {
			Thread thread = new Thread(() -> {
				int selectedRow = customerTable.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để cập nhật.");
				} else {
					String passengerID = (String) customerTable.getValueAt(selectedRow, 0);
					Passenger passenger = passengerDAO.getPassengerByID(passengerID);
					System.out.println(passenger);
					customerUpdateDialog = new CustomerUpdateDialog(passenger);
					customerUpdateDialog.setFormCustomerManagement(this);
					customerUpdateDialog.setModal(true);
					customerUpdateDialog.setVisible(true);
				}
			});
			thread.start();
		}

	};

	public void handleSearch() {
		String searchBy = (String) cboFilter.getSelectedItem();
		String searchText = txtSearch.getText().trim();
		if (searchBy.equalsIgnoreCase("Theo Tên")) {
			List<Passenger> passengers = passengerDAO.findPassengersByName(searchText);
			customerTableModel.setCustomerList(passengers);
			customerTableModel.fireTableDataChanged();
		} else if (searchBy.equalsIgnoreCase("Theo Số Định Danh")) {
			List<Passenger> passengers = passengerDAO.findPassengersByIdentifier(searchText);
			customerTableModel.setCustomerList(passengers);
			customerTableModel.fireTableDataChanged();
		}
	}

}
