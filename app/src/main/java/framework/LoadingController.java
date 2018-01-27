package framework;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable{

    @FXML
    ImageView spinner;

    @FXML
    ImageView ripple;

    @FXML
    AnchorPane rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        rootPane.setOpacity(0);
//        FadeTransition fadeIn=new FadeTransition();
//        fadeIn.setDuration(Duration.seconds(1.0));
//        fadeIn.setNode(rootPane);
//        fadeIn.setFromValue(0.0);
//        fadeIn.setToValue(1.0);
//        fadeIn.play();
//        fadeIn.setOnFinished(e->{
//            LoadingUtility obj=new LoadingUtility();
//            obj.makeTransparent();
//        });
    }

    public void exit()
    {
        ((Stage)(rootPane.getScene().getWindow())).close();
    }
}
