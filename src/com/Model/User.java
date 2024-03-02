package com.Model;

public class User {

	private int id;
	private String name;
	private String phone;
	private String email;
	private double walletBalance;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int id, String name, String phone, String email, double walletBalance) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.walletBalance = walletBalance;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", walletBalance="
				+ walletBalance + "]";
	}

}
