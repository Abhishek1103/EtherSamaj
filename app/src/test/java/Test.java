import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;


public class Test {
    static String[] webArray = new String[1];
    public static void main(String[] args){
//        webArray[0]="https://www.coindesk.com/vitalik-first-part-ethereums-sharding-roadmap-nearly-done/";
//        try {
//            Desktop.getDesktop().browse(new URI(""+webArray[0]));
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        } catch (URISyntaxException e1) {
//            e1.printStackTrace();
//        }
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter the Details");
        dialog.setHeaderText("Enter Account Address");
        dialog.setContentText("Enter public key of the Account");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent())
        {
            //TODO: add user address
            try {
//                    int amount = Integer.parseInt(result.get());
//                    TransactionReceipt receipt = Main.contractCw.addFunds("0xe470a002afbd470488fa4dc8ccf8089878b8b683", BigInteger.valueOf(projectId), BigInteger.valueOf(amount)).send();
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

    }
}
