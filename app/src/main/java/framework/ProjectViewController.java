package framework;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.nashorn.internal.runtime.options.Option;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import javax.xml.soap.Text;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable {

    int projectId;
    final double[] xoffset = new double[1];  // This is because variables used in lambda expression should
    final double[] yoffset = new double[1];  // final or effectively final. And IDE suggested this method

    Stage window;
    int count;
    Service<Void> projectDetailsThread;
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
    static BigInteger numPoints;
    static int[] x;
    static int[] y;
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
            projectDescription = newVal.toString();
        });

        //System.out.println(""+projectId);

        executeProjectDetailsThread();
    }

    void executeProjectDetailsThread(){
//        ProjectDetails pr = new ProjectDetails(projectId);
//        pr.start();


        projectDetailsThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try{
//                            Main.web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/Nb9v0iQy5LYKUBBYu3Hp"));
//                            Main.web3ClientVersion = Main.web3j.web3ClientVersion().send();
//                            String clientVersion = Main.web3ClientVersion.getWeb3ClientVersion();
//                            System.out.println(clientVersion);
//
//                            Main.credentials = WalletUtils.loadCredentials("123456789","/home/aks/.ethereum/testnet/keystore/UTC--2018-01-22T10-02-54.636000000Z--e470a002afbd470488fa4dc8ccf8089878b8b683.json"/*LoginController.password, Main.walletFileAddress*/);
//                            System.out.println("Credentials: "+Main.credentials.toString());
                           Tuple3<Boolean, Boolean, Boolean> res =  Main.contractCw.getProjectStatus(BigInteger.valueOf(projectId)).send();
                           isAccepted= res.getValue1();
                           isCompleted=res.getValue2();
                           isOpen = res.getValue3();
                           Tuple2<String, String> res1 = Main.contractCw.getProjectInfo(BigInteger.valueOf(projectId)).send();
                           //projectDescriptionProp.setValue(res1.getValue1());
                           updateMessage(res1.getValue1());
                           Tuple3<BigInteger, BigInteger, BigInteger> res2 = Main.contractCw.getProjectValues(BigInteger.valueOf(projectId)).send();
                           allocatedFunds = res2.getValue1();
                           targetAmount = res2.getValue2();
                           fundingDuration = res2.getValue3();
                           numPoints = Main.contractCw.numberOfPoints(BigInteger.valueOf(projectId)).send();
                           x = new int[numPoints.intValue()];
                           y = new int[numPoints.intValue()];

                           for(int i=0;i<numPoints.intValue();i++){
                               Tuple2<BigInteger, BigInteger> tuple = (Main.contractCw.pointForPlot(BigInteger.valueOf(projectId), BigInteger.valueOf(i)).send());
                               x[i] = tuple.getValue1().intValue();
                               y[i] = tuple.getValue2().intValue();
                           }
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

        projectDetailsThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                projectDescriptionProp.bind(projectDetailsThread.messageProperty());
            }
        });
        projectDetailsThread.restart();

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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Fund to Project #"+projectId);
        dialog.setHeaderText("Funding Amount");
        dialog.setContentText("Enter the amount you want to fund?");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent())
        {
                                                        //TODO: add user address
                try {
                    int amount = Integer.parseInt(result.get());
//                    TransactionReceipt receipt = Main.contractCw.addFunds("0xe470a002afbd470488fa4dc8ccf8089878b8b683",BigInteger.valueOf(projectId), BigInteger.valueOf(amount)).send();
                    FundProject fd = new FundProject(projectId,amount);
                    fd.start();
                }catch (Exception ex) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setHeaderText("Error Occured during Funding");
                    a.setContentText("There was an error occured while funding\n" +
                            "The project please check balance or funding amount(should be a whole number)\n" +
                            "and fund again.");
                    a.showAndWait();
                }
        }
//        Stage settingsWindow = new Stage();
//        settingsWindow.initModality(Modality.APPLICATION_MODAL);
//        settingsWindow.initStyle(StageStyle.TRANSPARENT);
//        try {
//            Parent settingsRoot = FXMLLoader.load(getClass().getResource("../../resources/")); // TODO: to be created
//            Scene sc = new Scene(settingsRoot);
//            sc.setFill(Color.TRANSPARENT);
//            settingsWindow.setScene(sc);
//            settingsWindow.showAndWait();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public void onUndertakeProjectButtonClicked(){

//        Stage settingsWindow = new Stage();
//        settingsWindow.initModality(Modality.APPLICATION_MODAL);
//        settingsWindow.initStyle(StageStyle.TRANSPARENT);
//        try {
//            Parent settingsRoot = FXMLLoader.load(getClass().getResource("../../resources/UndertakeProjectConfirmBox.fxml")); //
//            Scene sc = new Scene(settingsRoot);
//            sc.setFill(Color.TRANSPARENT);
//            settingsWindow.setScene(sc);
//            settingsWindow.showAndWait();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../resources/UndertakeProjectConfirmBox.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UndertakeProjectConfirmController obj = loader.getController();
        obj.setId(projectId);

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
        window.show();
    }

    public void onProjectIdClicked(){
        //LoginController.showAlert("Project Details", "Project #"+projectId,"Allocated Funds: "+allocatedFunds+"\nTarget Amount: "+targetAmount);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Project Details");
        a.setContentText("Allocated Funds: "+allocatedFunds+"\nTarget Amount: "+targetAmount);
        a.setHeaderText("Project #"+projectId);
        a.showAndWait();
    }

    public void onAllotedFundsClicked(){
        //LoginController.showAlert("Allocated Funds","Project : #"+projectId,"Funds Allocated: "+allocatedFunds.toString());
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Project Details");
        a.setContentText("Allocated Funds: "+allocatedFunds+"\nTarget Amount: "+targetAmount);
        a.setHeaderText("Project #"+projectId);
        a.showAndWait();
    }

    public void onProjectStatisticsClicked(){

        try {
            Stage window = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/FundingGraph.fxml"));
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("../../resources/FundingGraph.fxml"));
//        Parent root = null;
//        try {
//            root = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Stage win= new Stage();
//        win.initModality(Modality.APPLICATION_MODAL);
//        win.initStyle(StageStyle.TRANSPARENT);
//        Scene scene = new Scene(root);
//        scene.setFill(Color.TRANSPARENT);
//        win.setScene(scene);
//        win.show();
    }
}




class FundProject extends Thread{
    int projectID, amount;
    FundProject(int _projectId, int _amount){
        this.projectID = _projectId;
        this.amount = _amount;
    }

    public void run(){
        try{
            TransactionReceipt receipt = Main.contractCw.addFunds("0xe470a002afbd470488fa4dc8ccf8089878b8b683",BigInteger.valueOf(projectID), BigInteger.valueOf(amount)).send();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}



//class ProjectDetails extends Thread{
//
//    BigInteger projectId;
//
//    ProjectDetails(BigInteger _projectId){
//        projectId = _projectId;
//    }
//
//    public void run(){
//        try {
//            Web3j web3j = Main.web3j;
//            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
//            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
//            System.out.println(clientVersion);
//
//            Credentials credentials = WalletUtils.loadCredentials("123456789","/home/aks/.ethereum/testnet/keystore/UTC--2018-01-22T10-02-54.636000000Z--e470a002afbd470488fa4dc8ccf8089878b8b683.json"/*LoginController.password, Main.walletFileAddress*/);
//            System.out.println("Credentials: "+credentials.toString());
//
//            CommunityWork contract = CommunityWork.load("0x3a974d6c802bbb0d388783c291c5da19bc335dd3", web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
//
//            BigInteger projectID=projectId;
//            //TransactionReceipt transactionReceipt =
//            //System.out.println("TransactionReceipt: "+transactionReceipt.toString());
//            Tuple3<Boolean, Boolean, Boolean> result1 = contract.getProjectStatus(projectID).send();
//            Tuple3<BigInteger, BigInteger, BigInteger> result2 = contract.getProjectValues(projectID).send();
//            Tuple2<String, String> result3 = contract.getProjectInfo(projectID).send();
//
////            System.out.println("Allocated Fund "+result.getValue1());
////            System.out.println("Target Amount "+result.getValue2());
////            System.out.println("Funding Duration "+result.getValue3());
//            ProjectViewController.isAccepted = result1.getValue1();
//            ProjectViewController.isCompleted = result1.getValue2();
//            ProjectViewController.isOpen = result1.getValue3();
//            ProjectViewController.allocatedFunds = result2.getValue1();
//            ProjectViewController.targetAmount = result2.getValue2();
//            ProjectViewController.fundingDuration = result2.getValue3();
//            ProjectViewController.projectDescription = result3.getValue1();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//}