package servercontocorrente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paolo
 */
public class ContiManager {
    private HashMap<String, ContoCorrente> lista;

    public ContiManager() {
        lista = new HashMap();
        genera();
    }
    
    private void genera(){
        try {
            BufferedReader file = new BufferedReader(new FileReader("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\bank_accounts.txt"));
            String account = file.readLine();
                while(account != null) {
                    lista.put(account.split(";")[0], new ContoCorrente(account.split(";")[0], account.split(";")[1], account.split(";")[2], Integer.parseInt(account.split(";")[3])));
                    account = file.readLine();
                }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ContiManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getListaConti(){
        String l="";
        for(String s : lista.keySet()){
            l = l +"::"+s;
        }
        return l;
    }
    
    public boolean addClient(Socket s, String nconto){
        lista.get(nconto).addClient(s);
        return true;
    }
    
    public void updateFile(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\bank_accounts.txt"));
            String write = "";
            for (ContoCorrente cc : lista.values()){
                write += cc.getId() + ";" + cc.getEmail() + ";" + cc.getPassword() + ";" + cc.getSaldo() + "\n";
            }
            System.out.println(write);
            bw.write(write);
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(ContiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
