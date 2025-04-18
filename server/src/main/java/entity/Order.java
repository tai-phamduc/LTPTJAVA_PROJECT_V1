package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Order implements Serializable {

	private String orderID;
	private LocalDateTime orderDate;
	private String note;
	private LocalTime timeRemaining;
	private String orderStatus;
	private Customer customer;
	private TrainJourney trainJourney;
	private Employee employee;

	public Order(String orderID) {
		super();
		this.orderID = orderID;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public LocalTime getTimeRemaining() {
		return timeRemaining;
	}

	public void setTimeRemaining(LocalTime timeRemaining) {
		this.timeRemaining = timeRemaining;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public TrainJourney getTrainJourney() {
		return trainJourney;
	}

	public void setTrainJourney(TrainJourney trainJourney) {
		this.trainJourney = trainJourney;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getOrderID() {
		return orderID;
	}

	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", orderDate=" + orderDate + ", note=" + note + ", timeRemaining="
				+ timeRemaining + ", orderStatus=" + orderStatus + ", customer=" + customer + ", trainJourney="
				+ trainJourney + ", employee=" + employee + "]";
	}

}
