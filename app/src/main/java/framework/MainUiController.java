package framework;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Bytes8;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.Tuple;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainUiController extends Thread implements Initializable  {

    public String getBitcoinPrice() {
        return bitcoinPrice.get();
    }

    public StringProperty bitcoinPriceProperty() {
        return bitcoinPrice;
    }

    public void setBitcoinPrice(String bitcoinPrice) {
        this.bitcoinPrice.set(bitcoinPrice);
    }

    StringProperty bitcoinPrice = new SimpleStringProperty(this,"bitcoinPrice", "11178.50");

    public String getEthPrice() {
        return ethPrice.get();
    }

    public StringProperty ethPriceProperty() {
        return ethPrice;
    }

    public void setEthPrice(String ethPrice) {
        this.ethPrice.set(ethPrice);
    }

    StringProperty ethPrice = new SimpleStringProperty(this,"ethPrice","7056.48");

    public String getDashPrice() {
        return dashPrice.get();
    }

    public StringProperty dashPriceProperty() {
        return dashPrice;
    }

    public void setDashPrice(String dashPrice) {
        this.dashPrice.set(dashPrice);
    }

    StringProperty dashPrice = new SimpleStringProperty(this,"dashPrice","756.32");
    StringProperty b1 = new SimpleStringProperty();
    StringProperty e1 = new SimpleStringProperty();
    StringProperty d1 = new SimpleStringProperty();
    StringProperty exchange = new SimpleStringProperty();
    String balanceFromBalanceSetter;
    @FXML
    AnchorPane mainAnchor;
    @FXML
    JFXButton newsitem1, settingsButton, aboutUsButton, commWorkButton, commFundButton, medicalFundButton;
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
    @FXML
    ImageView newsIcon;

    @FXML Label newsLabel1, newsLabel2, newsLabel3, newsLabel4;

    String[] webArray = new String[4];
    int a[], b[];

    static  List<Pair<Integer, Integer> > trendingList;

    static BalanceSetter bs;

    Service<Void> bkgThread, contractThread, balanceThread, cwThread, coinThread;

    //Web3j web3j;
    //static Web3ClientVersion web3ClientVersion;
    //static Credentials credentials;
    //static CommunityWork contractCW, contractCF, contractMF;

    final double[] xoffset = new double[1];  // This is because variables used in lambda expression should
    final double[] yoffset = new double[1];  // final or effectively final. And IDE suggested this method
    int count=0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        b1.bind(bitcoinPrice);
        e1.bind(ethPrice);
        d1.bind(dashPrice);
        System.out.println("Binding Done");

        initPopup();
        drawer.close();
        AnchorPane ap = null;
        try {
            ap = FXMLLoader.load(getClass().getResource("../../resources/SlideInPane.fxml"));
            System.out.println("SlidePane accessed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        drawer.setSidePane(ap);

        drawer.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            drawer.open();

            try{

            }catch(Exception ex){

            }
        });

        drawer.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            drawer.close();
        });

        mainAnchor.setOpacity(0);
        loading();

        TimeSetter ts = new TimeSetter(timeLabel);
        ts.start();
        System.out.println("Time Set");


        nameLabel.setText(Main.fullName);
        publicKeyLabel.setText(Main.publicKey);
        //Main.web3j = Main.web3j;

        //web3Function();

//        NewsLoader nl = new NewsLoader();
//        nl.start();

        try{
            System.out.println("going to load news");
            loadNews();
            System.out.println("News Done");
            web3Function();
            System.out.println("news and web3 finished");
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public void web3Function(){
        contractThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try{
                            Main.web3j = Web3j.build(new HttpService("https://rinkeby.infura.io/Nb9v0iQy5LYKUBBYu3Hp"));
                            Main.web3ClientVersion = Main.web3j.web3ClientVersion().send();
                            String clientVersion = Main.web3ClientVersion.getWeb3ClientVersion();
                            System.out.println(clientVersion);

                           Main.credentials = WalletUtils.loadCredentials("123456789","/home/aks/.ethereum/testnet/keystore/UTC--2018-01-22T10-02-54.636000000Z--e470a002afbd470488fa4dc8ccf8089878b8b683.json"/*LoginController.password, Main.walletFileAddress*/);
                            System.out.println("Credentials: "+Main.credentials.toString());
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

        contractThread.restart();

        try {




//            BigInteger projectID=BigInteger.valueOf(10L);
//            TransactionReceipt transactionReceipt = contract.projectDeployer("0xe470a002afbd470488fa4dc8ccf8089878b8b683",BigInteger.ONE, "This is sample project 1",BigInteger.valueOf(2L),BigInteger.valueOf(60),projectID,BigInteger.ZERO).send();
//            System.out.println("TransactionReceipt: "+transactionReceipt.toString());
//            Tuple3<BigInteger, BigInteger, BigInteger> result = contract.getProjectValues(projectID).send();
//            System.out.println("Allocated Fund "+result.getValue1());
//            System.out.println("Target Amount "+result.getValue2());
//            System.out.println("Funding Duration "+result.getValue3());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public  void commWorkClicked()
    {
        cwThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try{
                            Main.contractCw=CommunityWork.load(
                                    Main.contractAddress, Main.web3j, Main.credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
                            BigInteger totalProjects = Main.contractCw.getProjectIdLength().send();
                            System.out.println("Total leng"+ totalProjects);
                            a =new int[totalProjects.intValue()];
                            for (int i = 0; i < totalProjects.intValue(); i++) {
                                a[i] = (Main.contractCw.getAllProjectId(BigInteger.valueOf(i)).send()).intValue();
                                System.out.println(i+"->"+a[i]);
                            }
//                            for(int i=0;i<totalProjects.intValue();i++){
//                                Tuple3<BigInteger, BigInteger, BigInteger> result = Main.contractCw.getProjectValues(BigInteger.valueOf(a[i])).send();
//                                //trending=new Tuple<>((result.getValue1()).intValue(), a[i]);
//                               trendingList = new ArrayList<>();
//                                Pair<Integer, Integer> pair = new Pair<>((result.getValue1()).intValue(), a[i]);
//                                trendingList.add(pair);
//                            }
//                            //Arrays.sort();
//
//
//
//                            Comparator<Pair<Integer, Integer>> comparator = new Comparator<Pair<Integer, Integer>>(){
//                                public int compare(Pair<Integer, Integer> pairA,
//                                                   Pair<Integer, Integer> pairB)
//                                {
//                                    return pairA.getKey().compareTo(pairB.getKey());
//                                }
//                            };

                            for(int i=0;i<totalProjects.intValue();i++){
                                Tuple3<BigInteger, BigInteger, BigInteger> result = Main.contractCw.getProjectValues(BigInteger.valueOf(a[i])).send();
                                b[i]=result.getValue1().intValue();
                            }

                            for(int i=0;i<totalProjects.intValue();i++)
                            {
                                for(int j=0;j<totalProjects.intValue()-i-1;i++)
                                {
                                    if(b[j]<b[j+1])
                                    {
                                        int temp = b[j];
                                        b[j]=b[j+1];
                                        b[j+1]=temp;

                                        int temp2 = a[j];
                                        a[j]=a[j+1];
                                        a[j+1]=temp2;
                                    }
                                }
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

        cwThread.setOnSucceeded(e->{
//            SlidePaneController obj=new SlidePaneController();
//            obj.updateProjectsView();
//            Comparator<Pair<Integer, Integer>> comparator = new Comparator<Pair<Integer, Integer>>(){
//                public int compare(Pair<Integer, Integer> pairA,
//                                   Pair<Integer, Integer> pairB)
//                {
//                    return pairA.getKey().compareTo(pairB.getKey());
//                }
//            };
            //Collections.sort(trendingList, comparator);
            trendlbl1.setText("Project #"+a[0]);


            SlidePaneController.updateProjectsView();
        });

        cwThread.restart();
    }

    @FXML
    public void commFundClicked(){

    }

    @FXML
    public void medicalFundClicked(){

    }

    @FXML
    public void onNewTaskClicked(){
        Stage newTaskWindow = new Stage();
        newTaskWindow.initModality(Modality.APPLICATION_MODAL);
        newTaskWindow.initStyle(StageStyle.TRANSPARENT);
        try {
            Parent newTaskRoot = FXMLLoader.load(getClass().getResource("../../resources/NewTask.fxml"));
            Scene sc = new Scene(newTaskRoot);
            sc.setFill(Color.TRANSPARENT);
            newTaskWindow.setScene(sc);
            newTaskWindow.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
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


    @FXML public void newsLabel1Entered(MouseEvent event) {
        newsLabel1.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-background-color: #808080;");
    }
    @FXML public void newsLabel2Entered(MouseEvent event) {
        newsLabel2.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-background-color: #808080;");
    }
    @FXML public void newsLabel3Entered(MouseEvent event) {
        newsLabel3.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-background-color: #808080;");
    }
    @FXML public void newsLabel4Entered(MouseEvent event) {
        newsLabel4.setStyle("-fx-font-weight: bold; -fx-font-style: italic; -fx-background-color: #808080;");
    }

    @FXML public void newsLabel1Clicked(MouseEvent event) {
        try {
            System.out.println(webArray[0]);
           // Desktop.getDesktop().browse(new URI(""+"http://www.google.com"));
            OpenBrowser ob = new OpenBrowser(""+webArray[0]);
            ob.start();
            System.out.println("sjdfkhsjkf");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    @FXML public void newsLabel2Clicked(MouseEvent event) {
        try {
            OpenBrowser ob = new OpenBrowser(""+webArray[1]);
            ob.start();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    @FXML public void newsLabel3Clicked(MouseEvent event) {
        try {
            OpenBrowser ob = new OpenBrowser(""+webArray[2]);
            ob.start();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    @FXML public void newsLabel4Clicked(MouseEvent event) {
        try {
            OpenBrowser ob = new OpenBrowser(""+webArray[3]);
            ob.start();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @FXML public void newsLabel1Exited(MouseEvent event) {
        newsLabel1.setStyle("-fx-background-color: WHITE;");
    }
    @FXML public void newsLabel2Exited(MouseEvent event) {
        newsLabel2.setStyle("-fx-background-color: WHITE;");
    }
    @FXML public void newsLabel3Exited(MouseEvent event) {
        newsLabel3.setStyle("-fx-background-color: WHITE;");
    }
    @FXML public void newsLabel4Exited(MouseEvent event) {
        newsLabel4.setStyle("-fx-background-color: WHITE;");
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
        coinThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try{
                            String str, bitcoin, eth, dash;

//                        ProcessBuilder pb = new ProcessBuilder("python","/home/aks/Documents/Hack36/src/main/resources/CoinRate.py");
//                        Process p = pb.start();

                            Process p1 = Runtime.getRuntime().exec("python /home/aks/Documents/Hack36/src/main/resources/CoinRate.py");
                            BufferedReader stdInput1 = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                            Double con=0.0;
                            try {
                                System.out.println("Con:"+con);
                                str = stdInput1.readLine();
                                con = Double.parseDouble(str);
                                System.out.println("Con parsed");
                            }catch(Exception e){
                                e.printStackTrace();
                                con = 62.50;
                            }

                            try {
                                bitcoin = (stdInput1.readLine()).substring(1);
                                eth = (stdInput1.readLine()).substring(1);
                                dash = (stdInput1.readLine()).substring(1);
                                bitcoinPrice.setValue(bitcoin);
                                ethPrice.setValue(eth);
                                dashPrice.setValue(dash);
                                exchange.setValue(con.toString());

                            }catch (Exception e){

                            }
                        }catch(Exception e){

                        }
                        return null;
                    }
                };
            }
        };

        coinThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                bitcoinDollar.setText(b1.getValue());
                bitcoinRup.setText(""+(Double.parseDouble(b1.getValue()))*Double.parseDouble(exchange.getValue()));
                ethDollar.setText(e1.getValue());
                ethRup.setText(""+(Double.parseDouble(e1.getValue()))*Double.parseDouble(exchange.getValue()));
                dashDollar.setText(d1.getValue());
                dashRup.setText(""+(Double.parseDouble(d1.getValue()))*Double.parseDouble(exchange.getValue()));
            }
        });
        coinThread.restart();
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

    public void loadNews(){
        // TODO: Load news
        System.out.println("In news Method");
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", "/home/aks/Documents/Hack36/src/main/resources/CryptocurrencyNews.py");
            Process process = processBuilder.start();

            //Process process = Runtime.getRuntime().exec("python /home/aks/Documents/Hack36/src/main/resources/CryptocurrencyNews.py");

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String S;
            int i = 0;
            while((S = reader.readLine()) != null && i != 8)
            {
 //               webArray[i] = S;//.substring(S.indexOf("http"),S.indexOf("/'")+1);

                if(i%2 == 0) {
                    if(i/2 == 0) {
                        newsLabel1.setText(S);

                    }
                    else if(i/2 == 1) {
                        newsLabel2.setText(S);
                    }
                    else if(i/2 == 2) {
                        newsLabel3.setText(S);
                    }
                    else if(i/2 == 3) {
                        newsLabel4.setText(S);
                    }
                }
                else {

                    webArray[i/2] = S.trim();
                }

                i++;
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void onPassBookClicked(){
        Stage settingsWindow = new Stage();
        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.initStyle(StageStyle.TRANSPARENT);
        try {
            Parent settingsRoot = FXMLLoader.load(getClass().getResource("../../resources/PassbookView.fxml"));
            Scene sc = new Scene(settingsRoot);
            sc.setFill(Color.TRANSPARENT);
            settingsWindow.setScene(sc);
            settingsWindow.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
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
//        bs = new BalanceSetter(etherBalanceLabel);
//        bs.setDaemon(true);
//        bs.start();
        System.out.println("Fetching balance");


        // TODO: Start service

       balanceThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try{
                            String str;                                                                         // TODO: replace with userPublic key
                            Process p1 = Runtime.getRuntime().exec("python /home/aks/Documents/Hack36/src/main/resources/Balance.py "+"0xE470A002AFBD470488FA4dc8cCF8089878b8b683");
                            BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()));

                            try {
                                str = br.readLine();
                                str = br.readLine();
                                //System.out.println(str);
                                str=str.substring(0,str.lastIndexOf("E")).trim();
                                //System.out.println(str);
                                balanceFromBalanceSetter = str;

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
            }
        };

        balanceThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                etherBalanceLabel.setText(balanceFromBalanceSetter);
            }
        });
        balanceThread.restart();



    }

    public void onMinimize(MouseEvent evt) {
        ((Stage) ((Label) evt.getSource()).getScene().getWindow()).setIconified(true);
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

// For loading news
class NewsLoader extends Thread{
    public void run(){
        MainUiController mui = new MainUiController();
        mui.loadNews();
    }
}


class OpenBrowser extends Thread{

    String path;
    OpenBrowser(String path){
        this.path = path;
    }

    public void browser() {
        try {
            Desktop.getDesktop().browse(new URI(""+path));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        browser();
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