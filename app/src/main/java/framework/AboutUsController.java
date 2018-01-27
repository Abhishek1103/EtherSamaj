package framework;

import com.jfoenix.controls.JFXToggleButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutUsController implements Initializable {

    @FXML
    ImageView exit, settings, user, awards;
    @FXML
    AnchorPane settingsPane, userPane, awardsPane, mainPanel;
    @FXML
    JFXToggleButton toggle1, toggle2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn=new FadeTransition();
        fadeIn.setDuration(Duration.seconds(1.0));
        fadeIn.setNode(mainPanel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public void handleButtonAction(MouseEvent evt){
        if(evt.getTarget() == settings){
            //exitClickCount=0;
            settingsPane.setVisible(true);
            userPane.setVisible(false);
            awardsPane.setVisible(false);
        }else if(evt.getTarget() == user){
            //exitClickCount=0;
            userPane.setVisible(true);
            settingsPane.setVisible(false);
            awardsPane.setVisible(false);
        }else if(evt.getTarget() == awards){
            //exitClickCount=0;
            awardsPane.setVisible(true);
            userPane.setVisible(false);
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
        FadeTransition fadeOut=new FadeTransition(Duration.seconds(0.5),mainPanel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
        fadeOut.setOnFinished(e -> {
            ((Stage)((ImageView)evt.getSource()).getScene().getWindow()).close();
        });
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
}