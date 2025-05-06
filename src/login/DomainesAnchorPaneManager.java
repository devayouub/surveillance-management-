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
import management.DomainInfo;
import management.ModuleInfo;
	import management.Professor;
	import management.Module;
	public class DomainesAnchorPaneManager {
		ComboBox<String> cycle;
	    TextField domain;
	    Button confirm;
	    Button delete;
	    TableView table;
	    TextField searchField;
	    Label ErrorMessage;
	    Label NoSelectedDomain;
	    private ObservableList<DomainInfo> masterData = FXCollections.observableArrayList();
	       public DomainesAnchorPaneManager(ComboBox<String> cycle, TextField domain, Button confirm, Button delete,
				TableView table, TextField searchField, Label errorMessage, Label noSelectedDomain) {
			super();
			this.cycle = cycle;
			this.domain = domain;
			this.confirm = confirm;
			this.delete = delete;
			this.table = table;
			setupTable();
			this.searchField = searchField;
			ErrorMessage = errorMessage;
			NoSelectedDomain = noSelectedDomain;
		}
		public void initialize() {
	    	    reloadTable();
	    	    setupFiltering();
	    	    confirm.setOnAction(event -> getDomainInfoFromForm());
	    	    delete.setOnAction(event -> deleteDomain());

	    	    // Fill the cycle ComboBox
	    	    cycle.getItems().addAll(DatabaseManagement.getCycles());
	    	  
	    	}
	       private void setupFiltering() {
	   	    FilteredList<DomainInfo> filteredData = new FilteredList<>(masterData, p -> true);

	   	    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
	   	        filteredData.setPredicate(DomainInfo -> {
	   	            if (newValue == null || newValue.isEmpty()) return true;

	   	            String lowerCaseFilter = newValue.toLowerCase();

	   	            return DomainInfo.getCycleName().toLowerCase().contains(lowerCaseFilter) ||
	   	            		DomainInfo.getDomainName().toLowerCase().contains(lowerCaseFilter) ;
	   	        });
	   	    });
	       
	   	    SortedList<DomainInfo> sortedData = new SortedList<>(filteredData);
	   	    sortedData.comparatorProperty().bind(table.comparatorProperty());
	   	 table.setItems(sortedData);
	   	}
		
		private void setupTable() {
	        @SuppressWarnings("unchecked")
			TableColumn<DomainInfo, String> cycleColumn = (TableColumn<DomainInfo, String>) table.getColumns().get(0);
	        @SuppressWarnings("unchecked")
			TableColumn<DomainInfo, String> domainColumn =  (TableColumn<DomainInfo, String>)table.getColumns().get(1);
	        cycleColumn.setCellValueFactory(new PropertyValueFactory<>("cycleName"));
	        domainColumn.setCellValueFactory(new PropertyValueFactory<>("domainName")); 

	       
	    }
		public void getDomainInfoFromForm() {




	if( domain.getText().trim().isEmpty() || cycle.getValue()== null) {
		ErrorMessage.setText("All fields must be filled");
		ErrorMessage.setOpacity(1);
		ErrorMessage.setTextFill(javafx.scene.paint.Color.RED);
		return;
	}

	String cycleName = cycle.getValue();
	String Domain = domain.getText().trim();
    Cycle cycle = DatabaseManagement.getCycle(cycleName);
	Domain domain = new Domain(Domain,cycle.getId());
	DatabaseManagement.addDomain(domain) ;
	ErrorMessage.setText("Domain Added Successfully");
	ErrorMessage.setOpacity(1);
	ErrorMessage.setTextFill(javafx.scene.paint.Color.GREEN);
	reloadTable();
	}
		 

		public void deleteDomain() {
	        if (NoSelectedDomain == null) {
	            throw new IllegalStateException("Warning label not set in UsersTableManager");
	        }

	        DomainInfo selectedDomain = (DomainInfo) Controllermethods.getSelected(table);

	        if (selectedDomain == null) {
	        	NoSelectedDomain.setText("No Module was selected.");
	        	NoSelectedDomain.setStyle("-fx-text-fill: red;");
	        	NoSelectedDomain.setOpacity(1);
	            return;
	        }

	         Cycle cycle = DatabaseManagement.getCycle(selectedDomain.getCycleName());

	        DatabaseManagement.deleteDomain(new Domain(selectedDomain.getId(),selectedDomain.getDomainName(),cycle.getId()));
	       reloadTable();
	    }
		public void reloadTable() {
		    masterData.clear();
		    masterData.addAll(DatabaseManagement.getAllDomaines());
		}


		    }