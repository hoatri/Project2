/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Valan
 */
public class Product {
    private ObjectProperty<Integer> productId;
    private StringProperty productName;
    private ObjectProperty<Double> price;
    private ObjectProperty<Integer> stock;
    private StringProperty description;
    private StringProperty image;
    private ObjectProperty<Integer> categoryId;
    private StringProperty categoryName;
    private ObjectProperty<Integer> statusCode;
    private StringProperty status;

    public Product() {
        this.productId = new SimpleObjectProperty<>();
        this.productName = new SimpleStringProperty();
        this.price = new SimpleObjectProperty<>();
        this.stock = new SimpleObjectProperty<>();
        this.description = new SimpleStringProperty();
        this.image = new SimpleStringProperty();
        this.categoryId = new SimpleObjectProperty<>();
        this.categoryName = new SimpleStringProperty();
        this.statusCode = new SimpleObjectProperty<>();
        this.status = new SimpleStringProperty();
    }

    public Integer getProductId() {
        return productId.get();
    }

    public String getProductName() {
        return productName.get();
    }

    public Double getPrice() {
        return price.get();
    }

    public Integer getStock() {
        return stock.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getImage() {
        return image.get();
    }
    
    public Integer getCategoryId() {
        return categoryId.get();
    }

    public String getCategoryName() {
        return categoryName.get();
    }

    public Integer getStatusCode() {
        return statusCode.get();
    }

    public String getStatus() {
        return status.get();
    }

    public void setProductId(Integer productId) {
        this.productId.set(productId);
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public void setPrice(Double price) {
        this.price.set(price);
    }

    public void setStock(Integer stock) {
        this.stock.set(stock);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setImage(String image) {
        this.image.set(image);
    }
    
    public void setCategoryId(Integer categoryId) {
        this.categoryId.set(categoryId);
    }

    public void setCategoryName(String categoryName) {
        this.categoryName.set(categoryName);
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode.set(statusCode);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public ObjectProperty<Integer> getProductIdProperty() {
        return productId;
    }

    public StringProperty getProductNameProperty() {
        return productName;
    }

    public ObjectProperty<Double> getPriceProperty() {
        return price;
    }

    public ObjectProperty<Integer> getStockProperty() {
        return stock;
    }

    public StringProperty getDescriptionProperty() {
        return description;
    }

    public StringProperty getImageProperty() {
        return image;
    }
    
    public ObjectProperty<Integer> getCategoryIdProperty() {
        return categoryId;
    }

    public StringProperty getCategoryNameProperty() {
        return categoryName;
    }

    public StringProperty getStatusProperty() {
        return status;
    }
}
