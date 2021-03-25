package com.example.demo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Car {

	private @Id @GeneratedValue Long id;
	private String manufacturer;
	private String model;
	private String horsePower;

	Car() {}

	Car(String manufacturer, String model, String horsePower) {
		this.manufacturer = manufacturer;
		this.model = model;
		this.horsePower = horsePower;
	}

	public String getName() {
		return this.manufacturer + " " + this.model;
	}

	public void setName(String name) {
		String[] parts = name.split(" ");
		this.manufacturer = parts[0];
		this.model = parts[1];
	}

	public Long getId() {
		return this.id;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public String getModel() {
		return this.model;
	}

	public String getHorsePower() {
		return this.horsePower;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setHorsePower(String horsePower) {
		this.horsePower = horsePower;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Car))
			return false;
		Car employee = (Car) o;
		return Objects.equals(this.id, employee.id) && Objects.equals(this.manufacturer, employee.manufacturer)
				&& Objects.equals(this.model, employee.model) && Objects.equals(this.horsePower, employee.horsePower);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.manufacturer, this.model, this.horsePower);
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + this.id + ", manufacturer='" + this.manufacturer + '\'' + ", model='" + this.model
				+ '\'' + ", horsePower='" + this.horsePower + '\'' + '}';
	}
}
