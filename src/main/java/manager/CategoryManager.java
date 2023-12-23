package manager;

import db.DBConnectionProvider;
import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {

    Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void add(Category category) {
        String query = "INSERT INTO category(name) VALUES (?)";

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, category.getName());

            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();

            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                category.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(Category category, String newName) {
        // Check if the category with the given name exists
        if (getCategoryByName(category.getName()) == null) {
            System.out.println("Category with name: " + category.getName() + " doesn't exist");
            return;
        }

        String query = "UPDATE category SET name = '%s' WHERE name = '%s'";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newName);
            ps.setString(2, category.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM category WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if there is a category with the specified ID
            while (resultSet.next()) {
                String name = resultSet.getString("name");

                // Create and return a new Category object with the retrieved ID and name
                return new Category(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteCategoryById(int id) {
        if (getCategoryById(id) == null) {
            System.out.println("Category with " + id + " doesn't exist");
            return;
        }

        String sql = "DELETE FROM category WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Category deleted successfully!");
            } else {
                System.out.println("Failed to delete category. Please check the ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Category getCategoryByName(String categoryName) {

        // SQL query to select a category by its name (case-insensitive)
        String sql = "SELECT * FROM category WHERE LOWER(name) = LOWER(?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryName);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if there is a category with the specified name
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                return new Category(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM category";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                categories.add(new Category(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

}
