package com.Model;

import java.util.Objects;

public class Menu {

	private int id;
	private String name;
	private String foodType;
	private double price;
	private int vendorid;
	private double fooCalories;

	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Menu(int id, String name, String foodType, double price, int vendorid, double fooCalories) {
		super();
		this.id = id;
		this.name = name;
		this.foodType = foodType;
		this.price = price;
		this.vendorid = vendorid;
		this.fooCalories = fooCalories;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFoodType() {
		return foodType;
	}

	public void setFoodType(String foodType) {
		this.foodType = foodType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getVendorid() {
		return vendorid;
	}

	public void setVendorid(int vendorid) {
		this.vendorid = vendorid;
	}

	public double getFooCalories() {
		return fooCalories;
	}

	public void setFooCalories(double fooCalories) {
		this.fooCalories = fooCalories;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", foodType=" + foodType + ", price=" + price + ", vendorid="
				+ vendorid + ", fooCalories=" + fooCalories + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fooCalories, foodType, id, name, price, vendorid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		return Double.doubleToLongBits(fooCalories) == Double.doubleToLongBits(other.fooCalories)
				&& Objects.equals(foodType, other.foodType) && id == other.id && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price) && vendorid == other.vendorid;
	}
	
	

}
