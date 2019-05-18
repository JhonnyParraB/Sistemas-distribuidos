/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinador;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import rmiinterface_banco.RMIInterfaceBanco;
import rmiinterface_coordinador.RMIInterfaceCoordinador;
import clasesrmi.Producto;
import rmiinterface_servidor.RMIInterfaceServidor;

public class Coordinador extends UnicastRemoteObject implements RMIInterfaceCoordinador{

    private static final long serialVersionUID = 1L;
    private static RMIInterfaceBanco look_up_banco;
    private static RMIInterfaceServidor look_up_servidor_alimentos;
    private static RMIInterfaceServidor look_up_servidor_aseo;

    protected Coordinador() throws RemoteException {

        super();

    }

    @Override
    public int sumar(int a, int b) throws RemoteException{

        System.err.println("Haciendo suma");
        return a+b;
    }
    
    @Override
    public long registrarUsuarioBanco(String nombre_usuario, String contrasena) throws RemoteException{

        System.out.println("Registrando un usuario ...");
        return look_up_banco.registrarUsuario(nombre_usuario, contrasena);
    }
    
    

    public static void main(String[] args){

        try {
            
            Registry registryClientes = LocateRegistry.createRegistry(1234);
            registryClientes.rebind("//127.0.0.1/Coordinador", new Coordinador());
            
            Registry registryBanco = LocateRegistry.getRegistry(1235);
            Registry registryServidorAlimentos = LocateRegistry.getRegistry(1236);
            Registry registryServidorAseo = LocateRegistry.getRegistry(1237);
            
            System.out.println("Coordinador preparado");
            look_up_banco = (RMIInterfaceBanco) registryBanco.lookup("//127.0.0.1/Banco");
            look_up_servidor_alimentos = (RMIInterfaceServidor) registryServidorAlimentos.lookup("//127.0.0.1/ServidorAlimentos");
            look_up_servidor_aseo = (RMIInterfaceServidor) registryServidorAseo.lookup("//127.0.0.1/ServidorAseo");

        } catch (Exception e) {

            System.out.println("Error al iniciar el coordinador: " + e.toString());
            e.printStackTrace();

        }

    }

    @Override
    public boolean validarUsuario(String nombre_usuario, long numero_tarjeta, String contrasena) throws RemoteException {
        return look_up_banco.validarUsuario(nombre_usuario, numero_tarjeta, contrasena);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    //Pregunta a todos los servidores cuales son sus productos y los entrega al cliente

    @Override
    public List<Producto> obtenerProductos() throws RemoteException {
        List<Producto> productos = new ArrayList<>();
        
        productos.addAll(look_up_servidor_alimentos.obtenerProductos());
        productos.addAll(look_up_servidor_aseo.obtenerProductos());
        return productos;    
    }
    
    
    
    
    
    
    
    
    

}