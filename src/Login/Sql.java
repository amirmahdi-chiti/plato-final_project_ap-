package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public boolean insert(String username, String email, String question, String answer, String pass) {
        if(!isValidEmailAddress(email))return false;
        if(!checkCreate(email, pass, username))return false;
        int rowsInserted = -1;
        PreparedStatement statement = null;
        try {
            String sql = "INSERT INTO [dbo].[user2] VALUES " + "('" + username + "','" + email + "','" + question
                    + "','" + answer + "','" + pass + "')";

            statement = conn.prepareStatement(sql);

            rowsInserted = statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ridiiiiiiiii");
            return false;
        }

        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
            return true;
        }
        return false;
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
    private boolean checkCreate(String email,String pass,String username){
        if(pass.length()<8)return false;
        String sql = "SELECT * FROM [dbo].[user2]";

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while (result.next()) {
                 if(username.equals(result.getString("username"))){
                     return false;
                 }
                 if(email.equals(result.getString("email"))){
                     return false;
                 }
            }
        } catch (SQLException ex) {
            System.out.println("oops");
        }
        
        return true;
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
}