package entity;

import java.io.Serializable;
import java.util.Objects;

public class Service implements Serializable {
	private String serviceID;
	private String serviceName;
	private double price;
	private String type;
	private String imageSource;

	public Service(String serviceID) {
		this.serviceID = serviceID;
	}

	public Service(String serviceID, String serviceName, double price, String type, String imageSource) {
		super();
		this.serviceID = serviceID;
		this.serviceName = serviceName;
		this.price = price;
		this.type = type;
		this.imageSource = imageSource;
	}

	public Service(String serviceName, double price, String type, String imageSource) {
		super();
		this.serviceName = serviceName;
		this.price = price;
		this.type = type;
		this.imageSource = imageSource;
	}

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		if (this.serviceID == null) {
			this.serviceID = serviceID;
		}
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	@Override
	public String toString() {
		return "Service [serviceID=" + serviceID + ", serviceName=" + serviceName + ", price=" + price + ", type="
				+ type + ", setImageSource=" + imageSource + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(serviceID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Service other = (Service) obj;
		return Objects.equals(serviceID, other.serviceID);
	}

}