/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_aseo;

import rmiinterface_servidor.Producto;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rmiinterface_servidor.RMIInterfaceServidor;

/**
 *
 * @author green
 */
public class ServidorAseo extends UnicastRemoteObject implements RMIInterfaceServidor {

    private static Map<String, Integer> productos;
    /**
     * @param args the command line arguments
     */
    protected ServidorAseo() throws RemoteException {

        super();

    }

    public static void main(String[] args) {
        // TODO code application logic here

        try {
            Registry registry = LocateRegistry.createRegistry(1237);
            registry.rebind("//127.0.0.1/ServidorAseo", new ServidorAseo());
            System.out.println("Servidor de aseo preparado");
            productos = leerProductos();

        } catch (Exception e) {

            System.out.println("Error al iniciar el servidor de aseo: " + e.toString());
            e.printStackTrace();
        }

    }

    public static Map<String, Integer> leerProductos() {
        Map<String, Integer> productos = new HashMap<String, Integer>();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("Productos.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                String lines[] = linea.split(" ");
                productos.put(lines[0], Integer.parseInt(lines[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
                return productos;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return productos;
    }
    
    public List<Producto> obtenerProductosYPrecios (){
        return null;
    }

}
