package login;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class LoginController  implements Initializable {
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
        

        @FXML
        public Button buttondisplay;


        @FXML
        private AnchorPane anchorpane;
        @FXML
        private PasswordField PasswordField;

        @FXML
        private CheckBox RememberChek;

        @FXML
        private Button buttonLogin;

        @FXML
        private TextField usernameField;


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
        //das methode pour change scene
        private void makeFadeout() {
                FadeTransition ft = new FadeTransition();
                ft.setDuration(Duration.seconds(0));
                ft.setNode(anchorpane);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.setOnFinished(actionEvent -> {
                        try {
                                Loadscene();
                        } catch (IOException e) {
                                throw new RuntimeException(e);
                        }
                });
                ft.play();
        }
        //das methode pour change scene
        private void Loadscene() throws IOException {
                Parent seconde;
                seconde =(AnchorPane) FXMLLoader.load(getClass().getResource("dachboard.fxml"));
                Scene cc=new Scene(seconde);
                Stage stage1=(Stage) anchorpane.getScene().getWindow();
                stage1.setScene(cc);

        }


        public void LoginAction(javafx.event.ActionEvent actionEvent) {
                makeFadeout();

        }

        public void actionexit(MouseEvent mouseEvent) {

                System.exit(0);
        }
}

    



