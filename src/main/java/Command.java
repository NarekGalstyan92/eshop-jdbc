public interface Command {

    String EXIT = "0";
    String ADD_CATEGORY= "1";
    String EDIT_CATEGORY = "2";
    String DELETE_CATEGORY = "3";
    String ADD_PRODUCT = "4";
    String EDIT_PRODUCT = "5";
    String DELETE_PRODUCT = "6";
    String PRINT_SUM_OF_PRODUCTS = "7";
    String PRINT_MAX_PRICE_FROM_PRODUCT= "8";
    String PRINT_MIN_PRICE_FROM_PRODUCT = "9";
    String PRINT_AVG_PRICE_FROM_PRODUCT = "10";

    String DASHES = "-----------------------------------------";


    static void printCommands() {
        System.out.println("Please enter " + EXIT + " to EXIT");
        System.out.println("Please enter " + ADD_CATEGORY + " to add category");
        System.out.println("Please enter " + EDIT_CATEGORY + " to edit category");
        System.out.println("Please enter " + DELETE_CATEGORY + " to delete category");
        System.out.println("Please enter " + ADD_PRODUCT + " to add product");
        System.out.println("Please enter " + EDIT_PRODUCT + " to edit_product");
        System.out.println("Please enter " + DELETE_PRODUCT + " to delete product");
        System.out.println("Please enter " + PRINT_SUM_OF_PRODUCTS + " to print the summary of products");
        System.out.println("Please enter " + PRINT_MAX_PRICE_FROM_PRODUCT + " to print priciest product price");
        System.out.println("Please enter " + PRINT_MIN_PRICE_FROM_PRODUCT + " to print cheapest product price");
        System.out.println("Please enter " + PRINT_AVG_PRICE_FROM_PRODUCT + " to average product price");
    }
}
