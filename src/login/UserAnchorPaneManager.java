package login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import management.User;

public class UserAnchorPaneManager {

    private TableView<User> usersTable;
    private TextField searchField;
    private FilteredList<User> filteredData;
    private final javafx.beans.property.BooleanProperty showPasswords = new javafx.beans.property.SimpleBooleanProperty(false);

    private Label warningLabel;
    private Label weakPasswordLabel;
    private TextField passwordField;
    private TextField passwordTextField;
    private TextField usernameField;
    private CheckBox makeAdmin;
    private CheckBox showpassword;
    private CheckBox showpasswords;
    private Button addUserButton;
    private Button deleteUserButton;
    public UserAnchorPaneManager(TableView<User> usersTable,
    		TextField usernameField,TextField passwordField,
    		 TextField passwordTextField,CheckBox makeAdmin,
    		   Label weakPasswordLabel,Label WarningLabel,
    		     Button addUserButton,Button deleteUserButton,
    		        CheckBox showpassword, CheckBox showpasswords) {
        this.usersTable = usersTable;
        setupTable();
        setUsernameField(usernameField);
        setPasswordFields(passwordField, passwordTextField);
        setMakeAdminCheckbox(makeAdmin);
        setWeakPasswordLabel(weakPasswordLabel);
    	setWarningLabel(WarningLabel);
    	setAddUserButton(addUserButton);
    	setDeleteUserButton(deleteUserButton);
    	setShowassword(showpassword);
    	setShowasswords(showpasswords);
    }
	
	public void initialize() {
	    addUserButton.setOnAction(event -> addUser());
	    deleteUserButton.setOnAction(event -> deleteUser());
	    showpassword.setOnAction(event -> toggleshowPassword());
	    showpasswords.setOnAction(event -> toggleShowPasswords());
		
	}
    public void setSearchField(TextField searchField) {
        this.searchField = searchField;
        setupSearch();
    }

    public void setWarningLabel(Label warningLabel) {
        this.warningLabel = warningLabel;
    }

    public void setWeakPasswordLabel(Label weakPasswordLabel) {
        this.weakPasswordLabel = weakPasswordLabel;
    }

    public void setPasswordFields(TextField passwordField, TextField passwordTextField) {
        this.passwordField = passwordField;
        this.passwordTextField = passwordTextField;
        setupPasswordFieldFocus();
    }
    private void setShowassword(CheckBox showpassword) {
		this.showpassword=showpassword;
		
	}
    public void setUsernameField(TextField usernameField) {
        this.usernameField = usernameField;
    }

    public void setMakeAdminCheckbox(CheckBox makeAdmin) {
        this.makeAdmin = makeAdmin;
    }

    private void setShowasswords(CheckBox showasswords) {
		this.showpasswords = showasswords;
	}
	private void setAddUserButton(Button addUserButton) {
		this.addUserButton = addUserButton;
	}
	private void setDeleteUserButton(Button deleteUserButton) {
		this.deleteUserButton = deleteUserButton;
	}
	private void setupTable() {
        usersTable.getColumns().get(0).setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("userID"));
        usersTable.getColumns().get(1).setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("username"));
        usersTable.getColumns().get(2).setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("isAdmin"));

        TableColumn<User, String> passwordColumn = (TableColumn<User, String>) usersTable.getColumns().get(3);
        passwordColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String password, boolean empty) {
                super.updateItem(password, empty);
                if (empty || password == null) {
                    setText(null);
                } else {
                    if (showPasswords.get()) {
                        setText(password);
                    } else {
                        setText(maskPassword(password));
                    }
                }
            }
        });

        passwordColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("password"));

        DatabaseManagement.loadUsersFromDatabase(usersTable);
    }

    private void setupSearch() {
        if (searchField != null) {
            filteredData = new FilteredList<>(usersTable.getItems(), p -> true);

            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(user -> {
                    if (newValue == null || newValue.isEmpty()) return true;
                    String lowerCaseFilter = newValue.toLowerCase();
                    return user.getUsername().toLowerCase().contains(lowerCaseFilter);
                });
            });

            usersTable.setItems(filteredData);
        }
    }

    private void setupPasswordFieldFocus() {
        if (passwordField != null && passwordTextField != null) {
            passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());

            passwordField.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
                if (newFocus) {
                    showWeakPasswordRules();
                } else {
                    hideWeakPasswordRules();
                }
            });

            passwordTextField.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
                if (newFocus) {
                    showWeakPasswordRules();
                } else {
                    hideWeakPasswordRules();
                }
            });
        }
    }

    private void showWeakPasswordRules() {
        if (weakPasswordLabel != null) {
            weakPasswordLabel.setText("Password must contain at least 8 characters, an uppercase letter and a number");
            weakPasswordLabel.setStyle("-fx-text-fill: yellow;");
            weakPasswordLabel.setOpacity(1);
        }
    }

    private void hideWeakPasswordRules() {
        if (weakPasswordLabel != null) {
            weakPasswordLabel.setOpacity(0);
        }
    }

    public void reloadTable() {
        usersTable.getItems().clear();
        DatabaseManagement.loadUsersFromDatabase(usersTable);
    }
    public void toggleshowPassword() {
        passwordField.setVisible(!passwordField.isVisible());
        passwordTextField.setVisible(!passwordTextField.isVisible());
    }
    public void toggleShowPasswords() {
        showPasswords.set(!showPasswords.get());
        usersTable.refresh();
    }
    

    public void addUser() {
        if (usernameField == null || passwordField == null || makeAdmin == null || weakPasswordLabel == null) {
            throw new IllegalStateException("User input components not set in UsersTableManager");
        }

        String username = usernameField.getText();
        String password = passwordField.getText();
        User temporaryUser = new User(username, makeAdmin.isSelected(), password);

        if (temporaryUser.getUsername().isEmpty()) {
            weakPasswordLabel.setStyle("-fx-text-fill: red;");
            weakPasswordLabel.setText("Must fill username field");
            weakPasswordLabel.setOpacity(1);
            return;
        }

        String validation = DatabaseManagement.isValidPassword(temporaryUser.getPassword());
        if (validation.equals("Valid")) {
            DatabaseManagement.addUser(temporaryUser);
            reloadTable();
            weakPasswordLabel.setText("User added successfully");
            weakPasswordLabel.setStyle("-fx-text-fill: green;");
            weakPasswordLabel.setOpacity(1);
        } else {
            weakPasswordLabel.setText(validation);
            weakPasswordLabel.setStyle("-fx-text-fill: red;");
            weakPasswordLabel.setOpacity(1);
        }
    }

    public void deleteUser() {
        if (warningLabel == null) {
            throw new IllegalStateException("Warning label not set in UsersTableManager");
        }

        User selectedUser = getSelectedUser();

        if (selectedUser == null) {
            warningLabel.setText("No user selected.");
            warningLabel.setStyle("-fx-text-fill: red;");
            warningLabel.setOpacity(1);
            return;
        }

        if (usersTable.getItems().size() == 1) {
            warningLabel.setText("You cannot delete the last user.");
            warningLabel.setStyle("-fx-text-fill: red;");
            warningLabel.setOpacity(1);
            return;
        }

        DatabaseManagement.removeUser(selectedUser);
        reloadTable();
    }

    public User getSelectedUser() {
        return usersTable.getSelectionModel().getSelectedItem();
    }

    private String maskPassword(String password) {
        return "•".repeat(password.length());
    }


    
}
