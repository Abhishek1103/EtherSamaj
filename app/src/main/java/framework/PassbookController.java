package framework;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class PassbookController implements Initializable{

    @FXML public WebView webpage;
    @FXML public Button refreshTran;
    public WebEngine engine;

    public final String webLink = "https://rinkeby.etherscan.io/address/";
    public static String id = "0x83A0f349986d38cAfF6639a2df3760FA19e531A9";




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

}
