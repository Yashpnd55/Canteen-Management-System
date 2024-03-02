package com.Persistence;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import com.Model.Menu;
import com.Model.Order;
import com.Model.User;
import com.Model.Vendor;

public class DB {

	private String dbName = "CMS_DB";
	private String dbUser = "root";
	private String dbPassword = "Password123";
	private String url = "jdbc:mysql://localhost:3306/" + dbName;
	private String driver = "com.mysql.cj.jdbc.Driver";

	private Connection con;

	// Step 2

	public void dbConnect() {
		try {
			Class.forName(driver); // loads the driver
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			con = DriverManager.getConnection(url, dbUser, dbPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void dbClose() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Menu> fetchMenu() {
		dbConnect();
		String sql = "select * from menu";
		ArrayList<Menu> list = new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {

				int id = rst.getInt(1);
				String name = rst.getString(2);
				String foodType = rst.getString(3);
				double price = rst.getDouble(4);
				int vendorid = rst.getInt(5);
				double foodCalories = rst.getDouble(6);

				Menu menu = new Menu(id, name, foodType, price, vendorid, foodCalories);
				list.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return list;
	}

	public ArrayList<String> getReviews(int foodId) {
		dbConnect();
		String sql = "select * from reviews where menu_id=?";
		ArrayList<String> list = new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setInt(1, foodId);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				String reviewText = rst.getString(2);
				list.add(reviewText);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return list;
	}

	public int getRating(int foodId) {
		dbConnect();
		int rating = 0;
		String sql = "select * from reviews where menu_id=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, foodId);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				rating = rst.getInt(3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbClose();
		return rating;
	}

	public boolean getdoLogin(int userId, String password) {
		dbConnect();
		boolean status = false;
		String sql = "select * from user where id =? AND password = ?";
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setInt(1, userId);
			pstmt.setString(2, password);
			ResultSet rst = pstmt.executeQuery();

			if (rst.next()) {
				status = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return status;
	}

	public double currentBalance(int userId) {
		dbConnect();
		double balance = 0;
		String sql = "select * from user where id = ?";
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setInt(1, userId);
			ResultSet rst = pstmt.executeQuery();

			if (rst.next()) {
				balance = rst.getDouble(5);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return balance;
	}

	public void updateBalance(double updatedBalance, int userId) {
		dbConnect();
		String sql = "update user SET walletBalance = ? where id = ?";
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setDouble(1, updatedBalance);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();

	}

	public String getCoupon(int userId) {
		dbConnect();
		String coupon = null;
		String sql = "select * from user where id = ?";
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setInt(1, userId);
			ResultSet rst = pstmt.executeQuery();

			if (rst.next()) {
				coupon = rst.getString(6);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return coupon;
	}

	public Object updateCoupon(String coupon, int userId) {
		dbConnect();
		String sql = "update user SET coupon = ? where id = ?";
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setString(1, coupon);
			pstmt.setInt(2, userId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return null;
	}

	public void saveOrderHistory(int id, int val, int userId, LocalDate todaysDate, int vendorId) {
		dbConnect();
		String sql = "insert into order_history(foodid, quantity, userid, processdate, vendorid) values(?,?,?,?,?)";
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setInt(1, id);
			pstmt.setInt(2, val);
			pstmt.setInt(3, userId);
			pstmt.setString(4, todaysDate.toString());
			pstmt.setInt(5, vendorId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbClose();

	}

	public User fetchCustomerDetails(int userId) {
		dbConnect();
		User u = new User();
		String sql = "select id, name, phone, email, walletBalance from user where id = ?";
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setInt(1, userId);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {

				int id = rst.getInt(1);
				String name = rst.getString(2);
				String phone = rst.getString(3);
				String email = rst.getString(4);
				double walletBalance = rst.getDouble(5);

				u.setId(id);
				u.setName(name);
				u.setPhone(phone);
				u.setEmail(email);
				u.setWalletBalance(walletBalance);

				break;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbClose();
		return u;
	}

	public ArrayList<Order> fetchOrderList(int userId) {
		dbConnect();
		ArrayList<Order> list = new ArrayList<>();
		String sql = " select m.foodName,m.foodPrice, o.quantity, o.processdate, o.status, m.vendorid from order_history as o, menu as m where m.id = o.foodid "
				+ "AND o.userid = ?";
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setInt(1, userId);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {

				String itemName = rst.getString(1);
				double itemPrice = rst.getDouble(2);
				int quantity = rst.getInt(3);
				String processDate = rst.getString(4);
				String status = rst.getString(5);
				int vendorid = rst.getInt(6);

				Order order = new Order(itemName, itemPrice, quantity, LocalDate.parse(processDate), status, vendorid);
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return list;
	}

	public double getPrice(int foodOrderId) {
		dbConnect();
		double price = 0;
		String sql = "select foodPrice from menu where id = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, foodOrderId);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				price = rst.getDouble(1);
				return price;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return price;

	}

	public void updateNewLogin(String name, String email, String phone, String pass) {
		dbConnect();
		String sql = "insert into user(name,email,phone,password) values(?,?,?,?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, phone);
			pstmt.setString(4, pass);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
	}

	public int getNewUserId(String pass1) {
		dbConnect();
		int newid = 0;
		String sql = "select id from user where password = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pass1);
			ResultSet rst = pstmt.executeQuery();

			if (rst.next()) {
				newid = rst.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbClose();
		return newid;
	}

	public String getPassword(int ID) {
		dbConnect();
		String oldpass = "";
		String sql = "select * from user where id = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ID);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				oldpass = rst.getString(7);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return oldpass;
	}

	public void updateNewPassword(String newpass, int ID) {
		dbConnect();
		String sql = "update user SET password = ? where id = ? ";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newpass);
			pstmt.setInt(2, ID);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
	}

	public String getFoodName(int foodOrderId) {
		dbConnect();
		String foodName = "";
		String sql = "select * from menu where id=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, foodOrderId);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				foodName = rst.getString(2);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return foodName;
	}

	public String getName(int userId) {
		dbConnect();
		String userName = "";
		String sql = "select * from user where id=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, userId);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				userName = rst.getString(2);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return userName;
	}

	public boolean getdoVendorLogin(int venId, String password) {
		dbConnect();
		boolean venStatus = false;
		String sql = "select * from vendor where id =? AND password = ?";
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setInt(1, venId);
			pstmt.setString(2, password);
			ResultSet rst = pstmt.executeQuery();

			if (rst.next()) {
				venStatus = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return venStatus;
	}

	public ArrayList<Menu> fetchMenu(int venId) {
		dbConnect();
		String sql = "select * from menu where vendorid =?";
		ArrayList<Menu> list = new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setInt(1, venId);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {

				int id = rst.getInt(1);
				String name = rst.getString(2);
				String foodType = rst.getString(3);
				double price = rst.getDouble(4);
				int vendorid = rst.getInt(5);
				double foodCalories = rst.getDouble(6);

				Menu menu = new Menu(id, name, foodType, price, vendorid, foodCalories);
				list.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return list;
	}

	public Vendor fetchVendorDetails(int venId) {
		dbConnect();
		Vendor v = new Vendor();
		String sql = "select * from vendor where id = ?";
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setInt(1, venId);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {

				int id = rst.getInt(1);
				String name = rst.getString(2);
				String phone = rst.getString(3);
				String email = rst.getString(4);
				String speciality = rst.getString(5);

				v.setId(id);
				v.setName(name);
				v.setPhone(phone);
				v.setEmail(email);
				v.setSpeciality(speciality);

				break;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbClose();
		return v;
	}

	public void updateMenuType(String newType, int serialNo) {
		dbConnect();
		String sql = "update menu SET foodtype = ? where id = ? ";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newType);
			pstmt.setInt(2, serialNo);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();

	}

	public void updateMenuName(String newName, int serialNo1) {
		dbConnect();
		String sql = "update menu SET foodname = ? where id = ? ";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newName);
			pstmt.setInt(2, serialNo1);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateMenuPrice(double newPrice, int serialNo11) {
		dbConnect();
		String sql = "update menu SET foodprice = ? where id = ? ";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setDouble(1, newPrice);
			pstmt.setInt(2, serialNo11);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateNewFood(String newFoodType, String newFoodName, double newFoodPrice, int venId) {
		dbConnect();
		String sql = "insert into menu(foodtype,foodname,foodprice,vendorid) values(?,?,?,?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newFoodType);
			pstmt.setString(2, newFoodName);
			pstmt.setDouble(3, newFoodPrice);
			pstmt.setInt(4, venId);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();

	}

	public void deleteFoodItem(int deleteFood) {
		dbConnect();
		String sql = "delete from menu where id=?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, deleteFood);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
	}

	public String getVendorName(int venId) {
		dbConnect();
		String name = "";
		String sql = "select * from vendor where id = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, venId);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				name = rst.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbClose();
		return name;
	}

	public ArrayList<Order> getCancelOrderPrice(int cancelip) {
		dbConnect();
		ArrayList<Order> refundlist = new ArrayList<>();
		String sql = " select foodPrice, quantity from order_history as o, menu as m where m.id = o.foodid "
				+ "AND o.userid = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cancelip);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {

				double itemPrice = rst.getDouble(2);
				int quantity = rst.getInt(3);

				Order order = new Order(sql, itemPrice, quantity, null, sql, 0);
				refundlist.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbClose();
		return refundlist;
	}

	public ArrayList<Order> fetchOrderHistoryToCancel(String cancelip) {
		dbConnect();
		ArrayList<Order> list = new ArrayList<>();
		String sql = " select m.foodName,m.foodPrice, o.quantity, o.processdate, o.status, m.vendorid from order_history as o, menu as m where m.id = o.foodid "
				+ "AND m.foodname = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cancelip);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {

				String itemName = rst.getString(1);
				double itemPrice = rst.getDouble(2);
				int quantity = rst.getInt(3);
				String processDate = rst.getString(4);
				String status = rst.getString(5);
				int vendorid = rst.getInt(6);

				Order order = new Order(itemName, itemPrice, quantity, LocalDate.parse(processDate), status, vendorid);
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return list;
	}

	public int getFoodId(String cancelip) {
		dbConnect();
		int foodid = 0;
		String sql = "select * from menu where foodname = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cancelip);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				foodid = rst.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbClose();
		return foodid;
	}

	public int deleteOrderId(int cancelid) {
		dbConnect();
		int deleteid = 0;
		String sql = "select id from order_history where foodid = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cancelid);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				deleteid = rst.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return deleteid;
	}

	public void CancelOrder(int deleteorderid) {
		dbConnect();
		String sql = "delete from order_history where id = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, deleteorderid);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbClose();

	}

	public void updateOrderStatus(int orderId, String orderStatus, int venId) {
		dbConnect();
		String sql = "update order_history SET status = ? where id = ? AND vendorid =?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, orderStatus);
			pstmt.setInt(2, orderId);
			pstmt.setInt(3, venId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();

	}

	public int getVendorId(int foodOrderId1) {
		dbConnect();
		int vendorId = 0;
		String sql = "select * from menu where id = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, foodOrderId1);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				vendorId = rst.getInt(5);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dbClose();
		return vendorId;
	}

	public ArrayList<Order> fetchOrderHistoryForVendor(int venId) {
		dbConnect();
		ArrayList<Order> list = new ArrayList<>();
		String sql = " select m.foodName,m.foodPrice, o.quantity, o.processdate, o.status, m.vendorid from order_history as o, menu as m where m.id = o.foodid "
				+ "AND m.vendorid = ?";
		try {
			PreparedStatement pstmt = con.prepareCall(sql);
			pstmt.setInt(1, venId);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {

				String itemName = rst.getString(1);
				double itemPrice = rst.getDouble(2);
				int quantity = rst.getInt(3);
				String processDate = rst.getString(4);
				String status = rst.getString(5);
				int vendorid = rst.getInt(6);

				Order order = new Order(itemName, itemPrice, quantity, LocalDate.parse(processDate), status, vendorid);
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return list;
	}

	public void updateNewVendorLogin(String venName, String venPhone, String venEmail, String venSpec, String venPassword) {
		dbConnect();
		String sql = "insert into vendor(name,phone,email,speciality,password) values(?,?,?,?,?)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, venName);
			pstmt.setString(2, venPhone);
			pstmt.setString(3, venEmail);
			pstmt.setString(4, venSpec);
			pstmt.setString(5, venPassword);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
	}

	public int getNewVendorId(String venPassword1) {
		dbConnect();
		int newid = 0;
		String sql = "select id from vendor where password = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, venPassword1);
			ResultSet rst = pstmt.executeQuery();

			if (rst.next()) {
				newid = rst.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return newid;
	}

	public String getPasswordofVendor(int ID) {
		dbConnect();
		String oldpass = "";
		String sql = "select * from vendor where id = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ID);
			ResultSet rst = pstmt.executeQuery();

			while (rst.next()) {
				oldpass = rst.getString(6);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		return oldpass;
	}

	public void updateNewPasswordofVendor(String newpass, int ID) {
		dbConnect();
		String sql = "update vendor SET password = ? where id = ? ";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newpass);
			pstmt.setInt(2, ID);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		dbClose();
		
	}

}
