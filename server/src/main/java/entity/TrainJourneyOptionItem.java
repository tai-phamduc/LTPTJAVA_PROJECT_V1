package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class TrainJourneyOptionItem implements Serializable {

	private String trainJourneyID;

	private Train train;
	private int numberOfAvailableSeatsLeft;

	private LocalDate departureDate;
	private LocalTime departureTime;

	private LocalDate arrivalDate;
	private LocalTime arrivalTime;

	private int journeyDuration;

	private Station departureStation;
	private Station arrivalStation;

	public TrainJourneyOptionItem(String trainJourneyID, Train train, int numberOfAvailableSeatsLeft,
			LocalDate departureDate, LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime,
			int journeyDuration, Station departureStation, Station arrivalStation) {
		super();
		this.trainJourneyID = trainJourneyID;
		this.train = train;
		this.numberOfAvailableSeatsLeft = numberOfAvailableSeatsLeft;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.arrivalDate = arrivalDate;
		this.arrivalTime = arrivalTime;
		this.journeyDuration = journeyDuration;
		this.departureStation = departureStation;
		this.arrivalStation = arrivalStation;
	}

	public String getTrainJourneyID() {
		return trainJourneyID;
	}

	public void setTrainJourneyID(String trainJourneyID) {
		this.trainJourneyID = trainJourneyID;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public int getNumberOfAvailableSeatsLeft() {
		return numberOfAvailableSeatsLeft;
	}

	public void setNumberOfAvailableSeatsLeft(int numberOfAvailableSeatsLeft) {
		this.numberOfAvailableSeatsLeft = numberOfAvailableSeatsLeft;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalTime departureTime) {
		this.departureTime = departureTime;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getJourneyDuration() {
		return journeyDuration;
	}

	public void setJourneyDuration(int journeyDuration) {
		this.journeyDuration = journeyDuration;
	}

	public Station getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(Station departureStation) {
		this.departureStation = departureStation;
	}

	public Station getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(Station arrivalStation) {
		this.arrivalStation = arrivalStation;
	}

	@Override
	public String toString() {
		return "TrainJourneyOptionItem [trainJourneyID=" + trainJourneyID + ", train=" + train
				+ ", numberOfAvailableSeatsLeft=" + numberOfAvailableSeatsLeft + ", departureDate=" + departureDate
				+ ", departureTime=" + departureTime + ", arrivalDate=" + arrivalDate + ", arrivalTime=" + arrivalTime
				+ ", journeyDuration=" + journeyDuration + ", departureStation=" + departureStation
				+ ", arrivalStation=" + arrivalStation + "]";
	}

}