package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class surveillanceapplication  extends Application {
    @Override
    public void start(Stage stage)  {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Login.fxml"));
			Parent root = loader.load();
			   Scene scene=new Scene(root);
		        scene.getStylesheets().add(getClass().getResource("view.css").toExternalForm());
		        stage.setTitle("Exams Management");
		        stage.setScene(scene);
		        stage.show();
		        stage.setResizable(false);
		        stage.setAlwaysOnTop(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
    }

    public static void main(String[] args) {
        launch();
    }
}


