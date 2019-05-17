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
import java.util.Scanner;
import rmiinterface.RMIInterfaceCoordinador;

/**
 *
 * @author green
 */
public class Cliente {

    private static RMIInterfaceCoordinador look_up_coordinador;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        // TODO code application logic here
        look_up_coordinador = (RMIInterfaceCoordinador) Naming.lookup("//127.0.0.1/Coordinador");
        
        Scanner in = new Scanner (System.in);
        System.out.println("Ingrese a:");
        int a = in.nextInt();
        System.out.println("Ingrese b:");
        int b = in.nextInt();
        int respuesta = look_up_coordinador.sumar(a, b);
        System.out.println("La respuesta es "+respuesta);
        
        
        
    }
    
    public void registro(RMIInterfaceCoordinador look_up_coordinador) throws RemoteException{
        String contrasena;
        Scanner in = new Scanner (System.in);
        System.out.println("-------------Registro-------------\n");
        System.out.println("Ingrese la contraseña:\n");
        contrasena = in.nextLine();
        look_up_coordinador.registrarUsuarioBanco(contrasena);
    }
}
