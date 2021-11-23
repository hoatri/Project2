/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import database.Admin;
import database.AdminSession;
import database.Admin_query;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Valan
 */
public class AdminController {
    private final Admin_query adminQuery = new Admin_query();
    private ObservableList<Admin> dataList = FXCollections.observableArrayList();
    private Admin editAdmin = null;

    private ChangeListener<String> nameListener;
    private ChangeListener<String> emailListener;
    private ChangeListener<String> phoneListener;
    private ChangeListener<String> pwdListener;
    private ChangeListener<String> pwdConfirmListener;
    
    @FXML
    private Label admin;

    @FXML
    private JFXTextField filterField;

    @FXML
    private Label labelEditor;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPhone;
    
    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXPasswordField txtConfirmPass;

    @FXML
    private Label noti;

    @FXML
    private TableView<Admin> tvAdmins;

    @FXML
    private TableColumn<Admin, String> tcAdminName;

    @FXML
    private TableColumn<Admin, String> tcEmail;

    @FXML
    private TableColumn<Admin, String> tcPhoneNumber;

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
    void clear(ActionEvent event) {
        resetListener();
        clearFields();
    }
    
    private void addListenerAdmin() {
        txtName.textProperty().addListener(nameListener);
        txtEmail.textProperty().addListener(emailListener);
        txtPhone.textProperty().addListener(phoneListener);
        txtPassword.textProperty().addListener(pwdListener);
        txtConfirmPass.textProperty().addListener(pwdConfirmListener);
    }
    
    private void removeListenerAdmin() {
        txtName.textProperty().removeListener(nameListener);
        txtEmail.textProperty().removeListener(emailListener);
        txtPhone.textProperty().removeListener(phoneListener);
        txtPassword.textProperty().removeListener(pwdListener);
        txtConfirmPass.textProperty().removeListener(pwdConfirmListener);
    }
    
    public void resetListener(){
        txtName.setText("1");
        txtEmail.setText("asd@asd.asd");
        txtPassword.setText("asdf1234");
        txtConfirmPass.setText("asdf1234");
        txtPhone.setText("0000000000");
        removeListenerAdmin();
        txtName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtConfirmPass.setText("");
        txtPhone.setText("");
        addListenerAdmin();
    }
    
    @FXML
    void createNew(ActionEvent event) {
        resetListener();
        labelEditor.setText("New Admin Details");
        noti.setText("");
        editAdmin = null;
        clearFields();
    }

    @FXML
    void delete(ActionEvent event) throws IOException {
        Admin selectedAdmin = tvAdmins.getSelectionModel().getSelectedItem();
        if (selectedAdmin == null) {
            selectWarning();
        } else {
            if (dataList.size() == 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("DELETE");
                alert.setHeaderText("You cannnot delete any more admin!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("DELETE");
                alert.setHeaderText("Are you sure you want to delete "+ selectedAdmin.getAdminName() +" ?");
                Optional<ButtonType> confirmationResponse = alert.showAndWait();
                if (confirmationResponse.get() == ButtonType.OK) {
                    boolean result = adminQuery.delete(selectedAdmin);
                    if (result) {
                        if (AdminSession.getInstance().getEmail().equals(selectedAdmin.getEmail())) {
                            Alert logoutAlert = new Alert(Alert.AlertType.INFORMATION);
                            logoutAlert.setTitle("Notification");
                            logoutAlert.setHeaderText("You will be logged out now.");
                            logoutAlert.showAndWait();
                            AdminSession.getInstance().clearAdminSession();
                            Navigator.getInstance().gotoLogin();
                        } else {
                            dataList.remove(selectedAdmin);
                            tvAdmins.setItems(dataList);
                        }
                    } else {
                    }
                }
            }
            labelEditor.setText("New Admin Details");
            editAdmin = null;
        }
    }

    @FXML
    void edit(ActionEvent event) {
        Admin selectedAdmin = tvAdmins.getSelectionModel().getSelectedItem();
        if (selectedAdmin == null) {
            selectWarning();
        } else {
            labelEditor.setText("Edit Admin Details");
            noti.setText("");
            editAdmin = selectedAdmin;
            txtName.setText(selectedAdmin.getAdminName());
            txtEmail.setText(selectedAdmin.getEmail());
            txtPhone.setText(selectedAdmin.getPhone());
        }
    }
    
    @FXML
    void refresh(ActionEvent event) {
        resetListener();
        refreshDataList();
    }

    @FXML
    void save(ActionEvent event) throws NoSuchAlgorithmException {
        noti.setText("");
        removeListenerAdmin();
        if (validate()) {
            Admin extracted = extract_admin_from_fields();
            if (editAdmin == null && adminQuery.isUnique(extracted.getEmail())) {
                extracted = adminQuery.insert(extracted);
                if (extracted != null) {
                    noti.setText("New admin added successfully.");
                    noti.setTextFill(Paint.valueOf("green"));
                    refreshDataList();
                } else {
                    noti.setText("Some errors occurred.");
                    noti.setTextFill(Paint.valueOf("red"));
                }
                clearFields();
            } else if (editAdmin != null && (extracted.getEmail().equals(editAdmin.getEmail()) || adminQuery.isUnique(extracted.getEmail()))) {
                extracted.setAdminId(editAdmin.getAdminId());
                boolean result = adminQuery.update(extracted);
                if (result) {
                    noti.setText("Admin edited successfully.");
                    noti.setTextFill(Paint.valueOf("green"));
                    refreshDataList();
                } else {
                    noti.setText("Some errors occurred.");
                    noti.setTextFill(Paint.valueOf("red"));
                }
                editAdmin = null;
                clearFields();
                labelEditor.setText("New Admin Details");
            } else {
                noti.setText("This email address already existed. Please register with another email.");
                noti.setTextFill(Paint.valueOf("red"));
            }
        } else {
            addListenerAdmin();
        }
    }

    @FXML
    void search(KeyEvent event) {
        FilteredList<Admin> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Admin admin) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (admin.getPhone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (admin.getAdminName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return String.valueOf(admin.getEmail()).toLowerCase().contains(lowerCaseFilter);
                }
            });
        });
        SortedList<Admin> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvAdmins.comparatorProperty());
        tvAdmins.setItems(sortedData);
    }
    
    private Admin extract_admin_from_fields() throws NoSuchAlgorithmException {
        Admin a = new Admin();
        if(txtPassword.getText().equals(txtConfirmPass.getText())){
            a.setAdminName(txtName.getText());
            a.setEmail(txtEmail.getText());
            a.setPassword(sha1(txtPassword.getText()));
            a.setPhone(txtPhone.getText());
        }else{
            noti.setText("Password didn't match");
            noti.setTextFill(Paint.valueOf("red"));
        }
        return a;
    }
    
    private boolean validate() {
        RequiredFieldValidator required = new RequiredFieldValidator();
        RegexValidator regexEmail = new RegexValidator("Please enter valid email");
        regexEmail.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        RegexValidator regexPwd = new RegexValidator("Password must have at least 8 characters and include letters and numbers");
        regexPwd.setRegexPattern("^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,20}$");
        required.setMessage("Input Required");
        NumberValidator number = new NumberValidator();
        number.setMessage("Must be number");
        txtName.getValidators().add(required);
        txtEmail.getValidators().addAll(required, regexEmail);
        txtPassword.getValidators().addAll(required, regexPwd);
        txtConfirmPass.getValidators().addAll(required, regexPwd);
        txtName.validate();
        txtEmail.validate();
        txtPassword.validate();
        txtConfirmPass.validate();
        txtPhone.getValidators().addAll(required, number);
        txtPhone.validate();
        return txtName.validate() && txtEmail.validate() &&
                txtPassword.validate() && txtPhone.validate() && txtConfirmPass.validate();
    }
    
    private void refreshDataList() {
        dataList = adminQuery.selectAll();
        tvAdmins.setItems(dataList);
    }
    
    public String sha1(String input) throws NoSuchAlgorithmException {
        String sha1 = null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            System.err.println("sha1 error");
        }
        return sha1;
    }
    
    private void selectWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText("Please select something first!");
        alert.showAndWait();
    }
    
    private void clearFields() {
        noti.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtPassword.setText("");
        txtConfirmPass.setText("");
    }
    
    public void initialize() throws SQLException {
        admin.setText(AdminSession.getInstance().getEmail());
        dataList = adminQuery.selectAll();
        tvAdmins.setItems(dataList);
        tcAdminName.setCellValueFactory((admin) -> {
            return admin.getValue().getAdminNameProperty();
        });
        tcEmail.setCellValueFactory((admin) -> {
            return admin.getValue().getEmailProperty();
        });
        tcPhoneNumber.setCellValueFactory((admin) -> {
            return admin.getValue().getPhoneProperty();
        });
        nameListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                txtName.validate();
            }
        };
        emailListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                txtEmail.validate();
            }
        };
        phoneListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                txtPhone.validate();
            }
        };
        pwdListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                txtPassword.validate();
            }
        };
        pwdConfirmListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                txtConfirmPass.validate();
            }
        };
    }
}
