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
public class OrderDetail {
    private ObjectProperty<Integer> orderId;
    private ObjectProperty<Integer> productId;
    private ObjectProperty<Integer> quantity;
    private ObjectProperty<Double> unitPrice;

    public OrderDetail() {
        this.orderId = new SimpleObjectProperty<>();
        this.productId = new SimpleObjectProperty<>();
        this.quantity = new SimpleObjectProperty<>();
        this.unitPrice = new SimpleObjectProperty<>();
    }

    public Integer getOrderId() {
        return orderId.get();
    }

    public Integer getProductId() {
        return productId.get();
    }

    public Integer getQuantity() {
        return quantity.get();
    }
    
    public Double getSubTotal() {
        return quantity.get() * unitPrice.get();
    }
    
    public Double getUnitPrice() {
        return unitPrice.get();
    }

    public void setOrderId(Integer orderId) {
        this.orderId.set(orderId);
    }

    public void setProductId(Integer productId) {
        this.productId.set(productId);
    }

    public void setQuantity(Integer quantity) {
        this.quantity.set(quantity);
    }
    
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    public ObjectProperty<Integer> getOrderIdProperty() {
        return orderId;
    }

    public ObjectProperty<Integer> getProductIdProperty() {
        return productId;
    }

    public ObjectProperty<Integer> getQuantityProperty() {
        return quantity;
    }

    public ObjectProperty<Double> getUnitPriceProperty() {
        return unitPrice;
    }
}
