package login;

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
    private Label weakPasswordLabel;
    @FXML private Button DeleteUserButton;
    @FXML private Label WarningLabel;
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
     
     @FXML private TableColumn<Professor, Boolean> lastnameColumn;
    
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
        professorsAnchorPaneManager = new ProfessorsAnchorPaneManager();
    }
    public void switchform(javafx.event.ActionEvent event) {
        Controllermethods.switchPane(event,
            Arrays.asList(buttonacc, buttondisplay, buttonuser, buttonmanegment),
            Arrays.asList(anchoraccuil, anchordisplay, anchoruser, anchorDepartmentManagment)
        );
    }
    public void LogoutActionListener(ActionEvent event) {
    	DatabaseManagement.RememberMeUpdater(DatabaseManagement.getRememberedUser(), false);
        Controllermethods.FadeInto(anchorpanedachboard,"Login.fxml");
        UsersTable.getItems().clear();
    }
    public void actionexit(MouseEvent mouseEvent) {
    	
        System.exit(0);
    }

}
