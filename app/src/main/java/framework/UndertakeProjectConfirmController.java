package framework;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;

public class UndertakeProjectConfirmController implements Initializable{

    @FXML
    JFXButton yesButton, noButton;
    @FXML
    JFXToggleButton toggleButton;
    int projectID;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setId(int id){
        projectID = id;
    }

    public void onYes(){
        if(toggleButton.isSelected()){
            try {
                if(!ProjectViewController.isAccepted) {
                    TransactionReceipt receipt = Main.contractCw.acceptProject(BigInteger.valueOf(projectID)).send();
                    System.out.println("Project Accepted");
                }

            }catch (Exception e){
                System.out.println("Project Pre-Accepted");
            }
        }else {
            showAlert("Accept Terms and Conditions", "Please Accept the terms and conditions to proceed further","");
        }
    }

    public void onNo(){
        Stage s = (Stage)noButton.getScene().getWindow();
        s.close();
    }

    public void showAlert( String title, String headerText, String contentText){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(headerText);
        a.setContentText(contentText);
        a.setResizable(false);
        a.setTitle(title);
        a.showAndWait();
    }
}
