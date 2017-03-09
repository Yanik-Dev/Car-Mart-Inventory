/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package common;

import java.util.HashMap;
import java.util.Map;
import classes.DatabaseConfiguration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.FileService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Vice Principal
 */
public class Database {
    private static EntityManagerFactory _entityManagerFactoryInstance = null;
    private static EntityManager _entityManagerInstance = null;
    
    /**
     * gets a single connection to database
     * @return an EntityManager object
     */
    public static EntityManager getInstance(){
        if(_entityManagerInstance == null){
            _entityManagerInstance = createEntityManager();
        }
        if(!_entityManagerInstance.isOpen()){
            _entityManagerInstance = createEntityManager();
        }
        return _entityManagerInstance;
    }
    
    /**
     * creates an EntityManager object from an EntityManagerFactory
     * @return an EntityManager object
     */
    private static EntityManager createEntityManager(){
       //TODO: _entityManagerFactoryInstance = Persistence.createEntityManagerFactory("autoxPU", getProperties());
        _entityManagerFactoryInstance = Persistence.createEntityManagerFactory("autoxPU");
        return _entityManagerFactoryInstance.createEntityManager();
    }
    
    /**
     * set Persistence Unit Properties
     */
    private static Map getProperties(){
        Map properties = new HashMap();   
        DatabaseConfiguration databaseConfig = (DatabaseConfiguration) FileService.readFromFile("db_config.conf");
        if(databaseConfig != null){
            properties.put("javax.persistence.jdbc.url", 
                           "jdbc:"+databaseConfig.getType().toLowerCase()
                           +"://"+databaseConfig.getUrl()+
                           ":"+databaseConfig.getPort()+"/"
                           +databaseConfig.getDatabaseName());
            properties.put("javax.persistence.jdbc.driver", databaseConfig.getDriver());
            properties.put("javax.persistence.jdbc.user", databaseConfig.getUser());
            properties.put("javax.persistence.jdbc.password", databaseConfig.getPassword());
        }

        return properties;
    }
    
    /**
     * close connections to database
     */
    public static void close(){
        if(_entityManagerInstance != null){
            _entityManagerInstance.close();
        }
        
        if(_entityManagerFactoryInstance != null){
            _entityManagerFactoryInstance.close();
        }
    }
    
    /**
     * return jbdc connection to database
     */
    public static Connection getDatabase(){
        String url = "";
        String username = "";
        String password = "";
        Connection connection = null;
        try{
            Class.forName("com.mysql.Driver");
            connection = DriverManager.getConnection(url, username, password);
        }catch(Exception ex){
            
        }
        return connection;
    }
    
    /**
     * initialize tables in the database
     */
    public static void initDatabaseTables(){
        try {
            getDatabase().setAutoCommit(false);
            PreparedStatement prepareStatement = getDatabase().prepareStatement("");
        } catch (SQLException ex) {
            try {
                getDatabase().rollback();
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex1) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
    }
}
