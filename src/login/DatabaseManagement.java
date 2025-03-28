
package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class DatabaseManagement {
    private static final String URL = "jdbc:mysql://localhost:3306/surveillance";
    private static final String USER = "root";
    private static final String DB_PASSWORD = "password";
 
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, DB_PASSWORD);
    }

    //  password: at least 8 characters, 1 uppercase letter, 1 number
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
    		
    		return rs.next(); // ida user exist return true
    		
    	}catch (SQLException e) {
    		e.printStackTrace();
			return false;
		}
    	
    	
    }
    
    public static boolean addUser(String adminusername ,String user_name ,String user_password , boolean is_admin , String user_email) throws SQLException {
    	if(!isValidPassword(user_password)){
    		return false;
    	}
    	if(!check_if_is_admin(adminusername)) { 
    		return false;
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
    
    public static boolean removeUser(String adminusername ,String user_name)throws SQLException{
    	if(!check_if_is_admin(adminusername)) { 
    		return false;
    	}
    	if(check_if_is_admin(user_name)) { // can't remove admin_user
    		return false;
    	}
    	
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
    public static boolean update_user(String adminusername, String which_user_name , String new_password , String new_email) throws SQLException{
    	if(!check_if_is_admin(adminusername)) { //which_user_name howa l user li rak baghi tmodifih
    		return false;
    	}
    	String query =  "UPDATE users SET password = ?, user_email = ? WHERE username = ?";
    	try(Connection conn =getConnection();
    			PreparedStatement stmnt = conn.prepareStatement(query)){
    		 stmnt.setString(1, new_password);
    		 stmnt.setString(2, new_email);
    		 stmnt.setString(3, which_user_name);
    		 int rowaffect = stmnt.executeUpdate();
    		 if (rowaffect >0) return true;
    	}catch (SQLException e) {
    		e.getStackTrace();
		}
		return false;
    }

    
    public static boolean check_if_is_admin(String user_name)throws SQLException{
    	String query = "select is_admin from users where username = ?";
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
