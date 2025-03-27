
package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class DatabaseManagement {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String DB_PASSWORD = "";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, DB_PASSWORD);
    }

    // Validate password: at least 8 characters, 1 uppercase letter, 1 number
    public static boolean isValidPassword(String user_password) {
        String password_entered = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
        return Pattern.matches(password_entered, user_password);
    }
    
    public static boolean authenticateUser(String user_name , String user_password) throws SQLException {
    	
    	String query = "SELECT * FROM users WHERE username = ? AND password = ?";
    	
    	try (Connection conn = getConnection();
    			PreparedStatement stmnt = conn.prepareStatement(query)){
    		stmnt.setString(1, user_name);
    		stmnt.setString(2, user_password);
    		
    		ResultSet rs = stmnt.executeQuery();
    		return rs.next();
    		
    	}catch (SQLException e) {
    		e.printStackTrace();
			return false;
		}
    	
    	
    }
    
    public static void adduser(String user_name ,String user_password , boolean is_admin , String user_email) throws SQLException {
    	if(!isValidPassword(user_password)){
    		System.out.println("Password must be at least 8 characters, contain 1 uppercase letter, and 1 number.");
    	}
    	
    	String query = "insert into users (username, password, is_admin, user_email) VALUES (?, ?, ?, ?)";
    	try (Connection conn = getConnection();
    			PreparedStatement stmnt = conn.prepareStatement(query)){
    		stmnt.setString(1, user_name);
    		stmnt.setString(2, user_password);
    		stmnt.setBoolean(3, is_admin);
    		stmnt.setString(4, user_email);
    		stmnt.executeUpdate();
    		System.out.println("user added succesfully");
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	    			    			
    }
    
    public static void removeuser(String user_name)throws SQLException{
    	String query = "delete from users where username = ?";
    	try(Connection conn = getConnection();
    			PreparedStatement stmnt = conn.prepareStatement(query)){
    		stmnt.setString(1, user_name);
    		int rowsaffected = stmnt.executeUpdate();
    		if(rowsaffected>0) {System.out.println("user deleted succesfully");}
    		else if(rowsaffected==0) {System.out.println("user not found");}
    		
    	}catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public static boolean check_if_is_admin(String user_name)throws SQLException{
    	String query = "select from users where username = ?";
    	try(Connection conn = getConnection();
    			PreparedStatement stmnt = conn.prepareStatement(query)){
    		stmnt.setString(1, user_name);
    		ResultSet rs = stmnt.executeQuery();
    		if(rs.next()) {
    			return rs.getBoolean("is_admin");
    		}
    		
    	}catch (SQLException e) {
			e.getStackTrace();
		}
    	return false;


    	
    }
    
    
  
    
}
