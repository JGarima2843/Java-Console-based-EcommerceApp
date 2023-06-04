package com.models.ex;
import java.sql.Connection;
import java.sql.DriverManager;
public class  jdbc_Connection {
    public static Connection jdbcConnector(){
        Connection con=getConnection();
        return con ;
    }
    private static Connection getConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String databaseurl = "jdbc:mysql://localhost:3306/e_commerce";
        String username = "root";
        String password = "pass123";
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(databaseurl, username, password);
            System.out.println("Database Connected");
//            String query="select * from student_data";
//            Statement stmt=con.createStatement();
//            ResultSet res=stmt.executeQuery(query);
//            while(res.next()){
//                int id=res.getInt("stdId");
//                String name=res.getString("stdName");
//                int marks=res.getInt("Percentage");
//                System.out.println(id+"  "+name+"  "+marks);
//
//            }
            return con;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
