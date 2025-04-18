package gui.application.form.other.trainjourney;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.Station;
import net.miginfocom.swing.MigLayout;

public class LineItem extends JPanel {

	private JLabel indexColumn;
	private JComboBox<Station> stationCombobox;
	private JTextField distanceTextField;

	public LineItem(int i, List<Station> stations) {
		this.setLayout(new MigLayout("wrap", "[100px][grow][150px]"));
		indexColumn = new JLabel(i + 1 + "");
		stationCombobox = new JComboBox<Station>();
		for (Station station : stations) {
			stationCombobox.addItem(station);
		}
		distanceTextField = new JTextField(20);
		this.add(indexColumn);
		this.add(stationCombobox);
		this.add(distanceTextField);
		
	}

	public JLabel getIndexColumn() {
		return indexColumn;
	}

	public void setIndexColumn(JLabel indexColumn) {
		this.indexColumn = indexColumn;
	}

	public JComboBox<Station> getStationCombobox() {
		return stationCombobox;
	}

	public void setStationCombobox(JComboBox<Station> stationCombobox) {
		this.stationCombobox = stationCombobox;
	}

	public JTextField getDistanceTextField() {
		return distanceTextField;
	}

	public void setDistanceTextField(JTextField distanceTextField) {
		this.distanceTextField = distanceTextField;
	}

}