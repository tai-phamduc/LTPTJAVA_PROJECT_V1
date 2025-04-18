package entity;

import java.io.Serializable;

public class Ticket implements Serializable {

	private String ticketID;
	private String status;
	private TrainJourney trainJourney;
	private Seat seat;
	private Passenger passenger;
	private Order order;

	public Ticket(String ticketID, String status, TrainJourney trainJourney, Seat seat, Passenger passenger,
			Order order) {
		super();
		this.ticketID = ticketID;
		this.status = status;
		this.trainJourney = trainJourney;
		this.seat = seat;
		this.passenger = passenger;
		this.order = order;
	}

	public Ticket(String ticketID, TrainJourney trainJourney, Seat seat, Passenger passenger, Order order) {
		super();
		this.ticketID = ticketID;
		this.trainJourney = trainJourney;
		this.seat = seat;
		this.passenger = passenger;
		this.order = order;
	}

	public Ticket(String ticketID) {
		super();
		this.ticketID = ticketID;
	}

	public Ticket(TrainJourney trainJourney, Seat seat) {
		super();
		this.trainJourney = trainJourney;
		this.seat = seat;
	}

	public String getTicketID() {
		return ticketID;
	}

	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TrainJourney getTrainJourney() {
		return trainJourney;
	}

	public void setTrainJourney(TrainJourney trainJourney) {
		this.trainJourney = trainJourney;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Ticket [ticketID=" + ticketID + ", status=" + status + ", trainJourney=" + trainJourney + ", seat="
				+ seat + ", passenger=" + passenger + ", order=" + order + "]";
	}

}
