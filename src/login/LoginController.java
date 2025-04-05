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

public class LoginController implements Initializable {

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
                ft.setDuration(Duration.seconds(0.1));
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
