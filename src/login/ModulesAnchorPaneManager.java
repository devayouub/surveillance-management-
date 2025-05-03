package login;

import java.awt.Color;

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
import javafx.scene.control.cell.TextFieldTableCell;
import management.Cycle;
import management.Domain;
import management.ModuleInfo;
import management.Professor;
import management.Module;
public class ModulesAnchorPaneManager {
	ComboBox<String> cycle;
    ComboBox<String> domain;
    ComboBox<Integer> term;
    TextField module;
    Button confirm;
    Button delete;
    TableView table;
    TextField searchField;
    Label ErrorMessage;
    private ObservableList<ModuleInfo> masterData = FXCollections.observableArrayList();
       public ModulesAnchorPaneManager(ComboBox<String> cycle, ComboBox<String> domain, ComboBox<Integer> term,
			Button confirm, Button delete, TableView table,TextField searchbar,TextField module,Label ErrorMessage) {
    	   
		this.cycle = cycle;
		this.domain = domain;
		this.term = term;
		this.confirm = confirm;
		this.delete = delete;
		this.table = table;
		this.searchField=searchbar;
        this.module=module;	
        this.ErrorMessage=ErrorMessage;
        setupTable();
       }
       public void initialize() {
    	    reloadTable();
    	    setupFiltering();
    	    confirm.setOnAction(event -> getModuleInfoFromForm());
    	    delete.setOnAction(event -> deleteModule());

    	    // Fill the cycle ComboBox
    	    cycle.getItems().addAll(DatabaseManagement.getCycles());
      	    term.getItems().setAll(FXCollections.observableArrayList(1, 2));
    	    // Add listener to update domain ComboBox when cycle changes
    	    cycle.setOnAction(event -> {
    	        String selectedCycle = cycle.getValue();
    	        domain.getItems().clear();
    	        if (selectedCycle != null) {
    	            domain.getItems().addAll(DatabaseManagement.getDomaines(selectedCycle));
    	        }
    	    });
    	}
       private void setupFiltering() {
   	    FilteredList<ModuleInfo> filteredData = new FilteredList<>(masterData, p -> true);

   	    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
   	        filteredData.setPredicate(ModuleInfo -> {
   	            if (newValue == null || newValue.isEmpty()) return true;

   	            String lowerCaseFilter = newValue.toLowerCase();

   	            return ModuleInfo.getCycleName().toLowerCase().contains(lowerCaseFilter) ||
   	            		ModuleInfo.getDomainName().toLowerCase().contains(lowerCaseFilter) ||
   	            		ModuleInfo.getModuleName().toLowerCase().contains(lowerCaseFilter);
   	        });
   	    });

   	    SortedList<ModuleInfo> sortedData = new SortedList<>(filteredData);
   	    sortedData.comparatorProperty().bind(table.comparatorProperty());
   	 table.setItems(sortedData);
   	}
	
	private void setupTable() {
        @SuppressWarnings("unchecked")
		TableColumn<ModuleInfo, String> cycleColumn = (TableColumn<ModuleInfo, String>) table.getColumns().get(0);
        @SuppressWarnings("unchecked")
		TableColumn<ModuleInfo, String> domainColumn =  (TableColumn<ModuleInfo, String>)table.getColumns().get(1);
        @SuppressWarnings("unchecked")
		TableColumn<ModuleInfo, Integer> semesterColumn = (TableColumn<ModuleInfo, Integer>)table.getColumns().get(2);
        @SuppressWarnings("unchecked")
		TableColumn<ModuleInfo, String> moduleColumn =  (TableColumn<ModuleInfo, String>)table.getColumns().get(3);
        cycleColumn.setCellValueFactory(new PropertyValueFactory<>("cycleName"));
        domainColumn.setCellValueFactory(new PropertyValueFactory<>("domainName"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semesterNo"));
        moduleColumn.setCellValueFactory(new PropertyValueFactory<>("moduleName")); 
        table.setEditable(true);
        moduleColumn.setEditable(true);

        moduleColumn.setCellFactory(TextFieldTableCell.<ModuleInfo>forTableColumn());
        moduleColumn.setOnEditCommit(event -> {
            ModuleInfo moduleInfo = event.getRowValue();
            String oldKey = moduleInfo.getModuleName();
            moduleInfo.setModuleName(event.getNewValue());
         	 DatabaseManagement.updateModule(oldKey,new Module(moduleInfo.getModuleName()));
        	 reloadTable();

        });

       
    }
	public void getModuleInfoFromForm() {




if( module.getText().trim().isEmpty() || domain.getValue()== null|| term.getValue() == null) {
	ErrorMessage.setText("All fields must be filled");
	ErrorMessage.setOpacity(1);
	ErrorMessage.setTextFill(javafx.scene.paint.Color.RED);
	return;
}

String domainName = domain.getValue();
Integer semesterNo = term.getValue();
String moduleName = module.getText().trim();

Module module = new Module(moduleName);
DatabaseManagement.addModuleToDomainSemester(domainName,semesterNo, module);
ErrorMessage.setText("Module Added Successfully");
ErrorMessage.setOpacity(1);
ErrorMessage.setTextFill(javafx.scene.paint.Color.GREEN);
reloadTable();
}
	 

	public void deleteModule() {
        if (ErrorMessage == null) {
            throw new IllegalStateException("Warning label not set in UsersTableManager");
        }

        ModuleInfo selectedModule = (ModuleInfo) Controllermethods.getSelected(table);

        if (selectedModule == null) {
        	ErrorMessage.setText("No Module was selected.");
        	ErrorMessage.setStyle("-fx-text-fill: red;");
        	ErrorMessage.setOpacity(1);
            return;
        }

      

        DatabaseManagement.deleteModule(new Module(selectedModule.getModuleName()));
       reloadTable();
    }
	public void reloadTable() {
	    masterData.clear();
	    masterData.addAll(DatabaseManagement.loadModuleInfo());
	}


	    }