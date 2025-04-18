package entity;

import java.io.Serializable;
import java.util.Objects;

public class Year implements Serializable {

	private int value;
	private String name;

	public Year(int value, String name) {
		super();
		this.value = value;
		this.name = name;
	}

	public Year(int year) {
		this.value = year;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Year other = (Year) obj;
		return value == other.value;
	}

}
