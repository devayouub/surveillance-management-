package login;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import management.Cycle;
import management.Domain;
import management.DomainInfo;
import management.ModuleInfo;
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
    private DepartmentAnchorPaneManager departmentAnchorPaneManager;
    //------------------Professors Table---------------------------------
     @FXML private  TableView<Professor> professorsTable;
     
     @FXML private TableColumn<Professor, Integer> professorsIdColumn;
    
     @FXML private TableColumn<Professor, String> firstnameColumn;
     
     @FXML private TableColumn<Professor, String> lastnameColumn;
    
     @FXML private TableColumn<Professor, String> EmailColumn;
     private ProfessorsAnchorPaneManager professorsAnchorPaneManager;
    //---------------------Modules Table---------------------------
     @FXML
     private TableView<ModuleInfo> modulesTable;

     @FXML
     private TableColumn<ModuleInfo, String> cycleColumn;
     @FXML
     private TableColumn<ModuleInfo, String> domainColumn;
     @FXML
     private TableColumn<ModuleInfo, Integer> semesterColumn;
     @FXML
     private TableColumn<ModuleInfo, String> moduleColumn;
      private ModulesAnchorPaneManager modulesAnchorPaneManager;
      @FXML
      private ComboBox<String> CycleComboBox;
      @FXML
      private ComboBox<String>DomainComboBox;
      @FXML
      private ComboBox<Integer> ComboBoxTerm;
      @FXML
      private TextField ModuleName; 
      @FXML
      private Button ModuleConfirm;
      @FXML
      private Button ModuleDelete;
      @FXML
      private TextField ModuleSearchBar;
      @FXML
      private Label ErrorMessage;
    //---------------------domaines Table---------------------------
      @FXML
      private TableView<DomainInfo> domainTable;

      @FXML
      private TableColumn<DomainInfo, String> CycleColumn;
      @FXML
      private TableColumn<DomainInfo, String> Domainolumn;

       private DomainesAnchorPaneManager domainesAnchorPaneManager;
       @FXML
       private ComboBox<String> ChooseCycle;
       @FXML
       private TextField DomainName; 
       @FXML
       private Button ConfirmDomain;
       @FXML
       private Button DeleteDomaine;
       @FXML
       private TextField searchDomain;
       @FXML
       private Label DomainErrorMessage;
       @FXML
       private Label NoselectedDomain;
       
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    //---------------------------userAnchorPane SetUp---------------------------------------   
    	 userAnchorPaneManager = new UserAnchorPaneManager(UsersTable,
    			usernameField,passwordField,passwordTextField,
    			     makeAdmin,weakPasswordLabel,WarningLabel,AdduserButton,
    			     DeleteUserButton,showPassword,showPasswords);
        userAnchorPaneManager.initialize();
        //---------------------------Department AnchorPane SetUp---------------------------------------   
           departmentAnchorPaneManager = new DepartmentAnchorPaneManager(buttonengenment, buttonspeciality,
        		   buttonmodules, anchorProfessors, anchorDomaines,
        		   anchormodules);    
              departmentAnchorPaneManager.initialize();
           //---------------------------Department Professor AnchorPane SetUp---------------------------------------   
          
         professorsAnchorPaneManager = new ProfessorsAnchorPaneManager(professorsTable,searchField,
        		NoProfessorSelected,EmailFormatError,firstname,
        		lastname, email,ConfirmProfessor,
        		DeleteProfessor);
        professorsAnchorPaneManager.initialize();
          //---------------------------Department Module AnchorPane SetUp---------------------------------------   
        modulesAnchorPaneManager = new ModulesAnchorPaneManager(CycleComboBox,DomainComboBox,
        		ComboBoxTerm,ModuleConfirm,ModuleDelete,
        		modulesTable,ModuleSearchBar,ModuleName,ErrorMessage);
        modulesAnchorPaneManager.initialize();
      //---------------------------Department domain AnchorPane SetUp---------------------------------------   
        domainesAnchorPaneManager = new DomainesAnchorPaneManager(ChooseCycle,DomainName,ConfirmDomain,DeleteDomaine,
        		domainTable,searchDomain,DomainErrorMessage,NoselectedDomain);
        domainesAnchorPaneManager.initialize();
        
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
