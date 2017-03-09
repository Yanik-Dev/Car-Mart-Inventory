/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import common.Database;
import entities.Customer;
import java.util.List;

/**
 *
 * @author Vice Principal
 */
public class CustomerService {
    
    public static Customer exist(Customer obj){
        Customer customer;
        try{
            customer = (Customer) Database.getInstance().createQuery("Select obj from Customer obj where obj.TRN = :trn or (obj.email != '' && obj.email = :email)")
                                  .setParameter("trn", obj.getTRN())
                                  .setParameter("email", obj.getEmail())
                                  .getSingleResult();
        }catch(Exception ex){
            customer = null;
        }finally{
           Database.getInstance().close();
        }
        return customer;
    }
    
    public static List<Customer> search(Customer obj){
        List<Customer> customers;
        try{
            customers = Database.getInstance().createQuery("Select obj from Customer obj where obj.TRN like :trn or obj.email like :email or "
                    + "                                     concat(obj.firstname, '', obj.lastname) like :name or obj.type like :type")
                                  .setParameter("trn", obj.getTRN()+"%")
                                  .setParameter("email", obj.getEmail()+"%")
                                  .setParameter("name", obj.getFirstname()+"%")
                                  .setParameter("type", obj.getType())
                                  .getResultList();
            System.out.print(customers);
        }catch(Exception ex){
            ex.printStackTrace();
            customers = null;
        }finally{
           Database.getInstance().close();
        }
        return customers;
    }
}
