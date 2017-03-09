/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 *
 * @author Vice Principal
 */
public class NavController {
        public void openMain(Window window, User user){
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/MainView.fxml"));
        try {
            Stage stage = (Stage) window;
            root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets()
                  .add( getClass().getResource("../css/main.css").toString());
            
            MainViewController controller = loader.<MainViewController>getController();
            controller.setUser(user);
            stage.setScene(scene);
           
            stage.show();
            
           // Get current screen of the stage      
           ObservableList<Screen> screens = Screen.getScreensForRectangle(new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()));

            // Change stage properties
            Rectangle2D bounds = screens.get(0).getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
        } catch (IOException ex) {
            Logger.getLogger(NavController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void signOut(Window window){
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/LoginView.fxml"));
        try {
            Stage stage = (Stage) window;
            root = loader.load();
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.setWidth(625);
            stage.setHeight(504);
            

            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(NavController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void showDialog(Stage owner, URL url, String title) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(loader.load());
            scene.getStylesheets()
                  .add( getClass().getResource("../css/main.css").toString());
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void load(Window window, URL url){
        Parent root;
        try {
            Stage stage = (Stage) window;
            root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            //stage.setMaximized(true);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(NavController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public <E> void showEditDialog(Stage owner, URL url, String title, E className) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(url);

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(loader.load());
            dialogStage.setScene(scene);
            
            E controller = loader.getController();
            
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
}
