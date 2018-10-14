/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author jerryco09
 */
public class Utils {
     public static String md5(String s){
        String res = "";
        try{
           MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(s.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            res = sb.toString();
        }catch(Exception e){
            
        }
        
        return res;
    }
    public static void main(String args[]){
        System.out.println(md5("mmdapo"));
    }
    public static boolean isValidEmailAddress(String email) {
       String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
       java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
       java.util.regex.Matcher m = p.matcher(email);
       return m.matches();
    }
    public static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
}
