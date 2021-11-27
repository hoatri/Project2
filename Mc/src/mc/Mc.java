/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mc;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Valan
 */
public class Mc extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Navigator.getInstance().setStage(primaryStage);
        Navigator.getInstance().getStage().getIcons().add(new Image("Image/arches-logo_108x108.jpg"));
        Navigator.getInstance().getStage().setTitle("Mc");
        Navigator.getInstance().gotoLogin();
        Navigator.getInstance().getStage().show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
