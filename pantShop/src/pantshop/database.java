/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantshop;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author aphinat
 */
public class database {

    public static Connection connectDB() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/pants", "root", "");
            return connect;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
