
import java.sql.*;
import java.util.ArrayList;

public class GestorDeProductosJDBC {
    public void guardarProductosEnLaBaseDeDatos(ArrayList<Producto> productosRecuperados) throws ProductoException {
        DBUtils dbUtils = new DBUtils();
        Connection connection = null;

        try {
            connection = dbUtils.recuperarConnection();
            String sqlInsert = "INSERT INTO productos (ID, Nombre, Descripcion, Precio, Fecha_alta) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);

            for (Producto producto : productosRecuperados) {
                preparedStatement.setInt(1, producto.getId());
                preparedStatement.setString(2, producto.getNombre());
                preparedStatement.setString(3, producto.getDescripcion());
                preparedStatement.setDouble(4, producto.getPrecio());
                preparedStatement.setDate(5, new java.sql.Date(producto.getFechaAlta().getTime()));
                preparedStatement.execute();
            }
        } catch (Exception e) {
            throw new ProductoException("Hay problemas al guardar los productos en la BD", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<Producto> recuperarProductosDeLaBaseDeDatos() throws ProductoException {
        DBUtils dbUtils = new DBUtils();
        ArrayList<Producto> productos = new ArrayList<>();
        Producto producto = null;
        Connection connection = null;

        try {
            connection = dbUtils.recuperarConnection();
            String sqlSelect = "SELECT ID, Nombre, Descripcion, Precio, Fecha_alta FROM productos;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                producto = new Producto();
                producto.setId(resultSet.getInt("ID"));
                producto.setNombre(resultSet.getString("Nombre"));
                producto.setDescripcion(resultSet.getString("Descripcion"));
                producto.setPrecio(resultSet.getDouble("Precio"));
                producto.setFechaAlta(resultSet.getDate("Fecha_alta"));
                productos.add(producto);
            }
        } catch (Exception e) {
            throw new ProductoException("Hay problemas al recuperar los productos de la BD", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return productos;
    }
}


