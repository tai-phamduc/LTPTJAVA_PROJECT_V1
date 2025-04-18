package entity;

import java.io.Serializable;

public class LineStop implements Serializable {

	private Line line;
	private Station station;
	private int stopOrder;
	private int distance;

	public LineStop(Line line, Station station, int stopOrder, int distance) {
		super();
		this.line = line;
		this.station = station;
		this.stopOrder = stopOrder;
		this.distance = distance;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public int getStopOrder() {
		return stopOrder;
	}

	public void setStopOrder(int stopOrder) {
		this.stopOrder = stopOrder;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "LineStop [line=" + line + ", station=" + station + ", stopOrder=" + stopOrder + ", distance=" + distance
				+ "]";
	}

}
