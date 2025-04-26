
package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import management.User;
public class DatabaseManagement {
    private static final String URL = "jdbc:mysql://localhost:3306/surveillance_data_base";
    private static final String USER = "root";
    private static final String DB_PASSWORD = "ayoub2005";
    static ObservableList<User> users = FXCollections.observableArrayList();
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, DB_PASSWORD);
    }

    // Validate password: at least 8 characters, 1 uppercase letter, 1 number
    public static boolean isValidPassword(String user_password) {
        String password_entered = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
        return Pattern.matches(password_entered, user_password);
    }
    
    public static boolean authenticateUser(String user_name , String user_password) {
    	
    	String query = "SELECT * FROM user WHERE user_name = ? AND password = ?";
    	
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
    
    public static  boolean addUser(User user) {
        // Validate password
        if (!isValidPassword(user.getPassword())) {
            return false;
        }

        // Prepare SQL insert statement (no need to include userID because it's AUTO_INCREMENT)
        String query = "INSERT INTO user (user_name, is_admin, remember_me, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmnt = conn.prepareStatement(query)) {

            // Set parameters for the insert statement
            stmnt.setString(1, user.getUsername());
            stmnt.setBoolean(2, user.getIsAdmin());
            stmnt.setBoolean(3, user.getisRemembered());
            stmnt.setString(4, user.getPassword());

         
            int result = stmnt.executeUpdate();
            if (result >= 1) {

                return true; 
            }
        } catch (SQLException e) {
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
    
    public static boolean RememberMeUpdater(String user_name,boolean call) {
        String query = "UPDATE user SET remember_me = ? WHERE user_name = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, call);
            stmt.setString(2,user_name);
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
    String query = "SELECT user_name FROM user WHERE remember_me = TRUE LIMIT 1";
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
public static String getRememberedUser() {
	String query = "SELECT user_name FROM user WHERE remember_me = TRUE LIMIT 1";
	try (Connection conn = getConnection(); 
	         PreparedStatement stmt = conn.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {
	        if (rs.next()) {
	        	 return	rs.getString(1);	              // returns remembered user

	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;  // No user remembered
	}

public static void loadUsersFromDatabase( TableView UsersTable) {
    try (Connection conn = getConnection(); // your DB connection method
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT ID_user, user_name, is_admin,remember_me,password FROM user")) {

        while (rs.next()) {
            int id = rs.getInt("ID_user");
            String username = rs.getString("user_name");
            boolean isAdmin = rs.getBoolean("is_admin");
            boolean isRemembered = rs.getBoolean("remember_me");
            String password = rs.getString("password");
            users.add(new User(id, username, isAdmin, isRemembered, password));
        }
       
        UsersTable.setItems(users);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

