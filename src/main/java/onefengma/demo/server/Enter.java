package onefengma.demo.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import onefengma.demo.server.core.BaseManager;
import onefengma.demo.server.services.products.ProductManager;
import onefengma.demo.server.services.user.UserManager;

/**
 * @author yfchu
 * @date 2016/4/1
 */
public class Enter {

    private static List<BaseManager> managers = Arrays.asList(
            new UserManager(),
            new ProductManager()
    );

    public static void main(String[] args) {
        try {
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/b2b?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // user modules
        for(BaseManager manager : managers) {
            manager.init();
        }
    }

}
