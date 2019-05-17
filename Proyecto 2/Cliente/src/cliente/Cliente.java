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
import rmiinterface_coordinador.RMIInterfaceCoordinador;

/**
 *
 * @author green
 */
public class Cliente {

    private static RMIInterfaceCoordinador look_up_coordinador;
    Scanner in = new Scanner (System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        // TODO code application logic here
        look_up_coordinador = (RMIInterfaceCoordinador) Naming.lookup("//127.0.0.1/Coordinador");
        
        
        
        
        
        
    }
    
    public void registro(RMIInterfaceCoordinador look_up_coordinador) throws RemoteException{
        String contrasena;
        String nombre_usuario;
        
        
        System.out.println("-------------Registro-------------\n");
        System.out.println("Ingrese el nombre de usuario:\n");
        nombre_usuario = in.nextLine();
        System.out.println("Ingrese la contraseña:\n");
        contrasena = in.nextLine();
        System.out.println ("Este es su número de tarjeta: "+ look_up_coordinador.registrarUsuarioBanco(nombre_usuario, contrasena));
    }
}
