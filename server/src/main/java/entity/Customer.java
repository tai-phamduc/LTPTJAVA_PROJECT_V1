package entity;

import java.io.Serializable;

public class Customer implements Serializable {
	private String customerID;
	private String fullName;
	private String phoneNumber;
	private String email;
	private String identificationNumber;
	
	
	public Customer(String customerID, String fullName, String phoneNumber, String email, String identificationNumber) {
		super();
		this.customerID = customerID;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.identificationNumber = identificationNumber;
	}
	
	public Customer( String fullName, String phoneNumber, String email, String identificationNumber) {
		super();
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.identificationNumber = identificationNumber;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber
				+ ", email=" + email + ", identificationNumber=" + identificationNumber + "]";
	}

}
