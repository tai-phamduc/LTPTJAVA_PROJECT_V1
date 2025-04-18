package entity;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import dao.OrderDAO;

public class TicketInfo implements Serializable {
	private Ticket ticket;
	private Passenger passenger;
	private LocalDateTime depatureDateTime;
	private Seat seat;
	private Coach coach;
	private String ticketType;
	private String ticketRefundInfo;
	private double distance;

	public TicketInfo(Ticket ticket, Passenger passenger, LocalDateTime depatureDateTime, Seat seat, Coach coach,
			String ticketType, String ticketRefundInfo, double baseTotal) {
		super();
		this.ticket = ticket;
		this.passenger = passenger;
		this.depatureDateTime = depatureDateTime;
		this.seat = seat;
		this.coach = coach;
		this.ticketType = ticketType;
		this.ticketRefundInfo = ticketRefundInfo;
		this.distance = baseTotal;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public LocalDateTime getDepatureDateTime() {
		return depatureDateTime;
	}

	public String getDepatureDateTimeToString() {
		return depatureDateTime.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
	}

	public void setDepatureDateTime(LocalDateTime depatureDateTime) {
		this.depatureDateTime = depatureDateTime;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getTicketRefundInfo() {
		return ticketRefundInfo;
	}

	public void setTicketRefundInfo(String ticketRefundInfo) {
		this.ticketRefundInfo = ticketRefundInfo;
	}

	public double getBaseTotal() {
		return distance;
	}

	public double caculateTotal() {
		double k = 1;
		if (this.getCoach().getCoachType().equalsIgnoreCase("Giường nằm khoang 6 điều hòa")) {
			k = 1.2;
		} else if (this.getCoach().getCoachType().equalsIgnoreCase("Giường nằm khoang 4 điều hòa")) {
			k = 1.5;
		}
		return k * this.distance * this.getTicket().getTrainJourney().getBasePrice();
	}

	public double caculateRefundFee() {
		int ticketCount = (new OrderDAO()).getTicketCountByOrderID(this.getTicket().getOrder().getOrderID());
		double refundFee = 0;
		double ticketPrice = this.caculateTotal();
		double remainingHours = getRemainingHours();

		if (ticketCount == 1) {
			if (remainingHours < 24 && remainingHours >= 4) {
				refundFee = ticketPrice * 0.20;
			} else if (remainingHours >= 24) {
				refundFee = ticketPrice * 0.10;
			}
		} else if (ticketCount > 1) {
			if (remainingHours < 72 && remainingHours >= 24) {
				refundFee = ticketPrice * 0.20;
			} else if (remainingHours >= 72) {
				refundFee = ticketPrice * 0.10;
			}
		}
		return refundFee;
	}

	public double caculateRefund() {
		return caculateTotal() - caculateRefundFee();
	}

	public double getRemainingHours() {
		LocalDateTime currentTime = LocalDateTime.now();
		Duration duration = Duration.between(currentTime, depatureDateTime);
		return duration.toHours() + (double) duration.toMinutesPart() / 60;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ticket.getTicketID());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketInfo other = (TicketInfo) obj;
		return Objects.equals(ticket.getTicketID(), other.ticket.getTicketID());
	}

	@Override
	public String toString() {
		return "TicketInfo [ticket=" + ticket + ", passenger=" + passenger + ", depatureDateTime=" + depatureDateTime
				+ ", seat=" + seat + ", coach=" + coach + ", ticketType=" + ticketType + ", ticketRefundInfo="
				+ ticketRefundInfo + ", distance=" + distance + "]";
	}
}
