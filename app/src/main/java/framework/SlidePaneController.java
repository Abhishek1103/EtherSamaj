package framework;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    Web3j web3j = Main.web3j;

    @FXML
    JFXButton project1, project2, project3, project4, project5;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void updateProjectsView(){
        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            System.out.println(clientVersion);

            Credentials credentials = WalletUtils.loadCredentials("123456789","/home/aks/.ethereum/testnet/keystore/UTC--2018-01-22T10-02-54.636000000Z--e470a002afbd470488fa4dc8ccf8089878b8b683.json"/*LoginController.password, Main.walletFileAddress*/);
            System.out.println("Credentials: "+credentials.toString());

            CommunityWork contract = CommunityWork.load(
                    "0x3a974d6c802bbb0d388783c291c5da19bc335dd3", web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

            BigInteger projectID=BigInteger.valueOf(10);
            TransactionReceipt transactionReceipt = contract.projectDeployer("0xe470a002afbd470488fa4dc8ccf8089878b8b683",BigInteger.ONE, "This is sample project 1",BigInteger.valueOf(2L),BigInteger.valueOf(60),projectID,BigInteger.ZERO).send();
            System.out.println("TransactionReceipt: "+transactionReceipt.toString());
            Tuple3<BigInteger, BigInteger, BigInteger> result = contract.getProjectValues(projectID).send();
            System.out.println("Allocated Fund "+result.getValue1());
            System.out.println("Target Amount "+result.getValue2());
            System.out.println("Funding Duration "+result.getValue3());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}


class UpdateLabels extends Thread{
    JFXButton project1, project2, project3, project4, project5;
    Web3j web3j = Main.web3j;
    UpdateLabels(JFXButton _project1, JFXButton _project2, JFXButton _project3, JFXButton _project4, JFXButton _project5){
        this.project1 = _project1;
        this.project2 = _project2;
        this.project3 = _project3;
        this.project4 = _project4;
        this.project5 = _project5;
    }

    public void run(){

        Web3ClientVersion web3ClientVersion = null;
        try {
            web3ClientVersion = web3j.web3ClientVersion().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println(clientVersion);

        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials("123456789","/home/aks/.ethereum/testnet/keystore/UTC--2018-01-22T10-02-54.636000000Z--e470a002afbd470488fa4dc8ccf8089878b8b683.json"/*LoginController.password, Main.walletFileAddress*/);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        System.out.println("Credentials: "+credentials.toString());

        CommunityWork contract = CommunityWork.load(
                "0x3a974d6c802bbb0d388783c291c5da19bc335dd3", web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);


        Platform.runLater(new Runnable() {
            @Override
            public void run() {


                for(int i=10;i<=50;i++){
                    BigInteger projectID=BigInteger.valueOf(i);
                    TransactionReceipt transactionReceipt = null;
                    try {
                        transactionReceipt = contract.projectDeployer("0xe470a002afbd470488fa4dc8ccf8089878b8b683", BigInteger.ONE, "This is sample project 1",BigInteger.valueOf(2L),BigInteger.valueOf(60),projectID,BigInteger.ZERO).send();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("TransactionReceipt: "+transactionReceipt.toString());
                    Tuple3<BigInteger, BigInteger, BigInteger> result = null;
                    try {
                        result = contract.getProjectValues(projectID).send();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("Allocated Fund "+result.getValue1());
                    System.out.println("Target Amount "+result.getValue2());
                    System.out.println("Funding Duration "+result.getValue3());
                    project1.setText("Project : #");
                }
            }
        });
    }
}