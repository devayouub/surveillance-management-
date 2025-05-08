package login;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import javafx.scene.text.Text;


import java.net.URL;

import java.util.*;

import javafx.util.Duration;

import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import management.ClassRoom;

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
    @FXML
    private AnchorPane anchorclassroom;
    @FXML
    private AnchorPane anchorexamsManagement;
    @FXML
    private AnchorPane anchorexamsAssignment;
    //---------------------other------------------------------------
    @FXML
    private Button managementButton;
    @FXML
    private Button assignmentButton;
    @FXML
    private Button buttonengenment;
    @FXML
    private Button buttonmodules;
    @FXML
    private Button buttonspeciality;

    @FXML
    private Button buttonclassroom;

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
    @FXML
    private FlowPane classroom_flowpane;
    @FXML
    private ScrollPane scrollpane_after_confirm_display_salle_exam_assignment;
    @FXML
    private TextField textfield_list_teacher_direct_teacher_exam_assignment;
    private List<String> professorNames = new ArrayList<>();
    
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

       //--------------------------------test for salle -----------------------------------
       private Map<String, Integer> roomProfessorsCount = new HashMap<>();
       private Map<String, Integer> roomMinCapacity = new HashMap<>();
       private Map<String, Integer> roomMaxCapacity = new HashMap<>();
       private Map<String, List<String>> roomAssignedProfessors = new HashMap<>();

       //----------------------Classroom AnchorPane---------------------
       @FXML
       private TextField ClassroomName;
       @FXML
       private ComboBox<String> ClassroomType;
       @FXML
       private Button ConfirmeClassroom;
       @FXML
       private TextField searchClassroom;
       @FXML    
       private TableView<ClassRoom> ClassroomTabel;
       @FXML
       private TableColumn<ClassRoom, String> NumberClassroomColumn;
       @FXML
       private TableColumn<ClassRoom, String> TypeClassroomColumn;
       @FXML
       private Button deleteClassroom;
       @FXML
       private Label ClassExists;
       @FXML
       private Label NoSelectedClassRoom;
        ClassRoomAnchorPaneManager classRoomAnchorPaneManager;
       //-----------------------------------Assignment------------------------------------
       @FXML
       private DatePicker AssignmentDate;
       @FXML
       private ComboBox<String> AssignmentTime;
       @FXML
       private ComboBox<String> AssignmentCycle;
       @FXML
       private ComboBox<String> AssignmentDomain;
       @FXML
       private FlowPane ClassroomFlowPane;
       @FXML
       private TextField ProfessorTexField;
       @FXML
       private ListView<String> professors;
       @FXML
       private Button Confirm;
       @FXML
       private Button Confirm1;
       
       AssignmentAnchorPaneManager assignmentAnchorPaneManager ;
       
       

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
        		   buttonmodules,buttonclassroom, anchorProfessors, anchorDomaines,
        		   anchormodules,anchorclassroom);    
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


      //---------------------------Exam Assignment Salle AnchorPane SetUp---------------------------------------
        classroom_flowpane.setStyle("-fx-background-color:#000428;");
        classroom_flowpane.setHgap(20);
        classroom_flowpane.setVgap(20);
        classroom_flowpane.setAlignment(Pos.TOP_CENTER);
        classroom_flowpane.setPadding(new Insets(20)); // Add padding around content

        scrollpane_after_confirm_display_salle_exam_assignment.setFitToWidth(true);

        List<String> roomNames = List.of("A5", "A8", "1203", "1205", "B1", "B2", "C1", "C2", "D1", "D2", "E1", "E2", "F1", "F2", "G1","2220","3030","1100","0000");

        for (String roomName : roomNames) {
            roomProfessorsCount.put(roomName, 0);
            roomMinCapacity.put(roomName, 1);
            roomMaxCapacity.put(roomName, 2);
            roomAssignedProfessors.put(roomName, new ArrayList<>());
        }

        int delay = 0;
        for (String roomName : roomNames) {
            VBox roomPane = createRoomPane(roomName);
            roomPane.setOpacity(0);
            classroom_flowpane.getChildren().add(roomPane);

            FadeTransition fade = new FadeTransition(Duration.millis(400), roomPane);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.setDelay(Duration.millis(delay));
            fade.play();

            delay += 150;

    }

      //---------------------------Department ClassRoom AnchorPane SetUp---------------------------------------   
        classRoomAnchorPaneManager = new ClassRoomAnchorPaneManager(ClassroomName,ClassroomType,
        		ConfirmeClassroom,searchClassroom,  ClassroomTabel,
        		deleteClassroom,ClassExists,NoSelectedClassRoom);
        classRoomAnchorPaneManager.initialize();
     //------------------------Assignment AnchorPane setUp--------------------------------------------
        AssignmentAnchorPaneManager assignmentAnchorPaneManager = new AssignmentAnchorPaneManager(AssignmentDate,
        		AssignmentTime, AssignmentCycle, AssignmentDomain, ClassroomFlowPane,
        		ProfessorTexField, Confirm,professors,Confirm1);
        assignmentAnchorPaneManager.initialize();

    }
    public void switchform(javafx.event.ActionEvent event) {
        Controllermethods.switchPane(event,
            Arrays.asList(buttonacc, buttondisplay, buttonuser, buttonmanegment,managementButton, assignmentButton),
            Arrays.asList(anchoraccuil, anchordisplay, anchoruser, anchorDepartmentManagment,anchorexamsManagement,anchorexamsAssignment)
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
  //-------------------------------Methode for Salle -----------------------------------//
  private VBox createRoomPane(String roomName) {
      VBox box = new VBox();
      box.setAlignment(Pos.CENTER);
      box.setPrefSize(100, 100);

      setRoomColor(box, Color.web("#ff4d4d"));

      Label label = new Label(roomName);
      label.setFont(new Font("Arial Rounded MT Bold", 16));
      label.setTextFill(Color.WHITE);
      label.setWrapText(true);
      label.setAlignment(Pos.CENTER);
      label.setMaxWidth(180);
      label.setStyle("-fx-text-alignment: center;");

      box.getChildren().add(label);

      box.setOnMouseClicked((MouseEvent event) -> {
         String professor = getNextProfessor();
          if (professor == null || professor.isEmpty()) {
              showAlert("No professor selected! Please add professor first.");
             return;
          }

          int currentCount = roomProfessorsCount.get(roomName);
          int maxCount = roomMaxCapacity.get(roomName);

          if (currentCount >= maxCount) {
              showAlert("Room " + roomName + " is already full!");
              return;
          }

          currentCount++;
          roomProfessorsCount.put(roomName, currentCount);
          roomAssignedProfessors.get(roomName).add(professor);



          if (currentCount == maxCount) {
              setRoomColor(box, Color.web("#4CAF50"));
          } else {
              setRoomColor(box, Color.web("#FFA500"));
          }

          showAlert("Professor assigned to Room " + roomName);

      });

      box.setOnMouseEntered(event -> {
          ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), box);
          scaleTransition.setToX(1.12);
          scaleTransition.setToY(1.12);
          scaleTransition.play();
      });

      box.setOnMouseExited(event -> {
          ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), box);
          scaleTransition.setToX(1);
          scaleTransition.setToY(1);
          scaleTransition.play();
      });

      return box;
  }
    private String getNextProfessor() {
        if (!professorNames.isEmpty()) {
            return professorNames.remove(0);
        }
        return null;
    }


    private void setRoomColor(VBox box, Color color) {
        box.setBackground(new Background(new BackgroundFill(color, new CornerRadii(16), Insets.EMPTY)));
        box.setStyle("-fx-border-color: white; -fx-border-width: 2; -fx-border-radius: 16; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Room Assignment");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void onConfirmProfessor() {
        String name = textfield_list_teacher_direct_teacher_exam_assignment.getText().trim();
        if (!name.isEmpty()) {
            professorNames.add(name);
            textfield_list_teacher_direct_teacher_exam_assignment.clear();
            showAlert("Professor \"" + name + "\" added to the list.");
        } else {
            showAlert("Please enter a valid professor name.");
        }
    }

}
