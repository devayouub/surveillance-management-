package management;

import javafx.beans.property.*;

public class User {
    // Static increment for ID generation
    private static SimpleIntegerProperty increment = new SimpleIntegerProperty(2);

    private SimpleIntegerProperty userID;
    private SimpleStringProperty username;
    private SimpleBooleanProperty isAdmin;
    private SimpleBooleanProperty isRemembered;
    private SimpleStringProperty password;

    // Constructor
    public User(String username, boolean isAdmin, String password) {

        this.userID = new SimpleIntegerProperty();
        this.username = new SimpleStringProperty(username);
        this.isAdmin = new SimpleBooleanProperty(isAdmin);
        this.isRemembered = new SimpleBooleanProperty(false); 
        this.password = new SimpleStringProperty(password);
    }
    public User(int Id,String username, boolean isAdmin,boolean isRemembered, String password) {
    	this.userID = new SimpleIntegerProperty(Id); 
        this.username = new SimpleStringProperty(username);
        this.isAdmin = new SimpleBooleanProperty(isAdmin);
        this.isRemembered = new SimpleBooleanProperty(isRemembered);
        this.password = new SimpleStringProperty(password);
    }

    // Getters and Setters
    public int getUserID() {
        return userID.get();
    }

    public void setUserID(int userID) {
        this.userID.set(userID); // Properly set the value for userID
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username); // Set the value for username
    }

    public boolean getIsAdmin() {
        return isAdmin.get();
    }
    public void setAdmin(boolean isAdmin) {
        this.isAdmin.set(isAdmin); // Set the value for isAdmin
    }

    public boolean getisRemembered() {
        return isRemembered.get();
    }

    public void setRemembered(boolean isRemembered) {
        this.isRemembered.set(isRemembered); // Set the value for isRemembered
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password); // Set the value for password
    }
}

