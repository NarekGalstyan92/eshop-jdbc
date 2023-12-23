import manager.CategoryManager;
import manager.ProductManager;
import model.Category;
import model.Product;

import java.util.List;
import java.util.Scanner;

public class EshopMain implements Command {

    private static final Scanner scanner = new Scanner(System.in);

    private static final CategoryManager categoryManager = new CategoryManager();
    private static final ProductManager productManager = new ProductManager();


    public static void main(String[] args) {

        boolean isRun = true;
        while (isRun) {
            Command.printCommands();
            String command = scanner.nextLine();
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case ADD_CATEGORY:
                    addCategory();
                    break;
                case EDIT_CATEGORY:
                    editCategory();
                    break;
                case DELETE_CATEGORY:
                    deleteCategory();
                    break;
                case ADD_PRODUCT:
                    addProduct();
                    break;
                case EDIT_PRODUCT:
                    editProduct();
                    break;
                case DELETE_PRODUCT:
                    deleteProduct();
                    break;
                case PRINT_SUM_OF_PRODUCTS:
                    printSumOfProducts();
                    break;
                case PRINT_MAX_PRICE_FROM_PRODUCT:
                    printMaxPriceFromProducts();
                    break;
                case PRINT_MIN_PRICE_FROM_PRODUCT:
                    printMinPriceFromProducts();
                    break;
                case PRINT_AVG_PRICE_FROM_PRODUCT:
                    printAvgPriceFromProducts();
                    break;
                default:
                    System.out.println("Invalid command. Try again!\n");
            }
        }
    }

    private static void printAvgPriceFromProducts() {
        System.out.println(DASHES);
        double AvgPrice = productManager.getAvgPriceOfProducts();
        if (AvgPrice <= 0.0) {
            System.out.println("We dont have any products to check the prices");
            return;
        }
        System.out.println("The average product price is  " + AvgPrice + " USD");
        System.out.println(DASHES);
    }

    private static void printMinPriceFromProducts() {
        System.out.println(DASHES);
        double MinPrice = productManager.getMinPriceOfProducts();
        if (MinPrice <= 0.0) {
            System.out.println("We dont have any products to check the prices");
            return;
        }
        System.out.println("The cheapest product price is  " + MinPrice + " USD");
        System.out.println(DASHES);
    }

    private static void printMaxPriceFromProducts() {
        System.out.println(DASHES);
        double MaxPrice = productManager.getMaxPriceOfProducts();
        if (MaxPrice <= 0.0) {
            System.out.println("We dont have any products to check the prices");
            return;
        }
        System.out.println("The most expensive product price is  " + MaxPrice + " USD");
        System.out.println(DASHES);
    }

    private static void printSumOfProducts() {
        System.out.println(DASHES);
        int sumOfProducts = productManager.getSumOfProducts();
        if (sumOfProducts <= 0) {
            System.out.println("We dont have any products");
            return;
        }
        System.out.println("We have " + sumOfProducts + " products");
        System.out.println(DASHES);
    }

    private static void deleteProduct() {
        System.out.println(DASHES);
        System.out.println("Please input product name you want to delete");
        String productName = scanner.nextLine();

        if (checkProductNameCredentials(productName)) {
            return;
        }

        Product productFromDB = productManager.getProductByName(productName);
        if (productFromDB != null) {
            int id = productFromDB.getId();
            productManager.deleteProductById(id);
        } else {
            System.out.println("product not found!");
        }

        System.out.println(DASHES);
    }

    private static void editProduct() {
        System.out.println(DASHES);
        System.out.println("Please input product name you want to change");
        String productName = scanner.nextLine();
        if (checkProductNameCredentials(productName)) {
            return;
        }
        Product productFromDB = productManager.getProductByName(productName);


        if (productFromDB == null) {
            System.out.println("Product with provided name \"" + productName + "\" does not found");
            System.out.println(DASHES);
            return;
        }

        System.out.println("Please input product new name");
        String productNewName = scanner.nextLine();
        System.out.println("Please input product description");
        String productDescription = scanner.nextLine();
        System.out.println("Please input product price");
        String productPrice = scanner.nextLine();
        System.out.println("Please input product quantity");
        String productQuantity = scanner.nextLine();
        System.out.println("Please input product category number from the list");

        List<Category> categories = categoryManager.getAllCategories();

        for (Category category : categories) {
            System.out.println(category.getName() + " = " + category.getId());
        }

        String productCategoryId = scanner.nextLine();

        if (checkProductCredentials(productNewName, productPrice, productQuantity, productCategoryId)) {
            int categoryId = Integer.parseInt(productCategoryId);
            Category productCategoryFromDB = categoryManager.getCategoryById(categoryId);
            Product product = new Product(productName, productDescription, Double.parseDouble(productPrice), Integer.parseInt(productQuantity), productCategoryFromDB);
            productManager.updateProduct(product, productNewName);
        } else {
            System.out.println("Something went wrong with credentials");
        }
        System.out.println(DASHES);
    }

    private static boolean checkProductNameCredentials(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            System.out.println("Category name cannot be null or empty. Please provide a valid name.");
            return true;
        }

        if (!productName.matches("^[a-zA-Z0-9_-]*$")) {
            System.out.println("Category name can only contain letters, numbers, underscores, and hyphens.");
            return true;
        }

        return false;
    }

    private static void addProduct() {
        System.out.println(DASHES);
        System.out.println("Please input product name");
        String productName = scanner.nextLine();
        System.out.println("Please input product description");
        String productDescription = scanner.nextLine();
        System.out.println("Please input product price");
        String productPrice = scanner.nextLine();
        System.out.println("Please input product quantity");
        String productQuantity = scanner.nextLine();
        System.out.println("Please input product category number from the list");

        List<Category> categories = categoryManager.getAllCategories();

        for (Category category : categories) {
            System.out.println(category.getName() + " = " + category.getId());
        }

        String productCategoryId = scanner.nextLine();


        if (checkProductCredentials(productName, productPrice, productQuantity, productCategoryId)) {
            int categoryId = Integer.parseInt(productCategoryId);
            Category productCategoryFromDB = categoryManager.getCategoryById(categoryId);
            Product product = new Product(productName, productDescription, Double.parseDouble(productPrice), Integer.parseInt(productQuantity), productCategoryFromDB);
            productManager.add(product);
            System.out.println("Product successfully added!");
        } else {
            System.out.println("Something went wrong");
        }
        System.out.println(DASHES);
    }

    private static boolean checkProductCredentials(String productName, String productPrice, String productQuantity, String inputtedId) {
        if (productName == null || productName.trim().isEmpty() || !productName.matches("^[a-zA-Z0-9_-]*$")) {
            System.out.println("Product name is invalid. It can only contain letters, numbers, underscores, and hyphens.");
            return false;
        }

        if (productPrice == null || productPrice.trim().isEmpty()) {
            System.out.println("Product price cannot be null or empty. Please provide a valid price.");
            return false;
        }

        try {
            Double.parseDouble(productPrice);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for product price. Please enter a valid numeric value.");
            return false;
        }

        if (productQuantity == null || productQuantity.trim().isEmpty()) {
            System.out.println("Product quantity cannot be null or empty. Please provide a valid quantity.");
            return false;
        }

        try {
            Integer.parseInt(productQuantity);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for product quantity. Please enter a valid numeric value.");
            return false;
        }

        try {
            Integer.parseInt(inputtedId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input for category id. Please enter a valid numeric value.");
            e.printStackTrace();
            return false;
        }

        // All checks passed, credentials are valid
        return true;
    }

    private static void deleteCategory() {
        System.out.println(DASHES);
        System.out.println("Please input category name you want to delete");
        String categoryName = scanner.nextLine();

        if (checkCategoryNameCredentials(categoryName)) {
            return;
        }
        Category categoryFromDB = categoryManager.getCategoryByName(categoryName);
        if (categoryFromDB != null) {
            int id = categoryFromDB.getId();
            categoryManager.deleteCategoryById(id);
        } else {
            System.out.println("Category not found!");
        }

        System.out.println(DASHES);
    }

    private static void editCategory() {
        System.out.println(DASHES);
        System.out.println("Please input category name you want to edit");
        String categoryName = scanner.nextLine();

        if (checkCategoryNameCredentials(categoryName)) {
            return;
        }

        Category categoryFromDB = categoryManager.getCategoryByName(categoryName);

        if (categoryFromDB != null) {
            System.out.println("Please input the new category name");
            String newCategoryName = scanner.nextLine();

            if (newCategoryName == null || newCategoryName.trim().isEmpty()) {
                System.out.println("New category name cannot be null or empty. Please provide a valid name.");
                return;
            }

            if (!newCategoryName.matches("^[a-zA-Z0-9_-]*$")) {
                System.out.println("New category name can only contain letters, numbers, underscores, and hyphens.");
                return;
            }

            categoryManager.updateCategory(categoryFromDB, newCategoryName);

            System.out.println("Category name was changed!");
        } else {
            System.out.println("Category with \"" + categoryName + "\" doesn't exist!");
        }

        System.out.println(DASHES);
    }

    private static boolean checkCategoryNameCredentials(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            System.out.println("Category name cannot be null or empty. Please provide a valid name.");
            return true;
        }

        // Check if categoryName includes special characters
        if (!categoryName.matches("^[a-zA-Z0-9_-]*$")) {
            System.out.println("Category name can only contain letters, numbers, underscores, and hyphens.");
            return true;
        }
        return false;
    }


    private static void addCategory() {
        System.out.println(DASHES);
        System.out.println("Please input category name");
        String categoryName = scanner.nextLine();
        if (checkCategoryNameCredentials(categoryName)) {
            return;
        }
        List<Category> allCategories = categoryManager.getAllCategories();
        for (Category category : allCategories) {

            // Check if the entered category name already exists (case-insensitive)
            if (category.getName().equalsIgnoreCase(categoryName)) {
                System.out.println("Category already exist!");
                return;
            }
        }
        categoryManager.add(new Category(categoryName));
        System.out.println("Category successfully added!");
        System.out.println(DASHES);
    }

}

