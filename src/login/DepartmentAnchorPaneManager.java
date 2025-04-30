package login;

import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class DepartmentAnchorPaneManager {
    private Button buttonengenment;
    private Button buttonmodules;
    private Button buttonspeciality;
    private AnchorPane anchorProfessors;
    private AnchorPane anchormodules;
    private AnchorPane anchorDomaines;
    
    public void switchmenubar(ActionEvent event) {
		Controllermethods.switchPane(event,
            Arrays.asList(buttonengenment, buttonmodules, buttonspeciality),
            Arrays.asList(anchorProfessors, anchormodules, anchorDomaines)
        );
    }
}
