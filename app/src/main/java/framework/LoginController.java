package framework;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;

import java.io.*;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    AnchorPane pane1, pane2, rootPane;
    @FXML
    ImageView signInImg, signUpImg;
    @FXML
    Button signIn, signUp;
    @FXML
    JFXTextField usernameFeild, nameField, usernameField;
    @FXML
    JFXPasswordField passwordField, passwordField1;

    static String password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane1.setVisible(false);
        //TODO: set opacity to 0 in FXML
        rootPane.setOpacity(0);
        fadeInTransition();
    }

    // For Displaying Sign In Screen
    public void signInClicked(){
        if(!pane2.isVisible()) {
            pane2.setVisible(true);
            pane1.setVisible(false);
        }
    }

    // For Displaying Sign Up Screen
    public void signUpClicked(){
        if(!pane1.isVisible()) {
            pane1.setVisible(true);
            pane2.setVisible(false);
        }
    }

    public void signInSubmit(){
        String username = (usernameFeild.getText()).trim();
        password = passwordField.getText();

        if((username.isEmpty() || username.equals("")) && (password == null || password.isEmpty())){
            showAlert("Invalid Credentials", "Username and Password Empty..!!", "These are required fields\nand cannot be left blank..!!");
        }else if(username.isEmpty() || username.equals("") ){
            showAlert("Invalid Credentials", "Username Empty..!!", "This is a required field\nand cannot be left blank..!!");
        }else if(password == null || password.isEmpty() ){
            showAlert("Invalid Credentials", "Password Empty..!!", "This is a required field\nand cannot be left blank..!!");
        }else{
            //TODO: Get username from the stored file
            String fetchedUsername="", fetchedPassword="";
            try {
                FileReader fr = new FileReader("/home/aks/Documents/Hack36/src/main/resources/"+"credentials.txt");
                BufferedReader br = new BufferedReader(fr);
                Main.walletFileAddress = System.getProperty("user.home")+"/Desktop/"+br.readLine();
                Main.publicKey = br.readLine();
                Main.fullName = br.readLine();
                fetchedUsername = br.readLine();
                fetchedPassword = br.readLine();
                //System.out.println(fetchedUsername+"\n"+fetchedPassword+"\n"+password.hashCode()+"\n"+username);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(username.equals(fetchedUsername) && ((password.hashCode()))==(Integer.parseInt(fetchedPassword))){
//                Parent root = null;
//                try {
//                    root = FXMLLoader.load(getClass().getResource("../../resources/MainUI1.fxml"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Scene mainScene = new Scene(root);
//                Stage window = (Stage) rootPane.getScene().getWindow();
//                window.setScene(mainScene);
                fadeOut();

            }else{
                showAlert("Authentication Failure", "Invalid Credentials...!!", "Please enter correct details..!!");
            }
        }

        // Get Username and password from file and validate.
    }


    public void exitClicked(){
        System.exit(0);
    }

    public static  void showAlert( String title, String headerText, String contentText){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(headerText);
        a.setContentText(contentText);
        a.setResizable(false);
        a.setTitle(title);
        a.showAndWait();
    }

    private void fadeInTransition() {
        FadeTransition fdIn=new FadeTransition(Duration.seconds(2),rootPane);
        fdIn.setFromValue(0.0);
        fdIn.setToValue(1.0);
        fdIn.play();
    }

    private  void fadeOut(){
        FadeTransition fadeOut=new FadeTransition();
        fadeOut.setDuration(Duration.millis(500));
        fadeOut.setNode(rootPane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();

        fadeOut.setOnFinished(e->
        {
            Parent root = null;
            try
            {
                root = FXMLLoader.load(getClass().getResource("../../resources/MainUI1.fxml"));
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            Scene mainScene = new Scene(root);
            Stage window = (Stage) rootPane.getScene().getWindow();

            window.setScene(mainScene);

            LoadThread ld = new LoadThread();
            ld.start();

        });
    }

    public void passwordFieldClicked(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Note!");
        alert.setHeaderText("Password Requirements");
        alert.setContentText("Password length should be between\n" +
                "8 to 20 characters");
        alert.showAndWait();
    }

    public void signUpSubmit(){
        //Sign up Action performed
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField1.getText();
        int flag=1;
        if(name.isEmpty()||username.isEmpty())
        {
            flag=0;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Invalid input");
            alert.setContentText("You have not entered name or username correctly\n" +
                    "Please specify correct input.");
            alert.showAndWait();
        }
        else if(password.isEmpty() || !Qualified(password))
        {
            flag=0;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Invalid input in Password Field");
            alert.setContentText("You have not entered password correctly\n" +
                    "Please specify correct input.");
            alert.showAndWait();
        }
        if(flag==1)
        {
            try {
                String fileName = WalletUtils.generateFullNewWalletFile(password, new File(System.getProperty("user.home")+"/Desktop/"));
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("/home/aks/Documents/Hack36/src/main/resources/"+"credentials.txt")));
                pw.println(fileName);
                pw.println("0x"+fileName.substring(fileName.lastIndexOf("-")+1,fileName.lastIndexOf("-")+41));
                pw.println(name);
                pw.println(username);
                pw.println(password.hashCode());
                pw.close();
                System.out.println(fileName+"\n"+password+":"+password.hashCode());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (CipherException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FadeTransition fadeOut=new FadeTransition();
            fadeOut.setDuration(Duration.millis(500));
            fadeOut.setNode(rootPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();

            fadeOut.setOnFinished(e->
            {
                Parent root = null;
                try
                {
                    root = FXMLLoader.load(getClass().getResource("../../resources/MainUI1.fxml"));
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
                Stage curStage = (Stage) rootPane.getScene().getWindow();
                curStage.setScene(new Scene(root));
            });
        }
    }

    private boolean Qualified(String password) {
        //Function to make password at least one uppercase, one lowercase, one number and one special symbol
        if(password.length() >= 8 && password.length() <= 20)
        {
            return true;
        }
        else
            return false;
    }


}


class LoadThread extends Thread{
    public void run(){
        try{
            Stage loadingStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../../resources/LoadingView.fxml"));
            //loadingStage.initStyle(StageStyle.TRANSPARENT);
            Scene loadingScene = new Scene(root);
            //loadingScene.setFill(Color.TRANSPARENT);
            loadingStage.setScene(loadingScene);
            loadingStage.show();
            sleep(3000);
            loadingStage.close();
        }catch(Exception e){

        }
    }
}