import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public Connection recuperarConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String usuario = "root";
        String clave = "";
        String url = "jdbc:mysql://localhost/base_productos";

        Connection connection = DriverManager.getConnection(url, usuario, clave);
        return connection;
    }
}