/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import cliente.Cliente;
import rmiinterface.RMIInterface;

/**
 *
 * @author green
 */
public class Cliente {

    private static RMIInterface look_up;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        // TODO code application logic here
        look_up = (RMIInterface) Naming.lookup("//localhost/MyServer");
        String txt = JOptionPane.showInputDialog("What is your name?");

        String response = look_up.helloTo(txt);
        JOptionPane.showMessageDialog(null, response);
    }

}
