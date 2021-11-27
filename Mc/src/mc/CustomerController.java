/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc;

import com.jfoenix.controls.JFXTextField;
import database.AdminSession;
import database.Customer;
import database.Customer_query;
import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Valan
 */
public class CustomerController {
    private Customer_query customerQuery = new Customer_query();
    private ObservableList<Customer> dataList = FXCollections.observableArrayList();
    @FXML
    private Label admin;

    @FXML
    private JFXTextField filterField;

    @FXML
    private TableView<Customer> tvCustomer;

    @FXML
    private TableColumn<Customer, String> tcName;

    @FXML
    private TableColumn<Customer, String> tcEmail;

    @FXML
    private TableColumn<Customer, String> tcPhone;

    @FXML
    private TableColumn<Customer, String> tcAddress;

    @FXML
    void gotoAdmin(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoAdmin();
    }

    @FXML
    void gotoCategory(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoCategory();
    }

    @FXML
    void gotoCustomer(ActionEvent event) throws IOException{
        Navigator.getInstance().gotoCustomer();
    }

    @FXML
    void gotoOrder(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoOrder();
    }

    @FXML
    void gotoProduct(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoProduct();
    }

    @FXML
    void gotoStatus(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoStatus();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        AdminSession.getInstance().clearAdminSession();
        Navigator.getInstance().gotoLogin();
    }

    @FXML
    void refresh(ActionEvent event) {
        dataList = customerQuery.selectAll();
        tvCustomer.setItems(dataList);
    }

    @FXML
    void search(KeyEvent event) {
        FilteredList<Customer> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (customer.getFullName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (customer.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return customer.getPhone().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvCustomer.comparatorProperty());
        tvCustomer.setItems(sortedData);
    }
    
    public void initialize() throws SQLException {
        admin.setText(AdminSession.getInstance().getName());
        dataList = customerQuery.selectAll();
        tvCustomer.setItems(dataList);
        tcName.setCellValueFactory((customer) -> {
            return customer.getValue().getFullNameProperty();
        });
        tcEmail.setCellValueFactory((customer) -> {
            return customer.getValue().getEmailProperty();
        });
        tcPhone.setCellValueFactory((customer) -> {
            return customer.getValue().getPhoneProperty();
        });
        tcAddress.setCellValueFactory((customer) -> {
            return customer.getValue().getAddressProperty();
        });
    }
}
