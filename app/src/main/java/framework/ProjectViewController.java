package framework;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import javax.xml.soap.Text;

import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {

    int projectId;
    final double[] xoffset = new double[1];  // This is because variables used in lambda expression should
    final double[] yoffset = new double[1];  // final or effectively final. And IDE suggested this method

    Stage window;
    int count;
    @FXML
    AnchorPane mainAnchor;

    public static String getProjectDescriptionProp() {
        return projectDescriptionProp.get();
    }

    public static StringProperty projectDescriptionPropProperty() {
        return projectDescriptionProp;
    }

    public static void setProjectDescriptionProp(String projectDescriptionProp) {
        ProjectViewController.projectDescriptionProp.set(projectDescriptionProp);
    }

    static StringProperty projectDescriptionProp;
    static String projectDescription;
    static BigInteger allocatedFunds, targetAmount, fundingDuration;
    static  boolean isCompleted, isAccepted, isOpen;

    @FXML
    Label projectDescriptionLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*
        * Display the project Description And Project Id
        */

        projectDescriptionProp = new SimpleStringProperty(this,"projectDesc","Description of the Project");
        projectDescriptionProp.addListener((v, oldVal, newVal) -> {
            projectDescriptionLabel.setText(newVal.toString());
        });

        //System.out.println(""+projectId);

        //executeProjectDetailsThread();
    }

    void executeProjectDetailsThread(){
//        ProjectDetails pr = new ProjectDetails(projectId);
//        pr.start();
    }


    public void mainAnchorMousePressed(MouseEvent evt){
        xoffset[0] = evt.getSceneX();
        yoffset[0] = evt.getSceneY();
    }

    @FXML
    public void mainAnchorMouseDragged(MouseEvent evt){
        try {
            window.setX(evt.getScreenX() - xoffset[0]);
            window.setY(evt.getScreenY() - yoffset[0]);
        }catch (Exception e){
            oneTimeMouseClicked(evt);

        }
    }

    @FXML
    public void oneTimeMouseClicked(MouseEvent evt){
        if(count==0) {
            window = (Stage) mainAnchor.getScene().getWindow();
            count++;

        }
    }




    @FXML
    public void onExit(MouseEvent evt){
        ((Stage)((Label)evt.getSource()).getScene().getWindow()).close();
    }
    @FXML
    public void onBack(MouseEvent evt){
        ((Stage)((ImageView)evt.getSource()).getScene().getWindow()).close();
    }

    public void setProjectId(int id){
        this.projectId = id;
    }

    public void onFundProjectButtonClicked(){
        Stage settingsWindow = new Stage();
        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.initStyle(StageStyle.TRANSPARENT);
        try {
            Parent settingsRoot = FXMLLoader.load(getClass().getResource("")); // TODO: to be created
            Scene sc = new Scene(settingsRoot);
            sc.setFill(Color.TRANSPARENT);
            settingsWindow.setScene(sc);
            settingsWindow.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onUndertakeProjectButtonClicked(){
        Stage settingsWindow = new Stage();
        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.initStyle(StageStyle.TRANSPARENT);
        try {
            Parent settingsRoot = FXMLLoader.load(getClass().getResource("../../resources/UndertakeProjectConfirmController")); //
            Scene sc = new Scene(settingsRoot);
            sc.setFill(Color.TRANSPARENT);
            settingsWindow.setScene(sc);
            settingsWindow.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onProjectIdClicked(){
        LoginController.showAlert("Project Details", "Project #"+projectId,"Allocated Funds: "+allocatedFunds+"\nTarget Amount: "+targetAmount);
    }

    public void onAllotedFundsClicked(){
        LoginController.showAlert("Allocated Funds","Project : #"+projectId,"Funds Allocated: "+allocatedFunds.toString());
    }

    public void onProjectStatisticsClicked(){
        // TODO: Show graph for project Statistics
    }
}


class ProjectDetails extends Thread{

    BigInteger projectId;

    ProjectDetails(BigInteger _projectId){
        projectId = _projectId;
    }

    public void run(){
        try {
            Web3j web3j = Main.web3j;
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            System.out.println(clientVersion);

            Credentials credentials = WalletUtils.loadCredentials("123456789","/home/aks/.ethereum/testnet/keystore/UTC--2018-01-22T10-02-54.636000000Z--e470a002afbd470488fa4dc8ccf8089878b8b683.json"/*LoginController.password, Main.walletFileAddress*/);
            System.out.println("Credentials: "+credentials.toString());

            CommunityWork contract = CommunityWork.load("0x3a974d6c802bbb0d388783c291c5da19bc335dd3", web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

            BigInteger projectID=projectId;
            //TransactionReceipt transactionReceipt =
            //System.out.println("TransactionReceipt: "+transactionReceipt.toString());
            Tuple3<Boolean, Boolean, Boolean> result1 = contract.getProjectStatus(projectID).send();
            Tuple3<BigInteger, BigInteger, BigInteger> result2 = contract.getProjectValues(projectID).send();
            Tuple2<String, String> result3 = contract.getProjectInfo(projectID).send();

//            System.out.println("Allocated Fund "+result.getValue1());
//            System.out.println("Target Amount "+result.getValue2());
//            System.out.println("Funding Duration "+result.getValue3());
            ProjectViewController.isAccepted = result1.getValue1();
            ProjectViewController.isCompleted = result1.getValue2();
            ProjectViewController.isOpen = result1.getValue3();
            ProjectViewController.allocatedFunds = result2.getValue1();
            ProjectViewController.targetAmount = result2.getValue2();
            ProjectViewController.fundingDuration = result2.getValue3();
            ProjectViewController.projectDescription = result3.getValue1();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}