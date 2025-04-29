package login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import management.User;

import javax.swing.text.html.ImageView;


public class dashboardController implements Initializable{
    @FXML
    private AnchorPane anchorProfessors;
    @FXML
    private AnchorPane anchormodules;
    @FXML
    private Button buttonengenment;
    @FXML
    private Button buttonmodules;
    @FXML
    private AnchorPane anchorDomaines;
    @FXML
    private Button buttonspeciality;
    @FXML
    private Button buttonclassroom;
    @FXML
    private AnchorPane anchorclassroom;



    @FXML
    private AnchorPane anchorpanedachboard;
    @FXML
    private Button buttonacc;
    @FXML
    private ImageView imageview;
    @FXML
    private Button buttonuser;
    @FXML
    private AnchorPane anchoruser;
    @FXML
    private Button buttondisplay;
    @FXML
    private AnchorPane anchoraccuil;

    @FXML
    private AnchorPane anchordisplay;
    @FXML
    private Button buttonmanegment;
    @FXML
    private AnchorPane anchorDepartmentManagment;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField passwordTextField;
    @FXML
       private Button AdduserButton;
    @FXML
    private CheckBox makeAdmin;
    @FXML
    private CheckBox showPassword;
    @FXML
    private CheckBox showPasswords;
    private boolean showpasswords = false;
    @FXML
    private javafx.scene.control.Label weakPasswordLabel;
   
    @FXML private  TableView<User> UsersTable;
    
    @FXML private TableColumn<User, Integer> IDColumn;
    
    @FXML private TableColumn<User, String> UsernameColumn;
    
    @FXML private TableColumn<User, Boolean> IsAdminColumn;
    
    @FXML private TableColumn<User, String> PasswordColumn;
    @FXML private Button DeleteUserButton;
    @FXML private Label WarningLabel;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Make the appropriate panes visible or hidden (your existing logic)
        anchoraccuil.setVisible(true);
        anchordisplay.setVisible(false);
        anchoruser.setVisible(false);
        anchorProfessors.setVisible(false);
       passwordTextField.setVisible(false);
       passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

        // Set up cell value factories for the table columns
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        UsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        IsAdminColumn.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        // Custom cell factory for password column
        PasswordColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String password, boolean empty) {
                super.updateItem(password, empty);
                if (empty || password == null) {
                    setText(null);
                } else {
                    if (showpasswords) {
                        setText(password);
                    } else {
                        setText(maskPassword(password));
                    }
                }
            }
        });
        PasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        showPasswords.setOnAction(e -> {
            showpasswords = !showpasswords;
            UsersTable.refresh();
            });
        passwordField.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
            if (newFocus) {
            	weakPasswordLabel.setText("Password must contain at least 8 characters, an uppercase letter and a number");
            	weakPasswordLabel.setStyle("-fx-text-fill: yellow;");
            	weakPasswordLabel.setOpacity(1);
            	
            }
            if(oldFocus) {
            	weakPasswordLabel.setOpacity(0);

            }
            
        });
        passwordTextField.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
            if (newFocus) {
            	weakPasswordLabel.setText("Password must contain at least 8 characters, an uppercase letter and a number");
            	weakPasswordLabel.setStyle("-fx-text-fill: yellow;");
            	weakPasswordLabel.setOpacity(1);
            	
            }
            if(oldFocus) {
            	weakPasswordLabel.setOpacity(0);

            }
            
        });
        DatabaseManagement.loadUsersFromDatabase(UsersTable);
    }
    private String maskPassword(String password) {
        return "•".repeat(password.length());
    }

    public void LogoutActionListener(ActionEvent event) {
    	DatabaseManagement.RememberMeUpdater(DatabaseManagement.getRememberedUser(), false);
        Controllermethods.FadeInto(anchorpanedachboard,"Login.fxml");
        UsersTable.getItems().clear();
    }

    public void switchform(javafx.event.ActionEvent event) {
        Controllermethods.switchPane(event,
            Arrays.asList(buttonacc, buttondisplay, buttonuser, buttonmanegment),
            Arrays.asList(anchoraccuil, anchordisplay, anchoruser, anchorDepartmentManagment)
        );
    }

    public void switchmenubar(javafx.event.ActionEvent event) {
        Controllermethods.switchPane(event,
            Arrays.asList(buttonengenment, buttonspeciality, buttonmodules,buttonclassroom),
            Arrays.asList(anchorProfessors, anchorDomaines, anchormodules,anchorclassroom)
        );
    }
    public void showPasswordActionListener(javafx.event.ActionEvent e) {
    	passwordField.setVisible(!passwordField.isVisible());
    	passwordTextField.setVisible(!passwordTextField.isVisible());
    	
    }
    public void addUserActionListener(javafx.event.ActionEvent e) {
    	 String username = usernameField.getText();
         String Password = passwordField.getText();
         User temporaryUser = new User(username, makeAdmin.isSelected(), Password);
       
          if (temporaryUser.getUsername().equals("")){
            weakPasswordLabel.setStyle("-fx-text-fill: red;");
            weakPasswordLabel.setText("must fill username field");   
            weakPasswordLabel.setOpacity(1);
            return;
         } 
          
          if(DatabaseManagement.isValidPassword(temporaryUser.getPassword()).equals("Valid")) {
        	  DatabaseManagement.addUser(temporaryUser);
              UsersTable.getItems().clear();
              DatabaseManagement.loadUsersFromDatabase(UsersTable);
              weakPasswordLabel.setText("User added successfully");
              weakPasswordLabel.setStyle("-fx-text-fill: green;");
              weakPasswordLabel.setOpacity(1);
          }
          else {
             weakPasswordLabel.setText(DatabaseManagement.isValidPassword(temporaryUser.getPassword()));
             weakPasswordLabel.setStyle("-fx-text-fill: red;");
             weakPasswordLabel.setOpacity(1);
         }
    }

    public void deleteUserActionListener(javafx.event.ActionEvent e) {
    	User selectedUser = UsersTable.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
        	WarningLabel.setText("No user selected.");
        	WarningLabel.setStyle("-fx-text-fill: red;");
        	WarningLabel.setOpacity(1);
            return;
        }

        if (UsersTable.getItems().size() == 1) {
        	WarningLabel.setText("You cannot delete the last user.");
        	WarningLabel.setStyle("-fx-text-fill: red;");
        	WarningLabel.setOpacity(1);
            return;
        }
    	DatabaseManagement.removeUser(selectedUser);
    	UsersTable.getItems().clear();
        DatabaseManagement.loadUsersFromDatabase(UsersTable);
        
    	
    }
    

    public void actionexit(MouseEvent mouseEvent) {
    	
        System.exit(0);
    }

}
