package com.Factory;

import com.Model.Menu;
import com.Model.Order;
import com.Model.User;
import com.Model.Vendor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.Persistence.DB;

//GitHub project sync check 
public class Factory {

	public static ArrayList<Menu> displayMenu() {

		DB db = new DB();
		ArrayList<Menu> list = db.fetchMenu();
		return list;
	}

	public static ArrayList<String> getReviews(int foodId) {

		DB db = new DB();
		ArrayList<String> List = db.getReviews(foodId);
		return List;
	}

	public static int getRating(int foodId) {
		DB db = new DB();
		return db.getRating(foodId);
	}

	public static boolean doLogin(int userId, String password) {
		DB db = new DB();
		return db.getdoLogin(userId, password);
	}

	public static double getcurrentBalance(int userId) {
		DB db = new DB();
		return db.currentBalance(userId);
	}

	public static void updateBalance(double updatedBalance, int userId) {
		DB db = new DB();
		db.updateBalance(updatedBalance, userId);
	}

	public static double totalCost(ArrayList<Menu> list1, HashMap<Integer, Integer> map) {
		DB db = new DB();
		double total = 0;
		double price = 0;
		for (int foodOrderId : map.keySet()) {
			for (Menu m : list1) {
				if (m.getId() == foodOrderId) {
					price = db.getPrice(foodOrderId);
					break;
				}
			}
			int quantity = map.get(foodOrderId);
			total = total + (price * quantity);
		}
		return total;
	}

	public static String getCoupon(int userId) {
		DB db = new DB();
		return db.getCoupon(userId);
	}

	public static void updateCoupon(String coupon, int userId) {
		DB db = new DB();
		db.updateCoupon(coupon, userId);
	}

	public static void saveOrderHistory(HashMap<Integer, Integer> map, int userId, int vendorId) {
		DB db = new DB();
		for (int id : map.keySet()) {
			int val = map.get(id); // quantity
			db.saveOrderHistory(id, val, userId, LocalDate.now(), vendorId);
		}
	}

	public static User fetchCustomerDetails(int userId) {
		DB db = new DB();
		return db.fetchCustomerDetails(userId);
	}

	public static ArrayList<Order> fetchOrderHistory(int userId) {
		DB db = new DB();
		return db.fetchOrderList(userId);
	}

	public static void updateNewLoginUser(String name, String email, String phone, String pass) {
		DB db = new DB();
		db.updateNewLogin(name, email, phone, pass);

	}

	public static int getNewUserId(String pass1) {
		DB db = new DB();
		int newid = db.getNewUserId(pass1);
		return newid;
	}

	public static String getPassword(int ID) {
		DB db = new DB();
		return db.getPassword(ID);
	}

	public static void updateNewPassword(String newpass, int ID) {
		DB db = new DB();
		db.updateNewPassword(newpass, ID);
	}

	public static double totalCalories(HashMap<Integer, Integer> map1, ArrayList<Menu> list111) {
		double totalCalories = 0;
		double calories = 0;
		for (int orderIdFood : map1.keySet()) {
			for (Menu m : list111) {
				if (m.getId() == orderIdFood) {
					calories = m.getFooCalories();
					break;
				}
			}
			int quantity = map1.get(orderIdFood);
			totalCalories = totalCalories + (calories * quantity);
		}
		return totalCalories;
	}

	public static String getFoodName(int foodOrderId) {
		DB db = new DB();
		return db.getFoodName(foodOrderId);
	}

	public static String getName(int userId) {
		DB db = new DB();
		return db.getName(userId);
	}

	public static boolean doVendorLogin(int venId, String password) {
		DB db = new DB();
		return db.getdoVendorLogin(venId, password);
	}

	public static ArrayList<Menu> displayMenu(int venId) {
		DB db = new DB();
		ArrayList<Menu> list = db.fetchMenu(venId);
		return list;
	}

	public static Vendor fetchVendorDetails(int venId) {
		DB db = new DB();
		return db.fetchVendorDetails(venId);
	}

	public static void updateMenuType(String newType, int serialNo) {
		DB db = new DB();
		db.updateMenuType(newType, serialNo);
	}

	public static void updateMenuName(String newName, int serialNo1) {
		DB db = new DB();
		db.updateMenuName(newName, serialNo1);
	}

	public static void updateMenuPrice(double newPrice, int serialNo11) {
		DB db = new DB();
		db.updateMenuPrice(newPrice, serialNo11);
	}

	public static void updateNewFood(String newFoodType, String newFoodName, double newFoodPrice, int venId) {
		DB db = new DB();
		db.updateNewFood(newFoodType, newFoodName, newFoodPrice, venId);
	}

	public static void deleteFoodItem(int deleteFood) {
		DB db = new DB();
		db.deleteFoodItem(deleteFood);
	}

	public static String getVendorName(int venId) {
		DB db = new DB();
		return db.getVendorName(venId);
	}

	public static ArrayList<Order> fetchOrderHistoryToCancel(String cancelip) {
		DB db = new DB();
		return db.fetchOrderHistoryToCancel(cancelip);
	}

	public static int getFoodId(String cancelip) {
		DB db = new DB();
		return db.getFoodId(cancelip);
	}

	public static int deleteOrderId(int cancelid) {
		DB db = new DB();
		return db.deleteOrderId(cancelid);
	}

	public static void CancelOrder(int deleteorderid) {
		DB db = new DB();
		db.CancelOrder(deleteorderid);
	}

	public static void updateOrderStatus(int orderId, String orderStatus, int venId) {
		DB db = new DB();
		db.updateOrderStatus(orderId, orderStatus, venId);

	}

	public static int getVendorId(int foodOrderId1) {
		DB db = new DB();
		return db.getVendorId(foodOrderId1);
	}

	public static ArrayList<Order> fetchOrderHistoryForVendor(int venId) {
		DB db = new DB();
		return db.fetchOrderHistoryForVendor(venId);
	}

	public static void updateNewVendorLogin(String venName, String venPhone, String venEmail,String venSpec, String venPassword) {
		DB db = new DB();
		db.updateNewVendorLogin(venName,venPhone,venEmail,venSpec,venPassword);
		
	}

	public static int getNewVendorId(String venPassword1) {
		DB db = new DB();
		return db.getNewVendorId(venPassword1);
	}

	public static String getPasswordofVendor(int ID) {
		DB db = new DB();
		return db.getPasswordofVendor(ID);
	}
	
	public static void updateNewPasswordofVendor(String newpass, int ID) {
		DB db = new DB();
		db.updateNewPasswordofVendor(newpass, ID);
		
	}

}
