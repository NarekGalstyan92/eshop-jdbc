package manager;

import db.DBConnectionProvider;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {

    Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void add(Product product) {
        String query = "INSERT INTO product(name, description, price, quantity, category_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQty());
            ps.setInt(5, product.getCategory().getId());

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            // If generated keys exist, retrieve the auto-generated ID and set it in the product object
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                product.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateProduct(Product product, String newName) {
        String query = "UPDATE product SET name = ?, description = ?, price = ?, quantity = ?, category_id = ? WHERE name = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newName);
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQty());
            ps.setInt(5, product.getCategory().getId());
            ps.setString(6, product.getName());

            // Execute the update and get the number of rows affected

            int rowsUpdated = ps.executeUpdate();

            // The number of rows affected by the update is checked, and
            // a text is printed based on whether the product was successfully updated or not.
            if (rowsUpdated > 0) {
                System.out.println("Product successfully changed!");
            } else {
                System.out.println("Product with name: " + product.getName() + " doesn't exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Product getProductById(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int qty = resultSet.getInt("quantity");
                return new Product(name, description, price, qty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public void deleteProductById(int id) {
        if (getProductById(id) == null) {
            System.out.println("Product with " + id + " doesn't exist");
            return;
        }

        String sql = "DELETE FROM product WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Failed to delete product. Please check the ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Product getProductByName(String productName) {
        String sql = "SELECT * FROM product WHERE LOWER(name) = LOWER(?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, productName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                // Check if the result set contains any rows
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int qty = resultSet.getInt("quantity");


                return new Product(id, name, description, price, qty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int qty = resultSet.getInt("quantity");
                Product product = new Product(name, description, price, qty);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public int getSumOfProducts() {
        String sql = "SELECT SUM(quantity) AS total_quantity FROM product";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total_quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


    public double getMaxPriceOfProducts() {
        String sql = "SELECT MAX(price) AS max_price FROM product";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("max_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


    public double getMinPriceOfProducts() {
        String sql = "SELECT MIN(price) AS min_price FROM product";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("min_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public double getAvgPriceOfProducts() {
        String sql = "SELECT AVG(price) AS avg_price FROM product";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("avg_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

}
