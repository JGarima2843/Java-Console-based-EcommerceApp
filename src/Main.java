import com.Views.ex.admin_view;
import com.Views.ex.user_view;
import com.models.ex.jdbc_Connection;
import java.sql.Connection;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        int check;
        Scanner sc = new Scanner(System.in);
        Connection con=jdbc_Connection.jdbcConnector();
        System.out.println("✨✨ Welcome To Ecommerce Project ✨✨");
        System.out.println("To Login as Admin Press 1 ");
        System.out.println("To Login as User Press 2");
        check = sc.nextInt();
        if (check == 1) {
            admin_view admin = new admin_view();
            admin.adminDashboard(con);
        } else if (check == 2) {
            user_view user = new user_view();
            user.userDashboard(con);
        }
    }
}