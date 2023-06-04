package com.config.ex;
import com.Views.ex.admin_view;
import com.sun.tools.javac.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
    static int admin_id ;
    String name ;
    String email ;
    String password ;


    // TODO: it will check for the authentication of the email and password and then login to the app
    public boolean AdminLogin(Connection con,String email, String pass){
        String query="select * from admin";
//        System.out.println("email : "+email +"\n"+"pass :"+pass);
        boolean check=false ;
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if(!res.next()){
                System.out.println("No Admin exists !!");
            }
            while (res.next()) {
                int id = res.getInt("_id");
                String name = res.getString("name");
                String _email = res.getString("email");
                String password=res.getString("password");
//                System.out.println(id + "  " + name+_email+password);
                if (_email.equals(email) && password.equals(pass)){
                    check=true ;
                    break ;
                }
            }
        }
        catch(Exception e){
            System.out.println("Something Went wrong !! ");

        }
        return check ;

    };
    //TODO: this function will show all the registered user in app
    public void ShowAllUsers(Connection con){
        Scanner sc=new Scanner(System.in);
        try {
            String query = "select * from user";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if(!res.next()){
                System.out.println("No users Exists !!");
            }
            while (res.next()) {
                int id = res.getInt("_id");
                String name = res.getString("_name");
                String email = res.getString("email");
                String add=res.getString("address");
                Date dob=res.getDate("dob");
                System.out.println("🚹User-ID : "+id + "  " + name + "  " + email+"  "+add+"  "+dob);
            }

            System.out.println("👤 Hey !! ");
//            System.out.println("1) For Returning To the Main Menu Press 3 ");
            System.out.println("2) For Deleting any User Enter 1");
            System.out.println("3) Go to Admin Panel Press 2");
            System.out.println("4) Enter 0 to exit ");
            try {
                int key = sc.nextInt();
                if (key == 1) {
                    System.out.println("Enter User ID : ");
                    key = sc.nextInt();
                    RemoveTheUser(con, key);
                } else if (key == 2) {
                    admin_view ad = new admin_view();
                    ad.adminFunctions(con, this);
                }
                else {
                    return;
                }
            }catch (Exception e){
                System.out.println("Invalid Input entered !!");
            }

        }
        catch(Exception e) {
//            System.out.println(e);
            System.out.println("Something Went Wrong !!");
        }
        admin_view ad= new admin_view();
        ad.adminFunctions(con,this);

    };

    // TODO:   this function will create the user
    public void CreateUser(String name ,String email,String pass,String ph,String add,Connection con){
        Scanner sc=new Scanner(System.in);
        try {
            if(email.contains("@gmail.com")) {
                if(ph.length()==10) {
                    PreparedStatement pr = con.prepareStatement("insert into user (_name,email,_password,address) values(?,?,?,?)");
                    pr.setString(1, name);
                    pr.setString(2, email);
                    pr.setString(3, pass);
                    pr.setString(4, add);
                    pr.executeUpdate();
                    System.out.println("User Created Successfully !! ");
                }
                else{
                    System.out.println("Invalid Phone Number:");
                    System.out.println("Enter 10 digit Phone Number");
                    String st ;
                    st=sc.nextLine();
                    CreateUser(name,email,pass,st,add,con);
                }
            }
            else{
                System.out.println("Invalid email ID:");
                System.out.println("Enter email which contains @gmail.com in last");
                String st ;
                st=sc.nextLine();
                CreateUser(name,st,pass,ph,add,con);
            }
        }
        catch (Exception e){
//            System.out.println(e+"Line 79");
            System.out.println("Something Went Wrong !! ");
        }
        System.out.println("Hey If you want to go to Admin Panel Press 2  OR || Press 1 to go to the Main Menu OR|| Press 0 for exit !!");
        System.out.println("❌ If You want To Remove Any User Then Press 3 ❌");
        try {
            int key = sc.nextInt();
            if (key == 0) {
                return;
            } else if (key == 2) {
                admin_view view = new admin_view();
                view.adminFunctions(con, this);
            } else if (key == 3) {
                System.out.println("Enter The User Id : ");
                key = sc.nextInt();
                RemoveTheUser(con, key);
            }else if(key==1){
                Main.main(null);
            }
        }catch (Exception err){
            System.out.println("Invalid Input entered !!");
            admin_view view = new admin_view();
            view.adminFunctions(con, this);
        }

    }
    // TODO:   this will remove the user and the user is no longer to log in the application
    public void RemoveTheUser(Connection con,int id){
        try{
            PreparedStatement pr=con.prepareStatement("delete from user where _id=?");
            pr.setInt(1,id);
            pr.executeUpdate();
            System.out.println("User Deleted Successfully");

        }
        catch (Exception e){
//            System.out.println(e+"Line 84");
            System.out.println("Something Went Wrong !!");

        }
        admin_view view = new admin_view();
        view.adminFunctions(con, this);
    }
    // TODO:   this function will add the product with the given details ;
    public void AddProduct(Connection con){
        Product pd=new Product();
        pd.AddProduct(con,this);

   };
    //TODO: this will delete that product details from the storage
    public void RemoveProduct(Connection con,int productId){
        Product pd=new Product();
        pd.DeleteProduct(con,productId,this);
    }
    //TODO: this will update the product details
    public void EditProduct(Connection con,int productId){
        Scanner sc=new Scanner(System.in);
        System.out.println("Edit Product Menu : ");
        Product pd=new Product();
        pd.UpdateProduct(con,productId);
        System.out.println("Hey do you want to continue editing on  any product details then please enter the Product Id || Otherwise Press 1 For Returning to the Admin Panel OR || Press 0 for exit !!");
        System.out.println("❌ If You want To Delete Any Product Then Press 3 ❌");
            try {
                int key = sc.nextInt();
                if (key == 0) {
                    return;
                } else if (key == 1) {
                    admin_view view = new admin_view();
                    view.adminFunctions(con, this);
                } else if (key == 3) {
                    System.out.println("Enter The Product Id : ");
                    key = sc.nextInt();
                    RemoveProduct(con, key);
                } else {
                    this.EditProduct(con, key);
                }
            } catch (Exception err) {
                System.out.println("🙅🏽 Invalid Input Entered !! 🙅🏽‍♂️ ");
                admin_view ad = new admin_view();
                ad.adminFunctions(con, this);
            }


    }
    //TODO: this function will show all the products available
    public void ShowAllProducts(Connection con){
        Scanner sc=new Scanner(System.in);
        Product pd=new Product();
        pd.showAllProducts(con);
        System.out.println("Hey do you want to edit any product details then please enter the Product Id || Otherwise Press 1 For Returning to the Admin Panel OR ||  Press 0 for exit !!");
        System.out.println("❌ If You want To Delete Any Product Then Press 3 ❌");
        try {
            int key = sc.nextInt();
            if (key == 0) {
                return;
            } else if (key == 1) {
                admin_view view = new admin_view();
                view.adminFunctions(con, this);
            } else if (key == 3) {
                System.out.println("Enter The Product Id : ");
                key = sc.nextInt();
                RemoveProduct(con, key);
            }
            else {
                this.EditProduct(con, key);
            }
        }catch (Exception err){
            System.out.println("Invalid Input entered !!"+err);
            ShowAllProducts(con);
        }
    }

}



//remove user extra
//exception
//numbering in user
//main menu option
//create user credentials
//no order
