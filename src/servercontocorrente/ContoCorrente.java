/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servercontocorrente;

import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author Paolo
 */
public class ContoCorrente {
    private String id, email, password;
    private Vector<CommunicationManager> listaClient;
    private int saldo;
    
    
    public ContoCorrente(String id, String email, String password, int saldo) {
        this.id = id;  
        this.email = email;
        this.password = password;
        this.saldo = saldo;
        listaClient = new Vector();
    }

    public void addClient(Socket s){
        listaClient.add(new CommunicationManager(s, this));
        new Thread(listaClient.elementAt(listaClient.size() - 1)).start();
    }
    
    public synchronized boolean versa(int cifra){
        saldo += cifra;
        ServerInstance.cm.updateFile();
        return true;
    }
    
    public synchronized boolean preleva(int cifra){
        if(saldo - cifra >= 0) {
            saldo -= cifra;
            ServerInstance.cm.updateFile();
            return true;
        }
        return false;
    }

    public int getSaldo() {
        return saldo;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
