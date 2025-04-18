package entity;

import java.io.Serializable;
public class Account implements Serializable {

	private String username;
	private String password;
	private Employee employee;

	public Account() {}
	
	public Account(String username, String password, Employee employee) {
		super();
		this.username = username;
		this.password = password;
		this.employee = employee;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "Account [username=" + username + ", password=" + password + ", employee=" + employee + "]";
	}

}
