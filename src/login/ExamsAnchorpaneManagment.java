package login;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import java.util.Arrays;

public class ExamsAnchorpaneManagment {
    private Button ManualButton;
    private Button AutomaticButton;

   private AnchorPane InizialiseExamsManual;
   private AnchorPane InizialiseExamsAutomatic;
public ExamsAnchorpaneManagment(Button ManualButton, Button AutomaticButton,AnchorPane InizialiseExamsManual,AnchorPane InizialiseExamsAutomatic ) {
    super();
    this.ManualButton = ManualButton;
    this.AutomaticButton = AutomaticButton;
    this.InizialiseExamsManual = InizialiseExamsManual;
    this.InizialiseExamsAutomatic = InizialiseExamsAutomatic;
}
    public void initialize() {
        ManualButton.setOnAction(e->{
            switchmenubar(e);
        });
        AutomaticButton.setOnAction(e->{
            switchmenubar(e);
        });



    }
public void switchmenubar(ActionEvent event){
    Controllermethods.switchPane(event,
            Arrays.asList(ManualButton, AutomaticButton),
            Arrays.asList(InizialiseExamsManual,InizialiseExamsAutomatic)
    );
}
}
