package entity;

import java.io.Serializable;

public class ServiceDetail implements Serializable {

	private Service service;
	private Order order;
	private int quantity;

	public ServiceDetail(Service service, Order order, int quantity) {
		super();
		this.service = service;
		this.order = order;
		this.quantity = quantity;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}