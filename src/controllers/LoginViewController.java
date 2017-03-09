/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import entities.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import security.Security;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Vice Principal
 */
public class LoginViewController implements Initializable {
    @FXML
    private Pane loginPane;
    @FXML
    private Button signInBtn;
    @FXML
    private Label errLbl;
    @FXML
    private TextField usernameTxtbox;
    @FXML
    private PasswordField passwordTxtbox;
    @FXML
    private Button forgetPasswordBtn;
    @FXML
    private ProgressIndicator loginProgressIndicator;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    /**
     * authenticate a user credentials
     */
    public void authenticate(){
        User user = new User();
        user.setUsername(this.usernameTxtbox.getText().trim());
        user.setEmail(this.usernameTxtbox.getText().trim());
        
        User result = UserService.exist(user);
        
        //check if password hashed are equal
        if(result != null){
            String newp = Security.get_SHA_512_SecurePassword(this.passwordTxtbox.getText().trim(), result.getSalt());
            String oldp = result.getPassword();
            if( newp.equals(oldp) ){
                NavController navCtrl = new NavController();
                errLbl.setVisible(false); 
                navCtrl.openMain(this.signInBtn.getScene().getWindow(), result);
            }            
        }
        errLbl.setVisible(true);
        
    }
    
    /**
     * show a forget password dialog 
     */
    public void showHelpDialog(){
        NavController navCtrl = new NavController();
        navCtrl.showDialog((Stage) this.signInBtn.getScene().getWindow(), 
                getClass().getResource("../views/ForgotPassword.fxml"),
                "Forget Password");
    }
    
}
