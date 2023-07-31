package persistence;

import logic.*;
import java.sql.*;
import java.util.ArrayList;

public class ManagerJDBC {
	public void saveProductDB(ArrayList<Product> recoveryProduct) throws ProductException {
		
		DBConnector dbConnector = new DBConnector();
        Connection connection = null;

        try {
            connection = dbConnector.recoveryConnection();
            String sqlInsert = "INSERT INTO products (id, nameProduct, descriptionProduct, price, dischargeDate) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);

            for (Product product : recoveryProduct) {
                preparedStatement.setInt(1, product.getId());
                preparedStatement.setString(2, product.getNameProduct());
                preparedStatement.setString(3, product.getDescriptionProduct());
                preparedStatement.setDouble(4, product.getPrice());
                preparedStatement.setDate(5, new java.sql.Date(product.getDischargeDate().getTime()));
                preparedStatement.execute();
            }
        } catch (Exception e) {
            throw new ProductException("Se ha encontrado problemas al intentar guardar los productos en la BD", e);
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
	
	public ArrayList<Product> recoveryProductDB() throws ProductException {
		DBConnector dbConnector = new DBConnector();
        ArrayList<Product> products = new ArrayList<>();
        Product product = null;
        Connection connection = null;

        try {
            connection = dbConnector.recoveryConnection();
            String sqlSelect = "SELECT id, nameProduct, descriptionProduct, price, dischargeDate FROM products;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setNameProduct("nameProduct");
                product.setDescriptionProduct("descriptionProduct");
                product.setPrice(resultSet.getDouble("Price"));
                product.setDischargeDate(resultSet.getDate("dischargeDate"));
                products.add(product);
            }
        } catch (Exception e) {
            throw new ProductException("Hay problemas al recuperar los productos de la BD", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return products;
	}
}
