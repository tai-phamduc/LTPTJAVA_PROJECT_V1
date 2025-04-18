package entity;

import java.io.Serializable;

public class Coach implements Serializable {

	private int coachID;
	private int coachNumber;
	private String coachType;
	private int capacity;
	private Train train;

	public Coach(int coachID, int coachNumber, String coachType, int capacity, Train train) {
		super();
		this.coachID = coachID;
		this.coachNumber = coachNumber;
		this.coachType = coachType;
		this.capacity = capacity;
		this.train = train;
	}

	public Coach(int coachID, int coachNumber, String coachType, int capacity) {
		super();
		this.coachID = coachID;
		this.coachNumber = coachNumber;
		this.coachType = coachType;
		this.capacity = capacity;
	}

	public Coach(int coachNumber, String coachType, int capacity, Train train) {
		this.coachNumber = coachNumber;
		this.coachType = coachType;
		this.capacity = capacity;
		this.train = train;
	}

	public Coach(int coachID) {
		this.coachID = coachID;
	}

	public int getCoachID() {
		return coachID;
	}

	public void setCoachID(int coachID) {
		this.coachID = coachID;
	}

	public int getCoachNumber() {
		return coachNumber;
	}

	public void setCoachNumber(int coachNumber) {
		this.coachNumber = coachNumber;
	}

	public String getCoachType() {
		return coachType;
	}

	public void setCoachType(String coachType) {
		this.coachType = coachType;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "Coach [coachID=" + coachID + ", coachNumber=" + coachNumber + ", coachType=" + coachType + ", capacity="
				+ capacity + ", train=" + train + "]";
	}

}
