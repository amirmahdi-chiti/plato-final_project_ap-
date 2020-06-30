package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Sql {

    Connection conn;

    public Sql() {
        String dbURL = "jdbc:sqlserver://localhost\\SQL2016;databaseName=APFinal";
        String username = "sa";
        String password = "0902266985";

        try {

            conn = DriverManager.getConnection(dbURL, username, password);

            if (conn != null) {
                System.out.println("Connected");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
//        insert("amir","mahdi","hassan","fddf","fsgsdf");
//          System.out.println(searchLogin("amirmahd", "chiti"));
    }

    public int insert(String username, String email, String question, String answer, String pass) {
        if(!isValidEmailAddress(email))return -1; // -1 for incorrect information , 0 for duplicated information , 1 for correct information
        if(checkCreate(email, pass, username)!=1)return checkCreate(email, pass, username);
        int rowsInserted = -1;
        PreparedStatement statement = null;
        try {
            String sql = "INSERT INTO [dbo].[user2] VALUES " + "('" + username + "','" + email + "','" + question
                    + "','" + answer + "','" + pass + "')";

            statement = conn.prepareStatement(sql);

            rowsInserted = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ridiiiiiiiii");
            return -1;
        }

        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
            return 1;
        }
        return -1;
    }

    public boolean searchLogin(String username, String pass) {
        String sql = "SELECT * FROM [dbo].[user2]";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                 if(username.equals(result.getString("username"))&&pass.equals(result.getString("password"))){
                     return true;
                 }
            }
        } catch (SQLException ex) {
            System.out.println("oops");
        }
        
        return false;
    }
    private  boolean isValidEmailAddress(String email) {
       boolean result = true;
       try {
          InternetAddress emailAddr = new InternetAddress(email);
          emailAddr.validate();
       } catch (AddressException ex) {
          result = false;
       }
       return result;
    }
    private int  checkCreate(String email,String pass,String username){
        if(pass.length()<8)return -1;
        String sql = "SELECT * FROM [dbo].[user2]";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                 if(username.equals(result.getString("username"))){
                     return 0;
                 }
                 if(email.equals(result.getString("email"))){
                     return 0;
                 }
            }
        } catch (SQLException ex) {
            System.out.println("oops");
        }
        
        return 1;
    }
    public String[] recover(String email, String question, String answer){
        String sql = "SELECT * FROM [dbo].[user2]";
        String [] str = new String[2];
        str[0] = "0";
        str[1] = "0";
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                System.out.println(email.equals(result.getString("email")));
                System.out.println(question.equals(result.getNCharacterStream("selectedQuestion")));
                System.out.println(answer.equals(result.getString("answer")));
                
                 if(email.equals(result.getString("email"))&&answer.equals(result.getString("answer"))){
                     str[0]= result.getString("username");
                     str[1]= result.getString("password");
                     System.out.println("hasttttttttttttt");
                 }
            }
        } catch (SQLException ex) {
            System.out.println("oops");
        }
        
        return str;
    }
    public int  saveMessage(String text, String sender,String reciver){
         int rowsInserted = -1;
        PreparedStatement statement = null;
        try {
            String sql = "INSERT INTO private(sender,receiver,message) VALUES " + "('" + sender + "','" + reciver + "','"
                    + text + "')";

            statement = conn.prepareStatement(sql);

            rowsInserted = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }

        if (rowsInserted > 0) {
            System.out.println(" massage saved!");
            return 1;
        }
        return -1;
        
    }
    public ArrayList<String> getMessage(String sender,String receiver){
        ArrayList<String> arrayList = new ArrayList<String>();
       String sql = "SELECT * FROM private Where sender = '"+ sender + "' and receiver = '" + receiver +"'";
       
       Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                arrayList.add(result.getString("sender")+": "+result.getString("message"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return arrayList;
    }
}