/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author Vice Principal
 */
public class FileService {
    
    /**
     * write an object to a file 
     * 
     * @param <E> object to be written to the file
     * @param fileName name of file
     * @param fileExt file extension
     * @return true if file is written successfully
     */
    public static <E> boolean writeToFile(E obj, String fileName, String fileExt){
        boolean isSuccessful = false;
        FileOutputStream foutStream = null;
        ObjectOutputStream oStream = null;
        try {    
            foutStream = new FileOutputStream(fileName+"."+fileExt);
            oStream = new ObjectOutputStream(foutStream);
            oStream.writeObject(obj);
            isSuccessful = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex){
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                if(oStream != null ) oStream.close(); 
                if(foutStream != null ) foutStream.close();
            } catch (IOException ex) {
                Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return isSuccessful;
    }
    
    /**
     * reads an object from a file
     * @param fileName file name
     * @return an Object
     */
    public static Object readFromFile(String fileName){
        
        Object obj = null;
        FileInputStream finStream = null;
        ObjectInputStream iStream = null;
        try {   
            finStream = new FileInputStream(fileName);
            iStream = new ObjectInputStream(finStream);
            obj = iStream.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{ 
            try{
                if(iStream != null) iStream.close();
                if(finStream != null) finStream.close();
            }catch(IOException ex){
                Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obj;  
    }
    
    /**
     * opens a file multiselect file dialog
     * @param title dialog title
     * @return a list of select files
     */
     public static List<File> multiFileChooser(String title){
        List<File> files = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        //Set extension filter
        files = fileChooser.showOpenMultipleDialog(null);
        return files;
    }
}
