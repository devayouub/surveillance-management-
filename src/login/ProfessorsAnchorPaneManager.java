package login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import management.Professor;

public class ProfessorsAnchorPaneManager {
	private TableView<Professor> professorsTable;
    private TextField searchField;
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
		setupTable();
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
	    reloadTable();
	    setupFiltering();
	    ConfirmProfessor.setOnAction(event -> addProfessor());
	    deleteProfessorButton.setOnAction(event -> deleteProfessor());	    
	}
	private void setupFiltering() {
	    FilteredList<Professor> filteredData = new FilteredList<>(masterData, p -> true);

	    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
	        filteredData.setPredicate(professor -> {
	            if (newValue == null || newValue.isEmpty()) return true;

	            String lowerCaseFilter = newValue.toLowerCase();

	            return professor.getPrFirstName().toLowerCase().contains(lowerCaseFilter) ||
	                   professor.getPrLastName().toLowerCase().contains(lowerCaseFilter) ||
	                   professor.getPrEmail().toLowerCase().contains(lowerCaseFilter);
	        });
	    });

	    SortedList<Professor> sortedData = new SortedList<>(filteredData);
	    sortedData.comparatorProperty().bind(professorsTable.comparatorProperty());
	    professorsTable.setItems(sortedData);
	}

	private void setupTable() {
        @SuppressWarnings("unchecked")
		TableColumn<Professor, Integer> professorsIdColumn = (TableColumn<Professor, Integer>) professorsTable.getColumns().get(0);
        @SuppressWarnings("unchecked")
		TableColumn<Professor, String> firstnameColumn = (TableColumn<Professor, String>) professorsTable.getColumns().get(1);
        @SuppressWarnings("unchecked")
		TableColumn<Professor, String> lastnameColumn = (TableColumn<Professor, String>) professorsTable.getColumns().get(2);
        @SuppressWarnings("unchecked")
		TableColumn<Professor, String> emailColumn = (TableColumn<Professor, String>) professorsTable.getColumns().get(3);
        professorsIdColumn.setCellValueFactory(new PropertyValueFactory<>("ProfId"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("PrFirstName"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("PrLastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("PrEmail"));
        firstnameColumn.setEditable(true);
        lastnameColumn.setEditable(true);
        emailColumn.setEditable(true);

        firstnameColumn.setCellFactory(TextFieldTableCell.<Professor>forTableColumn());
        firstnameColumn.setOnEditCommit(event -> {
            Professor professor = event.getRowValue();
             professor.setPrFirstName(event.getNewValue());
         	 DatabaseManagement.updateProfessor(professor);
        	 reloadTable();

        });

        lastnameColumn.setCellFactory(TextFieldTableCell.<Professor>forTableColumn());
        lastnameColumn.setOnEditCommit(event -> {
        	Professor  professor = event.getRowValue();
        	 professor.setPrLastName(event.getNewValue());
        	 DatabaseManagement.updateProfessor(professor);
        	 reloadTable();
        });

        emailColumn.setCellFactory(TextFieldTableCell.<Professor>forTableColumn());
        emailColumn.setOnEditCommit(event -> {
        	Professor professor = event.getRowValue();
            professor.setPrEmail(event.getNewValue());
        	 DatabaseManagement.updateProfessor(professor);
        	 reloadTable();
        
        });
    }

	
	
	public void addProfessor() {
        if ( firstNameField == null || lastNameField == null  || EmailField == null|| informationError == null) {
            throw new IllegalStateException("User input components not set in UsersTableManager");
        }

        String username = firstNameField.getText();
        String lastname = lastNameField.getText();
        String email = EmailField.getText();
        Professor temporaryProfessor = new Professor(username, lastname, email);

        if (firstNameField.getText().isEmpty()) {
        	informationError.setStyle("-fx-text-fill: red;");
        	informationError.setText("Must fill professor's Firstname field");
        	informationError.setOpacity(1);
        	System.err.println("Must fill professor's Firstname field");
            return;
        }
        if (lastNameField.getText().isEmpty()) {
        	informationError.setStyle("-fx-text-fill: red;");
        	informationError.setText("Must fill professor's Lastname field");
        	informationError.setOpacity(1);
        	System.err.println("Must fill professor's Lastname field");

            return;
        }
        boolean validation = DatabaseManagement.isValidEmailFormat(temporaryProfessor.getPrEmail());
        if (validation == true) {
        	DatabaseManagement.addProfessor(temporaryProfessor);
            reloadTable();
            informationError.setText("Professor added successfully");
            informationError.setStyle("-fx-text-fill: green;");
            informationError.setOpacity(1);
            System.err.println("done");
        } else {
            informationError.setText("Email must follow the format : Example@example.com");
            informationError.setStyle("-fx-text-fill: red;");
        	informationError.setOpacity(1);
       System.err.println("wrong format");
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
       reloadTable();
    }
	public void reloadTable() {
	    masterData.clear();
	    masterData.addAll(DatabaseManagement.loadProfessorsFromDatabase());
	}


	    }

