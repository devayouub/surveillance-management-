package login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.DataFormat;
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
    private ComboBox<String> domainComboBox;
    private ComboBox<Integer> SemestreComboBox;
    private ComboBox<String> ModuleComboBox;
    private javafx.scene.control.TextField professorTextField;
    private javafx.scene.control.Button addteacherButton;
    private javafx.scene.control.TextField classroom;
    private javafx.scene.control.Button addclassroomButton;
    private Button displayexamsButton;
    private ComboBox<String> Cycle2;
    private ComboBox<String> Domain2;
    private Button confirme ;
  ListView Professor = new ListView();
    private final ObservableList<String> suggestions = FXCollections.observableArrayList();
    private ArrayList data = new ArrayList();
    public ManualMethodeAnchorpaneManagment(DatePicker dataexamspicker, ComboBox<String> timeComboBox, ComboBox<String> cycleComboBox, ComboBox<String> domainComboBox, ComboBox<Integer>SemestreComboBox , ComboBox<String> ModuleComboBox, javafx.scene.control.TextField professorTextField, javafx.scene.control.Button addteacherButton, ComboBox<String> Cycle2, ComboBox<String> Domain2 , javafx.scene.control.TextField classroom, javafx.scene.control.Button addclassroomButton, javafx.scene.control.Button displayexamsButton,Button  confirme,ListView Professor) {
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
        this.Cycle2 = Cycle2;
        this.Domain2 = Domain2;
        this.confirme = confirme;
        this.Professor = Professor;


        data = new ArrayList<>();
    }
    public void initialize() {
        cycleComboBox.getItems().addAll(DatabaseManagement.getCycles());
        SemestreComboBox.getItems().setAll(FXCollections.observableArrayList(1,2));
        timeComboBox.getItems().setAll(FXCollections.observableArrayList("8:00-9:30","10:00-11:30","12:00-1:30","14:00-15:30"));

        cycleComboBox.setOnAction(event -> {
            String selectedCycle = cycleComboBox.getValue();
            domainComboBox.getItems().clear();
            if (selectedCycle != null) {
                domainComboBox.getItems().addAll(DatabaseManagement.getDomaines(selectedCycle));
            }
        });
        domainComboBox.setOnAction(event -> {
            String selectedDomain = domainComboBox.getValue();
            int selectSemestre = SemestreComboBox.getValue();
            ModuleComboBox.getItems().clear();
            if (selectedDomain != null && selectSemestre != -1) {
                ModuleComboBox.getItems().addAll(DatabaseManagement.getModuleIdsByDomainAndSemester(selectedDomain,selectSemestre));
            }
        });
        Professor.setItems(suggestions);
        double rowHeight = 24;
        Professor.setFixedCellSize(rowHeight);

        // Whenever the suggestions list changes, update the ListView height
       suggestions.addListener((javafx.collections.ListChangeListener<String>) change -> {
            int size = suggestions.size();
            int maxVisibleRows = 5; // Optional: max number of rows to show
            int rowsToShow = Math.min(size, maxVisibleRows);

           Professor.setPrefHeight(rowsToShow * rowHeight + 2);
       });




        professorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue.isEmpty()) {

            suggestions.clear();
               Professor.setVisible(false);
            } else {
               Professor.setVisible(true);
                ObservableList<Professor> results = DatabaseManagement.searchProfessorsByName(newValue);
                List names = (List) results.stream().map(p -> p.getPrFirstName() + " " + p.getPrLastName()).collect(Collectors.toList());
                suggestions.setAll((Collection<? extends String>) names);
            }
        });
   }}




