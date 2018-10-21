/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;
import java.sql.*;
/**
 *
 * @author jerryco09
 */
public class Recipient {
    public String name = null;
    public String email = null;
    public String mobile = null;
    
    public Recipient(String n, String e, String m){
        name = n;
        email = e;
        mobile = m;
    }
    public String toString(){
        return name+","+email+", "+mobile;
    }
    public boolean insertRecipient(int id, MysqlConnect con){
        try{
            String query = "INSERT INTO memo_recipients (`memo_id`, `name`, `email`, `mobile`) VALUES (?,?,?,?)";
            PreparedStatement p = con.conn.prepareStatement(query);
            p.setInt(1, id);
            p.setString(2, name);
            p.setString(3, email);
            p.setString(4, mobile);
            p.executeUpdate();
            return true;
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    public boolean insertSchedRecipient(int id, MysqlConnect con){
        try{
            String query = "INSERT INTO msched_recipients (`sched_id`, `name`, `email`, `mobile`) VALUES (?,?,?,?)";
            PreparedStatement p = con.conn.prepareStatement(query);
            p.setInt(1, id);
            p.setString(2, name);
            p.setString(3, email);
            p.setString(4, mobile);
            p.executeUpdate();
            return true;
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
