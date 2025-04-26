package login;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController {
      @FXML
      private PasswordField PasswordField;

      @FXML
      private CheckBox RememberChek;

      @FXML
      private Button buttonLogin;

      @FXML
      private TextField usernameField;
      @FXML
      private Button buttondisplay;
      @FXML
      private Label errorMessage;
	  @FXML
      private  AnchorPane anchorpane;
	public void loginButtonActionListener(javafx.event.ActionEvent e) {
        //   --------------authentication-----------------------------
		  String usernameEntered = usernameField.getText();
		  String PasswordEntered = PasswordField.getText();
		   if(DatabaseManagement.authenticateUser(usernameEntered, PasswordEntered)){
			   //------------------managing the remember-me check-box ---------------
			   if(RememberChek.isSelected()) {
				   DatabaseManagement.RememberMeUpdater(usernameEntered,true);
			   }
			   
				//		-----------------switching scene ----------------------------
			  Controllermethods.FadeInto(anchorpane,"dashboard.fxml");
	}
		   else {
			   errorMessage.setOpacity(1);
		   }
		  } 
	  public void actionexit(MouseEvent mouseEvent) {
            Controllermethods.exit(mouseEvent);
}

}

    



