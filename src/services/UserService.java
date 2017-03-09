/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import common.Database;
import entities.User;
import java.util.List;

/**
 *
 * @author Vice Principal
 */
public class UserService {
    
    /**
     * searches for user based on their email or username
     * @param q search key
     * @return a list of users
     */
    public static List<User> search(String q){
        List<User> users;
        try{
            users = Database.getInstance().createQuery("Select o from User o where o.email like :q or o.username like :q ")
                    .setParameter("q", q+"%")
                    .getResultList();
        }catch(Exception e){
            users = null;
        }finally{
            Database.close();
        }
        return users;
    }
    
    /**
     * check if the user already exist
     * @param user 
     * @return a user object 
     */
    public static User exist(User user){
        User result;
        try{
            result = (User) Database.getInstance().createQuery("Select o from User o where o.email = :email or o.username = :username ")
                    .setParameter("email", user.getEmail())
                    .setParameter("username", user.getUsername())
                    .getSingleResult();
        }catch(Exception e){
            result = null;
        }finally{
            Database.close();
        }
        return result;
    }
}
