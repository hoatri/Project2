package mc;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import database.AdminSession;
import database.Admin_query;
import database.Customer;
import database.Customer_query;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javax.xml.bind.DatatypeConverter;

public class LoginController{
    private Customer_query customer_query = new Customer_query();
    //private Admin_query adminDao = new Admin_query();
    private ChangeListener<String> loginEmailListener;
    private ChangeListener<String> loginPassListener;
    private ChangeListener<String> nameListener;
    private ChangeListener<String> addressListener;
    private ChangeListener<String> emailListener;
    private ChangeListener<String> phoneListener;
    private ChangeListener<String> pwdListener;
    private ChangeListener<String> confirmPwdListener;
    
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

    @FXML
    private Label signUpSucc;
    
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
    void Login(MouseEvent event) throws NoSuchAlgorithmException, IOException {
        Login();
    }
    
    @FXML
    void SignUp(MouseEvent event) throws NoSuchAlgorithmException {
        signUp();
    }
    
    @FXML
    void onEnterPressed(KeyEvent event) throws NoSuchAlgorithmException, IOException {
        if (signUpPan.visibleProperty().getValue()==false && 
            event.getCode() == KeyCode.ENTER) {
            Login();
        }else if(signUpPan.visibleProperty().getValue()==true && 
            event.getCode() == KeyCode.ENTER){
            signUp();
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
        loginEmail.validate();
        loginPass.getValidators().add(required);
        loginPass.validate();
//        loginEmail.textProperty().addListener((o, oldVal, newVal) -> {
//            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
//                loginEmail.validate();
//            }
//        });
//        loginPass.textProperty().addListener((o, oldVal, newVal) -> {
//            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
//                loginPass.validate();
//            }
//        });
        return loginEmail.validate() && loginPass.validate();
    }
    
    void Login() throws NoSuchAlgorithmException, IOException {
        if (validateInfo()) {
            String email = loginEmail.getText();
            String password = sha1(loginPass.getText());
            if (txtAdmin.visibleProperty().getValue()==true && Admin_query.authenticateAdmin(email, password)) {
                AdminSession.getInstance().setEmail(email);
                Navigator.getInstance().gotoAdmin();
            }else if(txtCustomer.visibleProperty().getValue()==true && Admin_query.authenticateAdmin(email, password)){
                AdminSession.getInstance().setEmail(email);
                Navigator.getInstance().gotoAdmin();
            }
            else {
                loginError.setVisible(true);
            }
        }
    }
    
    @FXML
    void toggleSignUp(MouseEvent event) {
        if(signUpPan.visibleProperty().getValue()==false){
            fullName.setText("1");
            address.setText("1");
            signUpEmail.setText("asd@asd.asd");
            phoneNumber.setText("0000000000");
            signUpPass.setText("asdf1234");
            confirmPass.setText("asdf1234");
            removeListenerSignUp();
            fullName.setText("");
            address.setText("");
            signUpEmail.setText("");
            phoneNumber.setText("");
            signUpPass.setText("");
            confirmPass.setText("");
            addListenerSignUp();
            signUpPan.setVisible(true);
        }else{
            loginEmail.setText("1");
            loginPass.setText("1");
            removeListenerLogin();
            loginEmail.setText("");
            loginPass.setText("");
            addListenerLogin();
            signUpPan.setVisible(false);
        }
    }
    
    private void addListenerLogin() {
        loginEmail.textProperty().addListener(loginEmailListener);
        loginPass.textProperty().addListener(loginPassListener);
        
    }

    private void removeListenerLogin() {
        loginEmail.textProperty().removeListener(loginEmailListener);
        loginPass.textProperty().removeListener(loginPassListener);
    }
    
    private void addListenerSignUp() {
        fullName.textProperty().addListener(nameListener);
        address.textProperty().addListener(addressListener);
        signUpEmail.textProperty().addListener(emailListener);
        phoneNumber.textProperty().addListener(phoneListener);
        signUpPass.textProperty().addListener(pwdListener);
        confirmPass.textProperty().addListener(confirmPwdListener);
    }

    private void removeListenerSignUp() {
        fullName.textProperty().removeListener(nameListener);
        address.textProperty().removeListener(addressListener);
        signUpEmail.textProperty().removeListener(emailListener);
        phoneNumber.textProperty().removeListener(phoneListener);
        signUpPass.textProperty().removeListener(pwdListener);
        confirmPass.textProperty().removeListener(confirmPwdListener);
    }
    
    private boolean validateSignup() {
        RequiredFieldValidator required = new RequiredFieldValidator();
        RegexValidator regexEmail = new RegexValidator("Please enter valid email");
        regexEmail.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        RegexValidator regexPwd = new RegexValidator("Password must have at least 8 characters and include letters and numbers");
        regexPwd.setRegexPattern("^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,20}$");
        NumberValidator number = new NumberValidator();
        required.setMessage("Input Required");
        number.setMessage("Must be number");
        fullName.getValidators().add(required);
        fullName.validate();
        signUpEmail.getValidators().addAll(required, regexEmail);
        signUpEmail.validate();
        signUpPass.getValidators().addAll(required, regexPwd);
        signUpPass.validate();
        confirmPass.getValidators().add(required);
        confirmPass.validate();
        address.getValidators().add(required);
        address.validate();
        phoneNumber.getValidators().addAll(required, number);
        phoneNumber.validate();
        return fullName.validate() && address.validate() && signUpEmail.validate()
        && phoneNumber.validate() && signUpPass.validate() && confirmPass.validate();
    }
    
    private void signUp() throws NoSuchAlgorithmException {
        removeListenerSignUp();
        if (validateSignup()) {
            if (!signUpPass.getText().equals(confirmPass.getText())) {
                signUpSucc.setText("Password and confirm password do not match.");
                signUpSucc.setTextFill(Paint.valueOf("red"));
            } else {
                if (customer_query.isUnique(signUpEmail.getText())) {
                    Customer c = new Customer();
                    c.setFullName(fullName.getText());
                    c.setAddress(address.getText());
                    c.setEmail(signUpEmail.getText());
                    c.setPhone(phoneNumber.getText());
                    c.setPassword(sha1(signUpPass.getText()));
                    Customer newCustomer = customer_query.insert(c);
                    if (newCustomer != null) {
                        signUpSucc.setText("Register Successfully! You can login now.");
                        signUpSucc.setTextFill(Paint.valueOf("green"));
                        fullName.setText("");
                        address.setText("");
                        signUpEmail.setText("");
                        phoneNumber.setText("");
                        signUpPass.setText("");
                        confirmPass.setText("");
                    } else {
                        signUpSucc.setText("Register unsuccesfully.");
                        signUpSucc.setTextFill(Paint.valueOf("red"));
                    }
                } else {
                    signUpSucc.setText("This email is already registered.");
                    signUpSucc.setTextFill(Paint.valueOf("red"));
                }
            }
        } else {
            addListenerSignUp();
        }
    }
    
    public void initialize() {
        loginEmailListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                loginEmail.validate();
            }
        };
        loginPassListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                loginPass.validate();
            }
        };
        nameListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                fullName.validate();
            }
        };
        addressListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                address.validate();
            }
        };
        emailListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                signUpEmail.validate();
            }
        };
        phoneListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                phoneNumber.validate();
            }
        };
        pwdListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                signUpPass.validate();
            }
        };
        confirmPwdListener = (ObservableValue<? extends String> o, String oldVal, String newVal) -> {
            if (newVal == null ? oldVal != null : !newVal.equals(oldVal)) {
                confirmPass.validate();
            }
        };
    }
}
