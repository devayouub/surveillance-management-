package login;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
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
    private final ObservableList<String> suggestions = FXCollections.observableArrayList();
    private List<String> data = new ArrayList<>();

	public AssignmentAnchorPaneManager(DatePicker assignmentDate, ComboBox<String> assignmentTime,
			ComboBox<String> assignmentCycle, ComboBox<String> assignmentDomain, FlowPane classroomFlowPane,
			TextField professorTexField, Button confirm,ListView<String> ListView,Button Confirm1) {
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
	
	


	
	
	
	
	
	
	
	
	
