package login;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import management.Cycle;
import management.Exam;
import management.Professor;

public class AssignmentAnchorPaneManager {
    private DatePicker AssignmentDate;
    
    private ComboBox<String> AssignmentTime;
    
    private ComboBox<String> AssignmentCycle;
    
    private ComboBox<String> AssignmentDomain;
    
    private FlowPane ClassroomFlowPane;
    
    private ScrollPane scrollpane;
    
    private TextField ProfessorTexField;
    
    private ListView<Professor> professors;
    
    private Button Confirm;
    
    private Button Confirm1;
    Exam TemporaryExam ;
    
    ObservableList<Professor> suggestions = FXCollections.observableArrayList();
    
    private List<String> data = new ArrayList<>();

	private ObservableList<Professor> unassignedList = FXCollections.observableArrayList();

    //--------------------------------test for salle -----------------------------------
    private Map<String, Integer> roomProfessorsCount = new HashMap<>();
    private Map<String, Integer> roomMinCapacity = new HashMap<>();
    private Map<String, Integer> roomMaxCapacity = new HashMap<>();
    private Map<String, List<String>> roomAssignedProfessors = new HashMap<>();
    private Professor selectedProfessor;
    
	public AssignmentAnchorPaneManager(DatePicker assignmentDate, ComboBox<String> assignmentTime,
			ComboBox<String> assignmentCycle, ComboBox<String> assignmentDomain, FlowPane classroomFlowPane,
			TextField professorTexField, Button confirm,ListView<Professor> ListView,Button Confirm1
			,ScrollPane scrollpane) {
		super();
		AssignmentDate = assignmentDate;
		AssignmentTime = assignmentTime;
		AssignmentCycle = assignmentCycle;
		AssignmentDomain = assignmentDomain;
		ClassroomFlowPane = classroomFlowPane;
		ProfessorTexField = professorTexField;
		Confirm = confirm;
		professors = ListView;
        this.Confirm1 = Confirm1;
        this.scrollpane =scrollpane;
}	

	public void initialize() {
		professors.setCellFactory(param -> new ListCell<>() {
		    @Override
		    protected void updateItem(Professor prof, boolean empty) {
		        super.updateItem(prof, empty);
		        if (empty || prof == null) {
		            setText(null);
		        } else {
		            setText(prof.getPrFirstName() + " " + prof.getPrLastName());
		        }
		    }
		});
	    professors.setItems(suggestions);
	    double rowHeight = 24;
	    professors.setFixedCellSize(rowHeight);
	    suggestions.addListener((javafx.collections.ListChangeListener<Professor>) change -> {
	        int size = suggestions.size();
	        int maxVisibleRows = 5;
	        int rowsToShow = Math.min(size, maxVisibleRows);
	        professors.setPrefHeight(rowsToShow * rowHeight + 2);
	    });

	    AssignmentTime.getItems().setAll(FXCollections.observableArrayList(
	        "08:00-09:30", "10:00-11:30", "12:00-13:30", "14:00-15:30"
	    ));

	    AssignmentCycle.getItems().addAll(DatabaseManagement.getCycles());
	    AssignmentCycle.setOnAction(event -> {
	        String selectedCycle = AssignmentCycle.getValue();
	        AssignmentDomain.getItems().clear();
	        if (selectedCycle != null) {
	            AssignmentDomain.getItems().addAll(DatabaseManagement.getDomaines(selectedCycle));
	        }
	    });

	    Confirm1.setOnAction(e -> {
	        ClassroomFlowPane.setVisible(true);

	        Cycle cycle = DatabaseManagement.getCycle(AssignmentCycle.getValue());
	        int domainID = DatabaseManagement.getSpecialiteIdByCycleAndName(
	            cycle.getId(), AssignmentDomain.getValue());

	        int semester = DatabaseManagement.getSemesterByExamDateTimeAndDomain(
	            AssignmentDate.getValue(), AssignmentTime.getValue(), domainID);
	                management.Module module = new management.Module(
	            DatabaseManagement.getModuleIdsByDomainAndDateTime(AssignmentDomain.getValue(),semester,AssignmentDate.getValue(),AssignmentTime.getValue()));
	              String mnemonic = module.getUniqueName();
	               TemporaryExam = DatabaseManagement.getExamByDetails(AssignmentDate.getValue(), AssignmentTime.getValue(), mnemonic);
	                unassignedList = DatabaseManagement.getUnassignedSurveillants2(
	                     TemporaryExam.getId());
	                List<String> SallesList = new ArrayList<>(DatabaseManagement.getSallesWithStatusByExamId(TemporaryExam.getId()));

	                SettingUpSalles(SallesList);

	                // Load min/max capacity for each room
	                Map<String, Integer[]> capacities = DatabaseManagement.getRoomCapacities(TemporaryExam.getId());
	                for (Map.Entry<String, Integer[]> entry : capacities.entrySet()) {
	                    String room = entry.getKey();
	                    int min = entry.getValue()[0];
	                    int max = entry.getValue()[1];
	                    roomMinCapacity.put(room, min);
	                    roomMaxCapacity.put(room, max);
	                    roomProfessorsCount.putIfAbsent(room, 0);  // Initialize count if missing
	                    roomAssignedProfessors.putIfAbsent(room, new ArrayList<>());
	                }
	    });

	    // Search filter setup
	    ProfessorTexField.textProperty().addListener((observable, oldValue, newValue) -> {
	        if (newValue.isEmpty()) {
	            suggestions.clear();
	            professors.setVisible(false);
	        } else {
	            professors.setVisible(true);

	            ObservableList<Professor> results = filterProfessorsByName(unassignedList, newValue);
	            suggestions.setAll(results);	
	            }
	    });
	    professors.setOnMouseClicked(event -> {
	    	Professor selected = professors.getSelectionModel().getSelectedItem();
	    	if (selected != null) {
	    	   String SelectedProfessor1 = selected.getPrFirstName() + " " + selected.getPrLastName(); // Optional for display
	    	    ProfessorTexField.setText(SelectedProfessor1);
	    	    professors.setVisible(false);
	    	    selectedProfessor = selected;
	    	}
	   
	    });
	    Confirm.setOnAction(e -> {
	        if (!ProfessorTexField.getText().isEmpty()) {
	            String typedName = ProfessorTexField.getText().toLowerCase();
	            for (Professor prof : unassignedList) {
	                String fullName = (prof.getPrFirstName() + " " + prof.getPrLastName()).toLowerCase();
	                if (fullName.equals(typedName)) {
	                    selectedProfessor = prof;
	                    break;
	                }
	            }
	        }
	    });

	    ClassroomFlowPane.setStyle("-fx-background-color:#000428;");
	    ClassroomFlowPane.setHgap(20);
	    ClassroomFlowPane.setVgap(20);
	    ClassroomFlowPane.setAlignment(Pos.TOP_CENTER);
	    ClassroomFlowPane.setPadding(new Insets(20)); // Add padding around content

	    scrollpane.setFitToWidth(true);
    }
	

	public static ObservableList<Professor> filterProfessorsByName(ObservableList<Professor> inputList, String searchString) {
	    ObservableList<Professor> filteredList = FXCollections.observableArrayList();

	    String lowerSearch = searchString.toLowerCase();

	    for (Professor prof : inputList) {
	        String fullName = (prof.getPrFirstName() + " " + prof.getPrLastName()).toLowerCase();
	        if (prof.getPrLastName().toLowerCase().contains(lowerSearch) ||
	        		prof.getPrFirstName().toLowerCase().contains(lowerSearch) ||
	            fullName.contains(lowerSearch)) {
	            filteredList.add(prof);
	        }
	    }

	    return filteredList;
	}

	public void SettingUpSalles(List<String> rawStatusList) {
	    List<String> roomNames = new ArrayList<>();

	    for (String entry : rawStatusList) {
	            roomNames.add(entry);
	        }
	    

	    int delay = 0;
	    for (String roomName : roomNames) {
	    	 String[] parts = roomName.split("-");
	    	 if (parts.length == 2) {
	        VBox roomPane = createRoomPane(parts[1],parts[0]);
	        roomPane.setOpacity(0);
	        ClassroomFlowPane.getChildren().add(roomPane);
	    	 
	        FadeTransition fade = new FadeTransition(Duration.millis(400), roomPane);
	        fade.setFromValue(0);
	        fade.setToValue(1);
	        fade.setDelay(Duration.millis(delay));
	        fade.play();

	        delay += 150;
	    }
	}
	}
//-------------------------------Methode for Salle -----------------------------------//
	private VBox createRoomPane(String roomName,String color) {
	    VBox box = new VBox();
	    box.setAlignment(Pos.CENTER);
	    box.setSpacing(8);
	    box.setPrefSize(120, 120);
	    box.setPadding(new Insets(10));
	    box.setStyle("-fx-background-radius: 10; -fx-cursor: hand;");

	    // Initial color (red = under min capacity)
	    setRoomColor(box, Color.web(color));

	    Label label = new Label(roomName);
	    label.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
	    label.setTextFill(Color.WHITE);
	    label.setWrapText(true);
	    label.setAlignment(Pos.CENTER);
	    label.setMaxWidth(100);
	    label.setStyle("-fx-text-alignment: center;");

	    box.getChildren().add(label);

	    // Mouse click assigns professor
	    box.setOnMouseClicked(event -> {
	        try {
	            if (selectedProfessor == null) {
	                showAlert("No professor selected! Please select a professor before assigning.");
	                return;
	            }

	            String professorName = selectedProfessor.getPrLastName();
	            int currentCount = roomProfessorsCount.getOrDefault(roomName, 0);
	            int maxCount = roomMaxCapacity.getOrDefault(roomName, 2);
	            int minCount = roomMinCapacity.getOrDefault(roomName, 1);

	            // Initialize the list if it's null
	            roomAssignedProfessors.putIfAbsent(roomName, new ArrayList<>());
	            List<String> assigned = roomAssignedProfessors.get(roomName);

	            if (assigned.contains(professorName)) {
	                showAlert("Professor " + professorName + " is already assigned to room " + roomName + "!");
	                return;
	            }

	            if (currentCount >= maxCount) {
	                showAlert("Room " + roomName + " is already full!");
	                return;
	            }

	            // Assign professor
	            currentCount++;
	            roomProfessorsCount.put(roomName, currentCount);
	            assigned.add(professorName);
	            DatabaseManagement.insertSurveillance(TemporaryExam.getId(), selectedProfessor.getProfId(), roomName);
                DatabaseManagement.markProfessorPresent(selectedProfessor.getProfId(), TemporaryExam.getId());
	            // Update color based on new count
	            if (currentCount < minCount) {
	                setRoomColor(box, Color.web("#ff4d4d")); // Red
	            } else if (currentCount < maxCount) {
	                setRoomColor(box, Color.web("#FFA500")); // Yellow
	            } else {
	                setRoomColor(box, Color.web("#4CAF50")); // Green
	            }

	            showAlert("Professor assigned to Room " + roomName);
	        } catch (Exception e) {
	            showAlert("Error while assigning professor: " + e.getMessage());
	        }
	    });

	    // Hover effect
	    box.setOnMouseEntered(event -> {
	        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), box);
	        scaleTransition.setToX(1.1);
	        scaleTransition.setToY(1.1);
	        scaleTransition.play();
	    });

	    box.setOnMouseExited(event -> {
	        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), box);
	        scaleTransition.setToX(1.0);
	        scaleTransition.setToY(1.0);
	        scaleTransition.play();
	    });

	    return box;
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
}


	
	
	
	
	
	
	
	
	
