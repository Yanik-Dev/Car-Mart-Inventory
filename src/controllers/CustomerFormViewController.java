/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import common.ValidationHelper;
import constant.CustomerType;
import entities.Address;
import entities.Customer;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import services.DatabaseService;
import services.FileService;

/**
 * FXML Controller class
 *
 * @author Vice Principal
 */
public class CustomerFormViewController implements Initializable {
    private TextField trnTxtbx;
    private TextField firstnameTxtbx;
    private TextField lastnameTxtbx;
    private RadioButton maleRdbtn;
    @FXML
    private ToggleGroup genderGroup;
    private RadioButton femaleRdBtn;
    private DatePicker dateOfBirthDatePckr;
    private TextField emailTxtbx;
    private TextField streetTxtbx;
    private TextField cityTxtbx;
    private TextField provinceTxtbx;
    private TextField primaryNumberTxtbx;
    private TextField secondaryNumberTxtbx;
    private ListView<String> fileListView;
    @FXML
    private Button addBtn;
    private Button deleteFilesBtn;
    @FXML
    private Button clearBtn;
    
    Customer customer;
    List<File> files;
    
 
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.files = new ArrayList<>();
        //this.deleteFilesBtn.setDisable(true);
        
    }    
    
    /**
     * populate the form fields
     */
    public void setValues(Customer customer){
        this.firstnameTxtbx.setText(customer.getFirstname());
        this.lastnameTxtbx.setText(customer.getLastname());
        this.emailTxtbx.setText(customer.getEmail());
        this.primaryNumberTxtbx.setText(customer.getPrimaryNumber());
        this.primaryNumberTxtbx.setText(customer.getSecondaryNumber());

        if("Male".equals(customer.getGender())){
            this.maleRdbtn.fire();
        }else{
            this.femaleRdBtn.fire();
        }
        //SimpleDateFormat sf = new SimpleDateFormat();
       // sf.parse(customer.getDateOfBirth()).getTime();
       // this.dateOfBirthDatePckr.setValue(new LocalDate(1,2,3));
        
        this.cityTxtbx.setText(customer.getAddress().getCity());
        this.streetTxtbx.setText(customer.getAddress().getStreet());
        this.provinceTxtbx.setText(customer.getAddress().getProvince());
    }
    
    /**
     * add a new customer record or update a record
     */
    @FXML
    public void submit(){
         //check if the user wants to add customer without docs
        if(this.files.size() <= 0){
            Alert alert = new Alert(AlertType.CONFIRMATION,"Are you sure you want to add customer without any documents", ButtonType.YES, ButtonType.NO);
            alert.initOwner(this.addBtn.getScene().getWindow());
            Optional<ButtonType> response = alert.showAndWait(); 
            if(response.isPresent() && response.get() == ButtonType.NO){
                return;
            }   
        }
        //ends function if validate fails
        if(!this.isFormValid()){ return; }
        
       
        customer = new Customer();
        Address address = new Address();   
        
        address.setCity(this.cityTxtbx.getText());        
        address.setProvince(this.provinceTxtbx.getText());
        address.setStreet(this.streetTxtbx.getText());
        
        customer.setTRN(this.trnTxtbx.getText());
        customer.setFirstname(this.firstnameTxtbx.getText());
        customer.setLastname(this.lastnameTxtbx.getText());
        customer.setGender((this.maleRdbtn.isSelected())?"Male":"Female");
        if(this.dateOfBirthDatePckr.getValue()!=null){
            customer.setDateOfBirth(this.dateOfBirthDatePckr.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        customer.setEmail(this.emailTxtbx.getText());
        customer.setPrimaryNumber(this.primaryNumberTxtbx.getText());
        customer.setSecondaryNumber(this.secondaryNumberTxtbx.getText());
        customer.setNotes(this.firstnameTxtbx.getText());
        customer.setType(CustomerType.POTENTIAL);
        customer.setAddress(address);
        
        if(customer.getId() < 0){
            if(DatabaseService.create(customer)){
                this.clear();
            };
        }else{
            if(DatabaseService.update(customer)){
                this.clear();
            };
        }
    }
    
    /**
     * open file dialog to choose files associated with a customer
     */
    public void openFileDialog(){
      List<File> fileList = new ArrayList<>();
      fileList = FileService.multiFileChooser("Select Customer Files");
      ObservableList<String> fileNames = FXCollections.observableArrayList();
      if(fileList == null){ return; }
      for(File file : fileList){
           fileNames.add(file.getName());
           this.files.add(file);
                   
      }
      this.fileListView.setItems(fileNames);
    }
    
    /**
     * remove files from listview
     */
    public void deleteFiles(){
        ObservableList<Integer> indices = FXCollections.observableArrayList();
        indices = this.fileListView.getSelectionModel().getSelectedIndices();
        if(indices.size()<=0){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("No selection");
            alert.setContentText("Please select a file(s) to delete");
            alert.initOwner(this.addBtn.getScene().getWindow());
            alert.showAndWait();
            return;
        }
        for(Integer i : indices){
            this.fileListView.getItems().remove(i.intValue());
            this.files.remove(i.intValue());
        }
    }
    
    /**
     * determine if the button to delete files should be disabled
     */
    public void shouldDisableDelete(){
        if(this.fileListView.getSelectionModel().getSelectedIndex() >= 0){
            this.deleteFilesBtn.setDisable(false);
        }else{
            this.deleteFilesBtn.setDisable(true);
        }
    }
        
    /**
     * validate the form fields
     */
    public boolean isFormValid(){
        int counter = 0;
        String message = "";
        String errorStyle = "";
        boolean isValid = true;
        this.removeErrorClass();
        if(!this.trnTxtbx.getText().matches("[0-9]+") || this.trnTxtbx.getText().trim().replace("-", "").length() != 9){
            counter++;
            message += counter+". TRN must be at 9 digits\n";
            this.trnTxtbx.getStyleClass().add("error");;
            isValid = false;
        }
        if(this.firstnameTxtbx.getText().trim().length() < 2){
            counter++;
            message += counter+". First name cannot have less than 2 characters\n";
            this.firstnameTxtbx.getStyleClass().add("error");;
            isValid = false;
        }
        if(this.lastnameTxtbx.getText().trim().length() < 2){
            counter++;
            message += counter+". Last name cannot have less than 2 characters\n";
            this.lastnameTxtbx.getStyleClass().add("error");;
            isValid = false;
        }
        if(this.dateOfBirthDatePckr.getValue() != null){
            if(LocalDate.now().getYear() - this.dateOfBirthDatePckr.getValue().getYear() < 18){
                counter++;
                message += counter+". Invalid age - Customer is under 18\n";
                this.dateOfBirthDatePckr.getStyleClass().add("error");;
                isValid = false;
            }
        }
        if(!ValidationHelper.isEmailValid(this.emailTxtbx.getText().trim())){
            counter++;
            message += counter+". Invalid Email\n";
            this.emailTxtbx.getStyleClass().add("error");;
            isValid = false;
        }
        if(!ValidationHelper.isPhoneNumberValid(this.primaryNumberTxtbx.getText().trim())){
            counter++;
            message += counter+". Contact number 1 must be a valid phone number\n";
            this.primaryNumberTxtbx.getStyleClass().add("error");;
            isValid = false;
        }
        if(this.secondaryNumberTxtbx.getText().trim().length() != 0 &&  
                !ValidationHelper.isPhoneNumberValid(this.secondaryNumberTxtbx.getText().trim())){
            counter++;
            message += counter+". Contact number 2 must be a valid phone number\n";
            this.secondaryNumberTxtbx.getStyleClass().add("error");;
            isValid = false;
        }
        if(this.streetTxtbx.getText().trim().length() < 4){
            counter++;
            message += counter+". Street/District must be at least 4 characters\n";
            this.streetTxtbx.getStyleClass().add("error");
            isValid = false;
        }
        if(this.cityTxtbx.getText().trim().length() < 4){
            counter++;
            message += counter+". City/Town must be at least 4 characters\n";
            this.cityTxtbx.getStyleClass().add("error");
            isValid = false;
        }
        if(this.provinceTxtbx.getText().trim().length() < 5){
            counter++;
            message += counter+". Province must be at least 5 characters\n";
            this.provinceTxtbx.getStyleClass().add("error");
            isValid = false;
        }
        
        if(counter > 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText((counter > 1)?counter+" Errors": counter+" Error");
            alert.setContentText(message);
            alert.initOwner(this.addBtn.getScene().getWindow());
            alert.showAndWait();
        }
        return isValid;
    }
    
    /**
     * remove error style for form fields
     */
    public void removeErrorClass(){
        this.trnTxtbx.getStyleClass().remove("error");
        this.firstnameTxtbx.getStyleClass().remove("error");
        this.lastnameTxtbx.getStyleClass().remove("error");
        this.emailTxtbx.getStyleClass().remove("error");
        this.dateOfBirthDatePckr.getStyleClass().remove("error");
        this.primaryNumberTxtbx.getStyleClass().remove("error");
        this.secondaryNumberTxtbx.getStyleClass().remove("error");
        this.streetTxtbx.getStyleClass().remove("error");
        this.cityTxtbx.getStyleClass().remove("error");
        this.provinceTxtbx.getStyleClass().remove("error");
    }
    
   /**
     * clear form fields
     */
    public void clear(){
        this.trnTxtbx.clear();
        this.firstnameTxtbx.clear();
        this.lastnameTxtbx.clear();
        this.emailTxtbx.clear();
        this.dateOfBirthDatePckr.setValue(null);
        this.primaryNumberTxtbx.clear();
        this.secondaryNumberTxtbx.clear();
        this.streetTxtbx.getStyleClass().clear();
        this.cityTxtbx.getStyleClass().clear();
        this.provinceTxtbx.getStyleClass().clear();
        this.fileListView.getItems().clear();
    }
}
