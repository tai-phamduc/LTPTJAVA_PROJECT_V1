package gui.application.form.other.employee;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

import dao.EmployeeDAO;
import entity.Employee;
import net.miginfocom.swing.MigLayout;
import raven.toast.Notifications;
import raven.toast.Notifications.Location;
import raven.toast.Notifications.Type;

public class FormEmployeeManagement extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField searchTextField;
	private JButton updateButton;
	private JButton deleteButton;
	private JPanel container0;
	private JPanel container1;

	private EmployeeDAO employeeDAO;
	private JButton addNewButton;
	private EmployeeTableModel employeeTableModel;
	private JTable employeeTable;
	private EmployeeAddingDialog employeeAddingDialog;
	private EmployeeUpdateDialog employeeUpdateDialog;
	private Employee currentEmployee;

	public FormEmployeeManagement(Employee currentEmployee) {

		this.currentEmployee = currentEmployee;

		employeeDAO = new EmployeeDAO();

		setLayout(new BorderLayout());
		container0 = new JPanel();
		container1 = new JPanel();
		searchTextField = new JTextField();
		searchTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm Kiếm");
		addNewButton = new JButton("Thêm");
		updateButton = new JButton("Chỉnh Sửa");
		deleteButton = new JButton("Xóa");
		container1.setLayout(new MigLayout("", "[]push[][][]", ""));
		container1.add(searchTextField, "w 200!");
		container1.add(addNewButton);
		container1.add(updateButton);
		container1.add(deleteButton);

		addNewButton.setIcon(new FlatSVGIcon("gui/icon/svg/add.svg", 0.35f));
		updateButton.setIcon(new FlatSVGIcon("gui/icon/svg/edit.svg", 0.35f));
		deleteButton.setIcon(new FlatSVGIcon("gui/icon/svg/delete.svg", 0.35f));
		searchTextField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON,
				new FlatSVGIcon("gui/icon/svg/search.svg", 0.35f));

		employeeTableModel = new EmployeeTableModel();
		employeeTable = new JTable(employeeTableModel);
		container0.setLayout(new MigLayout("wrap, fill, insets 15", "[fill]", "[grow 0][fill]"));
		container0.add(container1);
		container0.add(new JScrollPane(employeeTable));

		if (employeeTable.getColumnModel().getColumnCount() > 0) {
			employeeTable.getColumnModel().getColumn(1).setPreferredWidth(150);
			employeeTable.getColumnModel().getColumn(4).setPreferredWidth(250);
		}

		// Change scroll style
		JScrollPane scroll = (JScrollPane) employeeTable.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		employeeTable.getTableHeader().putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
		employeeTable.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");

		// To Create table alignment
		employeeTable.getTableHeader()
				.setDefaultRenderer(getAlignmentCellRender(employeeTable.getTableHeader().getDefaultRenderer(), true));
		employeeTable.setDefaultRenderer(Object.class,
				getAlignmentCellRender(employeeTable.getDefaultRenderer(Object.class), false));

		searchTextField.getDocument().addDocumentListener(new DocumentListener() {

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

		// event handler
		addNewButton.addActionListener(this);
		updateButton.addActionListener(this);
		deleteButton.addActionListener(this);

		add(container0);

		this.setVisible(true);

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
					label.setHorizontalAlignment(SwingConstants.LEADING);
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
		if (e.getSource().equals(addNewButton)) {
			Thread thread = new Thread(() -> {
				employeeAddingDialog = new EmployeeAddingDialog();
				employeeAddingDialog.setFormStaffManagement(this);
				employeeAddingDialog.setModal(true);
				employeeAddingDialog.setVisible(true);
			});
			thread.start();
		}
		if (e.getSource().equals(updateButton)) {
			Thread thread = new Thread(() -> {
				int selectedRow = employeeTable.getSelectedRow();
				if (selectedRow == -1) {
					Notifications.getInstance().show(Type.WARNING, Location.TOP_CENTER,
							"Vui lòng chọn một dòng để cập nhật!");
				} else {
					String employeeID = (String) employeeTable.getValueAt(selectedRow, 0);
					Employee employee = employeeDAO.getEmployeeByID(employeeID);
					employeeUpdateDialog = new EmployeeUpdateDialog(employee);
					employeeUpdateDialog.setFormStaffManagement(this);
					employeeUpdateDialog.setModal(true);
					employeeUpdateDialog.setVisible(true);
				}
			});
			thread.start();
		}
		if (e.getSource().equals(deleteButton)) {
			int selectedRow = employeeTable.getSelectedRow();
			if (selectedRow == -1) {
				Notifications.getInstance().show(Type.WARNING, Location.TOP_CENTER, "Vui lòng chọn một dòng để xóa!");
			} else {

				String employeeID = (String) employeeTable.getValueAt(selectedRow, 0);
				if (currentEmployee.getEmployeeID().equals(employeeID)) {
					Notifications.getInstance().show(Type.ERROR, Location.TOP_CENTER,
							"Không thể xóa chính mình!");
					return;
				}

				int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên này?",
						"Warning", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					boolean isSuccessful = employeeDAO.removeEmployeeByID(employeeID);
					if (isSuccessful) {
						Notifications.getInstance().show(Type.SUCCESS, Location.TOP_CENTER,
								"Xóa nhân viên thành công!");
						handleSearch();
					} else {
						Notifications.getInstance().show(Type.ERROR, Location.TOP_CENTER,
								"Không thể xóa nhân viên này!");
					}
				} else {
					return;
				}
			}
		}
	}

	public void handleSearch() {
		String nameToFind = searchTextField.getText().trim();
		List<Employee> employeeList = employeeDAO.findEmployeByName(nameToFind);
		employeeTableModel.setEmployeeList(employeeList);
		employeeTableModel.fireTableDataChanged();
	}

}