package gui.application.form.other.ticket;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.formdev.flatlaf.FlatClientProperties;

import entity.Employee;
import entity.TicketInfo;
import entity.TrainJourneyOptionItem;
import gui.application.Application;
import net.miginfocom.swing.MigLayout;

public class TrainJourneyChoosingDialog extends JDialog {

	private FormSearchTrainJourney formSearchTrainJourney;
	private JPanel container;
	private JPanel container1;
	private JLabel chieuDiLabel;
	private JPanel container2;
	private JPanel container3;
	private JButton closeButton;
	private JPanel formContainer;

	public TrainJourneyChoosingDialog(List<TrainJourneyOptionItem> trainJourneyOptionItemList, Employee employee, TicketInfo ticket) {

		setLayout(new BorderLayout());
		container = new JPanel(new MigLayout("wrap, fill, insets 32", "[fill]", "[fill]"));
		formContainer = new JPanel(new MigLayout("wrap, fill", "[]", "[][][]"));

		container1 = new JPanel(new MigLayout());
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		chieuDiLabel = new JLabel(
				"Chiều đi: ngày " + trainJourneyOptionItemList.get(0).getDepartureDate().format(dateFormatter) + " từ "
						+ trainJourneyOptionItemList.get(0).getDepartureStation().getStationName() + " đến "
						+ trainJourneyOptionItemList.get(0).getArrivalStation().getStationName());
		chieuDiLabel.putClientProperty(FlatClientProperties.STYLE,
				"background: $primary; foreground: $clr-white; font:bold +12;");
		chieuDiLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		chieuDiLabel.setOpaque(true);
		container1.add(chieuDiLabel, "dock north");

		container2 = new JPanel(new MigLayout("wrap, fillx, insets 16 0 16 0, gap 16", "[500px][500px]", "[fill]"));

		for (TrainJourneyOptionItem trainJourneyOptionItem : trainJourneyOptionItemList) {
			TrainJourneyOptionItemCard trainJourneyOptionItemCard = new TrainJourneyOptionItemCard(
					trainJourneyOptionItem, employee, this, ticket);
			container2.add(trainJourneyOptionItemCard, "aligny, growx");
		}

		formContainer.add(container1, "dock north");
		formContainer.add(new JScrollPane(container2), "dock center");

		JScrollPane scroll = (JScrollPane) container2.getParent().getParent();
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Table.background;" + "track:$Table.background;" + "trackArc:999");

		container3 = new JPanel(new MigLayout("wrap, fill"));
		closeButton = new JButton("Đóng");
		closeButton.putClientProperty(FlatClientProperties.STYLE,
				"arc:5;background: $clr-red;foreground: $clr-white; hoverBackground: $clr-white; hoverForeground: $clr-red");
		closeButton.addActionListener(e -> {
			closeDialog();
		});
		container3.add(closeButton, "al right");
		formContainer.add(container3, "dock south");

		container.add(formContainer);

		this.add(container);
		this.setUndecorated(true);
		this.setSize(1200, 700);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JPanel defaultGlassPane = (JPanel) Application.getInstance().getGlassPane();
				defaultGlassPane.removeAll();
				Application.getInstance().setGlassPane(defaultGlassPane);
				defaultGlassPane.setVisible(false);
			}
		});
	}

	public void setFormSearchTrainJourney(FormSearchTrainJourney formSearchTrainJourney) {
		this.formSearchTrainJourney = formSearchTrainJourney;
	}

	public void closeDialog() {
		JPanel defaultGlassPane = (JPanel) Application.getInstance().getGlassPane();
		defaultGlassPane.removeAll();
		Application.getInstance().setGlassPane(defaultGlassPane);
		defaultGlassPane.setVisible(false);
		dispose();
	}

}