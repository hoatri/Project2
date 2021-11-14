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
public class Customer {
    private ObjectProperty<Integer> customerID;
    private StringProperty fullName;
    private StringProperty email;
    private StringProperty password;
    private StringProperty address;
    private StringProperty phone;

    public Customer() {
        this.customerID = new SimpleObjectProperty<>();
        this.fullName = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
    }

    public Integer getCustomerID() {
        return customerID.get();
    }

    public String getFullName() {
        return fullName.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public void setCustomerID(Integer customerID) {
        this.customerID.set(customerID);
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public ObjectProperty<Integer> getCustomerIdProperty() {
        return customerID;
    }

    public StringProperty getFullNameProperty() {
        return fullName;
    }

    public StringProperty getEmailProperty() {
        return email;
    }

    public StringProperty getPasswordProperty() {
        return password;
    }

    public StringProperty getAddressProperty() {
        return address;
    }

    public StringProperty getPhoneProperty() {
        return phone;
    }
}
