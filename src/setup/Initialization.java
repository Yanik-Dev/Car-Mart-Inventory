/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import common.Database;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yanik Blake
 */
public class Initialization {
    
    /**
     * setup database for application use
     * @return 
     */
    public static boolean setupDatabase(){
        boolean isSuccessful = true;
        final String sql = "CREATE DATABASE `autox_db`;";
        try {
            Database.getDatabase().setAutoCommit(false);
            Statement stmt = Database.getDatabase().createStatement();
            stmt.execute(sql);
            Database.getDatabase().commit();
        } catch (SQLException ex) {
            Logger.getLogger(Initialization.class.getName()).log(Level.SEVERE, null, ex);
            isSuccessful = false;
        }
        return isSuccessful;
    }
    
    /**
     * check if the database is already setup
     * @return 
     */
    public static boolean initDatabaseCheck(){
        boolean isSetup = false;
        
        
        return isSetup;
    }
    
     /**
     * check if the tables are already setup
     * @return 
     */
    public static boolean initTablesCheck(){
        boolean isSetup = false;
        
        
        return isSetup;
    }
    
    public static boolean setupTables(){
        boolean isSuccessful = true;
        
        
        return isSuccessful;
    }
    
   
}
