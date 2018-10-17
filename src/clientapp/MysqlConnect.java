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
public class MysqlConnect {
    public Connection conn;
    public Statement stmt = null;
    public MysqlConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://dbmslab.tk:3306/hrer", "user", "Mmdapo09!");
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:port","username" , "password");
            //return conn;
        }catch(Exception e) {
            System.out.println(e);
            //return null;
        }
    }
    public ResultSet query(String q){
        ResultSet rs = null;
       
        try{
            stmt = conn.createStatement();
            String query = q;
            rs = stmt.executeQuery(query);
            
        }
        catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
        catch (NullPointerException x){
            System.out.println(x.toString());
        }
        return rs;
    }
    public int queryUpdate(String q){
       int r = 0;
        try{
            stmt = conn.createStatement();
            String query = q;
            r = stmt.executeUpdate(query);
            
        }
        catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception: " + ex.getMessage());
                ex = ex.getNextException();
            }
        }
        catch (NullPointerException x){
            System.out.println(x.toString());
        }
        return r;
    }
    public int getInsertID(PreparedStatement p){
        try{
            ResultSet rs = p.getGeneratedKeys();
             if (rs.next()){
                return rs.getInt(1);
            }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }
    
    public void close(){
        try{
            conn.close();
            stmt.close();
        }catch(SQLException ex){
            
        }
        
    }
    public static void main(String[] args) {
        MysqlConnect c = new MysqlConnect();
        ResultSet r = c.query("SELECT * FROM users");
        try{
            while(r.next()){
                System.out.println(r.getString(2));
            }
        }catch(SQLException ex){
            System.out.print(ex.getMessage());
        }
    }
    
}
