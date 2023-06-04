package com.Views.ex;
import com.config.ex.Admin;
import java.sql.Connection;
import java.util.Scanner;
public class admin_view {
    public void adminDashboard(Connection con){
        Scanner sc=new Scanner(System.in);
        System.out.println("Hurray you are in Admin Dashboard : Please do Login ");
        System.out.println("Enter your email id :");
        String email,pass ;
        email=sc.nextLine();
        System.out.println("Enter your account password : ");
        pass=sc.nextLine();
        Admin ad=new Admin();
        boolean login=ad.AdminLogin(con,email,pass);
        if(login){
            adminFunctions(con,ad);
        }
        else{invalidCandidature(con);}
    }
    public void invalidCandidature(Connection con){
        Scanner sc=new Scanner(System.in);
        System.out.println("Hey You have entered Wrong email Id or Password !!");
        System.out.println("Hey Do you want to Try again---> Press 1 otherwise Press 0");
        short key ;
        try {
            key = sc.nextShort();
            if (key == 1) {
                adminDashboard(con);
            } else if (key == 0) {
                return;
            }
        }catch(Exception e){
                System.out.println("Invalid Input ....");
                invalidCandidature(con);
                return;
        }
    }
    public void adminFunctions(Connection con,Admin ad){
        Scanner sc=new Scanner(System.in);
        System.out.println("You are on Admin Panel Read the Below Instructions : ");
        System.out.println("Press 1 : To Know the List of the Available Products");
        System.out.println("Press 2 : To Know the List of the Available Users");
        System.out.println("Press 3 : To Create a User ");
        System.out.println("Press 4 : To add new Product");
        System.out.println("Press 5 : To Know Orders related to User ");
        System.out.println("Press 6 : To exit from the System ");
        int check ;
        try {
            check = sc.nextInt();
            if (check == 1) {
                ad.ShowAllProducts(con);
            } else if (check == 2) {
                ad.ShowAllUsers(con);
            } else if (check == 3) {
                String name;
                System.out.print("Please enter the name of the User :");
                name = sc.next();
                String em;
                System.out.print("Please enter the email of the User : ");
                em = sc.next();
                String password;
                System.out.print("Please enter the password for the account:");
                password = sc.next();
                String ph;
                System.out.print("Please enter the Phone No. of the user : ");
                ph = sc.next();
                String add;
                System.out.print("Please enter the address of the user : ");
                add = sc.next();
                System.out.println(name + " " + em + "  " + password + "  " + ph + "  " + add);
                ad.CreateUser(name, em, password, ph, add, con);
            } else if (check == 4) {
                ad.AddProduct(con);
            } else if (check == 5) {
            }  // in this different function which gives a relation b/w user and its orders
            else {
                return;
            }
        }catch (Exception e){
            System.out.println("🙋🏽‍♂️ Invalid Input Entered !!");
            adminFunctions(con,ad);
        }

    }
}
