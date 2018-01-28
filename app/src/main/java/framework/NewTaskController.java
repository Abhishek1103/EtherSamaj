package framework;

import javafx.animation.FadeTransition;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import rx.exceptions.Exceptions;

import java.awt.event.ActionEvent;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class NewTaskController implements Initializable {

    @FXML
    Button submitButton, cancelButton;
    @FXML
    AnchorPane rootPane;
    @FXML
    RadioButton workService, commService, medicalService;
    @FXML
    TextField daysField, targetField, contributeFund;
    @FXML
    TextArea textArea;
    String days;
    int duration;
    int target;
    int allocatedFund;
    String projectDescription;
    String option;


    ToggleGroup group;
    Service<Void> cwThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fadeIn=new FadeTransition();
        fadeIn.setDuration(Duration.seconds(1.0));
        fadeIn.setNode(rootPane);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
        group = new ToggleGroup();
        workService.setToggleGroup(group);
        workService.setSelected(true);
        commService.setToggleGroup(group);
        medicalService.setToggleGroup(group);
    }

    @FXML
    public void submitButtonClicked(javafx.event.ActionEvent evt){

        try {
            days = daysField.getText();
            duration = Integer.parseInt(days);
            target = Integer.parseInt(targetField.getText());
            allocatedFund = Integer.parseInt(contributeFund.getText());
            projectDescription = textArea.getText();

            if(days.isEmpty() || projectDescription.isEmpty())
                throw new Exception("Empty String");

            //option = (group.getSelectedToggle().getUserData().toString());


            startLoading();
            ((Stage) ((Button) evt.getSource()).getScene().getWindow()).close();

        }
        catch (Exception e){
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Invalid Input");
            a.setContentText("Please don't leave any field blank");
            a.setResizable(false);
            a.setTitle("Error");
            a.showAndWait();
        }
    }

    private void startLoading() {
        cwThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try{
                            Main.contractCw=CommunityWork.load(
                                    Main.contractAddress, Main.web3j, Main.credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
                            TransactionReceipt receipt = Main.contractCw.projectDeployer(Main.publicKey, BigInteger.valueOf(allocatedFund),projectDescription,BigInteger.valueOf(target),BigInteger.valueOf(duration),BigInteger.valueOf(60),BigInteger.valueOf(allocatedFund)).send();

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
            }
        };

        cwThread.setOnSucceeded(e->{
//            SlidePaneController obj=new SlidePaneController();
//            obj.updateProjectsView();
            SlidePaneController.updateProjectsView();
        });

        cwThread.restart();

    }

    @FXML
    public void cancelButtonClicked(Event evt){
        FadeTransition fadeOut=new FadeTransition(Duration.seconds(0.5),rootPane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
        fadeOut.setOnFinished(e -> {
            try {
                ((Stage) ((Label) evt.getSource()).getScene().getWindow()).close();
            }catch(Exception e1)
            {
                ((Stage) ((Button) evt.getSource()).getScene().getWindow()).close();

            }
        });
    }
}