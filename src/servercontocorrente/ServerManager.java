/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servercontocorrente;

import java.io.BufferedReader;
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
public class ServerManager implements Runnable{
    private Socket s;
    private String conti;

    public ServerManager(Socket s, String conti) {
        this.s = s;
        this.conti = conti;
    }
    
    
    @Override
    public void run() {
        String nConto = null;
        boolean logged = false;
        try {
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            JSONObject response = new JSONObject();
            
            while(true) {
                JSONObject input = new JSONObject(in.readLine());
                BufferedReader file = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\bank_accounts.txt"));
                String account = file.readLine();
                while(account != null) {
                    if(account.split(";")[1].equals(input.getString("email")) && account.split(";")[2].equals(input.getString("password"))){
                        nConto = account.split(";")[0];
                        logged = true;
                    }
                    account = file.readLine();
                }
                file.close();
                if(logged) {
                    response.put("response", "go");
                    break;
                }else
                    response.put("response", "nogo");
                
                pw.println(response.toString());
            }
            
            pw.println(response.toString());
            
            ServerInstance.cm.addClient(s, nConto);
            
        } catch (IOException ex) {
            Logger.getLogger(ServerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
