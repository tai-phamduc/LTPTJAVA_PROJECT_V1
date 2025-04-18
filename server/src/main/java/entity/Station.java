package entity;

import java.io.Serializable;
import java.util.Objects;

public class Station implements Serializable {

	private String stationID;
	private String stationName;

	public Station(String stationID, String stationName) {
		super();
		this.stationID = stationID;
		this.stationName = stationName;
	}

    public Station(String stationID) {
		super();
		this.stationID = stationID;
    }

    public String getStationID() {
		return stationID;
	}

	public void setStationID(String stationID) {
		this.stationID = stationID;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	@Override
	public String toString() {
		return this.getStationName();
	}

	@Override
	public int hashCode() {
		return Objects.hash(stationName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Station other = (Station) obj;
		return Objects.equals(stationName, other.stationName);
	}

}