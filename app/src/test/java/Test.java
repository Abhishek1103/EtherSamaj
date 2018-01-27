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



public class Test {
    static String[] webArray = new String[1];
    public static void main(String[] args){
        webArray[0]="https://www.coindesk.com/vitalik-first-part-ethereums-sharding-roadmap-nearly-done/";
        try {
            Desktop.getDesktop().browse(new URI(""+webArray[0]));
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }
}
