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
    private Button buttonsalle;
    
    private AnchorPane anchorProfessors;
    private AnchorPane anchormodules;
    private AnchorPane anchorDomaines;
    private AnchorPane anchorsalle;
    
  
    public DepartmentAnchorPaneManager(Button buttonengenment, Button buttonmodules, Button buttonspeciality,
			Button buttonsalle, AnchorPane anchorProfessors, AnchorPane anchormodules, AnchorPane anchorDomaines,
			AnchorPane anchorsalle) {
		super();
		this.buttonengenment = buttonengenment;
		this.buttonmodules = buttonmodules;
		this.buttonspeciality = buttonspeciality;
		this.buttonsalle = buttonsalle;
		this.anchorProfessors = anchorProfessors;
		this.anchormodules = anchormodules;
		this.anchorDomaines = anchorDomaines;
		this.anchorsalle = anchorsalle;
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
    	buttonsalle.setOnAction(e->{
    		switchmenubar(e);
    	});
    	
    }
	public void switchmenubar(ActionEvent event) {
		Controllermethods.switchPane(event,
            Arrays.asList(buttonengenment, buttonspeciality,buttonmodules,buttonsalle ),
            Arrays.asList(anchorProfessors,anchorDomaines, anchormodules,anchorsalle)
        );
    }
}
