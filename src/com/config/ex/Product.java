package com.config.ex;
import com.Views.ex.admin_view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class Product {
    int  product_id ;
    String title ;
    String brand ;
    int stock ;
    float price ;
    int rating ;
    // TODO: it will show all the products present
    boolean showAllProducts(Connection con){
        boolean check =false ;
        try {
            String query = "select * from products";
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(query);
            if(!res.next()){
                System.out.println("Nothing To show !!🧧🧧");
            }
            else {
                while (res.next()) {
                    int id = res.getInt("_id");
                    String name = res.getString("_name");
                    String brand = res.getString("brand");
                    int quantity = res.getInt("quantity");
                    int rating = res.getInt("rating");
                    float price = res.getFloat("price");
                    System.out.println("🎯 Product 🎯 : ");
                    System.out.println("PID: " + id + " | " + "Name : " + name + " | " + "Brand : " + brand + " | " + "quantity : " + quantity + " | " + "rating : " + rating + " | " + "Price : " + price + "/-");
                }
                check=true ;
            }

        }
        catch(Exception e) {
            System.out.println("Something Went Wrong !!");
        }
        return check ;
    }
    // TODO: it will update the product
    public void UpdateProduct(Connection con,int productId){
        Scanner sc=new Scanner(System.in);
        this.product_id=productId;
        System.out.println("Enter New Product Name : ");
        this.title=sc.nextLine();
        System.out.println("Enter the New Rating : ");
        this.rating=sc.nextInt();
        System.out.println("Enter the New Price : ");
        this.price=sc.nextFloat();
        System.out.println("Enter the New Stock Quantity : ");
        this.stock=sc.nextInt();

        try{
            PreparedStatement pr=con.prepareStatement("update products set _name=? , quantity=?,rating=?,price=? where _id=?");
            pr.setString(1,this.title);
            pr.setInt(2,this.stock);
            pr.setInt(3,this.rating);
            pr.setFloat(4,this.price);
            pr.setInt(5,this.product_id);
            pr.executeUpdate();
            System.out.println("Item Updated Successfully ✅✅ ");
        }catch (Exception e){
            System.out.println(e+"Line 29 ");
        }
    }
    // TODO: it'll delete the product whom this function referring
    public void DeleteProduct(Connection con,int productId,Admin ad){
        try{
            PreparedStatement pr=con.prepareStatement("delete from products where _id=?");
            pr.setInt(1,productId);
            pr.executeUpdate();
            System.out.println("Product Deleted Successfully");
        }
        catch (Exception e){
//            System.out.println(e+"Line 43");
            System.out.println("❌❌ Something Went Wrong !!  Try after some time");

        }
        admin_view view = new admin_view();
        view.adminFunctions(con, ad);

    }
    public void AddProduct(Connection con,Admin ad){
        System.out.println("Add Product : ➕");
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Name of Product : ");
        this.title=sc.nextLine();
        System.out.println("Enter Quantity: ");
        this.stock=sc.nextInt();
        System.out.println("Enter Rating : ");
        this.rating=sc.nextInt();
        System.out.println("Enter Price : ");
        this.price=sc.nextFloat();
        System.out.println("Enter Brand : ");
        String st ;
        st=sc.next();
        this.brand=st ;
        try{
            PreparedStatement pr=con.prepareStatement("insert into products (brand,_name,price,rating,quantity) values(?,?,?,?,?)");
            pr.setString(1,this.brand);
            pr.setString(2,this.title);
            pr.setFloat(3,this.price);
            pr.setInt(4,this.rating);
            pr.setInt(5,this.stock);
            pr.executeUpdate();
            System.out.println("Product inserted Successfully ✅✅");

        }catch (Exception e){
            System.out.println("Something Went Wrong !!");
        }
        admin_view view = new admin_view();
//        For giving the main option again
        view.adminFunctions(con,ad);
    }
}

//TODO: <!------------------------------------------------------!>
//I think order ka db bhi chahiye
//==>1 user info ,2 product info ,3 orders ka total
//==>2 specific to the user
//==>3 withdrawing of order is also
//==>4 order placed wali info bhi
