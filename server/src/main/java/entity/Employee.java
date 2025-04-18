package entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Employee implements Serializable {

	private String employeeID;
	private String fullName;
	private boolean gender;
	private LocalDate dateOfBirth;
	private String email;
	private String phoneNumber;
	private String role;
	private LocalDate startingDate;
	private String imageSource;

	public Employee(String employeeID, String fullName, boolean gender, LocalDate dateOfBirth, String email,
			String phoneNumber, String role, LocalDate startingDate) {
		super();
		this.employeeID = employeeID;
		this.fullName = fullName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.startingDate = startingDate;
	}

	public Employee(String fullName, boolean gender, LocalDate dateOfBirth, String email, String phoneNumber,
			String role, LocalDate startingDate) {
		super();
		this.fullName = fullName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.startingDate = startingDate;
	}

	public Employee(String employeeID, String fullName, boolean gender, LocalDate dateOfBirth, String email,
			String phoneNumber, String role, LocalDate startingDate, String imageSource) {
		super();
		this.employeeID = employeeID;
		this.fullName = fullName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.startingDate = startingDate;
		this.imageSource = imageSource;
	}

	public Employee(String employeeID, String fullName, boolean gender, LocalDate dateOfBirth, String email,
			String phoneNumber, String role) {
		super();
		this.employeeID = employeeID;
		this.fullName = fullName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public Employee(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDate getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(LocalDate startingDate) {
		this.startingDate = startingDate;
	}

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", fullName=" + fullName + ", gender=" + gender + ", dateOfBirth="
				+ dateOfBirth + ", email=" + email + ", phoneNumber=" + phoneNumber + ", role=" + role
				+ ", startingDate=" + startingDate + ", imageSource=" + imageSource + "]";
	}

}
