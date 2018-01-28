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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        series = new XYChart.Series<>();
        series.setName("Allocated Funds vs Time");

        loadGraph();
        lineChart.getData().add(series);
    }

    public void onExit(MouseEvent evt){
        ((Stage)((Label)evt.getSource()).getScene().getWindow()).close();
    }

    public void loadGraph(){
        try {
            Pair<Integer, Integer> p;
            int length = MainUiController.trendingList.size();
            for (int i=0;i<length;i++) {
                p = MainUiController.trendingList.get(i);
                series.getData().add(new XYChart.Data<>(p.getKey(),p.getValue()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
