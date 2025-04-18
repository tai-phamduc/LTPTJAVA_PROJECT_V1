package entity;

import java.io.Serializable;

public class TicketDetail implements Serializable {

	private Stop stopID;
	private Ticket ticketID;

	public TicketDetail(Stop stopID, Ticket ticketID) {
		super();
		this.stopID = stopID;
		this.ticketID = ticketID;
	}

	public Stop getStopID() {
		return stopID;
	}

	public void setStopID(Stop stopID) {
		this.stopID = stopID;
	}

	public Ticket getTicketID() {
		return ticketID;
	}

	public void setTicketID(Ticket ticketID) {
		this.ticketID = ticketID;
	}

	@Override
	public String toString() {
		return "TicketDetail [stopID=" + stopID + ", ticketID=" + ticketID + "]";
	}

}
