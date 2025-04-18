package gui.application.form.other.station;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import dao.StationDAO;
import entity.Station;
import gui.application.Application;
import net.miginfocom.swing.MigLayout;

public class StationScreen extends JPanel {
	private JPanel container1;
	private JTextField searchTextField;
	private JButton addNewButton;
	private JButton deleteButton;
	private JButton updateButton;
	private StationAddingDialog stationAddingDialog;
	private StationTableModel stationTableModel;
	private JTable stationTable;
	private JPanel container;
	private StationDAO stationDAO;
	private StationUpdateDialog stationUpdateDialog;

	public StationScreen() {

		stationDAO = new StationDAO();

		setLayout(new BorderLayout());

		container = new JPanel();

		// button container
		container1 = new JPanel();
		searchTextField = new JTextField();
		searchTextField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm");
		addNewButton = new JButton("Thêm mới");
		updateButton = new JButton("Cập nhật");
		deleteButton = new JButton("Xóa");
		container1.setLayout(new MigLayout("", "[]push[][][][][]", ""));
		container1.add(searchTextField, "w 200!");
		container1.add(addNewButton);
		container1.add(updateButton);
		container1.add(deleteButton);

		addNewButton.setIcon(new FlatSVGIcon("gui/icon/svg/add.svg", 0.35f));
		updateButton.setIcon(new FlatSVGIcon("gui/icon/svg/edit.svg", 0.35f));
		deleteButton.setIcon(new FlatSVGIcon("gui/icon/svg/delete.svg", 0.35f));

		searchTextField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON,
				new FlatSVGIcon("gui/icon/svg/search.svg", 0.35f));

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

		// table container
		stationTableModel = new StationTableModel();
		stationTable = new JTable(stationTableModel);

		container.setLayout(new MigLayout("wrap, fill, insets 15", "[fill]", "[grow 0][fill]"));
		container.add(container1);
		container.add(new JScrollPane(stationTable));

		if (stationTable.getColumnModel().getColumnCount() > 0) {
			stationTable.getColumnModel().getColumn(1).setPreferredWidth(300);
		}

		// Change scroll style
		JScrollPane scroll = (JScrollPane) stationTable.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		stationTable.getTableHeader().putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
		stationTable.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");

		// To Create table alignment
		stationTable.getTableHeader()
				.setDefaultRenderer(getAlignmentCellRender(stationTable.getTableHeader().getDefaultRenderer(), true));
		stationTable.setDefaultRenderer(Object.class,
				getAlignmentCellRender(stationTable.getDefaultRenderer(Object.class), false));

		addNewButton.addActionListener(e -> {
			JPanel glassPane = new BlurGlassPane();
			Application.getInstance().setGlassPane(glassPane);
			glassPane.setVisible(true);
			stationAddingDialog = new StationAddingDialog();
			stationAddingDialog.setFormTrainManagement(this);
			stationAddingDialog.setModal(true);
			stationAddingDialog.setVisible(true);
		});

		deleteButton.addActionListener(e -> {
			int selectedRow = stationTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa.");
			} else {
				String trainID = (String) stationTable.getValueAt(selectedRow, 0);

				int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa ga này?", "Warning",
						JOptionPane.YES_NO_OPTION);

				if (option == JOptionPane.YES_OPTION) {
					int rowsAffected = stationDAO.deleteStationByID(trainID);
					if (rowsAffected > 0) {
						search();
					} else {
						JOptionPane.showMessageDialog(this, "Không thể xóa!", "Thất bại", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		updateButton.addActionListener(e -> {
			int selectedRow = stationTable.getSelectedRow();

			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để cập nhật.");
			} else {
				JPanel glassPane = new BlurGlassPane();
				Application.getInstance().setGlassPane(glassPane);
				glassPane.setVisible(true);
				String stationID = (String) stationTable.getValueAt(selectedRow, 0);
				Station station = stationDAO.getStationByID(stationID);
				stationUpdateDialog = new StationUpdateDialog(station);
				stationUpdateDialog.setFormTrainManagement(this);
				stationUpdateDialog.setModal(true);
				stationUpdateDialog.setVisible(true);
			}
		});

		this.add(container);
	}

	public void search() {
		String stationNameToFind = searchTextField.getText().trim();
		List<Station> stations = stationDAO.findStationByName(stationNameToFind);
		stationTableModel.setTrainDetailsList(stations);
		stationTableModel.fireTableDataChanged();
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
					if (column == 1) {
						label.setHorizontalAlignment(SwingConstants.LEADING);
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

	// Custom Cell Renderer for multiline text
	@SuppressWarnings("serial")
	static class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
		public MultiLineCellRenderer() {
			setLineWrap(true);
			setWrapStyleWord(true);
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value != null ? value.toString() : "");
			setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
			setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
			return this;
		}
	}

	class BlurGlassPane extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage blurredImage;

		public BlurGlassPane() {
			setOpaque(false); // Making the glass pane transparent
			// Create a blank translucent image
			blurredImage = new BufferedImage(Application.getInstance().getRootPane().getWidth(),
					Application.getInstance().getRootPane().getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = blurredImage.createGraphics();
			g2d.setColor(new Color(0, 0, 0, 128)); // Set color with alpha for translucency
			g2d.fillRect(0, 0, Application.getInstance().getRootPane().getWidth(),
					Application.getInstance().getRootPane().getHeight()); // Fill the image with the translucent color
			g2d.dispose();

			// Apply blur effect
			blurredImage = blurImage(blurredImage);
		}

		// Method to blur an image
		private BufferedImage blurImage(BufferedImage image) {
			// You can implement your own image blurring algorithm or use libraries like
			// JavaFX or Apache Commons Imaging
			// Here, I'll use a simple averaging algorithm for demonstration purposes
			int blurRadius = 5;
			float weight = 1.0f / (blurRadius * blurRadius);
			float[] blurMatrix = new float[blurRadius * blurRadius];
			for (int i = 0; i < blurMatrix.length; i++) {
				blurMatrix[i] = weight;
			}
			Kernel kernel = new Kernel(blurRadius, blurRadius, blurMatrix);
			BufferedImageOp op = new ConvolveOp(kernel);
			return op.filter(image, null);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// Draw the blurred image onto the glass pane
			g.drawImage(blurredImage, 0, 0, Application.getInstance().getRootPane().getWidth(),
					Application.getInstance().getRootPane().getHeight(), null);
		}
	}
}
