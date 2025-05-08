package login;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private TextField ProfessorTexField;
    private ListView<String> professors;
    private Button Confirm;
    private Button Confirm1;
    private FlowPane classroom_flowpane;
    private ScrollPane  scrollpane_after_confirm_display_salle_exam_assignment;
    private final ObservableList<String> suggestions = FXCollections.observableArrayList();
    private List<String> data = new ArrayList<>();
    //--------------------------------test for salle -----------------------------------
    private Map<String, Integer> roomProfessorsCount = new HashMap<>();
    private Map<String, Integer> roomMinCapacity = new HashMap<>();
    private Map<String, Integer> roomMaxCapacity = new HashMap<>();
    private Map<String, List<String>> roomAssignedProfessors = new HashMap<>();
    private List<String> professorNames = new ArrayList<>();
    
	public AssignmentAnchorPaneManager(DatePicker assignmentDate, ComboBox<String> assignmentTime,
			ComboBox<String> assignmentCycle, ComboBox<String> assignmentDomain, FlowPane classroomFlowPane,
			TextField professorTexField, Button confirm,ListView<String> ListView,Button Confirm1,FlowPane classroom_flowpane
			,ScrollPane scrollpane_after_confirm_display_salle_exam_assignment) {
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
        this.classroom_flowpane=classroom_flowpane;
        this.scrollpane_after_confirm_display_salle_exam_assignment=scrollpane_after_confirm_display_salle_exam_assignment;
}	
	// Field-level variable (add this at the top of your class)
	private ObservableList<Professor> unassignedList = FXCollections.observableArrayList();

	public void initialize() {
	    professors.setItems(suggestions);
	    double rowHeight = 24;
	    professors.setFixedCellSize(rowHeight);

	    suggestions.addListener((javafx.collections.ListChangeListener<String>) change -> {
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
	                unassignedList = DatabaseManagement.getUnassignedSurveillants(
	                     AssignmentDate.getValue(), AssignmentTime.getValue(), mnemonic);
	                
	    });

	    // Search filter setup
	    ProfessorTexField.textProperty().addListener((observable, oldValue, newValue) -> {
	        if (newValue.isEmpty()) {
	            suggestions.clear();
	            professors.setVisible(false);
	        } else {
	            professors.setVisible(true);

	            ObservableList<Professor> results = filterProfessorsByName(unassignedList, newValue);
	            List<String> names = results.stream()
	                .map(p -> p.getPrFirstName() + " " + p.getPrLastName())
	                .collect(Collectors.toList());
	            suggestions.setAll(names);	
	            }
	    });
	    professors.setOnMouseClicked(event -> {
	        String selected = professors.getSelectionModel().getSelectedItem();
	        if (selected != null) {
	            ProfessorTexField.setText(selected);
	            professors.setVisible(false);
	        }
	    });
	    Confirm.setOnAction(e -> {
	        // handle final confirmation
	    });
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



	
	
	
	
	
	
	
	
	
