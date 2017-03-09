/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Vice Principal
 */
@Entity
public class CustomerOrder implements Serializable {

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
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * @param vehicle the vehicle to set
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String Summary; 
    private double cost;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    private double quantity;
    private double discountedAmount;
    @ManyToMany(targetEntity=AdditionalCharge.class)
    private List<AdditionalCharge> additionalCharges;
    private String purchaseDate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Email")
    private User user;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="CUSTOMER_ID", referencedColumnName="ID")
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="VEHICLE_ID")
    private Vehicle vehicle;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the Summary
     */
    public String getSummary() {
        return Summary;
    }

    /**
     * @param Summary the Summary to set
     */
    public void setSummary(String Summary) {
        this.Summary = Summary;
    }

    /**
     * @return the customer
     */
   
    /**
     * @return the unitCost
     */
    public double getUnitCost() {
        return cost;
    }

    /**
     * @param unitCost the unitCost to set
     */
    public void setUnitCost(double unitCost) {
        this.cost = unitCost;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the discountedAmount
     */
    public double getDiscountedAmount() {
        return discountedAmount;
    }

    /**
     * @param discountedAmount the discountedAmount to set
     */
    public void setDiscountedAmount(double discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    /**
     * @return the additionalCharge
     */
    public List<AdditionalCharge> getAdditionalCharge() {
        return additionalCharges;
    }

    /**
     * @param additionalCharge the additionalCharge to set
     */
    public void setAdditionalCharge(List<AdditionalCharge> additionalCharge) {
        this.additionalCharges = additionalCharge;
    }

    /**
     * @return the quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the purchaseDate
     */
    public String getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * @param purchaseDate the purchaseDate to set
     */
    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }


}
