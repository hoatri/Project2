/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Valan
 */
public final class AdminSession {
    private static AdminSession instance;
    
    private String email;
    
    private Admin_query adminDao = new Admin_query();
    
    private AdminSession(){
    }
    
    public static AdminSession getInstance(){
        if(instance==null){
            instance = new AdminSession();
        } 
        return instance;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void clearAdminSession() {
        instance = null;
    }
}
