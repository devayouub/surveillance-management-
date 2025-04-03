package login;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

import javax.swing.text.html.ImageView;


public class dachboardConroller implements Initializable {
    @FXML
    private Button buttonacc;
    @FXML
    private ImageView imageview;

    @FXML
    private Button buttondisplay;
    @FXML
    private AnchorPane anchoraccuil;

    @FXML
    private AnchorPane anchordisplay;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }


    public void switchform(javafx.event.ActionEvent event) {
        if(event.getSource()==buttonacc){
            anchoraccuil.setVisible(true);
            anchordisplay.setVisible(false);
        }else if(event.getSource()==buttondisplay){
            anchoraccuil.setVisible(false);
            anchordisplay.setVisible(true);
        }
    }
}
