/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import common.Database;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Vice Principal
 */
public class DatabaseService {
    /**
     * maps an object decorated with the @Entity annotation to a database table
     * @param obj Entity Object 
     * @return true if object is successfully mapped to database
     */
    public static <E> boolean create(E obj){
        boolean isSaved = false;
        try{
            Database.getInstance().getTransaction().begin();
            Database.getInstance().persist(obj);
            Database.getInstance().getTransaction().commit();
            isSaved = true;
        }catch(Exception ex){
            Database.getInstance().getTransaction().rollback();
            ex.printStackTrace();
                
        }finally{
            Database.getInstance().close();
        }
        return isSaved;
    }
    
   /**
     * Finds all records relating to the Entity class by its status
     * @param type Entity class     
     * @param status 0 for ACTIVE and 1 for INACTIVE
     * @return List of type Entity class
     */
    public static <E> List<E> findAll(Class<E> type, int status){
        List<E> list = null;
        
        try{
            Query query = Database.getInstance().createQuery("Select obj from "+type.getSimpleName()+" obj where status = ?");
            query.setParameter(1, status);
            list = (List<E>)query.getResultList();
        }catch(Exception ex){
            
        }finally{
            Database.getInstance().close();
        }
        
        return list;
    }
    
    /**
     * Finds all records relating to the Entity class 
     * @param type Entity class
     * @return List of type Entity class
     */
    public static <E> List<E> findAll(Class<E> type){
        List<E> userList = null;
        
        try{
             Query query = Database.getInstance().createQuery("Select obj from "+type.getSimpleName()+" obj");
         
            userList = (List<E>)query.getResultList();
            System.out.print(userList);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            Database.getInstance().close();
        }
        
        return userList;
    }
    
    /**
     * Finds an Entity record by its primary key
     * @param id primary key
     * @param type Entity class
     * @return an Entity object
     */
    public static <I, E> E findOne(I id, Class<E> type){
        E obj = null;
        try{
            obj = Database.getInstance().find(type, id);
        }catch(Exception ex){
            
        }finally{
            Database.getInstance().close();
        }
        return obj;
    }
    
   /**
     * Updates a record relating to the Entity object
     * @param obj Entity object
     * @return List of type Entity class
     */
    public static <E> boolean update(E obj){
        boolean isUpdated = false;
        try{
            Database.getInstance().getTransaction().begin();
            Database.getInstance().merge(obj);
            Database.getInstance().getTransaction().commit();
            isUpdated = true;
        }catch(Exception ex){
            
        }finally{
            Database.getInstance().close();
        }
        return isUpdated;
    }
}
