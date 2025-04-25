package login;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.text.html.ImageView;


public class dachboardConroller implements Initializable {
    @FXML
    private AnchorPane anchoreengnement;
    @FXML
    private AnchorPane anchormodules;
    @FXML
    private Button buttonengenment;
    @FXML
    private Button buttonmodules;
    @FXML
    private AnchorPane anchorspeciality;
    @FXML
    private Button buttonspeciality;


    @FXML
    private AnchorPane anchorpanedachboard;
    @FXML
    private Button buttonacc;
    @FXML
    private ImageView imageview;
    @FXML
    private Button buttonuser;
    @FXML
    private AnchorPane anchoruser;
    @FXML
    private Button buttondisplay;
    @FXML
    private AnchorPane anchoraccuil;

    @FXML
    private AnchorPane anchordisplay;
    @FXML
    private Button buttonmanegment;
    @FXML
    private AnchorPane anchormanegment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //das methode pour change scene
    private void makeFadeout() {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.seconds(0.1));
        ft.setNode(anchorpanedachboard);
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
    //das methode 2 pour change scene
    private void Loadscene() throws IOException {
        Parent seconde;
        seconde =(AnchorPane) FXMLLoader.load(getClass().getResource("zaki.fxml"));
        Scene cc=new Scene(seconde);
        Stage stage1=(Stage) anchorpanedachboard.getScene().getWindow();
        stage1.setScene(cc);

    }
    //nsit bah nekteb bali hada actionlisner te3 button leg out
    public void returntopage(ActionEvent event) {
        makeFadeout();

    }

        // pour switch les anchorpane
    public void switchform(javafx.event.ActionEvent event) {
        if(event.getSource()==buttonacc){
            anchoraccuil.setVisible(true);
            anchordisplay.setVisible(false);
            anchoruser.setVisible(false);
            anchormanegment.setVisible(false);
        }else if(event.getSource()==buttondisplay){
            anchoraccuil.setVisible(false);
            anchoruser.setVisible(false);
            anchordisplay.setVisible(true);
            anchormanegment.setVisible(false);
        }else if(event.getSource()==buttonuser){
            anchoraccuil.setVisible(false);
            anchordisplay.setVisible(false);
            anchoruser.setVisible(true);
            anchormanegment.setVisible(false);

        }else if(event.getSource()==buttonmanegment){
            anchoraccuil.setVisible(false);
            anchoruser.setVisible(false);
            anchordisplay.setVisible(false);
            anchormanegment.setVisible(true);
        }
    }
    public void switchmenubar(javafx.event.ActionEvent event) {
        if(event.getSource()==buttonengenment){
            anchoreengnement.setVisible(true);
            anchormodules.setVisible(false);
            anchorspeciality.setVisible(false);
        }else if(event.getSource()==buttonmodules){
            anchoreengnement.setVisible(false);
            anchormodules.setVisible(true);
            anchorspeciality.setVisible(false);
        } else if (event.getSource()==buttonspeciality) {
            anchoreengnement.setVisible(false);
            anchormodules.setVisible(false);
            anchorspeciality.setVisible(true);

        }

    }


    public void actionexit(MouseEvent mouseEvent) {
        System.exit(0);
    }
}

