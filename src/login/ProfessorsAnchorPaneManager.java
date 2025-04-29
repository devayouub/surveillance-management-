package login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import management.Professor;
import management.User;

public class ProfessorsAnchorPaneManager {
	private TableView<Professor> professorsTable;
    private TextField searchField;
    private FilteredList<Professor> filteredData;
    private Label warningLabel;
    private Label informationError;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField EmailField;
    private Button ConfirmProfessor;
    private Button deleteProfessorButton;
    private ObservableList<Professor> masterData = FXCollections.observableArrayList();

	public ProfessorsAnchorPaneManager(TableView<Professor> professorsTable, TextField searchField,
			 Label warningLabel, Label informationError, TextField firstNameField,
			TextField lastNameField, TextField emailField, Button addProfessorButton, Button deleteProfessorButton) {
		this.professorsTable = professorsTable;
		this.searchField = searchField;
		this.warningLabel = warningLabel;
		this.informationError = informationError;
		this.firstNameField = firstNameField;
		this.lastNameField = lastNameField;
		this.EmailField = emailField;
		this.ConfirmProfessor = addProfessorButton;
		this.deleteProfessorButton = deleteProfessorButton;
		
	}
	public void initialize() {
		// 1. Load all users into professor table
		DatabaseManagement.loadProfessorsFromDatabase(professorsTable);// <--- adapt to your method
		// After loading into the table, copy into masterData
		masterData.addAll(professorsTable.getItems());
	    // 2. Create a FilteredList wrapping masterData
	    FilteredList<Professor> filteredData = new FilteredList<>(masterData, p -> true);

	    // 3. Add a listener to the searchField
	    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
	        filteredData.setPredicate(Professor -> {
	            // If search field is empty, display all users
	            if (newValue == null || newValue.isEmpty()) {
	                return true;
	            }

	            String lowerCaseFilter = newValue.toLowerCase();

	            // Compare the username column with the filter
	            if (Professor.getPrFirstName().toLowerCase().contains(lowerCaseFilter)) {
	                return true; // Matches username
	            } 
	            return false; // Does not match
	        });
	    });

	    // 4. Wrap the FilteredList in a SortedList 
	    SortedList<Professor> sortedData = new SortedList<>(filteredData);

	    // 5. Bind the SortedList comparator to TableView comparator
	    sortedData.comparatorProperty().bind(professorsTable.comparatorProperty());

	    // 6. Set the sorted and filtered data to the table
	    professorsTable.setItems(sortedData);
	    ConfirmProfessor.setOnAction(event -> addProfessor());
	    deleteProfessorButton.setOnAction(event -> deleteProfessor());
	}
	
	public void addProfessor() {
        if (lastNameField == null || firstNameField == null || lastNameField == null  || informationError == null) {
            throw new IllegalStateException("User input components not set in UsersTableManager");
        }

        String username = firstNameField.getText();
        String lastname = lastNameField.getText();
        String email = EmailField.getText();
        Professor temporaryProfessor = new Professor(username, lastname, email);

        if (temporaryProfessor.getPrFirstName().isEmpty()) {
        	informationError.setStyle("-fx-text-fill: red;");
        	informationError.setText("Must fill professor's Firstname field");
        	informationError.setOpacity(1);
            return;
        }
        if (temporaryProfessor.getPrLastName().isEmpty()) {
        	informationError.setStyle("-fx-text-fill: red;");
        	informationError.setText("Must fill professor's Lastname field");
        	informationError.setOpacity(1);
            return;
        }
        boolean validation = DatabaseManagement.isValidEmailFormat(temporaryProfessor.getPrEmail());
        if (validation == true) {
            DatabaseManagement.addProfessor(temporaryProfessor);
            reloadTable(professorsTable);
            informationError.setText("Professor added successfully");
            informationError.setStyle("-fx-text-fill: green;");
            informationError.setOpacity(1);
        } else {
            informationError.setText("Email must follow the format : Example@example.com");
            informationError.setStyle("-fx-text-fill: red;");
        	informationError.setOpacity(1);
        }
    }

	public void deleteProfessor() {
        if (warningLabel == null) {
            throw new IllegalStateException("Warning label not set in UsersTableManager");
        }

        Professor selectedPrfessor = (Professor) Controllermethods.getSelected(professorsTable);

        if (selectedPrfessor == null) {
            warningLabel.setText("No Professor selected.");
            warningLabel.setStyle("-fx-text-fill: red;");
            warningLabel.setOpacity(1);
            return;
        }

      

        DatabaseManagement.deleteProfessor(selectedPrfessor);
       reloadTable(professorsTable);
    }
    public static void reloadTable(TableView Table) {
        Table.getItems().clear();
        DatabaseManagement.loadProfessorsFromDatabase(Table);
    }
	
	
	
	

	    }

