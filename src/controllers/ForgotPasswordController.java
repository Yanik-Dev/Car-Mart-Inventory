/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vice Principal
 */
public class ForgotPasswordController implements Initializable {
    @FXML
    private TextField emailTxtbx;
    @FXML
    private Button submitBtn;
    @FXML
    private Button tokenBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void showResetDialog(){
        NavController navCtrl = new NavController();
       
        navCtrl.showDialog((Stage) this.submitBtn.getScene().getWindow().getScene().getWindow(), 
                this.getClass().getResource("../views/ChangePasswordView.fxml"), "Reset Password");
        ((Stage)this.submitBtn.getScene().getWindow()).close();
    }
    
}
