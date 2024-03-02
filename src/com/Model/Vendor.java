package com.Model;

public class Vendor {
	
	private int id;
	private String name;
	private String phone;
	private String email;
	private String speciality;
	private String password;
	
	public Vendor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vendor(int id, String name, String phone, String email, String speciality, String password) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.speciality = speciality;
		this.password = password;
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

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Vendor [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", speciality="
				+ speciality + ", password=" + password + "]";
	}
	
	
	

}
