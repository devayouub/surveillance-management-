
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
    
    public static boolean authenticateUser(String user_name , String user_password) {
    	
    	String query = "SELECT * FROM users WHERE username = ? AND password = ?";
    	
    	try (Connection conn = getConnection();
    			PreparedStatement stmnt = conn.prepareStatement(query)){
    		stmnt.setString(1, user_name);
    		stmnt.setString(2, user_password);
    		
    		ResultSet rs = stmnt.executeQuery();
    		if(rs.next()) {
    			return true;
    			
    		}
    		return false;
    	}catch (SQLException e) {
    		e.printStackTrace();
			return false;
		}
    	
    	
    }
    
    public static boolean addUser(String user_name ,String user_password , boolean is_admin , String user_email) {
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
    		int result = stmnt.executeUpdate();
    		if(result >= 1 ){
    			return true;
    		}
    
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	    
    	return false;
    }
    
    public static boolean removeUser(String user_name){
    	String query = "delete from users where username = ?";
    	try(Connection conn = getConnection();
    			PreparedStatement stmnt = conn.prepareStatement(query)){
    		stmnt.setString(1, user_name);
    		int rowsaffected = stmnt.executeUpdate();
    		if(rowsaffected>0) {return true;
    		}
   
    	}catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public static boolean check_if_is_admin(String user_name){
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
    
    public static boolean RememberMeUpdater(String user_name) {
        String query = "UPDATE users SET remembered = TRUE WHERE username = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user_name);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
               return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
      
    }
    
public static boolean isUserRemembered() {
    String query = "SELECT username FROM users WHERE remembered = TRUE LIMIT 1";
    try (Connection conn = getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
            return true;  // User is remembered
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;  // No user remembered
}
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

