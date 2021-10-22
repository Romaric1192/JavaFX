

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    

Connection conn;
public ConnectDB(){

    try {
        Class.forName("com.mysql.jdbc.Driver");
    } catch (Exception e) {
        //TODO: handle exception
        e.printStackTrace();
    }
    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ftic", "root", "");
        System.out.println("connection okay");
    } catch (SQLException e) {
        //TODO: handle exception
        System.err.println(e);
    }
}
Connection getConnect(){
    return conn;
}


}
