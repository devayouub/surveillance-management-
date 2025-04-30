package login;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import management.Professor;
import management.User;

import javax.swing.text.html.ImageView;


public class dashboardController implements Initializable{
//-----------------------anchor-panes------------------------
    @FXML 
    private AnchorPane anchorpanedachboard;
    @FXML
    private AnchorPane anchoraccuil;
    @FXML
    private AnchorPane anchordisplay;
    @FXML
    private AnchorPane anchoruser;
    @FXML
    private AnchorPane anchorDepartmentManagment;
    @FXML 
    private AnchorPane anchorProfessors;
    @FXML
    private AnchorPane anchormodules;
    @FXML
    private AnchorPane anchorDomaines;

    //---------------------other------------------------------------
    @FXML
    private Button buttonengenment;
    @FXML
    private Button buttonmodules;
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
    private Button buttondisplay;
 
    @FXML
    private Button buttonmanegment;

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
    @FXML
    private Button DeleteUserButton;
    @FXML
    private Label weakPasswordLabel;
    @FXML
    private Label WarningLabel;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname ;
    @FXML
    private TextField email;
    @FXML
    private TextField  searchField;
    @FXML
    private Label EmailFormatError;
    @FXML
    private Button ConfirmProfessor;
    @FXML
    private Button DeleteProfessor;
    @FXML
    private Label NoProfessorSelected;
    
   //---------------------------Tables---------------------------
    //------------------user Table--------------------------------------
     @FXML private  TableView<User> UsersTable;
    
     @FXML private TableColumn<User, Integer> IDColumn;
    
     @FXML private TableColumn<User, String> UsernameColumn;
     
     @FXML private TableColumn<User, Boolean> IsAdminColumn;
    
     @FXML private TableColumn<User, String> PasswordColumn;
     private UserAnchorPaneManager userAnchorPaneManager;
    //------------------Professors Table---------------------------------
     @FXML private  TableView<Professor> professorsTable;
     
     @FXML private TableColumn<Professor, Integer> professorsIdColumn;
    
     @FXML private TableColumn<Professor, String> firstnameColumn;
     
     @FXML private TableColumn<Professor, String> lastnameColumn;
    
     @FXML private TableColumn<Professor, String> EmailColumn;
     private ProfessorsAnchorPaneManager professorsAnchorPaneManager;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    //---------------------------userAnchorPane SetUp---------------------------------------   
    	 userAnchorPaneManager = new UserAnchorPaneManager(UsersTable,
    			usernameField,passwordField,passwordTextField,
    			     makeAdmin,weakPasswordLabel,WarningLabel,AdduserButton,
    			     DeleteUserButton,showPassword,showPasswords);
        userAnchorPaneManager.initialize();
        //---------------------------Department Professor AnchorPane SetUp---------------------------------------   
        professorsAnchorPaneManager = new ProfessorsAnchorPaneManager(professorsTable,searchField,
        		NoProfessorSelected,EmailFormatError,firstname,
        		lastname, email,ConfirmProfessor,DeleteProfessor);
        professorsAnchorPaneManager.initialize();
        
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
