/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import constant.UserType;
import entities.User;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import security.Security;
import services.DatabaseService;
import services.UserService;

/**
 * FXML Controller class
 *
 * @author Vice Principal
 */
public class UserViewController implements Initializable {
    @FXML
    private TextField searchTxtbx;
    @FXML
    private TextField emailTxtbx;
    @FXML
    private TableView<User> userTable;
    @FXML
    private ContextMenu userContextMenu;
    @FXML
    private MenuItem editMenuItem;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> userTypeColumn;
    @FXML
    private Button addBtn;
    @FXML
    private TextField usernameTxtbx;
    @FXML
    private ComboBox<String> userTypeCmbx;
    @FXML
    private PasswordField passwordTxtbx;
    
    private ObservableList<User> userRecords = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTableColumns();
        initializeTable();
        setUserTypeComboBox();
    }    
    
    /**
     * specify the record to be stored in each column
     */
    public void initializeTableColumns(){
        this.emailColumn.setCellValueFactory((c)->{
            return  new SimpleStringProperty(c.getValue().getEmail());
        });
        this.usernameColumn.setCellValueFactory((c)->{
            return new SimpleStringProperty(c.getValue().getUsername());
        });
        this.userTypeColumn.setCellValueFactory((c)->{
            UserType val = c.getValue().getUserType();
            return new SimpleStringProperty((val == null)? null: val.name());
        });
    }
    
    /**
     * initialize user table with records
     */
    public void initializeTable(){
        this.userRecords.clear();
        List<User> users = DatabaseService.findAll(User.class);
        for(User u: users){
            this.userRecords.add(u);
        }
        userTable.setItems(this.userRecords);
    }
    
    /**
     * set the combo box list with the user types
     */
    private void setUserTypeComboBox(){
        String type;
        ObservableList<String> types = FXCollections.observableArrayList();
        for(UserType s : UserType.values()){
            type = s.name();
            types.add(type);
        }
        this.userTypeCmbx.setItems(types);
    }
    
    /**
     * search for a record based on the values passed to the search textbox
     */
    @FXML
    private void search(){
        this.userRecords.clear();
        List<User> users = UserService.search(this.searchTxtbx.getText());;
        for(User u: users){
            this.userRecords.add(u);
        }
        userTable.setItems(this.userRecords);
    }
    
    /**
     * create or update a user record on button click event
     */
    @FXML
    private void submit() {
        
        if(!this.isFormValid()){ return; }
        Security security = new Security();
        //hash password
        String salt = Security.generateSalt();
        String password = security.get_SHA_512_SecurePassword(passwordTxtbx.getText().trim(), salt);
        User user = new User();
        
        user.setEmail(emailTxtbx.getText());
        user.setUserType(UserType.ADMIN);
        user.setUsername(usernameTxtbx.getText());
        user.setPassword(password);
        user.setSalt(salt);
                
        if(!addBtn.getText().equals("Save")){
            if(UserService.exist(user) != null){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("The user you have entered already exist.");
                alert.show();
                return;
            }
            if(DatabaseService.create(user)){
                this.clear();
            };
        }else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().add(ButtonType.NO);
            alert.setContentText("Are you sure u want to modify this user?");
            if(ButtonType.NO == alert.showAndWait().get()){
                this.clear();
            }; 
            if(DatabaseService.update(user)){
                this.clear();
            }
        }
        this.initializeTable();
    }  
    
    /**
     * set the form fields on edit menu click event
     */
    @FXML
    private void setFormFields(){
        //exit function if the an record is not selected in the table
        if(userTable.getSelectionModel().getSelectedIndex() < 0){
            return;
        }
        String email = userTable.getSelectionModel().getSelectedItem().getEmail();
        User user = DatabaseService.findOne(email, User.class);
        if(user != null){
            this.emailTxtbx.setText(user.getEmail());
            this.usernameTxtbx.setText(user.getUsername());
            this.userTypeCmbx.getSelectionModel().select(user.getUserType().name());
            this.addBtn.setText("Save");
        }
    }
    
    /**
     * clear form fields
     */
    @FXML
    public void clear(){
        this.emailTxtbx.clear();
        this.usernameTxtbx.clear();
        this.passwordTxtbx.clear();
        this.userTypeCmbx.getSelectionModel().clearSelection();
        this.addBtn.setText("Add");
    }
        
    /**
     * validate form
     * @return true if form is valid
     */
    public boolean isFormValid(){
        boolean isClean = true;
        String content = "";
        int counter = 0;
        Alert alert = new Alert(AlertType.ERROR);
        if(this.usernameTxtbx.getText().trim().length() < 4){
            counter++;
            content +=counter+". Username must be 4 characters or more\n";
            isClean = false;
        }
        if(!this.addBtn.getText().equals("Save")){
            if(this.passwordTxtbx.getText().trim().length() < 8){
                counter++;
                content +=counter+". Password must be 8 characters or more\n";
                isClean = false;
            }
        }
        if(this.userTypeCmbx.getSelectionModel().getSelectedIndex() < 0){
            counter++;
            content +=counter+". You must select a user type\n";  
            isClean = false;
        }
        if(counter > 0){
            alert.setHeaderText((counter > 1)?counter+" Errors":counter+" Error");
            alert.setContentText(content);
            alert.show();
        }
        return isClean;
    }
    
    
}
