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
    
    public DepartmentAnchorPaneManager(Button buttonengenment, Button buttonmodules, Button buttonspeciality,
			AnchorPane anchorProfessors, AnchorPane anchormodules, AnchorPane anchorDomaines) {
		super();
		this.buttonengenment = buttonengenment;
		this.buttonmodules = buttonmodules;
		this.buttonspeciality = buttonspeciality;
		this.anchorProfessors = anchorProfessors;
		this.anchormodules = anchormodules;
		this.anchorDomaines = anchorDomaines;
	}
    public void initialize() {
    	buttonengenment.setOnAction(e->{
    		switchmenubar(e);
    	});
    	buttonmodules.setOnAction(e->{
    		switchmenubar(e);
    	});
    	buttonspeciality.setOnAction(e->{
    		switchmenubar(e);
    	});
    	
    }
	public void switchmenubar(ActionEvent event) {
		Controllermethods.switchPane(event,
            Arrays.asList(buttonengenment, buttonspeciality,buttonmodules ),
            Arrays.asList(anchorProfessors,anchorDomaines, anchormodules)
        );
    }
}
