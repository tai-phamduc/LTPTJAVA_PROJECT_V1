package gui.application.form.other.trainjourney;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
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

import entity.Line;
import entity.LineDetails;
import entity.StationLine;
import gui.application.Application;
import net.miginfocom.swing.MigLayout;
import utils.ServerFetcher;

public class FormLineManagement extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField txtSearch;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JPanel container0;
	private JPanel container1;

	private LineTableModel lineTableModel;
	private JTable lineTable;
//	private LineUpdateDialog lineUpdateDialog;
	private JButton btnAdd;
	private LineAddingDialog lineAddingDialog;
	private LineUpdateDialog lineUpdateDialog;

	public FormLineManagement() {

		setLayout(new BorderLayout());
		container0 = new JPanel();
		container1 = new JPanel();
		txtSearch = new JTextField();
		txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm Kiếm");
		btnAdd = new JButton("Thêm Mới");
		btnUpdate = new JButton("Cập Nhật");
		btnDelete = new JButton("Xóa");
		container1.setLayout(new MigLayout("", "[]push[][][]", ""));
		container1.add(txtSearch, "w 200!");
		container1.add(btnAdd);
		container1.add(btnUpdate);
		container1.add(btnDelete);

		btnAdd.setIcon(new FlatSVGIcon("gui/icon/svg/add.svg", 0.35f));
		btnUpdate.setIcon(new FlatSVGIcon("gui/icon/svg/edit.svg", 0.35f));
		btnDelete.setIcon(new FlatSVGIcon("gui/icon/svg/delete.svg", 0.35f));
		txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON,
				new FlatSVGIcon("gui/icon/svg/search.svg", 0.35f));

		lineTableModel = new LineTableModel();
		lineTable = new JTable(lineTableModel);
		container0.setLayout(new MigLayout("wrap, fill, insets 15", "[fill]", "[grow 0][fill]"));
		container0.add(container1);
		container0.add(new JScrollPane(lineTable));

		if (lineTable.getColumnModel().getColumnCount() > 0) {
			lineTable.getColumnModel().getColumn(1).setPreferredWidth(160);
			lineTable.getColumnModel().getColumn(3).setPreferredWidth(10);
		}

		// Change scroll style
		JScrollPane scroll = (JScrollPane) lineTable.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		lineTable.getTableHeader().putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
		lineTable.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");

		// To Create table alignment
		lineTable.getTableHeader()
				.setDefaultRenderer(getAlignmentCellRender(lineTable.getTableHeader().getDefaultRenderer(), true));
		lineTable.setDefaultRenderer(Object.class,
				getAlignmentCellRender(lineTable.getDefaultRenderer(Object.class), false));

		btnAdd.addActionListener(this);
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
			int selectedRow = lineTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa.");
			} else {
				int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa đường đi này?", "Warning",
						JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					String lineID = (String) lineTable.getValueAt(selectedRow, 0);
					HashMap<String, String> payload = new HashMap<>();
					payload.put("lineID", lineID);
					boolean isSuccessful = (boolean) ServerFetcher.fetch("line", "removeLineByID", payload);
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
				int selectedRow = lineTable.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để cập nhật.");
				} else {
					String lineID = (String) lineTable.getValueAt(selectedRow, 0);
					HashMap<String, String> payload = new HashMap<>();
					payload.put("lineID", lineID);
					Line line = (Line) ServerFetcher.fetch("line", "getLineByID", payload);

					payload = new HashMap<>();
					payload.put("lineID", line.getLineID());
					List<StationLine> stationLineList = (List<StationLine>) ServerFetcher.fetch("line", "getLineStopByLineID", payload);
					lineUpdateDialog = new LineUpdateDialog(line, stationLineList);
					lineUpdateDialog.setFormLineManagement(this);
					lineUpdateDialog.setModal(true);
					lineUpdateDialog.setVisible(true);
				}		
			});
			thread.start();
		}
		if (e.getSource().equals(btnAdd)) {
			JPanel glassPane = new BlurGlassPane();
			Application.getInstance().setGlassPane(glassPane);
			glassPane.setVisible(true);
			lineAddingDialog = new LineAddingDialog();
			lineAddingDialog.setFormLineManagement(this);
			lineAddingDialog.setModal(true);
			lineAddingDialog.setVisible(true);
		}

	};

	public void handleSearch() {
		String searchText = txtSearch.getText().trim();
		HashMap<String, String> payload = new HashMap<>();
		payload.put("lineName", searchText);
		List<LineDetails> lineDetailsList = (List<LineDetails>) ServerFetcher.fetch("line", "getAllLineDetailsByName", payload);
		lineTableModel.setLineDetailsList(lineDetailsList);
		lineTableModel.fireTableDataChanged();
	}

}
