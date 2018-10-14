/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;

/**
 *
 * @author jerryco09
 */
public class ClientApp {

    /**
     * @param args the command line arguments
     */
    public MainApp m;
    public user User;
    public ClientApp(){
        User = new user();
        m = new MainApp(User);
        RegisterForm regis = new RegisterForm();
        login Login = new login(this, User);
        regis.setToggle(Login);
        Login.setToggle(regis);
        
        Login.setVisible(true);
        regis.setVisible(false);
    }
    
    public static void main(String[] args) {
        ClientApp client = new ClientApp();
    }
    
    // initialize Main Application Window with the current logged in user
    public void viewMain()
    {
        m.initUser(User);
        m.setVisible(true);
    }
}
