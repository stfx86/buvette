/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vue;

/**
 *
 * @author dell
 */
public class User {
    private String name ; 
    private String password ;
    private String email  = "najiy514@gmail.com";
    
    
    public String getName(){
        return this.name ;
    }
    public void setName(String name){
        this.name= name ;
    }
    public String getPassword(){
        return this.password ;
    }
    public void setPassword(String password){
        this.password= password ;
    }
    
    public String getEmail(){
        return this.email ;
    }
    public void setEmail(String password){
        this.email= password ;
    }
    
}
