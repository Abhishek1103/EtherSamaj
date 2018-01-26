import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringWriter;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.modules._jythonlib.*;
import org.python.util.PythonInterpreter;

public class Test {

    public static void main(String[] args){
        try{
//            StringWriter writer = new StringWriter(); //ouput will be stored here
//
//            ScriptEngineManager manager = new ScriptEngineManager();
//            ScriptContext context = new SimpleScriptContext();
//
//            context.setWriter(writer); //configures output redirection
//            ScriptEngine engine = manager.getEngineByName("python3");
//            engine.eval(new FileReader("/home/aks/Documents/Hack36/src/main/resources/CoinRate.py"), context);
//            System.out.println(writer.toString());

            PythonInterpreter python = new PythonInterpreter();

            int number1 = 10;
            int number2 = 32;
            python.set("number1", new PyInteger(number1));
            python.set("number2", new PyInteger(number2));
            BufferedReader br = new BufferedReader(new FileReader("/home/aks/Documents/Hack36/src/main/resources/CoinRate.py"));
            String str;
            while((str=br.readLine())!=null)
            python.exec(str);
            //PyObject number3 = python.get("number3");
            //System.out.println("val : "+number3.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
