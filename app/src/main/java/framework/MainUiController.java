package framework;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class MainUiController extends Thread implements Initializable  {

    @FXML
    AnchorPane mainAnchor;
    @FXML
    JFXButton newsitem1, settingsButton, aboutUsButton;
    @FXML
    Label trendlbl1, trendlbl2, trendlbl3, exitLabel, nameLabel, publicKeyLabel, etherBalanceLabel;
    @FXML
    Label bitcoinDollar, bitcoinRup, ethDollar, ethRup, dashDollar, dashRup;
    JFXPopup popup;
    @FXML
    JFXDrawer drawer;
    @FXML
    Label timeLabel, balancelbl;
    Stage window;

    static CoinSetter cs;
    static BalanceSetter bs;

    Web3j web3j;

    final double[] xoffset = new double[1];  // This is because variables used in lambda expression should
    final double[] yoffset = new double[1];  // final or effectively final. And IDE suggested this method
    int count=0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initPopup();
        drawer.close();
        AnchorPane ap = null;
        try {
            ap = FXMLLoader.load(getClass().getResource("../../resources/SlideInPane.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        drawer.setSidePane(ap);

        drawer.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            drawer.open();
        });

        drawer.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            drawer.close();
        });

        mainAnchor.setOpacity(0);
        loading();

        TimeSetter ts = new TimeSetter(timeLabel);
        ts.start();


        nameLabel.setText(Main.fullName);
        publicKeyLabel.setText(Main.publicKey);
        web3j = Main.web3j;
    }

    public void initPopup(){
        JFXButton b1 = new JFXButton("task1");
        JFXButton b2 = new JFXButton("task2");
        JFXButton b3 = new JFXButton("task3");
        b1.setPadding(new Insets(10));
        b2.setPadding(new Insets(10));
        b3.setPadding(new Insets(10));
        VBox vbox = new VBox(b1, b2, b3);
        popup = new JFXPopup(vbox);

    }

    @FXML
    public void trendMouseEntered(MouseEvent evt){
        Label l = (Label)evt.getSource();
        l.setScaleX(2);
        l.setScaleY(2);
    }

    @FXML
    public void trendMouseExited(MouseEvent evt){
        Label l = (Label)evt.getSource();
        l.setScaleX(1);
        l.setScaleY(1);
    }

    @FXML
    public void trendMouseClicked(MouseEvent evt) {
        if (evt.getButton() == MouseButton.SECONDARY) {
            Label l = (Label) evt.getSource();
            popup.show(l, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, evt.getX(), evt.getY());
        }
    }

    @FXML
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
    public void coinSetter(){
        cs = new CoinSetter(bitcoinDollar, bitcoinRup, ethDollar, ethRup, dashDollar, dashRup);
        cs.setDaemon(true);
        cs.start();
    }

    @FXML
    public void onExit(MouseEvent evt){
        System.exit(0);
    }

    public  void loading()
    {
        FadeTransition fadeIn=new FadeTransition();
        fadeIn.setDuration(Duration.seconds(1.0));
        fadeIn.setNode(mainAnchor);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public void updateBalance(){
        // TODO: code to get balance
    }

    public void onSettingsClicked() {
        Stage settingsWindow = new Stage();
        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.initStyle(StageStyle.TRANSPARENT);
        try {
            Parent settingsRoot = FXMLLoader.load(getClass().getResource("../../resources/SettingsUI.fxml"));
            Scene sc = new Scene(settingsRoot);
            sc.setFill(Color.TRANSPARENT);
            settingsWindow.setScene(sc);
            settingsWindow.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onAboutusClicked(){
        Stage settingsWindow = new Stage();
        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.initStyle(StageStyle.TRANSPARENT);
        try {
            Parent aboutUsRoot = FXMLLoader.load(getClass().getResource("../../resources/AboutUs.fxml"));
            Scene sc = new Scene(aboutUsRoot);
            sc.setFill(Color.TRANSPARENT);
            settingsWindow.setScene(sc);
            settingsWindow.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getBalance(MouseEvent evt) {
        bs = new BalanceSetter(etherBalanceLabel);
        bs.setDaemon(true);
        bs.start();
//
//        TimeSetter ts = new TimeSetter(timeLabel);
//        ts.start();


//        MainUiController mic = new MainUiController();
//        mic.start();
    }

    public void onMinimize(MouseEvent evt){
        ((Stage)((Label)evt.getSource()).getScene().getWindow()).setIconified(true);
    }


//    public void run(){
//
//        try {
//            String str, bitcoin, eth, dash;
//
//            ProcessBuilder pb = new ProcessBuilder("python","/home/aks/Documents/Hack36/src/main/resources/CoinRate.py");
//            Process p = pb.start();
//
//            //Process p1 = Runtime.getRuntime().exec("python /home/aks/Documents/Hack36/src/main/resources/CoinRate.py");
//            BufferedReader stdInput1 = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            Double con=0.0;
//            try {
//                con = Double.parseDouble((str = stdInput1.readLine()));
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//
//            try {
//                bitcoin = (stdInput1.readLine()).substring(1);
//                eth = (stdInput1.readLine()).substring(1);
//                dash = (stdInput1.readLine()).substring(1);
//                bitcoinDollar.setText(bitcoin);
//                ethDollar.setText(eth);
//                dashDollar.setText(dash);
//
//                bitcoinRup.setText(""+Double.parseDouble(bitcoin)*con);
//                ethRup.setText(""+Double.parseDouble(eth)*con);
//                dashRup.setText(""+Double.parseDouble(dash)*con);
//
//            }catch (Exception e){
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public void web3Function(){
        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            System.out.println(clientVersion);

            Credentials credentials = WalletUtils.loadCredentials(LoginController.password, Main.walletFileAddress);
            System.out.println("Credentials: "+credentials.toString());

            TransactionReceipt transactionReceipt =

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}




// Class for Setting Time
class TimeSetter extends Thread{
    Label label;
    TimeSetter(Label _label){
        this.label = _label;
    }

    public void run(){
        while(true) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    Date date = new Date();
                    //System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43

                    try {
                        label.setText("" + date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


// Class for setting the coin rates
class CoinSetter extends Thread{
    Label bitcoinDollar, bitcoinRup, ethDollar, ethRup, dashDollar, dashRup;
    CoinSetter(Label _bitcoinDollar, Label _bitcoinRup, Label _ethDollar, Label _ethRup, Label _DashDollar, Label _dashRup ){
        this.bitcoinDollar = _bitcoinDollar;
        this.bitcoinRup = _bitcoinRup;
        this.ethDollar = _ethDollar;
        this.ethRup = _ethRup;
        this.dashDollar = _DashDollar;
        this.dashRup = _dashRup;
        System.out.println("Constructer of Coin Setter executed");
    }

    public void run(){
        while(true) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {
                        String str, bitcoin, eth, dash;

//                        ProcessBuilder pb = new ProcessBuilder("python","/home/aks/Documents/Hack36/src/main/resources/CoinRate.py");
//                        Process p = pb.start();

                        Process p1 = Runtime.getRuntime().exec("python /home/aks/Documents/Hack36/src/main/resources/CoinRate.py");
                        BufferedReader stdInput1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                        Double con=0.0;
                        try {
                            con = Double.parseDouble((str = stdInput1.readLine()));
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                        try {
                            bitcoin = (stdInput1.readLine()).substring(1);
                            eth = (stdInput1.readLine()).substring(1);
                            dash = (stdInput1.readLine()).substring(1);
                            bitcoinDollar.setText(bitcoin);
                            ethDollar.setText(eth);
                            dashDollar.setText(dash);

                            bitcoinRup.setText(""+Double.parseDouble(bitcoin)*con);
                            ethRup.setText(""+Double.parseDouble(eth)*con);
                            dashRup.setText(""+Double.parseDouble(dash)*con);

                        }catch (Exception e){

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class BalanceSetter extends Thread{
    Label balanceLabel;
    public BalanceSetter(Label _balanceLabel){
        this.balanceLabel = _balanceLabel;
    }

    public void run(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String str;
                    Process p1 = Runtime.getRuntime().exec("python /home/aks/Documents/Hack36/src/main/resources/Balance.py "+"0xE470A002AFBD470488FA4dc8cCF8089878b8b683");
                    BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()));

                    try {
                            str = br.readLine();
                            str = br.readLine();
                        //System.out.println(str);
                            str=str.substring(0,str.lastIndexOf("E")).trim();
                        //System.out.println(str);
                            balanceLabel.setText(str);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}


/*

            StringWriter writer = new StringWriter(); //ouput will be stored here

            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptContext context = new SimpleScriptContext();

            context.setWriter(writer); //configures output redirection
            ScriptEngine engine = manager.getEngineByName("python");
            engine.eval(new FileReader("/home/aks/Desktop/test.py"), context);


 */