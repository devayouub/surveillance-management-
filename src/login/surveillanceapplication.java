import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class surveillanceapplication  extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Parent root=FXMLLoader.load(getClass().getResource("loginscene.fxml"));//matansech libery ajout dossier lib
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getResource("view.css").toExternalForm());
        //matansech ki thol file css holo f ressource
        stage.setTitle("Hello World");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}


