package login;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import management.User;

public class Controllermethods {

	public static void FadeInto(AnchorPane currentPane, String interfaceToBeDisplayed) {
	    FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.2), currentPane); // Increased duration for smoothness
	    fadeOut.setFromValue(1.0);
	    fadeOut.setToValue(0.0);

	    fadeOut.setOnFinished(actionEvent -> {
	        try {
	            Parent newRoot = FXMLLoader.load(Controllermethods.class.getResource(interfaceToBeDisplayed));

	            // Apply fade-in to the new root *before* showing it
	            newRoot.setOpacity(0);
	            Scene newScene = new Scene(newRoot);
	            Stage currentStage = (Stage) currentPane.getScene().getWindow();
	            currentStage.setScene(newScene);

	            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.2), newRoot); // Match fadeOut duration
	            fadeIn.setFromValue(0.0);
	            fadeIn.setToValue(1.0);
	            fadeIn.play();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    });

	    fadeOut.play();
	}

    public static void exit(MouseEvent mouseEvent) {
            System.exit(0);
    }
    public static void switchPane(javafx.event.ActionEvent event, List<Button> buttons, List<AnchorPane> panes) {
        for (int i = 0; i < buttons.size(); i++) {
            panes.get(i).setVisible(event.getSource() == buttons.get(i));
        }
    }

    public static Object getSelected(TableView Table) {
        return Table.getSelectionModel().getSelectedItem();
    }
}
