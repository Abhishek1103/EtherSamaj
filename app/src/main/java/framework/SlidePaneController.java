package framework;

import com.jfoenix.controls.JFXButton;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ResourceBundle;

public class SlidePaneController implements Initializable {

    //Web3j web3j = Main.web3j;
    static Service<Void> cwThread;
    @FXML
    JFXButton project1, project2, project3, project4, project5;
    @FXML
    AnchorPane slideAnchor;

    static StringProperty p1 = new SimpleStringProperty("NA");
    static StringProperty p2 = new SimpleStringProperty("NA");
    static StringProperty p3 = new SimpleStringProperty("NA");
    static StringProperty p4 = new SimpleStringProperty("NA");
    static StringProperty p5 = new SimpleStringProperty("NA");
    final static int[] a = new int[100];
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //updateProjectsView();
        int count=0;
        System.out.println(slideAnchor.getChildren());
        ObservableList ob = slideAnchor.getChildren();
        System.out.println(ob.get(1));
        project1 = (JFXButton) ob.get(1);
        project2 = (JFXButton) ob.get(2);
        project3 = (JFXButton) ob.get(3);
        project4 = (JFXButton) ob.get(4);
        project5 = (JFXButton) ob.get(5);

//        p1.addListener((v, oldV,newV)->{
//            System.out.println(newV.toString());
//            project1.setText(newV);
//        });

        project1.textProperty().bind(p1);
        project2.textProperty().bind(p2);
        project3.textProperty().bind(p3);
        project4.textProperty().bind(p4);
        project5.textProperty().bind(p5);
        if(count == 1) {
            project1.setText("Project #" + a[0]);
            project2.setText("Project #" + a[1]);
            project3.setText("Project #" + a[2]);
            project4.setText("Project #" + a[3]);
            project5.setText("Project #" + a[4]);
        }
        count++;
    }


    public static void updateProjectsView() {


        cwThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            Main.contractCw = CommunityWork.load(
                                    Main.contractAddress, Main.web3j, Main.credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
                            BigInteger totalProjects = Main.contractCw.getProjectIdLength().send();
                            System.out.println("Total leng"+ totalProjects);
                            //a =new int[totalProjects.intValue()];
                            for (int i = 0; i < totalProjects.intValue(); i++) {
                                a[i] = (Main.contractCw.getAllProjectId(BigInteger.valueOf(i)).send()).intValue();
                                System.out.println(i+"->"+a[i]);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
            }
        };

        cwThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                {
//                    System.out.println(""+a[0]);
//                    if(project1 == null) System.out.println("Null aa rha");
//                    //System.out.println(project1+"  "+project1.toString());
//                    System.out.println(slideAnchor.getChildren());
////                    project1.setText("Project #" + a[0]);
////                    project2.setText("Project #" + a[1]);
////                    project3.setText("Project #" + a[2]);
////                    project4.setText("Project #" + a[3]);
////                    project5.setText("Project #" + a[4]);
                    p1.setValue("Project #" + a[0]);
                    p2.setValue("Project #" + a[1]);
                    p3.setValue("Project #" + a[2]);
                    p4.setValue("Project #" + a[3]);
                    p5.setValue("Project #" + a[4]);
                    System.out.println(p5.getValue());
                    //initialize(null,null);
                }
            }
        });

        cwThread.restart();
    }

    @FXML
    void onProjectClicked(ActionEvent event) {
        try {

            JFXButton jb = (JFXButton)(event.getSource());
            String str = jb.getText();
            str = str.substring(str.indexOf("#")+1).trim();

            Stage window = new Stage();
//            Parent root = FXMLLoader.load(getClass().getResource("../../resources/ProjectView.fxml"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../../resources/ProjectView.fxml"));
            Parent root = loader.load();
            ProjectViewController obj = loader.getController();
            obj.setProjectId(Integer.parseInt(str));

            window.initModality(Modality.APPLICATION_MODAL);
            window.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            window.setScene(scene);
            window.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}

//class UpdateLabels extends Thread{
//    JFXButton project1, project2, project3, project4, project5;
//    Web3j web3j = Main.web3j;
//    UpdateLabels(JFXButton _project1, JFXButton _project2, JFXButton _project3, JFXButton _project4, JFXButton _project5){
//        this.project1 = _project1;
//        this.project2 = _project2;
//        this.project3 = _project3;
//        this.project4 = _project4;
//        this.project5 = _project5;
//    }
//
//    public void run(){
//
//        Web3ClientVersion web3ClientVersion = null;
//        try {
//            web3ClientVersion = web3j.web3ClientVersion().send();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
//        System.out.println(clientVersion);
//
//        Credentials credentials = null;
//        try {
//            credentials = WalletUtils.loadCredentials("123456789","/home/aks/.ethereum/testnet/keystore/UTC--2018-01-22T10-02-54.636000000Z--e470a002afbd470488fa4dc8ccf8089878b8b683.json"/*LoginController.password, Main.walletFileAddress*/);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (CipherException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Credentials: "+credentials.toString());
//
//        CommunityWork contract = CommunityWork.load(
//                "0x3a974d6c802bbb0d388783c291c5da19bc335dd3", web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
//
//
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//
//
//                for(int i=10;i<=50;i++){
//                    BigInteger projectID=BigInteger.valueOf(i);
//                    TransactionReceipt transactionReceipt = null;
//                    try {
//                        transactionReceipt = contract.projectDeployer("0xe470a002afbd470488fa4dc8ccf8089878b8b683", BigInteger.ONE, "This is sample project 1",BigInteger.valueOf(2L),BigInteger.valueOf(60),projectID,BigInteger.ZERO).send();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("TransactionReceipt: "+transactionReceipt.toString());
//                    Tuple3<BigInteger, BigInteger, BigInteger> result = null;
//                    try {
//                        result = contract.getProjectValues(projectID).send();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("Allocated Fund "+result.getValue1());
//                    System.out.println("Target Amount "+result.getValue2());
//                    System.out.println("Funding Duration "+result.getValue3());
//                    project1.setText("Project : #");
//                }
//            }
//        });
//    }
//}