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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import rmiinterface_coordinador.RMIInterfaceCoordinador;

/**
 *
 * @author green
 */
public class Cliente {

    private static RMIInterfaceCoordinador look_up_coordinador;
    private static Scanner in = new Scanner (System.in);
    
    private static final int REGISTRO = 1;
    private static final int INICIAR_COMPRA = 2;
    private static final int CONSULTAR_SALDO = 3;
    private static final int INGRESAR_DINERO = 4;
    private static final int SALIR = 5;
    
    
    private static final long SALDO_INICIAL = 5000;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        // TODO code application logic here
        
        System.setProperty("java.security.policy", "./client.policy");
        
        Registry registry= LocateRegistry.getRegistry(1234);
        look_up_coordinador = (RMIInterfaceCoordinador) registry.lookup("//127.0.0.1/Coordinador");
        
        
              
        boolean mostrarOpciones = true;
        int opcion  = -1;
        while (opcion != 5){
            if (mostrarOpciones){
                System.out.println ("<<< Menú principal >>>");
                System.out.println("1. Registrarse");
                System.out.println("2. Iniciar compra");
                System.out.println("3. Consultar saldo");
                System.out.println("4. Ingresar dinero");
                System.out.println("5. Salir");
            }
            
            System.out.print("Opción: ");
            opcion = in.nextInt();
            mostrarOpciones = true;
            
            switch (opcion){
                case REGISTRO:
                    registrar();
                    break;
                case INICIAR_COMPRA:
                    break;
                case CONSULTAR_SALDO:
                    break;
                case INGRESAR_DINERO:
                    break;
                case SALIR:
                    break;
                default:
                    System.out.println ("Opcion incorrecta, inténtelo nuevamente.");
                    mostrarOpciones = false;
            }
        }
                   
    }
    
    private static void registrar() throws RemoteException{
        String contrasena;
        String nombre_usuario;        
        
        System.out.println("-------------Registro-------------\n");
        System.out.println("Ingrese el nombre de usuario:");
        nombre_usuario = in.next();
        System.out.println("Ingrese la contraseña:");
        contrasena = in.next();
        
        long numeroTarjeta = look_up_coordinador.registrarUsuarioBanco(nombre_usuario, contrasena);
        System.out.println ("Bienvenido, su nombre de usuario y contraseña ha sido registrado.");
        System.out.println ("El número de tarjeta es: "+ numeroTarjeta);
        
        System.out.println ("Recuerde que inicia con un saldo inicial de "+ SALDO_INICIAL);
        
    }
    
    
}
