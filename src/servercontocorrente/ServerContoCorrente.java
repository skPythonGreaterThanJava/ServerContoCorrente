/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servercontocorrente;

import java.util.Scanner;

/**
 *
 * @author Paolo
 */
public class ServerContoCorrente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Attivare");
        new ServerInstance(5000).goSever();
    }
    
}