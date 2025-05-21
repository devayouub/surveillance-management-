package login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.DataFormat;
import management.Domain;
import management.Exam;
import management.Module;
import management.Professor;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static login.DatabaseManagement.professors;


public class ManualMethodeAnchorpaneManagment {
    private DatePicker dataexamspicker;
    private ComboBox<String> timeComboBox;
    private ComboBox<String> cycleComboBox;
    private ComboBox<Domain> domainComboBox;
    private ComboBox<Integer> SemestreComboBox;
    private ComboBox<String> ModuleComboBox;
    private javafx.scene.control.TextField professorTextField;
    private javafx.scene.control.Button addteacherButton;
    private javafx.scene.control.TextField classroom;
    private javafx.scene.control.Button addclassroomButton;
    private Button displayexamsButton;
    private Button confirme ;
    private ListView ProfessorListView ;
    private ListView classroomListView ;
    private final ObservableList<String> suggestions = FXCollections.observableArrayList();
    private final ObservableList<String> suggestions2 = FXCollections.observableArrayList();
    public ManualMethodeAnchorpaneManagment(DatePicker dataexamspicker,
    		ComboBox<String> timeComboBox, ComboBox<String> cycleComboBox,
    		ComboBox<Domain> domainComboBox,
    		ComboBox<Integer>SemestreComboBox , 
    		ComboBox<String> ModuleComboBox,
    		javafx.scene.control.TextField professorTextField,
    		javafx.scene.control.Button addteacherButton,
    		javafx.scene.control.TextField classroom,
    		javafx.scene.control.Button addclassroomButton, 
    		javafx.scene.control.Button displayexamsButton,
    		Button  confirme,ListView Professor
    		,ListView classroomList) {
        this.dataexamspicker = dataexamspicker;
        this.timeComboBox = timeComboBox;
        this.cycleComboBox = cycleComboBox;
        this.domainComboBox = domainComboBox;
        this.ModuleComboBox = ModuleComboBox;
        this.addteacherButton = addteacherButton;
        this.addclassroomButton = addclassroomButton;
        this.displayexamsButton = displayexamsButton;
        this.SemestreComboBox=SemestreComboBox;
        this.professorTextField = professorTextField;
        this.classroom = classroom;
        this.confirme = confirme;
        this.ProfessorListView = Professor;
        this.classroomListView = classroomList;

    }
    public void initialize() {
        cycleComboBox.getItems().addAll(DatabaseManagement.getCycles());
        SemestreComboBox.getItems().setAll(FXCollections.observableArrayList(1,2));
        timeComboBox.getItems().setAll(FXCollections.observableArrayList("08:00-09:30","10:00-11:30","12:00-1:30","14:00-15:30"));

        
        cycleComboBox.setOnAction(event -> {
            String selectedCycle = cycleComboBox.getValue();
            domainComboBox.getItems().clear();
            if (selectedCycle != null) {
                domainComboBox.getItems().addAll(DatabaseManagement.getDomainesAsAWhole(selectedCycle));
            }
        });
        domainComboBox.setCellFactory(lv -> new ListCell<Domain>() {
            @Override
            protected void updateItem(Domain item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDomainName());
            }
        });

        domainComboBox.setButtonCell(new ListCell<Domain>() {
            @Override
            protected void updateItem(Domain item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDomainName());
            }
        });
        SemestreComboBox.setOnAction(event -> {
            Domain selectedDomain = domainComboBox.getValue();
            int selectSemestre = SemestreComboBox.getValue();
            ModuleComboBox.getItems().clear();
            if (selectedDomain != null && selectSemestre != -1) {
                ModuleComboBox.getItems().addAll(
                		DatabaseManagement.getModulesByDomainAndSemester(
                				selectedDomain,selectSemestre));
            }
        });
        
        confirme.setOnAction(e ->{
        	DatabaseManagement.InsertIntoExam(new Exam(dataexamspicker.getValue(),
        			timeComboBox.getValue(),new Module(ModuleComboBox.getValue())));
        	
        	
        });
        ProfessorListView.setItems(suggestions);
        double rowHeight = 24;
        ProfessorListView.setFixedCellSize(rowHeight);

        // Whenever the suggestions list changes, update the ListView height
       suggestions.addListener((javafx.collections.ListChangeListener<String>) change -> {
            int size = suggestions.size();
            int maxVisibleRows = 5; // Optional: max number of rows to show
            int rowsToShow = Math.min(size, maxVisibleRows);

            ProfessorListView.setPrefHeight(rowsToShow * rowHeight + 2);
       });




        professorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue.isEmpty()) {

            suggestions.clear();
            ProfessorListView.setVisible(false);
            } else {
            	ProfessorListView.setVisible(true);
                ObservableList<Professor> results = DatabaseManagement.getAllProfessors(newValue);
                ArrayList names = (ArrayList) results.stream().map(p -> p.getPrFirstName() + " " + p.getPrLastName()).collect(Collectors.toList());
                suggestions.setAll((Collection<? extends String>) names);
            }
        });
        
        
        classroomListView.setItems(suggestions2);
        double rowHeight2 = 24;
        ProfessorListView.setFixedCellSize(rowHeight);

        // Whenever the suggestions list changes, update the ListView height
       suggestions2.addListener((javafx.collections.ListChangeListener<String>) change -> {
            int size = suggestions2.size();
            int maxVisibleRows = 5; // Optional: max number of rows to show
            int rowsToShow = Math.min(size, maxVisibleRows);

            classroomListView.setPrefHeight(rowsToShow * rowHeight + 2);
       });
        classroom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {

             suggestions2.clear();
             classroomListView.setVisible(false);
             } else {
            	 classroomListView.setVisible(true);
//                 ObservableList<Professor> results = DatabaseManagement.getAllClassrooms(newValue);
//                 ArrayList names = (ArrayList) results.stream().map(p -> p.getPrFirstName() + " " + p.getPrLastName()).collect(Collectors.toList());
//                 suggestions.setAll((Collection<? extends String>) names);
             }
         });
   }}