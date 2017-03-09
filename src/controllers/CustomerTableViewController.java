/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import constant.CustomerType;
import entities.Customer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import services.CustomerService;
import services.DatabaseService;

/**
 * FXML Controller class
 *
 * @author Vice Principal
 */
public class CustomerTableViewController implements Initializable {
    @FXML
    private TextField trnTxtbx;
    @FXML
    private TextField nameTxtbx;
    @FXML
    private TextField emailTxtbx;
    @FXML
    private ComboBox<String> typeCmbx;
    @FXML
    private Button newBtn;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> trnColumn;
    @FXML
    private TableColumn<Customer, String> typeColumn;
    @FXML
    private TableColumn<Customer, String> firstnameColumn;
    @FXML
    private TableColumn<Customer, String> lastnameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> emailColumn;
    @FXML
    private TableColumn<Customer, String> PrimaryNumberColumn;
    @FXML
    private TableColumn<Customer, String> secondaryNumberColumn;
    @FXML
    private TableColumn<Customer, String> dateCreatedColumn;
    @FXML
    private TableColumn<Customer, String> lastUpdatedColumn;
    @FXML
    private FlowPane imageFlowGrid;
    @FXML
    private TreeView<String> fileTreeView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initTypeComboBox();
        this.initTableColumns();
        this.initTable();
    }    
    
    public void initTypeComboBox(){
        ObservableList<String> types = FXCollections.observableArrayList();
        for(CustomerType ct : CustomerType.values()){
            types.add(ct.name());
        }
        this.typeCmbx.setItems(types);
    }
    
    public void initTableColumns(){
        this.trnColumn.setCellValueFactory(cell->{
            return new SimpleStringProperty(cell.getValue().getTRN());
        });
        this.firstnameColumn.setCellValueFactory(cell->{
            return new SimpleStringProperty(cell.getValue().getFirstname());
        });
        this.lastnameColumn.setCellValueFactory(cell->{
            return new SimpleStringProperty(cell.getValue().getLastname());
        });
        this.typeColumn.setCellValueFactory(cell->{
            return new SimpleStringProperty(cell.getValue().getType().name());
        });
        this.addressColumn.setCellValueFactory(cell->{
            return new SimpleStringProperty(cell.getValue().getAddress().getStreet()+","
                    + " "+cell.getValue().getAddress().getCity()+", "+cell.getValue().getAddress().getProvince());
        });
        this.emailColumn.setCellValueFactory(cell->{
            return new SimpleStringProperty(cell.getValue().getEmail());
        });
        this.PrimaryNumberColumn.setCellValueFactory(cell->{
            return new SimpleStringProperty(cell.getValue().getPrimaryNumber());
        });
        this.secondaryNumberColumn.setCellValueFactory(cell->{
            return new SimpleStringProperty(cell.getValue().getSecondaryNumber());
        });
        this.dateCreatedColumn.setCellValueFactory(cell->{
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/YYYY");
            Date date = cell.getValue().getCreatedDate();
            return new SimpleStringProperty((date != null)? sf.format(date):"");
        });
        this.lastUpdatedColumn.setCellValueFactory(cell->{
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/YYYY");
            Date date = cell.getValue().getUpdatedDate();
            return new SimpleStringProperty((date != null)? sf.format(date):"");
        });
    }
    @FXML
    public void search(){
        Customer customerQuery = new Customer();
        customerQuery.setFirstname(this.nameTxtbx.getText().trim());
        customerQuery.setTRN(this.trnTxtbx.getText().trim());
        customerQuery.setEmail(this.emailTxtbx.getText().trim());
        //customerQuery.setType(CustomerType.valueOf(this.typeCmbx.getSelectionModel().getSelectedItem()));
        
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        List<Customer> result =  CustomerService.search(customerQuery);
        for(Customer c : result){
            customers.add(c);
        }
        this.customerTable.getItems().clear();
        this.customerTable.setItems(customers);
    }
    
    public void initTable(){
        DatabaseService.findAll(Customer.class);
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        for(Customer c: DatabaseService.findAll(Customer.class)){
            customers.add(c);
        }
        this.customerTable.setItems(customers);
    }
    
    public void openNewCustomerForm(){
        NavController navCtrl = new NavController();
        navCtrl.showDialog((Stage)this.newBtn.getScene().getWindow(), 
                this.getClass().getResource("../views/CustomerFormView.fxml"), "");
        this.initTable();
    }
    
}
