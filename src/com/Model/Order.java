package com.Model;

import java.time.LocalDate;

public class Order {

	private String foodName;
	private double foodPrice;
	private double quantity;
	private LocalDate processaDate;
	private String status;
	private int vendorid;
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(String foodName, double foodPrice, double quantity, LocalDate processaDate, String status,
			int vendorid) {
		super();
		this.foodName = foodName;
		this.foodPrice = foodPrice;
		this.quantity = quantity;
		this.processaDate = processaDate;
		this.status = status;
		this.vendorid = vendorid;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public double getFoodPrice() {
		return foodPrice;
	}

	public void setFoodPrice(double foodPrice) {
		this.foodPrice = foodPrice;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public LocalDate getProcessaDate() {
		return processaDate;
	}

	public void setProcessaDate(LocalDate processaDate) {
		this.processaDate = processaDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getVendorid() {
		return vendorid;
	}

	public void setVendorid(int vendorid) {
		this.vendorid = vendorid;
	}

	@Override
	public String toString() {
		return "Order [foodName=" + foodName + ", foodPrice=" + foodPrice + ", quantity=" + quantity + ", processaDate="
				+ processaDate + ", status=" + status + ", vendorid=" + vendorid + "]";
	}

	
}
