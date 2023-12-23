package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int qty;
    private Category category;


    public Product(String name, String description, double price, int qty) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.qty = qty;
    }

    public Product(int id, String name, String description, double price, int qty) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.qty = qty;
    }

    public Product(String productName, String productDescription, double productPrice, int productQty, Category productCategoryFromDB) {
        this.name = productName;
        this.description = productDescription;
        this.price = productPrice;
        this.qty = productQty;
        this.category = productCategoryFromDB;
    }
}
