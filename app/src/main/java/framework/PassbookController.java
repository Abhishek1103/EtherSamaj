package framework;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PassbookController implements Initializable{

    @FXML public WebView webpage;
    @FXML public Button refreshTran;
    public WebEngine engine;

    public final String webLink = "https://rinkeby.etherscan.io/address/";
    public static String id = "0x83A0f349986d38cAfF6639a2df3760FA19e531A9";


    @FXML
    Label payButton;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        engine = webpage.getEngine();
        engine.load(webLink + id + "#");
        engine.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        webpage.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY || event.getButton() == MouseButton.PRIMARY) {
                    event.consume();
                }
            }
        });
        webpage.addEventFilter(KeyEvent.ANY, KeyEvent::consume);

        refreshTran.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                refreshTran.setOpacity(1);
            }
        });

        refreshTran.addEventHandler(MouseEvent.MOUSE_EXITED,new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                refreshTran.setOpacity(0.5);
            }
        });
    }

    public void payEther(){
        String publicKey="";int f1=0, f2=0;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter the Details");
        dialog.setHeaderText("Enter Account Address");
        dialog.setContentText("Enter public key of the Account");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent())
        {
            //TODO: add user address
            try {
                publicKey = result.get();
                f1=1;
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


        TextInputDialog dialog1 = new TextInputDialog();
        dialog.setTitle("Enter the Details");
        dialog.setHeaderText("Enter Account Address");
        dialog.setContentText("Enter public key of the Account");
        int amount=0;
        Optional<String> result1 = dialog.showAndWait();
        if(result.isPresent())
        {
            //TODO: add user address
            try {
                amount = Integer.parseInt(result.get());
                f2=1;
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
        if(f1==1 && f2==1){
            try {
                TransactionReceipt receipt = Transfer.sendFunds(Main.web3j,Main.credentials,publicKey, BigDecimal.valueOf(amount), Convert.Unit.WEI).send();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setTitle("Transaction Status");
                a.setHeaderText("Transaction Successful");
                a.setContentText("Receipt: https://rinkeby.etherscan.io/tx/" + receipt.getTransactionHash());
                a.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error Occured during Payment");
            a.setContentText("Check The details and try again");
            a.showAndWait();
        }

    }

}
