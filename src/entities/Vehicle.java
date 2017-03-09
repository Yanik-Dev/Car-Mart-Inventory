/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import constant.UsageType;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Vice Principal
 */
@Entity
public class Vehicle implements Serializable {

    /**
     * @return the customerOrders
     */
    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    /**
     * @param customerOrders the customerOrders to set
     */
    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    private VehicleType vehicleType;
    private UsageType usageType;
    private String roadName;
    private String cc;
    @Column(columnDefinition="TEXT")
    private String notes;
    private int vehicleYear;
    private String model;
    private int quantity;
    private int reorderLevel;
    private double mileage;
    private double unitCost; 
    private double sellingCost;
    @ManyToOne()
    private User user;
    
    @OneToMany(mappedBy="vehicle")
    private List<VehicleImage> vehicleImages;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="MAKE_ID")
    private Make make;
        
    @OneToMany(mappedBy="vehicle")
    private List<CustomerOrder> customerOrders;
    
    @OneToMany(mappedBy="vehicle")
    private List<CustomerRental> customerRentals;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the make
     */
    public Make getMake() {
        return make;
    }

    /**
     * @param make the make to set
     */
    public void setMake(Make make) {
        this.make = make;
    }

    /**
     * @return the vehicleType
     */
    public VehicleType getVehicleType() {
        return vehicleType;
    }

    /**
     * @param vehicleType the vehicleType to set
     */
    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * @return the usageType
     */
    public UsageType getUsageType() {
        return usageType;
    }

    /**
     * @param usageType the usageType to set
     */
    public void setUsageType(UsageType usageType) {
        this.usageType = usageType;
    }

    /**
     * @return the roadName
     */
    public String getRoadName() {
        return roadName;
    }

    /**
     * @param roadName the roadName to set
     */
    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    /**
     * @return the vehicleYear
     */
    public int getVehicleYear() {
        return vehicleYear;
    }

    /**
     * @param vehicleYear the vehicleYear to set
     */
    public void setVehicleYear(int vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the reorderLevel
     */
    public int getReorderLevel() {
        return reorderLevel;
    }

    /**
     * @param reorderLevel the reorderLevel to set
     */
    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    /**
     * @return the mileage
     */
    public double getMileage() {
        return mileage;
    }

    /**
     * @param mileage the mileage to set
     */
    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    /**
     * @return the unitCost
     */
    public double getUnitCost() {
        return unitCost;
    }

    /**
     * @param unitCost the unitCost to set
     */
    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the sellingCost
     */
    public double getSellingCost() {
        return sellingCost;
    }

    /**
     * @param sellingCost the sellingCost to set
     */
    public void setSellingCost(double sellingCost) {
        this.sellingCost = sellingCost;
    }

    /**
     * @return the cc
     */
    public String getCc() {
        return cc;
    }

    /**
     * @param cc the cc to set
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the customerRentals
     */
    public List<CustomerRental> getCustomerRentals() {
        return customerRentals;
    }

    /**
     * @param customerRentals the customerRentals to set
     */
    public void setCustomerRentals(List<CustomerRental> customerRentals) {
        this.customerRentals = customerRentals;
    }

    /**
     * @return the vehicleImages
     */
    public List<VehicleImage> getVehicleImages() {
        return vehicleImages;
    }

    /**
     * @param vehicleImages the vehicleImages to set
     */
    public void setVehicleImages(List<VehicleImage> vehicleImages) {
        this.vehicleImages = vehicleImages;
    }
    
}
