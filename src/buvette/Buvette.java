/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package buvette;
import DB.DB;
import Vue.*;
/**
 *
 * @author stof
 */
public class Buvette {
    
    
    
    public static void main(String[] args) {
        
        DB.testConnection();
        SignIn sinIn = new SignIn();
        sinIn.setVisible(true);
        
        
        
        
    }
    
}
