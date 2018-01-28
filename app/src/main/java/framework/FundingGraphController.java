package framework;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class FundingGraphController implements Initializable {

    @FXML
    LineChart<Integer, Integer> lineChart;
    XYChart.Series<Integer, Integer> series;
    int count=0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        series = new XYChart.Series<>();
        series.setName("Allocated Funds vs Time");

        //loadGraph();
        lineChart.getData().add(series);
    }

    public void onExit(MouseEvent evt){
        ((Stage)((Label)evt.getSource()).getScene().getWindow()).close();
    }

    public void loadGraph(){
        if(count==0) {
            count++;
            try {
                Pair<Integer, Integer> p;
                int length = ProjectViewController.x.length;
                for (int i = 0; i < length; i++) {

                    series.getData().add(new XYChart.Data<>(ProjectViewController.x[i], ProjectViewController.y[i]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
