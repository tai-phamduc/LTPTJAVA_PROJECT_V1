package entity;

import java.io.Serializable;

public class LineDetails implements Serializable {

	private String lineID;
	private String lineName;
	private String departureStation;
	private String arrivalStation;
	private int distance;

	public LineDetails(String lineID, String lineName, String departureStation, String arrivalStation, int distance) {
		super();
		this.lineID = lineID;
		this.lineName = lineName;
		this.departureStation = departureStation;
		this.arrivalStation = arrivalStation;
		this.distance = distance;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}

	public String getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "LineDetails [lineID=" + lineID + ", lineName=" + lineName + ", departureStation=" + departureStation
				+ ", arrivalStation=" + arrivalStation + ", distance=" + distance + "]";
	}

}