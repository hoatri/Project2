/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import database.Admin_query;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Valan
 */
public class LoginController implements Initializable{
    @FXML
    private Text txtCustomer1;

    @FXML
    private Text txtAdmin1;

    @FXML
    private AnchorPane loginPan;

    @FXML
    private Text txtCustomer;

    @FXML
    private Text txtAdmin;

    @FXML
    private JFXTextField loginEmail;

    @FXML
    private JFXPasswordField loginPass;

    @FXML
    private Text loginError;

    @FXML
    private AnchorPane signUpPan;

    @FXML
    private JFXTextField fullName;

    @FXML
    private JFXTextField signUpEmail;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXPasswordField signUpPass;

    @FXML
    private JFXPasswordField confirmPass;

    @FXML
    private JFXTextField phoneNumber;
    
    @FXML
    private Line line;

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
    
    @FXML
    void toggleSignUp(MouseEvent event) {
        if(signUpPan.visibleProperty().getValue()==false){
            signUpPan.setVisible(true);
        }else{
            signUpPan.setVisible(false);
        }
    }
    
    @FXML
    void adminPan(MouseEvent event) {
        txtCustomer.setVisible(false);
        txtCustomer1.setVisible(false);
        txtAdmin.setVisible(true);
        txtAdmin1.setVisible(true);
        line.setVisible(true);
    }

    @FXML
    void customerPan(MouseEvent event) {
        txtCustomer.setVisible(true);
        txtCustomer1.setVisible(true);
        txtAdmin.setVisible(false);
        txtAdmin1.setVisible(false);
        line.setVisible(false);
    }
    
    private boolean validateInfo() {
        RequiredFieldValidator required = new RequiredFieldValidator();
        required.setMessage("Input Required");
        loginEmail.getValidators().add(required);
        loginPass.getValidators().add(required);
        loginEmail.textProperty().addListener((o, oldVal, newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                loginEmail.validate();
            }
        });
        loginPass.textProperty().addListener((o, oldVal, newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                loginPass.validate();
            }
        });
        return loginEmail.validate() && loginPass.validate();
    }
    
    void Login() throws NoSuchAlgorithmException {
        if (validateInfo()) {
            String email = loginEmail.getText();
            String password = sha1(loginPass.getText());
            if (txtAdmin.visibleProperty().getValue()==true&& Admin_query.authenticateAdmin(email, password)) {
//                AdminSession.getInstance().setEmail(email);
//                Navigator.getInstance().gotoProductIndex();
                
            }else if(txtCustomer.visibleProperty().getValue()==true){
                
            }
            else {
//                loginError.setVisible(true);
                loginError.setText("fail");
            }
        }
    }
    
    @FXML
    void Login(MouseEvent event) throws NoSuchAlgorithmException {
        Login();
    }
    
    @FXML
    void onEnterPressed(KeyEvent event) throws NoSuchAlgorithmException {
        if ((txtCustomer.visibleProperty().getValue()==true || txtAdmin.visibleProperty().getValue()==true) && event.getCode() == KeyCode.ENTER) {
            Login();
        }else{
            loginError.setText("sign up");
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
