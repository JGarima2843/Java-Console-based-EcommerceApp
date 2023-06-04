package com.config.ex;
import com.Views.ex.user_view;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class User {
    int user_id; // TODO: this is user is which is unique and it is auto intialised through static property behaviour
    String name;
    String ContactNo;
    String emailId;
    String password;
    String address;
    Date dob;

    public void ViewAllProducts(Connection con) {
        Product pd = new Product();
        boolean check=pd.showAllProducts(con);
//        TODO one more menu for ordering that product just take id of the product and show the relevant info related to orders
        if(check) {
            System.out.println("🔔🔔 Do You Want To order any Product From Available Products ---> If Yes Then enter the Product Id");
            System.out.println("Otherwise enter 0 to exit from the application");
            int key;
            Scanner sc = new Scanner(System.in);
            key = sc.nextInt();
            if (key == 0) {
                return;
            } else {
                boolean ans = OrderProduct(key, con);
                if (ans) {
                    System.out.println("Hey Your order is Successfully Placed !! ");
                    System.out.println("For going to the user Panel press 1");
                    try {
                        int a = sc.nextInt();
                        if (a == 1) {
//                    🎯 New Modificatio
                            user_view user = new user_view();
                            user.userFunctions(con, this);
                        }
                    }catch(Exception err){
                        System.out.println("Invalid Input ...");
                            user_view user = new user_view();
                            user.userFunctions(con, this);// error handling
                        }
                    }
                 else {
                    System.out.println("Some Error occur in ordering the product Please try after some time !!");
                }

            }
        }else{
            user_view user=new user_view();
            user.userFunctions(con,this);
        }
    }



    public boolean UserLogin(String e, String p, Connection con) {
        //TODO: this function works when any user tries to access his/her account . this function return 0/1 for the valid user
        boolean key = false;
        try {
//            String query ="select * from user where email=? , _password=?";  🚨Giving Error
            String query = "select * from user";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                String email = res.getString("email");
                String pass = res.getString("_password");
                if (email.equals(e) && pass.equals(p)) {
                    this.dob = res.getDate("dob");
                    this.user_id = res.getInt("_id");
                    this.name = res.getString("_name");
                    this.address = res.getString("address");
                    this.emailId = res.getString("email");
                    this.password = res.getString("_password");
                    return true;
                }
            }
        } catch (Exception err) {
            System.out.println(err);
        }
        return key;
    }

    public boolean editProfile(Connection con) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Edit Profile Menu : ");
        System.out.println("Enter Name : ");
        this.name = sc.nextLine();
        System.out.println("Enter Contact No : ");
        this.ContactNo = sc.nextLine();
        System.out.println("Enter Address : ");
        this.address = sc.nextLine();
        System.out.println("Enter Password :");
        this.password = sc.nextLine();
        try {
            PreparedStatement pr = con.prepareStatement("update user set _name=?,address=?,_password=? where _id=?");
            pr.setString(1, this.name);
            pr.setString(2, this.address);
            pr.setString(3, this.password);
            pr.setInt(4, this.user_id);
            pr.executeUpdate();
            System.out.println("Your Profile  is Edited Successfully 👍🏽");

        } catch (Exception e) {
            System.out.println(e + "Line 77");
        }

        return true;
    }

    public void RegisterUser(String email, String ph_No, String name, String pass, String add, Connection con) {
        Admin ad = new Admin();
        Scanner sc=new Scanner(System.in);
        if(email.contains("@gmail.com")) {
            ad.CreateUser(name, email, pass, ph_No, add, con);
        }else{
            System.out.println("Invalid Email Format ...");
            System.out.println("Enter a valid email which contains @gmail.com : ");
            String st;
            st=sc.nextLine();
            ad.CreateUser(name, st, pass, ph_No, add, con);
        }
    }

    ;

    public boolean OrderProduct(int productId, Connection con) {
        Scanner sc=new Scanner(System.in);
        //TODO: it receives the product related info and gives the detail whether order is placed or not
        try {
//------Step 1) finding the product with the given id --------------------------------------------
            PreparedStatement pr = con.prepareStatement("select * from products where _id=?");
            pr.setInt(1, productId);
            ResultSet res = pr.executeQuery();
            int stock;
//----------Step 2) If the product with given id exists then checking if the quantity is >= 1 or not--
            if (res.next()) {

                stock = res.getInt("quantity");
                System.out.println("Stock Product  " + stock);
                if (stock >= 1) {
//--------------Step 3) search if that particular product already exists in order schema (Redundancy Check)---
                    try {
//                        String st = ;
                        PreparedStatement prs = con.prepareStatement("select * from orders where product_id=?");
                        prs.setInt(1, productId);
                        ResultSet result = prs.executeQuery();
//----------------------Step 4) If that ID product exists in orders the JUST INC THE ITEMS BY 1 -----------------------
                        if (result.next()) {
                            try {
                                PreparedStatement prsupd = con.prepareStatement("update orders set items=items+1 where order_id=?");
                                prsupd.setInt(1, result.getInt("order_id"));
                                prsupd.executeUpdate();
                                System.out.println("Hurray Your Order is Placed !!");
                            } catch (Exception e) {
                                System.out.println(e + "line 97");
                            }
                        }
//----------------------Step 5) if the ID product doesn't exists then make record of that product in order -----------
                        else {
                            try {
                                PreparedStatement prsupd = con.prepareStatement("insert into orders (product_id,user_id) values(?,?)");
                                prsupd.setInt(1, productId);
                                prsupd.setInt(2, this.user_id);
                                prsupd.executeUpdate();
                                System.out.println("Hurray Your Order is Placed !!");
                            } catch (Exception e) {
                                System.out.println(e + "Line 109");
                            }
                        }
//------------------Step 6) Now one quantity is occupied from the total so decrement the product quantity by 1 ---------
                        PreparedStatement prs_pd = con.prepareStatement("update products set quantity=quantity-1 where _id=?");
                        prs_pd.setInt(1, productId);
                        prs_pd.executeUpdate();
                        System.out.println("Your product db is updated");
                    } catch (Exception e) {
                        System.out.println(e + "Line 121");
                    }
                }
                else{
                    System.out.println("🙅🏽‍♂️ OUT OF STOCK 🙅🏽");
                }
            }
            System.out.println("🥳 Done .. 💜");
            System.out.println("🔔 If you want to order more product the enter that product Id Otherwise--> ");
            System.out.println("✅ Press 1 for returning to user Panel ");
            System.out.println("✅ Press 0 to exit !!");
            int key = sc.nextInt();
            if (key == 1) {
                user_view user = new user_view();
                user.userFunctions(con, this);
            } else if (key == 0) {
                return true;
            } else {
                boolean ans = this.OrderProduct(key, con);
                if (!ans) {
                    System.out.println("🙃 OOps !! Something goes wrong Try after some Time !🙃 ");
                } else {
                    System.out.println("🙌🏽 Yup !! Order Placed");
                }

            }
        }
        catch(Exception e){
                System.out.println(e + "Line 130");
                return false;
            }
        return true;
        }

    public void ShowAllOrders(Connection con){
        // TODO: it will show all the order related to that user
//        Step 1) show all the products whose user_id is equal to this.user_id
        Scanner sc = new Scanner(System.in);
        try {
            PreparedStatement upd = con.prepareStatement("select products._name,products.brand,products.price,orders.order_id,orders.items from products inner join orders on orders.product_id=products._id where orders.user_id=?");
            upd.setInt(1, this.user_id);
            ResultSet res = upd.executeQuery();
            while (res.next()) {
                int item=res.getInt("items");
                if(item>0) {
                System.out.println("📦 My Orders 📦");

                    System.out.println("Order ID: " + res.getInt("order_id") + " | " + "Product Name : " + res.getString("_name") + " | " + " Product Brand : " + res.getString("brand") + "|" + " Product Price : " + res.getFloat("price") + "/-");
                }
            }
            System.out.println("Hey .. Do you want to withdraw any product then enter that Order-Id ***** Otherwise Press 0 to exit from the application or ||| Press 1 to return to user Panel : ");
            int key = sc.nextInt();
            if (key == 1) {
                user_view view = new user_view();
                view.userFunctions(con, this);
            } else if (key == 0) {
                return;
            } else {
                WithdrawOrder(con, key);
            }
        } catch (Exception e) {
            System.out.println(e + "Line 138");
        }
    }
    public void WithdrawOrder(Connection con,int orderId){
            //TODO: it will withdraw the order of the user
        Scanner sc=new Scanner(System.in);
            try {
                PreparedStatement pr = con.prepareStatement("update orders set items=items-1 where order_id=?");

                pr.setInt(1, orderId);
                pr.executeUpdate();
                System.out.println("Order Removed successfully ✅✅");
//      TODO:         See now you have to inc the stock by 1
            } catch (Exception e) {
                System.out.println(e + "Line 164");
            }

            System.out.println("🙋🏽‍♂️🙋🏽‍♂️ Hey if you want to withdraw more product then enter the product Id Otherwise : ");
            System.out.println("🎯 Press 1 for returning to User Panel ");
            System.out.println("🎯 Press 0 to exit !!!");

            int key=sc.nextInt();
            if(key==0){return ;}
            else if(key==1){
                user_view user=new user_view();
                user.userFunctions(con,this);
            }
            else{
                WithdrawOrder(con,key);
            }
        }
    }

//    duplicate user /admin
//    quantity issue of product



