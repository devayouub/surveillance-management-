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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import management.User;

import javax.swing.text.html.ImageView;


public class dashboardController implements Initializable{
    @FXML
    private AnchorPane anchoreengnement;
    @FXML
    private AnchorPane anchormodules;
    @FXML
    private Button buttonengenment;
    @FXML
    private Button buttonmodules;
    @FXML
    private AnchorPane anchorspeciality;
    @FXML
    private Button buttonspeciality;


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
    private AnchorPane anchormanegment;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
       private Button AdduserButton;
    @FXML
    private CheckBox makeAdmin;
    @FXML
    private javafx.scene.control.Label weakPasswordLabel;
   
    @FXML private  TableView<User> UsersTable;
    
    @FXML private TableColumn<User, Integer> IDColumn;
    
    @FXML private TableColumn<User, String> UsernameColumn;
    
    @FXML private TableColumn<User, Boolean> IsAdminColumn;
    
    @FXML private TableColumn<User, String> PasswordColumn;
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Make the appropriate panes visible or hidden (your existing logic)
        anchoraccuil.setVisible(true);
        anchordisplay.setVisible(false);
        anchoruser.setVisible(false);
        anchormanegment.setVisible(false);

        // Set up cell value factories for the table columns
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        UsernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        IsAdminColumn.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));
        PasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        // Load users into the TableView from the database
        DatabaseManagement.loadUsersFromDatabase(UsersTable);
    }


    public void LogoutActionListener(ActionEvent event) {
    	DatabaseManagement.RememberMeUpdater(DatabaseManagement.getRememberedUser(), false);
        Controllermethods.FadeInto(anchorpanedachboard,"Login.fxml");

    }

    public void switchform(javafx.event.ActionEvent event) {
        Controllermethods.switchPane(event,
            Arrays.asList(buttonacc, buttondisplay, buttonuser, buttonmanegment),
            Arrays.asList(anchoraccuil, anchordisplay, anchoruser, anchormanegment)
        );
    }

    public void switchmenubar(javafx.event.ActionEvent event) {
        Controllermethods.switchPane(event,
            Arrays.asList(buttonengenment, buttonmodules, buttonspeciality),
            Arrays.asList(anchoreengnement, anchormodules, anchorspeciality)
        );
    }
    public void addUserActionListener(javafx.event.ActionEvent e) {
    	 String username = usernameField.getText();
         String Password = passwordField.getText();
         User temporaryUser = new User(username, makeAdmin.isSelected(), Password);
         if (DatabaseManagement.addUser(temporaryUser)) {
        	 UsersTable.getItems().clear();
             DatabaseManagement.loadUsersFromDatabase(UsersTable);
             weakPasswordLabel.setText("User added successfully");
             weakPasswordLabel.setStyle("-fx-text-fill: green;");
             weakPasswordLabel.setOpacity(1);
         } else {
             weakPasswordLabel.setOpacity(1);
         }
    }

    public void actionexit(MouseEvent mouseEvent) {
        System.exit(0);
    }

}
