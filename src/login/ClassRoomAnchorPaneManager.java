package login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import management.ClassRoom;


public class ClassRoomAnchorPaneManager {
	 
    private TextField ClassroomName;
 
    private ComboBox<String> ClassroomType;
    
    private Button ConfirmeClassroom;
    
    private TextField searchClassroom;
           
    private TableView<ClassRoom> ClassroomTabel;
   
    private Button deleteClassroom;
    private Label ClassExists;
    private Label NoSelectedClassRoom;
    private ObservableList<ClassRoom> masterData = FXCollections.observableArrayList();

	public ClassRoomAnchorPaneManager(TextField classroomName, ComboBox<String> classroomType, Button confirmeClassroom,
			TextField searchClassroom, TableView<ClassRoom> classroomTabel, Button deleteClassroom,Label ClassExists, Label NoSelectedClassRoom) {
		super();
		ClassroomName = classroomName;
		ClassroomType = classroomType;
		ConfirmeClassroom = confirmeClassroom;
		this.searchClassroom = searchClassroom;
		ClassroomTabel = classroomTabel;
		this.deleteClassroom = deleteClassroom;
		this.ClassExists = ClassExists;
		this.NoSelectedClassRoom = NoSelectedClassRoom;
		setupTable();
				}
    
	public void initialize() {
	    reloadTable();
	    setupFiltering();
	    ClassroomType.getItems().setAll(FXCollections.observableArrayList("Amphi","Salle"));

	    ConfirmeClassroom.setOnAction(event -> addClassRoom());
	    deleteClassroom.setOnAction(event -> deleteClassRoom());	    
	}
	private void setupFiltering() {
	    FilteredList<ClassRoom> filteredData = new FilteredList<>(masterData, p -> true);

	    searchClassroom.textProperty().addListener((observable, oldValue, newValue) -> {
	        filteredData.setPredicate(classroom -> {
	            if (newValue == null || newValue.isEmpty()) return true;

	            String lowerCaseFilter = newValue.toLowerCase();

	            return classroom.getClassnumber().toLowerCase().contains(lowerCaseFilter) ||
	            		classroom.getType().toLowerCase().contains(lowerCaseFilter);
	        });
	    });

	    SortedList<ClassRoom> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(ClassroomTabel.comparatorProperty());
	    ClassroomTabel.setItems(sortedData);
	}

	private void setupTable() {
        @SuppressWarnings("unchecked")
		TableColumn<ClassRoom, Integer> ClassRoomNumber = (TableColumn<ClassRoom, Integer>) ClassroomTabel.getColumns().get(0);
        @SuppressWarnings("unchecked")
		TableColumn<ClassRoom, String> firstnameType = (TableColumn<ClassRoom, String>) ClassroomTabel.getColumns().get(1);
        ClassRoomNumber.setCellValueFactory(new PropertyValueFactory<>("classnumber"));
        firstnameType.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

	
	
	public void addClassRoom() {
        if ( ClassroomName == null || ClassroomType == null) {
            throw new IllegalStateException("classroom input components not set in UsersTableManager");
        }

        String classroomNumber = ClassroomName.getText();
        String classroomType = ClassroomType.getValue();
        if (ClassroomName.getText().isEmpty()) {
        	ClassExists.setStyle("-fx-text-fill: red;");
        	ClassExists.setText("Must fill classroom number field");
        	ClassExists.setOpacity(1);
            return;
        }
        if (ClassroomType.getValue()==null) {
        	ClassExists.setStyle("-fx-text-fill: red;");
        	ClassExists.setText("Must fill  classroom type field");
        	ClassExists.setOpacity(1);
        	return;
        }
        if (DatabaseManagement.doesSalleExist(classroomNumber)) {
        	ClassExists.setStyle("-fx-text-fill: red;");
        	ClassExists.setText("Classroom already exists");
        	ClassExists.setOpacity(1);
            return;
        }
        ClassRoom classroom;
        if(classroomNumber.equals("Amphi")) {
         classroom = new ClassRoom(classroomNumber,2,4);
         DatabaseManagement.addSalle(classroom.getClassnumber(),classroom.getMin(),classroom.getMax());
         reloadTable();
         ClassExists.setText("classroom added successfully");
         ClassExists.setStyle("-fx-text-fill: green;");
         ClassExists.setOpacity(1);
        }
        if(classroomNumber.equals("Salle")) {
          classroom = new ClassRoom(classroomNumber,1,3);
          DatabaseManagement.addSalle(classroom.getClassnumber(),classroom.getMin(),classroom.getMax());
          reloadTable();
          ClassExists.setText("classroom added successfully");
          ClassExists.setStyle("-fx-text-fill: green;");
          ClassExists.setOpacity(1);
        }
        	
        } 
 

	public void deleteClassRoom() {
        if (NoSelectedClassRoom == null) {
            throw new IllegalStateException("Warning label not set in UsersTableManager");
        }

        ClassRoom selectedClassRoom = (ClassRoom) Controllermethods.getSelected(ClassroomTabel);

        if (selectedClassRoom == null) {
        	NoSelectedClassRoom.setText("No classroom was selected.");
        	NoSelectedClassRoom.setStyle("-fx-text-fill: red;");
        	NoSelectedClassRoom.setOpacity(1);
            return;
        }

      

        DatabaseManagement.deleteSalleByName(selectedClassRoom.getClassnumber());
       reloadTable();
    }
	public void reloadTable() {
	    masterData.clear();
	    masterData.addAll(DatabaseManagement.loadAllSalles());
	}


	    }

