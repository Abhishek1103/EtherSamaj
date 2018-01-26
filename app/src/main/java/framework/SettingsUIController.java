package framework;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsUIController implements Initializable {

    @FXML
    ImageView exit, settings, user, awards;
    @FXML
    AnchorPane settingsPane, userPane, awardsPane, mainPanel;
    @FXML
    JFXToggleButton toggle1, toggle2;

    //int exitClickCount=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userPane.setVisible(false);
        settingsPane.setVisible(true);
        awardsPane.setVisible(false);
        toggle1.setText("Off");
        toggle2.setText("Off");
    }

    public void handleButtonAction(MouseEvent evt){
        if(evt.getTarget() == settings){
            //exitClickCount=0;
            settingsPane.setVisible(true);
            userPane.setVisible(false);
        }else if(evt.getTarget() == user){
            //exitClickCount=0;
            userPane.setVisible(true);
            settingsPane.setVisible(false);
        }else if(evt.getTarget() == exit){
            //exitClickCount++;
            //if(exitClickCount >= 2){
                ((Stage)((ImageView)evt.getSource()).getScene().getWindow()).close();
            //}
            settingsPane.setVisible(false);
            userPane.setVisible(false);
        }
    }

    public void exitButtonClicked(MouseEvent evt){
        ((Stage)((ImageView)evt.getSource()).getScene().getWindow()).close();
    }

    public void handleToggle(ActionEvent evt) {
        if (evt.getTarget() == toggle1) {
            if (toggle1.isSelected())
                toggle1.setText("On");
            else toggle1.setText("Off");
        } else if (evt.getTarget() == toggle2) {
            if (toggle2.isSelected())
                toggle2.setText("On");
            else toggle2.setText("Off");
        }

    }

    public void onSaveClicked(){
        System.out.println("You clicked Save");
    }

    public void onCancelClicked(){
        System.out.println("You Clicked Cancel");
    }

}
