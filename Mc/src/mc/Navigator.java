package mc;

import database.Product;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigator {
    public static final String LOGIN_FXML = "Login.fxml";
    public static final String ADMIN_FXML = "Admin.fxml";
    public static final String STATUS_FXML = "Status.fxml";
    public static final String CATEGORY_FXML = "Categories.fxml";
    public static final String PRODUCT_FXML = "Products.fxml";
    public static final String PRODUCT_EDIT_FXML = "ProductEdit.fxml";
    public static final String CUSTOMER_FXML = "Customers.fxml";
    public static final String ORDER_FXML = "Orders.fxml";
    public static final String ORDER_DETAIL_FXML = "OrderDetail.fxml";
    public static final String HOME_FXML = "Home.fxml";
    public static final String BROWSE_FXML = "BrowseProducts.fxml";
    public static final String TEST_FXML = "TreeView.fxml";
    public static final String DETAIL_FXML = "Detail.fxml";
    public static final String ACCOUNT_FXML = "MyAccount.fxml";
    public static final String ABOUT_US_FXML = "AboutUs.fxml";
    public static final String CART_FXML = "Cart.fxml";
    public static final String CHECKOUT_FXML = "Checkout.fxml";
    
    private FXMLLoader loader;
    private Stage stage = null;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public Stage getStage() {
        return this.stage;
    }
    
    private static Navigator nav = null;
    
    private Navigator() {
    }
    
    public static Navigator getInstance() {
        if (nav == null) {
            nav = new Navigator();
        }
        return nav;
    }
    
    private void goTo(String fxml) throws IOException {
        this.loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.centerOnScreen();
    }
    
    public void gotoLogin() throws IOException {
        this.goTo(LOGIN_FXML);
    }

    public void gotoAdmin() throws IOException {
        this.goTo(ADMIN_FXML);
    }
    
    public void gotoStatus() throws IOException {
        this.goTo(STATUS_FXML);
    }
    public void gotoCategory() throws IOException {
        this.goTo(CATEGORY_FXML);
    }

    public void gotoProduct() throws IOException {
        this.goTo(PRODUCT_FXML);
    }

//    public void gotoProductEdit(Product editProduct) throws IOException, SQLException {
//        this.goTo(PRODUCT_EDIT_FXML);
//        ProductEditController ctrl = loader.getController();
//        ctrl.initialize(editProduct);
//    }

    public void gotoCustomer() throws IOException {
        this.goTo(CUSTOMER_FXML);
    }

    public void gotoOrder() throws IOException {
        this.goTo(ORDER_FXML);
    }

//    public void gotoOrderDetail(Order order) throws IOException, SQLException {
//        this.goTo(ORDER_DETAIL_FXML);
//        OrderDetailController ctrl = loader.getController();
//        ctrl.initialize(order);
//    }

    public void gotoHome() throws IOException {
        this.goTo(HOME_FXML);
    }

    public void gotoMyAccount() throws IOException {
        this.goTo(ACCOUNT_FXML);
    }

    public void gotoAboutUs() throws IOException {
        this.goTo(ABOUT_US_FXML);
    }

    public void gotoTest() throws IOException {
        this.goTo(TEST_FXML);
    }

//    public void gotoBrowse(Category category) throws IOException, SQLException {
//        this.goTo(BROWSE_FXML);
//        BrowseProductsController ctrl = loader.getController();
//        ctrl.initialize(category);
//    }

//    public void gotoBrowse(String search) throws IOException, SQLException {
//        this.goTo(BROWSE_FXML);
//        BrowseProductsController ctrl = loader.getController();
//        ctrl.initialize(search);
//    }

//    public void gotoDetail(Product product) throws IOException, SQLException {
//        this.goTo(DETAIL_FXML);
//        DetailController ctrl = loader.getController();
//        ctrl.initialize(product);
//    }

    public void gotoCart() throws IOException, SQLException {
        this.goTo(CART_FXML);
    }

    public void gotoCheckout() throws IOException, SQLException {
        this.goTo(CHECKOUT_FXML);
    }
}
