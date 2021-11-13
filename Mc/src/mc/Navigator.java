package mc;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navigator {
    public static final String LOGIN_FXML = "Login.fxml";
    public static final String ADMIN_FXML = "Admin.fxml";
    
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
}
