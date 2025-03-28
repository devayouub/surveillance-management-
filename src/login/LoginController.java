package login;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.*;
public class LoginController {
	        Stage stage;
	        Scene scene;
	        Parent root;
	        
	        @FXML   usernameField;
	public void loginButtonActionListener(ActionEvent e) {
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
		
		
//		------------------------authentication--------------------
		
		String username = 
		
		
		
		
	}
        
        
}

    
