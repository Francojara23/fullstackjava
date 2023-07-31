package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	public Connection recoveryConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String user = "root";
        String password = "";
        String url = "jdbc:mysql://localhost/database_products";
        
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
	}
}