package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Passenger implements Serializable {
	private String passengerID;
	private String fullName;
	private LocalDate dateOfBirth;
	private String identifierType;
	private String identifier;
	private String passengerType;

	public Passenger(String passengerID, String fullName, LocalDate dateOfBirth, String identifier,
			String identifierType, String passengerType) {
		this.passengerID = passengerID;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.identifier = identifier;
		this.identifierType = identifierType;
		this.passengerType = passengerType;
	}

	public Passenger() {
		super();
		this.passengerType = "Người lớn";
		this.dateOfBirth = LocalDate.now();
	}

	public Passenger(String fullName, String passengerType, String identifier) {
		super();
		this.fullName = fullName;
		this.identifier = identifier;
		this.passengerType = passengerType;
	}

	public String getPassengerID() {
		return passengerID;
	}

	public void setPassengerID(String customerID) {
		this.passengerID = customerID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getIdentifierType() {
		return identifierType;
	}

	public void setIdentifierType(String identitierType) {
		this.identifierType = identitierType;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identitier) {
		this.identifier = identitier;
	}

	public String getPassengerType() {
		return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}

	@Override
	public String toString() {
		return "Passenger [passengerID=" + passengerID + ", fullName=" + fullName + ", dateOfBirth=" + dateOfBirth
				+ ", identitierType=" + identifierType + ", identitier=" + identifier + ", passengerType="
				+ passengerType + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(passengerID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passenger other = (Passenger) obj;
		return Objects.equals(passengerID, other.passengerID);
	}

}
