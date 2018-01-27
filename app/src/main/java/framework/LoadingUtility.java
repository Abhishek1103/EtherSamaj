package framework;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingUtility
{
    Stage settingsWindow;

    public void makeTransparent()
    {
        settingsWindow = new Stage();
        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.initStyle(StageStyle.TRANSPARENT);
        try {
            Parent settingsRoot = FXMLLoader.load(getClass().getResource("../../resources/LoadingView.fxml"));
            Scene sc = new Scene(settingsRoot);
            sc.setFill(Color.TRANSPARENT);
            settingsWindow.setScene(sc);
            settingsWindow.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
