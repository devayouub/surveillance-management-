package login;

import java.io.IOException;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.*;
public class LoginController {
	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField PasswordField;
	@FXML
	private CheckBox RememberChek;
	        Stage stage;
	        Scene scene;
	        Parent root;
	public void loginButtonActionListener(ActionEvent e) {

        //   --------------authentication-----------------------------
		
		  String usernameEntered = usernameField.getText();
		  String PasswordEntered = PasswordField.getText();
		   if(DatabaseManagement.authenticateUser(usernameEntered, PasswordEntered)){
			   //------------------managing the remember-me checkbox ---------------
			   if(RememberChek.isSelected()) {
				   DatabaseManagement.RememberMeUpdater(usernameEntered);
			   }
			   
				//		-----------------switching scene ----------------------------
			   FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("Dashboard.fxml"));
				try {
					 root = loader.load();
					 stage =(Stage)((Node)e.getSource()).getScene().getWindow();
					 scene = new Scene(root);
					 stage.setScene(scene);
					 stage.show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		   }
		   else {
			   System.out.println("wrong info");
			   
		   }
		
	
		
		
		
		
	}
}
        


    


