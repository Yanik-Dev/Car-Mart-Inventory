/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vice Principal
 */
public class MainViewController implements Initializable {
    User user;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private SplitMenuButton splitMenuButton;
    public void loadChild(URL url){
        try {
            this.mainBorderPane.setLeft(FXMLLoader.load(url));
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    public void setUser(User user){
        this.user = user;
        this.splitMenuButton.setText(user.getUsername().toUpperCase());
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    @FXML
    public void loadCustomerView(){
        this.loadChild(getClass().getResource("../views/CustomerTableView.fxml"));
    }
    @FXML
    public void loadVehicleView(){
        this.loadChild(getClass().getResource("../views/VehicleTableView.fxml"));
    }
    
     @FXML
    public void loadUserView(){
         NavController navCtrl = new NavController();
         navCtrl.showDialog((Stage)this.mainBorderPane.getScene().getWindow(), 
                this.getClass().getResource("../views/UserView.fxml"), "");  
    }
    
    @FXML
    public void signOut(){
        NavController nav = new NavController();
        nav.signOut((Stage)this.mainBorderPane.getScene().getWindow());
    }
}
