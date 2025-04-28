package login;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import management.User;

public class ProfessorsAnchorPaneManager {
	private TableView<User> professorsTable;
    private TextField searchField;
    private FilteredList<User> filteredData;
    private Label warningLabel;
    private Label informationError;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField EmailField;
    private Button addProfessorButton;
    private Button deleteProfessorButton;
}
