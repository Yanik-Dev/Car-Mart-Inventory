/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author Vice Principal
 */
@Entity()
public class AdditionalCharge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chargeName;
    private double cost;
    @ManyToMany(targetEntity=CustomerRental.class)
    private List<CustomerRental> customerRentals;
    @ManyToMany(targetEntity=CustomerOrder.class)
    private List<CustomerOrder> customerOrders;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the chargeName
     */
    public String getChargeName() {
        return chargeName;
    }

    /**
     * @param chargeName the chargeName to set
     */
    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    /**
     * @return the value
     */
    public double getCost() {
        return cost;
    }

    /**
     * @param value the value to set
     */
    public void setCost(double value) {
        this.cost = value;
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
  
}
