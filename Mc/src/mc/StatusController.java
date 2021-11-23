/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import database.AdminSession;
import database.Status;
import database.Status_query;
import java.io.IOException;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;

/**
 *
 * @author Valan
 */
public class StatusController{
    private final Status_query statusQuery = new Status_query();
    private ObservableList<Status> dataList = FXCollections.observableArrayList();
    private ChangeListener<String> nameListener;
    private Status editStatus = null;
    @FXML
    private Label admin;
    
    @FXML
    private JFXTextField filterField;

    @FXML
    private JFXTextField txtStatus;

    @FXML
    private Label noti;

    @FXML
    private Label labelEditor;

    @FXML
    private TableView<Status> tvStatus;

    @FXML
    private TableColumn<Status, Integer> tcStatusID;

    @FXML
    private TableColumn<Status, String> tcStatusName;

    @FXML
    void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            save();
        }
    }
    
    public void resetListener(){
        txtStatus.setText("1");
        removeListenerStatus();
        txtStatus.setText("");
        addListenerStatus();
    }
    
    @FXML
    void clear(ActionEvent event) {
        resetListener();
        clearFields();
        noti.setText("");
    }

    @FXML
    void createNew(ActionEvent event) {
        resetListener();
        labelEditor.setText("New Status Details");
        noti.setText("");
        editStatus = null;
        clearFields();
    }

    private void selectWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText("Select something first");
        alert.showAndWait();
    }
    
    @FXML
    void delete(ActionEvent event) {
        Status selectedStatus = tvStatus.getSelectionModel().getSelectedItem();
        if (selectedStatus == null) {
            selectWarning();
        } else {
            if (statusQuery.isReferenced(selectedStatus.getStatusID())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("You cannot delete status that are already referenced.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("DELETE");
                alert.setHeaderText("Are you sure you want to delete "+ selectedStatus.getStatusName() +" status?");
                Optional<ButtonType> confirmationResponse = alert.showAndWait();
                if (confirmationResponse.get() == ButtonType.OK) {
                    boolean result = statusQuery.delete(selectedStatus);
                    if (result) {
                        dataList.remove(selectedStatus);
                        tvStatus.setItems(dataList);
                    }
                }
            }
            labelEditor.setText("New Status Details");
            editStatus = null;
        }
    }

    @FXML
    void edit(ActionEvent event) {
        Status selectedStatus = tvStatus.getSelectionModel().getSelectedItem();
        if (selectedStatus == null) {
            selectWarning();
        } else {
            labelEditor.setText("Edit Status Details");
            noti.setText("");
            editStatus = selectedStatus;
            txtStatus.setText(selectedStatus.getStatusName());
        }
    }

    @FXML
    void gotoAdmin(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoAdmin();
    }

    @FXML
    void gotoCategory(ActionEvent event) throws IOException {
        Navigator.getInstance().gotoCategory();
    }

    @FXML
    void gotoCustomer(ActionEvent event) throws IOException {
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
    
    private void addListenerStatus() {
        txtStatus.textProperty().addListener(nameListener);
    }
    
    private void removeListenerStatus() {
        txtStatus.textProperty().removeListener(nameListener);
    }
    
    @FXML
    void refresh(ActionEvent event) {
        resetListener();
        noti.setText("");
        refreshDataList();
    }

    public void save(){
        noti.setText("");
        txtStatus.textProperty().removeListener(nameListener);
        if (validate()) {
            Status extracted = extract();
            if (editStatus == null && statusQuery.isUnique(extracted.getStatusName())) {
                extracted = statusQuery.insert(extracted);
                if (extracted != null) {
                    noti.setText("New status added successfully.");
                    noti.setTextFill(Paint.valueOf("green"));
                    refreshDataList();
                } else {
                    noti.setText("An error occurred.");
                    noti.setTextFill(Paint.valueOf("red"));
                }
                clearFields();
            } else if (editStatus != null && (extracted.getStatusName().equals(editStatus.getStatusName()) || statusQuery.isUnique(extracted.getStatusName()))) {
                extracted.setStatusID(editStatus.getStatusID());
                boolean result = statusQuery.update(extracted);
                if (result) {
                    noti.setText("Status edited successfully.");
                    noti.setTextFill(Paint.valueOf("green"));
                    refreshDataList();
                } else {
                    noti.setText("An error occurred.");
                    noti.setTextFill(Paint.valueOf("red"));
                }
                editStatus = null;
                clearFields();
                labelEditor.setText("New Status Details");
            } else {
                noti.setText("This status already existed.");
                noti.setTextFill(Paint.valueOf("red"));
            }
        } else {
            txtStatus.textProperty().addListener(nameListener);
        }
    }
    
    @FXML
    void save(ActionEvent event) throws InterruptedException {
        save();
    }

    @FXML
    void search(KeyEvent event) {
        FilteredList<Status> filteredData = new FilteredList<>(dataList, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Status status) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return status.getStatusName().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Status> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvStatus.comparatorProperty());
        tvStatus.setItems(sortedData);
    }
    
    private Status extract() {
        Status s = new Status();
        s.setStatusName(txtStatus.getText());
        return s;
    }
    
    private boolean validate() {
        RequiredFieldValidator required = new RequiredFieldValidator();
        required.setMessage("Input Required");
        txtStatus.getValidators().add(required);
        txtStatus.validate();
        return txtStatus.validate();
    }
    
    private void refreshDataList() {
        dataList = statusQuery.selectAll();
        tvStatus.setItems(dataList);
    }
    
    private void clearFields() {
        txtStatus.setText("");
    }
    
    public void initialize() throws SQLException {
        admin.setText(AdminSession.getInstance().getName());
        dataList = statusQuery.selectAll();
        tvStatus.setItems(dataList);
        tcStatusName.setCellValueFactory((status) -> {
            return status.getValue().getStatusNameProperty();
        });
        tcStatusID.setCellValueFactory((status) -> {
            return status.getValue().getStatusIDProperty();
        });

        nameListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                txtStatus.validate();
            }
        };
    }
}
