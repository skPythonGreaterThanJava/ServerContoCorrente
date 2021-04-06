
package servercontocorrente;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Paolo
 */
public class CommunicationManager implements Runnable{
    private final ContoCorrente cc;
    private Socket s;
    //stream    
    
    public CommunicationManager(Socket s, ContoCorrente cc) {
        this.s = s;
        this.cc = cc;
    }

    
    @Override
    public void run() {
        try {
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            BufferedReader file = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\bank_accounts.txt"));
            
            while(true) {
                JSONObject input = new JSONObject(in.readLine());
                String event = input.getString("event");
                JSONObject response = new JSONObject();
                
                switch (event) {
                    
                    case "depot":
                        int amount = input.getInt("amount"); 
                        cc.versa(amount);
                        response.put("response", "go");
                        break;
                        
                    case "take":
                        amount = input.getInt("amount"); 
                        if(cc.preleva(amount)) 
                            response.put("response", "go");
                        else
                            response.put("response", "nogo");
                        break;
                    
                    case "getSaldo":
                        response.put("balance", cc.getSaldo());
                        break;
                        
                }
                pw.println(response.toString());
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CommunicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
