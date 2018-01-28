package framework;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;

public class test
{
    public static void main(String args[]) {
        //        final String authUser = "edcguest";
//        final String authPassword = "edcguest";
//        Authenticator.setDefault(
//                new Authenticator() {
//                    @Override
//                    public PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(authUser, authPassword.toCharArray());
//                    }
//                }
//        );
//        System.setProperty("http.proxyHost", "172.31.52.54");
//        System.setProperty("http.proxyPort", "3128");
//        System.setProperty("http.proxyUser", authUser);
//        System.setProperty("http.proxyPassword", authPassword);
        try {
            Web3j web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/Nb9v0iQy5LYKUBBYu3Hp"));
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            System.out.println(clientVersion);

            Credentials credentials = WalletUtils.loadCredentials("123456789", "/home/aks/.ethereum/testnet/keystore/UTC--2018-01-22T10-02-54.636000000Z--e470a002afbd470488fa4dc8ccf8089878b8b683.json");
            System.out.println("Credentials: " + credentials.toString());

            CommunityWork contract = CommunityWork.deploy(web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT, BigInteger.valueOf(0L)).send();
            String contractAddress = contract.getContractAddress();
            Mortal mortalCon = Mortal.deploy(web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT).send();
            String mortalConAddress = mortalCon.getContractAddress();
            System.out.println("contract add: "+contractAddress);
            System.out.println("contract add(mortal): "+mortalConAddress);

            //CommunityWork contract = CommunityWork.load(
              //      "0x4243d91ec3181a57ed807a0f6379bfb3530b8711", web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
//            TransactionReceipt receipt = contract.projectDeployer("0xe470a002afbd470488fa4dc8ccf8089878b8b683",BigInteger.ONE,"Sample Project 1",BigInteger.valueOf(2), BigInteger.valueOf(500),BigInteger.TEN,BigInteger.ZERO).send();
//            TransactionReceipt receipt1 = contract.projectDeployer("0xe470a002afbd470488fa4dc8ccf8089878b8b683",BigInteger.ONE,"Sample Project 2",BigInteger.valueOf(2), BigInteger.valueOf(500),BigInteger.valueOf(20),BigInteger.ONE).send();
//            TransactionReceipt receipt2 = contract.projectDeployer("0xe470a002afbd470488fa4dc8ccf8089878b8b683",BigInteger.ONE,"Sample Project 3",BigInteger.valueOf(2), BigInteger.valueOf(500),BigInteger.valueOf(30),BigInteger.ONE).send();
//            TransactionReceipt receipt3 = contract.projectDeployer("0xe470a002afbd470488fa4dc8ccf8089878b8b683",BigInteger.ONE,"Sample Project 4",BigInteger.valueOf(2), BigInteger.valueOf(500),BigInteger.valueOf(40),BigInteger.ONE).send();
//            TransactionReceipt receipt4 = contract.projectDeployer("0xe470a002afbd470488fa4dc8ccf8089878b8b683",BigInteger.ONE,"Sample Project 5",BigInteger.valueOf(2), BigInteger.valueOf(500),BigInteger.valueOf(50),BigInteger.ONE).send();
//            System.out.println(receipt);
//            System.out.println(receipt1);
//            System.out.println(receipt2);
//            System.out.println(receipt3);
//            System.out.println(receipt4);

//            TextInputDialog dialog = new TextInputDialog();
//            dialog.setTitle("Enter the Details");
//            dialog.setHeaderText("Enter Account Address");
//            dialog.setContentText("Enter public key of the Account");
//
//            Optional<String> result = dialog.showAndWait();
//            if(result.isPresent())
//            {
//                //TODO: add user address
//                try {
////                    int amount = Integer.parseInt(result.get());
////                    TransactionReceipt receipt = Main.contractCw.addFunds("0xe470a002afbd470488fa4dc8ccf8089878b8b683", BigInteger.valueOf(projectId), BigInteger.valueOf(amount)).send();
//                }catch (Exception ex) {
//                    Alert a = new Alert(Alert.AlertType.ERROR);
//                    a.setTitle("Error");
//                    a.setHeaderText("Error Occured during Funding");
//                    a.setContentText("There was an error occured while funding\n" +
//                            "The project please check balance or funding amount(should be a whole number)\n" +
//                            "and fund again.");
//                    a.showAndWait();
//                }
//            }
        }

        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
