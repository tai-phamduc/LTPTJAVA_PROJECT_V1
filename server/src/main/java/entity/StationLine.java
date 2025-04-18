package entity;

import java.io.Serializable;

public class StationLine implements Serializable {

	private int index;
	private Station station;
	private int distance;

	public StationLine(int index, Station station, int distance) {
		super();
		this.index = index;
		this.station = station;
		this.distance = distance;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "StationLine [index=" + index + ", station=" + station + ", distance=" + distance + "]";
	}

}
