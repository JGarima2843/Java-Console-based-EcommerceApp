package com.Views.ex;
import com.config.ex.User;
import java.sql.Connection;
import java.util.Scanner;
public class user_view {
    public void userDashboard(Connection con){
        Scanner sc=new Scanner(System.in);
        System.out.println("Hurray you are in User's Dashboard : Please do Login ");
        System.out.println("Enter your email id :");
        String email,pass ;
        email=sc.nextLine();
        System.out.println("Enter your account password : ");
        pass=sc.nextLine();
        User user=new User();
        boolean login= user.UserLogin(email,pass,con) ;
        if(login){
            userFunctions(con,user);
        }else{
            invalidCandidature(con);
        }
    }
    public void invalidCandidature(Connection con){
        System.out.println("Hey You have entered Wrong email Id or Password !!");
        Scanner sc=new Scanner(System.in);
        System.out.println("Hey You have entered Wrong email Id or Password !!");
        System.out.println("Hey Do you want to Try again---> Press 1 otherwise Press 0");
        short key ;
        key=sc.nextShort();
        if(key==1){userDashboard(con);}
        else {return;}
    }
    public void userFunctions(Connection con,User  user){
//        User user=new User();
        Scanner sc=new Scanner(System.in);
        System.out.println("Hey You are Logged in to User's Panel --> Please read the below Instructions ");
        System.out.println("Press 1: To Show all the Products Available ");
        System.out.println("Press 2: To show your all Orders ");
        System.out.println("Press 3: To edit your Profile ");
        System.out.println("Press 4: To exit from the application");
        int check ;
        check= sc.nextInt();
        if(check==1){user.ViewAllProducts(con);}
        else if (check==2) {user.ShowAllOrders(con);}
        else if(check==3){user.editProfile(con);}
        else if(check==4){return ;}
    }
}
