/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import constant.CCUnit;
import constant.UsageType;
import entities.Make;
import entities.Vehicle;
import entities.VehicleType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import services.DatabaseService;
import services.FileService;

/**
 * FXML Controller class
 *
 * @author Vice Principal
 */
public class VehicleFormViewController implements Initializable {
    
    @FXML
    private Button addBtn;
    @FXML
    private Button deleteFilesBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Button addImageBtn;
    @FXML
    private TextField modelTxtbx;
    @FXML
    private RadioButton newRdbtn;
    @FXML
    private RadioButton usedRdBtn;
    @FXML
    private Button CancelBtn;
    @FXML
    private Button deleteImageBtn;
    @FXML
    private ComboBox<String> makeCmbx;
    @FXML
    private TextField roadNameTxtbx;
    @FXML
    private Spinner<Integer> mileageSpnr;
    @FXML
    private Spinner<Double> sellingPriceSpnr;
    @FXML
    private Spinner<Double> unitCostSpnr;
    @FXML
    private Spinner<Integer> quantitySpnr;
    @FXML
    private TextArea noteTxtArea;
    @FXML
    private Spinner<Integer> reoderLevelSpnr;
    @FXML
    private ComboBox<String> vehicleTypeCmbx;
    @FXML
    private Spinner<Integer> ccTxtbx;
    @FXML
    private ComboBox<String> unitCmbx;
    @FXML
    private Spinner<Integer> yearSpnr;
    @FXML
    private HBox hbox;
    @FXML
    private ScrollPane imageScrollPane;
    
    Vehicle vehicle;
    List<File> files;
    List<ImageView> images;
    List<BorderPane> selectedImages;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.files = new ArrayList<>();
        this.images = new ArrayList<>();
        this.selectedImages = new ArrayList<>();
        this.initMake();
        this.initVehicleType();
        this.initUnit();
        
    } 
    
    /**
     * load unit list
     */
    public void initUnit(){
        ObservableList<String> unitList = FXCollections.observableArrayList();
        for(CCUnit unit : CCUnit.values()){
           unitList.add(unit.name());
        }
         this.unitCmbx.setItems(unitList);
    }
    
    /**
     * load make
     */
    public void initMake(){
        ObservableList<String> makes = FXCollections.observableArrayList();
        List<Make> makeList = DatabaseService.findAll(Make.class);
        for(Make m : makeList){
            makes.add(m.getMake());
        }
        this.makeCmbx.setItems(makes);
    }
    
    /**
     * load vehicle type
     */
    public void initVehicleType(){
        initSpinners();
        ObservableList<String> vehicleTypes = FXCollections.observableArrayList();
        List<VehicleType> typeList = DatabaseService.findAll(VehicleType.class);
        for(VehicleType vt : typeList){
            vehicleTypes.add(vt.getType());
        }
        this.vehicleTypeCmbx.setItems(vehicleTypes);
    }
    
    /**
     * initialize spinners
     */
    public void initSpinners(){
        SpinnerValueFactory yearSpnrFactory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 3000);
        this.yearSpnr.setValueFactory(yearSpnrFactory);
        SpinnerValueFactory ccSpnrFactory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 1000000);
        this.ccTxtbx.setValueFactory(ccSpnrFactory);
        SpinnerValueFactory quantitySpnrFactory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20000);
        SpinnerValueFactory reorderSpnrFactory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20000);
        this.quantitySpnr.setValueFactory(quantitySpnrFactory);
        this.reoderLevelSpnr.setValueFactory(reorderSpnrFactory);
        SpinnerValueFactory mileageSpnrFactory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 3000);
        this.mileageSpnr.setValueFactory(mileageSpnrFactory);
        SpinnerValueFactory unitCostSpnrFactory =  new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 0);
        this.unitCostSpnr.setValueFactory(unitCostSpnrFactory);
        SpinnerValueFactory sellingCostSpnrFactory =  new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 0);
        this.sellingPriceSpnr.setValueFactory(sellingCostSpnrFactory); 
    }
    
    /**
     * populate the form fields
     */
    public void setValues(Vehicle vehicle){
    
    }
    
    /**
     * copy images to application directory
     */
    public boolean copyImages(){
        Boolean copied = true;
        InputStream in = null;
        URI u = URI.create("http://java.sun.com/");
        try{
            in = u.toURL().openStream();
            Files.copy(in, new File("").toPath());
        }catch(Exception ex){
            copied = false;
        }finally{
            if(in != null){
                try{
                 in.close();
                }catch(IOException ie){
                    
                }
            }
           
        }
       return copied; 
    }
    
    /**
     * add a new customer record or update a record
     */
    @FXML
    public void submit(){
       
         //check if the user wants to add customer without docs
        if(this.files.size() <= 0){
            Alert alert = new Alert(AlertType.CONFIRMATION,"Are you sure you want to add vehicle without an image", ButtonType.YES, ButtonType.NO);
            alert.initOwner(this.addBtn.getScene().getWindow());
            Optional<ButtonType> response = alert.showAndWait(); 
            if(response.isPresent() && response.get() == ButtonType.NO){
                return;
            }   
        }
        //ends function if validate fails
        if(!this.isFormValid()){ return; }
        
        //check if images are copied to app directory
        if(!this.copyImages()) { return; }
        
        Make make = new Make();
        make.setMake(this.unitCmbx.getSelectionModel().getSelectedItem().toString());
        VehicleType vehicleType = new VehicleType();
        vehicleType.setType(this.vehicleTypeCmbx.getSelectionModel().getSelectedItem());
        this.vehicle.setMileage(this.mileageSpnr.getValue());
        this.vehicle.setModel(this.modelTxtbx.getText());
        this.vehicle.setNotes(this.noteTxtArea.getText());
        this.vehicle.setQuantity(this.quantitySpnr.getValue());
        this.vehicle.setReorderLevel(this.reoderLevelSpnr.getValue());
        this.vehicle.setRoadName(this.roadNameTxtbx.getText());
        this.vehicle.setSellingCost(this.sellingPriceSpnr.getValue());
        this.vehicle.setUnitCost(this.unitCostSpnr.getValue());
        this.vehicle.setUsageType((this.usedRdBtn.isSelected())?UsageType.Used: UsageType.New);
        this.vehicle.setVehicleYear(this.yearSpnr.getValue());
        this.vehicle.setCc(this.ccTxtbx.getValue()+" "+this.unitCmbx.getSelectionModel().getSelectedItem().toString());
        this.vehicle.setMake(make);
        this.vehicle.setVehicleType(vehicleType);
      
        if(vehicle.getId() < 0){
            if(DatabaseService.create(vehicle)){
                this.clear();
            };
        }else{
            if(DatabaseService.update(vehicle)){
                this.clear();
            };
        }
    }
    
    
    /**
     * open file dialog to choose files associated with a customer
     */
    @FXML
    public void openFileDialog() throws IOException{
             
      List<File> fileList = new ArrayList<>();
      fileList = FileService.multiFileChooser("Select Vehicle Images");
      ObservableList<String> fileNames = FXCollections.observableArrayList();
      if(fileList == null){ return; }
      ContextMenu imageContextMenu = new ContextMenu();
      MenuItem menuItem = new MenuItem("Delete");
      
      menuItem.setOnAction((e)->{
          this.deleteImage();
      });
      
      imageContextMenu.getItems().add(menuItem);
      
      
      for(File file : fileList){
            fileNames.add(file.getName());
            this.files.add(file);
            BorderPane imagePane = new BorderPane();
            
            imagePane.setPadding(new Insets(10));
            ImageView imageView = new ImageView();
            imageView.onMouseClickedProperty();
            
            imagePane.setOnMouseClicked((e)->{
                if(e.getButton() == MouseButton.SECONDARY){
                    imagePane.setStyle("-fx-border-color: #e60000; -fx-border-style: solid; -fx-border-width: 2;");
                    this.selectedImages.add((BorderPane) e.getSource()); 
                }
            });
            
            imagePane.setOnContextMenuRequested((e)->{
                imageContextMenu.show(imagePane, e.getScreenX(), e.getScreenY());
            });
            
            this.imageScrollPane.focusedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) -> {
                resetBorder();
            });
                
            imageView.setId(file.getName());
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
            imageView.setImage(new Image(file.toURI().toString()));
            imagePane.setCenter(imageView);
            
            this.hbox.getChildren().add(imagePane);
            imageView.getStyleClass().add("selected");
      }
    }
    
    /**
     * remove selection from the selected images
     */
    public void resetBorder(){
        for(Node node : this.hbox.getChildren()){
           if(node instanceof BorderPane){
               ((BorderPane)node).setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 0;");
           }
        }
        this.selectedImages.clear();
    }
    
    /**
     * remove selected file from listview
     */
    public void deleteImage(){
       this.hbox.getChildren().removeAll(this.selectedImages); 
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
        if(this.vehicleTypeCmbx.getSelectionModel().getSelectedIndex() < 0){
            counter++;
            message += counter+". A vehicle type must be selected\n";
            this.vehicleTypeCmbx.getStyleClass().add("error");;
            isValid = false;
        }
        if(this.makeCmbx.getSelectionModel().getSelectedIndex() < 0){
            counter++;
            message += counter+". A vehicle make must be selected\n";
            this.makeCmbx.getStyleClass().add("error");;
            isValid = false;
        }
        if(this.ccTxtbx.getValue() < 0){
            message += counter+". CC value cannot be less than 0\n";
            this.makeCmbx.getStyleClass().add("error");;
            isValid = false;
        }
        if(this.unitCmbx.getSelectionModel().getSelectedIndex() < 0){
            counter++;
            message += counter+". A unit must be selected\n";
            this.unitCmbx.getStyleClass().add("error");;
            isValid = false;
        }
        if(this.roadNameTxtbx.getText().length() < 4){
            counter++;
            message += counter+". Road name cannot be less than 4 characters\n";
            this.roadNameTxtbx.getStyleClass().add("error");;
            isValid = false;
        }
        if(this.modelTxtbx.getText().length() < 2){
            counter++;
            message += counter+". Model cannot be less than 2 characters\n";
            this.modelTxtbx.getStyleClass().add("error");;
            isValid = false;
        }
        
        if(this.noteTxtArea.getText().length() < 2){
            counter++;
            message += counter+". Model cannot be less than 2 characters\n";
            this.noteTxtArea.getStyleClass().add("error");;
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
        this.vehicleTypeCmbx.getStyleClass().remove("error");
        this.makeCmbx.getStyleClass().remove("error");
        this.unitCmbx.getStyleClass().remove("error");
        this.roadNameTxtbx.getStyleClass().remove("error");
        this.modelTxtbx.getStyleClass().remove("error");
        this.noteTxtArea.getStyleClass().remove("error");
        this.yearSpnr.getStyleClass().remove("error");
        this.mileageSpnr.getStyleClass().remove("error");
        this.ccTxtbx.getStyleClass().remove("error");
        this.sellingPriceSpnr.getStyleClass().remove("error");
        this.reoderLevelSpnr.getStyleClass().remove("error");
        this.quantitySpnr.getStyleClass().remove("error");
    }
    
   /**
     * clear form fields
     */
    public void clear(){
        this.vehicleTypeCmbx.getSelectionModel().clearSelection();
        this.makeCmbx.getSelectionModel().clearSelection();
        this.unitCmbx.getSelectionModel().clearSelection();
        this.roadNameTxtbx.clear();
        this.modelTxtbx.clear();
        this.noteTxtArea.clear();
        this.yearSpnr.getValueFactory().setValue(0);
        this.mileageSpnr.getValueFactory().setValue(0);
        this.ccTxtbx.getValueFactory().setValue(0);
        this.sellingPriceSpnr.getValueFactory().setValue(0.00);
        this.reoderLevelSpnr.getValueFactory().setValue(0);
        this.quantitySpnr.getValueFactory().setValue(0);
        this.newRdbtn.fire(); 
    }
}
