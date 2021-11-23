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
public class Admin {
    private ObjectProperty<Integer> adminId;
    private StringProperty adminName;
    private StringProperty email;
    private StringProperty password;
    private StringProperty phone;
    
    public Admin() {
        this.adminId = new SimpleObjectProperty<>();
        this.adminName = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
    }

    public Integer getAdminId() {
        return adminId.get();
    }

    public void setAdminId(Integer adminId) {
        this.adminId.set(adminId);
    }

    public String getAdminName() {
        return adminName.get();
    }

    public void setAdminName(String adminName) {
        this.adminName.set(adminName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
    
    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    
    public ObjectProperty<Integer> getAdminIdProperty() {
        return adminId;
    }

    public StringProperty getAdminNameProperty() {
        return adminName;
    }
    
    public StringProperty getEmailProperty() {
        return email;
    }

    public StringProperty getPasswordProperty() {
        return password;
    }
    
    public StringProperty getPhoneProperty() {
        return phone;
    }
}
