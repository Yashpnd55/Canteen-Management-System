package com.Cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.Factory.Factory;
import com.Model.Menu;
import com.Model.Order;
import com.Model.User;
import com.Model.Vendor;
import com.Model.User;


// GitHub project sync check 
public class CliMain {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int venId = 0;
		int userId = 0;
		String password = "";
		boolean loginState = true;
		while (loginState) {

			System.out.println("Canteen Management System");
			System.out.println("1. Login");
			System.out.println("2. New Registration");
			System.out.println("3. Change Password");
			System.out.println("4. Log Out");

			int startInput = sc.nextInt();

			switch (startInput) {

			case 1:
				System.out.println("=============WELCOME TO CUSTOMER LOGIN=================");
				System.out.println("Enter the UserId/VendorId: ");
				userId = sc.nextInt();
				System.out.println("Enter the Password: ");
				password = sc.next();

				venId = userId;
				boolean doVendorLogin = Factory.doVendorLogin(venId, password);

				boolean loginStatus = Factory.doLogin(userId, password);
				if (loginStatus == true) {

					String userName = Factory.getName(userId);
					System.out.println("Login Successfull!! Welcome " + userName);

					boolean status = true;
					while (status) {
						System.out.println("1. Show Menu");
						System.out.println("2. Placing Order");
						System.out.println("3. Order History");
						System.out.println("4. Profile ");
						System.out.println("5. Wallet Balance");
						System.out.println("6. Cancel Order");
						System.out.println("7. Rating Order");
						System.out.println("8. Total Calories Consumption");
						System.out.println("9. Previous Menu");

						int input = sc.nextInt();

						switch (input) {

						case 1: //////////////////// SHOW MENU ///////////////////
							System.out.println("----------Displaying MENU--------------");
							ArrayList<Menu> list = Factory.displayMenu();
							for (Menu m : list) {
								System.out.println(m.getId() + "   " + m.getName() + "   " + m.getFoodType() + "   "
										+ m.getPrice() + "  " + m.getVendorid() + "  " + m.getFooCalories());
							}
							System.out.println("-------------------------------------------------");

							System.out.println("You want to see ratings and review of food items ?");
							System.out.println("1. Yes");
							System.out.println("2. No");

							int ip = sc.nextInt();

							if (ip == 1) {
								System.out.println("Enter the ID of the item you want review for ");

								while (true) {
									int foodId = sc.nextInt();
									if (foodId == 0)
										break;

									ArrayList<String> List = Factory.getReviews(foodId);
									System.out.println("Reviews and Ratings for Item with ID " + foodId + "-->");
									for (String s : List) {
										System.out.println("Review - " + s);
									}
									int rating = Factory.getRating(foodId);
									System.out.println("Rating - " + rating + "/5");
									System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
									break;
								}
							} else {
							}
							System.out.println("Enter 0 to go back to Main Menu");
							int ip1 = sc.nextInt();
							if (ip1 == 0) {
								break;
							}

						case 2: ///////////////////////// PLACING ORDER //////////////////////////

							HashMap<Integer, Integer> map = new HashMap<>();
							System.out.println("----------Displaying MENU--------------");
							ArrayList<Menu> list1 = Factory.displayMenu();
							for (Menu m : list1) {
								System.out.println(m.getId() + "   " + m.getName() + "   " + m.getFoodType() + "   "
										+ m.getPrice() + "  " + m.getVendorid() + "  " + m.getFooCalories());
							}
							System.out.println("-------------------------------------------------");

							int foodOrderId1 = 0;
							int foodQuantity = 0;
							int vendorId = 0;
							boolean reorder = true;
							while (reorder) {
								System.out.println("Enter the ID of the food you would like to order");
								foodOrderId1 = sc.nextInt();
								System.out.println("Enter the quantity of the ordered food");
								foodQuantity = sc.nextInt();

								vendorId = Factory.getVendorId(foodOrderId1);

								map.put(foodOrderId1, foodQuantity);

								System.out.println("Press 1 to add more items");
								System.out.println("Press 2 for Placing Order");
								int act = sc.nextInt();

								if (act == 2) {
									reorder = false;
								}
							}

							System.out.println("Placing your Order...");

							double totalCost = Factory.totalCost(list1, map);
							double walletBalance = Factory.getcurrentBalance(userId);

							if (walletBalance < totalCost) {
								System.out.println("Insufficient Balance");
								System.out.println("Your Current Balance is INR. " + walletBalance);
								System.out.println("Total Cost on the order is INR. " + totalCost);
							} else {
								String coupon = Factory.getCoupon(userId);
								if (coupon == null) {
									coupon = "FIRST_BUY_" + userId;
									double originaltotalCost = totalCost;
									totalCost = totalCost * 0.9;
									Factory.updateCoupon(coupon, userId);
									System.out.println("Total order cost: INR. " + totalCost);
									System.out.println("Discount of 10% is given for the Customer who are new!!");
									System.out.println("Discount provided was INR." + (originaltotalCost * 0.1));
									System.out
											.println("New Wallet Balance: INR. " + (walletBalance - originaltotalCost));

								} else {
									Factory.updateBalance(walletBalance - totalCost, userId);
									// save it in order_history
									Factory.saveOrderHistory(map, userId, vendorId);
									System.out.println("Total order cost: INR. " + totalCost);
									System.out.println("New Wallet Balance: INR. " + (walletBalance - totalCost));

									System.out.println("-------------------------------------------------------");
								}
							}

							String foodName = Factory.getFoodName(foodOrderId1);
							System.out.println("Your Order has been placed successfully!!");
							System.out.println("You have ordered " + foodQuantity + " " + foodName);
							System.out.println(
									"Thank you!! You will get the Confirmation on your order soon in Order History");
							System.out.println("-------------------------------------------------------");
							break;

						case 3: //////////////////////////// ORDER HISTORY//////////////////////////////
							System.out
									.println("*************************ORDER HISTORY***********************************"
											+ userId);
							ArrayList<Order> order = Factory.fetchOrderHistory(userId);
							int srNo = 1;
							System.out.println(
									"srNo \t Item Name \t Item Price \t Quantity \t Total amount \t Purchase Date \t Order Status \t VendorId");
							for (Order o : order) {
								System.out.println(srNo + "." + "\t" + o.getFoodName() + "\t" + o.getFoodPrice() + "\t"
										+ o.getQuantity() + "\t"
										+ (o.getFoodPrice() * o.getQuantity() + "\t" + o.getProcessaDate().toString())
										+ "\t" + o.getStatus() + "\t" + o.getVendorid());
								srNo++;
							}
							System.out.println("********************************************************************");
							break;

						case 4: ////////////////////////////// PROFILE///////////////////////////
							System.out.println(
									"********************************CUSTOMER PROFILE***********************************");
							User user = Factory.fetchCustomerDetails(userId);
							System.out.println("Customer ID: " + user.getId());
							System.out.println("Name: " + user.getName());
							System.out.println("Phone: " + user.getPhone());
							System.out.println("Email: " + user.getEmail());
							System.out.println("Wallet Balance: INR. " + user.getWalletBalance());
							break;
						case 5:////////////////////////// WALLET BALANCE ///////////////////////////
							System.out.println("=========================WALLET BALANCE===================");
							double currentBalance = Factory.getcurrentBalance(userId);
							System.out.println("The Current Balance is : INR." + currentBalance);

							/////////////// UPDATE BALANCE ///////////////
							System.out.println("Would you like to Update your Balance ?");
							System.out.println("1. Yes ");
							System.out.println("2. No");

							int action = sc.nextInt();
							if (action == 1) {
								System.out.println("How much Money would you like to add in yor wallet?");
								double amount = sc.nextDouble();
								double updatedBalance = currentBalance + amount;
								Factory.updateBalance(updatedBalance, userId);
								System.out.println("You Updated Balance is : INR." + updatedBalance);
								break;
							} else {
								break;
							}

						case 6:
							System.out.println("**************CANCEL ORDER******************");

							System.out
									.println("*************************ORDER HISTORY***********************************"
											+ userId);
							ArrayList<Order> order1 = Factory.fetchOrderHistory(userId);
							int srNo1 = 1;
							System.out.println(
									"srNo \t Item Name \t Item Price \t Quantity \t Total amount \t Purchase Date \t Order Status \t VendorId");
							for (Order o : order1) {
								System.out.println(srNo1 + "." + "\t" + o.getFoodName() + "\t" + o.getFoodPrice() + "\t"
										+ o.getQuantity() + "\t"
										+ (o.getFoodPrice() * o.getQuantity() + "\t" + o.getProcessaDate().toString())
										+ "\t" + o.getStatus() + "\t" + o.getVendorid());
								srNo1++;
							}

							Scanner sc1 = new Scanner(System.in);
							System.out.println("Enter the name of the food you would like to Cancel: ");
							String cancelip = sc1.nextLine();
							System.out.println(cancelip);

							ArrayList<Order> refundlist = Factory.fetchOrderHistoryToCancel(cancelip);
							double walletbalance = Factory.getcurrentBalance(userId);
							double price = 0;
							double quan = 0;
							for (Order o : refundlist) {
								price = o.getFoodPrice();
								quan = o.getQuantity();
								System.out.println("Your current wallet balance is Rs." + walletbalance);
								System.out.println("Unit Price on order is Rs." + price);
								System.out.println("Quantity you ordered is " + quan);
								System.out.println("Total Cost on the order is Rs." + (price * quan));
								break;
							}

							int cancelid = Factory.getFoodId(cancelip);

							int deleteorderid = Factory.deleteOrderId(cancelid);

							System.out.println("Press 1 to confirm Order Cancellation: ");
							int confirm = sc.nextInt();

							double updatedWalletBalance = 0;
							if (confirm == 1) {
								updatedWalletBalance = walletbalance + (price * quan);
								Factory.CancelOrder(deleteorderid);
								System.out.println("The order has been Cancelled!!");
								Factory.updateBalance(updatedWalletBalance, userId);
								System.out.println("Your updated wallet balance is Rs." + updatedWalletBalance);

								System.out.println("Press 0 to go back");
								int goback = sc.nextInt();
								if (goback == 0) {
									break;
								}
							} else {
								break;
							}

						case 7:
							System.out.println("*********************RATINGS & REVIEWS********************");
							System.out.println("----------Displaying MENU--------------");
							ArrayList<Menu> list11 = Factory.displayMenu();
							for (Menu m : list11) {
								System.out.println(m.getId() + "   " + m.getName() + "   " + m.getFoodType() + "   "
										+ m.getPrice() + "  " + m.getVendorid() + "  " + m.getFooCalories());
							}
							System.out.println("-------------------------------------------------");

							System.out.println("Enter the ID of the food you want Reviews for: ");
							int review = sc.nextInt();

							ArrayList<String> List = Factory.getReviews(review);
							System.out.println("Reviews and Ratings for Item with ID " + review + "-->");
							for (String s : List) {
								System.out.println("Review - " + s);
							}
							int rating = Factory.getRating(review);
							System.out.println("Rating - " + rating + "/5");
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							System.out.println("Press 0 to get back ");
							int inp1 = sc.nextInt();
							if (inp1 == 0) {
								break;
							} else {
								System.out.println("Press 0 to get back");
							}

						case 8:
							System.out.println("*************** TOTAL CLORIES CONSUMPTION***********************");
							HashMap<Integer, Integer> map1 = new HashMap<>();
							System.out.println("----------Displaying MENU--------------");
							ArrayList<Menu> list111 = Factory.displayMenu();
							for (Menu m : list111) {
								System.out.println(m.getId() + "   " + m.getName() + "   " + m.getFoodType() + "   "
										+ m.getPrice() + "  " + m.getVendorid() + "  " + m.getFooCalories());
							}
							System.out.println("-------------------------------------------------");
							System.out.println("Enter the Food Id of the food you have ordered: ");
							int orderIdFood = sc.nextInt();
							System.out.println("Enter the Quantity of the food: ");
							int quantityFood = sc.nextInt();

							map1.put(orderIdFood, quantityFood);

							double totalCalories = Factory.totalCalories(map1, list111);
							System.out.println("Total Calories Consumption is " + totalCalories + " Cal.");
							System.out.println("**************************************");
							System.out.println("Press 0 to get back ");
							int inp = sc.nextInt();
							if (inp == 0) {
								break;
							} else {
								System.out.println("Press 0 to get back");
							}

						case 9:
							status = false;
							break;

						default:
						}
					}
				}

				if (doVendorLogin == true) {

					String VendorName = Factory.getVendorName(venId);
					System.out.println("Login Successfull!! Welcome " + VendorName);

					boolean status = true;
					while (status) {
						System.out.println("1. Show Menu");
						System.out.println("2. Accept & Reject");
						System.out.println("3. Order History");
						System.out.println("4. Edit Menu ");
						System.out.println("5. Vendor Profile");
						System.out.println("6. Previous Menu");

						int input = sc.nextInt();

						switch (input) {

						case 1:
							System.out.println("----------Displaying MENU--------------");
							ArrayList<Menu> list = Factory.displayMenu(venId);
							for (Menu m : list) {
								System.out.println(m.getId() + "   " + m.getName() + "   " + m.getFoodType() + "   "
										+ m.getPrice() + "  " + m.getVendorid() + "  " + m.getFooCalories());
							}
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							break;

						case 2:
							System.out.println("**********************Accept & Reject Order***********************");

							System.out
									.println("*************************ORDER HISTORY***********************************"
											+ venId);
							ArrayList<Order> order1 = Factory.fetchOrderHistoryForVendor(venId);
							int SrNo = 1;
							System.out.println(
									"SrNo \t Item Name \t Item Price \t Quantity \t Total amount \t Purchase Date");
							for (Order o : order1) {
								System.out.println(SrNo + "." + "\t" + o.getFoodName() + "\t" + o.getFoodPrice() + "\t"
										+ o.getQuantity() + "\t"
										+ (o.getFoodPrice() * o.getQuantity() + "\t" + o.getProcessaDate().toString())
										+ "\t" + o.getStatus() + "\t" + o.getVendorid());
								SrNo++;
							}
							System.out.println("Enter the name of the food of which you want to change status ");
							Scanner sc2 = new Scanner(System.in);
							String orderName = sc2.nextLine();
							Scanner sc1111 = new Scanner(System.in);
							System.out.println("Update Order status ");
							String orderStatus = sc1111.nextLine();

							int foodId1 = Factory.getFoodId(orderName);

							int orderId = Factory.deleteOrderId(foodId1);

							Factory.updateOrderStatus(orderId, orderStatus, venId);

							System.out.println("The order Status is Updated.");

							System.out.println("Enter 0 to see the updated order History");
							int updatedOrderStatus = sc.nextInt();

							if (updatedOrderStatus == 0) {
								System.out.println(
										"*************************ORDER HISTORY***********************************"
												+ venId);
								ArrayList<Order> order11 = Factory.fetchOrderHistoryForVendor(venId);
								int SrNo1 = 1;
								System.out.println(
										"SrNo \t Item Name \t Item Price \t Quantity \t Total amount \t Purchase Date");
								for (Order o : order11) {
									System.out.println(SrNo1 + "." + "\t" + o.getFoodName() + "\t" + o.getFoodPrice()
											+ "\t" + o.getQuantity() + "\t"
											+ (o.getFoodPrice() * o.getQuantity() + "\t"
													+ o.getProcessaDate().toString())
											+ "\t" + o.getStatus() + "\t" + o.getVendorid());
									SrNo1++;
								}
								System.out.println(
										"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							}

							System.out.println("Press 0 to go back");
							int gobackoption = sc.nextInt();

							if (gobackoption == 0) {
								break;
							}

							else {
								break;
							}

						case 3:
							System.out
									.println("*************************ORDER HISTORY***********************************"
											+ venId);
							ArrayList<Order> order11 = Factory.fetchOrderHistoryForVendor(venId);
							int SrNo1 = 1;
							System.out.println(
									"SrNo \t Item Name \t Item Price \t Quantity \t Total amount \t Purchase Date");
							for (Order o : order11) {
								System.out.println(SrNo1 + "." + "\t" + o.getFoodName() + "\t" + o.getFoodPrice() + "\t"
										+ o.getQuantity() + "\t"
										+ (o.getFoodPrice() * o.getQuantity() + "\t" + o.getProcessaDate().toString())
										+ "\t" + o.getStatus() + "\t" + o.getVendorid());
								SrNo1++;
							}
							System.out.println(
									"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`");
							break;

						case 4:
							System.out.println("**************EDIT MENU*****************");
							System.out.println("----------Displaying MENU--------------");
							ArrayList<Menu> list11 = Factory.displayMenu(venId);
							for (Menu m : list11) {
								System.out.println(m.getId() + "   " + m.getName() + "   " + m.getFoodType() + "   "
										+ m.getPrice() + "  " + m.getVendorid() + "  " + m.getFooCalories());
							}

							System.out.println("Enter the Edit option from below ");
							System.out.println("1. Edit Type ");
							System.out.println("2. Edit Name ");
							System.out.println("3. Edit Price ");
							System.out.println("4. Add new Food Item ");
							System.out.println("5. Delete Food Item");

							int editOption = sc.nextInt();

							switch (editOption) {
							case 1:
								System.out.println("Enter the serial number of the food you would like to edit");
								int serialNo = sc.nextInt();
								System.out.println("Enter the new Type ");
								String newType = sc.next();

								Factory.updateMenuType(newType, serialNo);

								System.out.println("The Menu is Edited!!");

								System.out.println("Enter 0 to see the Updated Menu ");
								int updatedMenu = sc.nextInt();
								if (updatedMenu == 0) {
									System.out.println("----------Displaying MENU--------------");
									ArrayList<Menu> list111 = Factory.displayMenu(venId);
									for (Menu m : list111) {
										System.out.println(m.getId() + "   " + m.getName() + "   " + m.getFoodType()
												+ "   " + m.getPrice() + "  " + m.getVendorid() + "  "
												+ m.getFooCalories());
									}
									System.out.println("-------------------------------------------------");
									break;
								}

								else {
									break;
								}

							case 2:
								System.out.println("Enter the serial number of the food you would like to edit");
								int serialNo1 = sc.nextInt();
								Scanner sc11 = new Scanner(System.in);
								System.out.println("Enter the new Name ");
								String newName = sc11.nextLine();

								Factory.updateMenuName(newName, serialNo1);

								System.out.println("The Menu is Edited!!");

								System.out.println("Enter 0 to see the Updated Menu ");
								int updatedMenu1 = sc.nextInt();
								if (updatedMenu1 == 0) {
									System.out.println("----------Displaying MENU--------------");
									ArrayList<Menu> list111 = Factory.displayMenu(venId);
									for (Menu m : list111) {
										System.out.println(m.getId() + "   " + m.getName() + "   " + m.getFoodType()
												+ "   " + m.getPrice() + "  " + m.getVendorid() + "  "
												+ m.getFooCalories());
									}
									System.out.println("-------------------------------------------------");
									break;
								}

								else {
									break;
								}

							case 3:
								System.out.println("Enter the serial number of the food you would like to edit");
								int serialNo11 = sc.nextInt();
								System.out.println("Enter the new Price ");
								double newPrice = sc.nextDouble();

								Factory.updateMenuPrice(newPrice, serialNo11);

								System.out.println("The Menu is Edited!!");

								System.out.println("Enter 0 to see the Updated Menu ");
								int updatedMenu11 = sc.nextInt();
								if (updatedMenu11 == 0) {
									System.out.println("----------Displaying MENU--------------");
									ArrayList<Menu> list111 = Factory.displayMenu(venId);
									for (Menu m : list111) {
										System.out.println(m.getId() + "   " + m.getName() + "   " + m.getFoodType()
												+ "   " + m.getPrice() + "  " + m.getVendorid() + "  "
												+ m.getFooCalories());
									}
									System.out.println("-------------------------------------------------");
									break;
								}

								else {
									break;
								}

							case 4:
								System.out.println("**********ADD NEW FOOD ITEM**************");
								System.out.println("Enter the Type of the Food ");
								String newFoodType = sc.next();
								Scanner sc111 = new Scanner(System.in);
								System.out.println("Enter the Name of the Food ");
								String newFoodName = sc111.nextLine();
								System.out.println("Enter the Price of the Food ");
								double newFoodPrice = sc.nextDouble();

								Factory.updateNewFood(newFoodType, newFoodName, newFoodPrice, venId);

								System.out.println("The Menu is Updated!!");

								System.out.println("Enter 0 to see the Updated Menu ");
								int updatedMenu111 = sc.nextInt();
								if (updatedMenu111 == 0) {
									System.out.println("----------Displaying MENU--------------");
									ArrayList<Menu> list111 = Factory.displayMenu(venId);
									for (Menu m : list111) {
										System.out.println(m.getId() + "   " + m.getName() + "   " + m.getFoodType()
												+ "   " + m.getPrice() + "  " + m.getVendorid() + "  "
												+ m.getFooCalories());
									}
									System.out.println("-------------------------------------------------");
									break;
								} else {
									break;
								}

							case 5:
								System.out.println("************DELETE FOOD ITEM*************");
								System.out.println("Enter the serial number of the Food Item you want to delete ");
								int deleteFood = sc.nextInt();

								Factory.deleteFoodItem(deleteFood);

								System.out.println("The Menu is Updated!!");

								System.out.println("Enter 0 to see the Updated Menu ");
								int updatedMenu1111 = sc.nextInt();
								if (updatedMenu1111 == 0) {
									System.out.println("----------Displaying MENU--------------");
									ArrayList<Menu> list111 = Factory.displayMenu(venId);
									for (Menu m : list111) {
										System.out.println(m.getId() + "   " + m.getName() + "   " + m.getFoodType()
												+ "   " + m.getPrice() + "  " + m.getVendorid() + "  "
												+ m.getFooCalories());
									}
									System.out.println("-------------------------------------------------");
									break;
								}

							default:
							}
							break;

						case 5: ///////////// VENDOR PROFILE////////////////
							System.out.println("**********************Vendor Profile**********************");
							Vendor vendor = Factory.fetchVendorDetails(venId);
							System.out.println("Vendor ID: " + vendor.getId());
							System.out.println("Name: " + vendor.getName());
							System.out.println("Phone: " + vendor.getPhone());
							System.out.println("Email: " + vendor.getEmail());
							System.out.println("Speciality : " + vendor.getSpeciality());
							System.out.println("***********************************************************");
							break;

						case 6:
							status = false;
							break;

						default:
						}
					}
				}

				else {
					System.out.println("Invalid Login Details!!");
					System.out.println("Please Login Again!!");
				}
				break;

			case 2: //////////////// NEW REGISTRATION//////////////////
				System.out.println("*************NEW REGISTRATION*****************");
				System.out.println("Select one of the below Registration option");
				System.out.println("1. User Registration");
				System.out.println("2. Vendor Registration");
				int regis = sc.nextInt();

				if (regis == 1) {
					System.out.println("Enter your Name: ");
					String name = sc.next();
					System.out.println("Enter your Email address: ");
					String email = sc.next();
					System.out.println("Enter your Phone number: ");
					String phone = sc.next();
					System.out.println("Set Password: ");
					String pass = sc.next();

					Factory.updateNewLoginUser(name, email, phone, pass);

					System.out.println("Confirm your Password: ");
					String pass1 = sc.next();

					if (pass1.equals(pass)) {
						int newid = Factory.getNewUserId(pass1);
						System.out.println("You have been Registered Successfully!");
						System.out.println("Your UserId is " + newid);
						break;
					}
				}

				if (regis == 2) {
					System.out.println("Enter your Name: ");
					String venName = sc.next();
					System.out.println("Enter you Phone: ");
					String venPhone = sc.next();
					System.out.println("Enter your Email: ");
					String venEmail = sc.next();
					System.out.println("Enter you Food Speciality: ");
					String venSpec = sc.next();
					System.out.println("Set Password: ");
					String venPassword = sc.next();

					Factory.updateNewVendorLogin(venName, venPhone, venEmail, venSpec, venPassword);

					System.out.println("Confirm your Password: ");
					String venPassword1 = sc.next();

					if (venPassword1.equals(venPassword)) {
						int newid = Factory.getNewVendorId(venPassword1);
						System.out.println("You have been Registered Successfully");
						System.out.println("Your VendorId is " + newid);
						break;
					}

					else {
						break;
					}

				}
			case 3:
				System.out.println("****************CHANGE PASSWORD*****************");

				System.out.println("Select one of the below Password Change option");
				System.out.println("1. User ");
				System.out.println("2. Vendor ");
				int passOption = sc.nextInt();

				if (passOption == 1) {

					System.out.println("Enter your UserId: ");
					int ID = sc.nextInt();
					System.out.println("Enter your Current Password: ");
					String oldpass = sc.next();
					String checkpassword = Factory.getPassword(ID);
					if (checkpassword.equals(oldpass)) {
						System.out.println("Enter the New Password: ");
						String newpass = sc.next();
						Factory.updateNewPassword(newpass, ID);
						System.out.println("Your password has been changed successfully!!");

						break;
					} else {
						System.out.println("Your old password you entered is Incorrect!");
						break;
					}
				}

				if (passOption == 2) {
					System.out.println("Enter your VendorId: ");
					int ID = sc.nextInt();
					System.out.println("Enter your Current Password: ");
					String oldpass = sc.next();
					String checkpassword = Factory.getPasswordofVendor(ID);
					if (checkpassword.equals(oldpass)) {
						System.out.println("Enter the New Password: ");
						String newpass = sc.next();
						Factory.updateNewPasswordofVendor(newpass, ID);
						System.out.println("Your password has been changed successfully!!");

						break;
					} else {
						System.out.println("Your old password you entered is Incorrect!");
						break;
					}
				}

				else {
					break;
				}

			case 4:
				loginState = false;
			default:
			}
		}
		System.out.println("You are Logged Out!! Thank You! Please visit again");
	}
}
