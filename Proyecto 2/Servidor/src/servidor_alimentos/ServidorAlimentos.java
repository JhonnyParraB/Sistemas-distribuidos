/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_alimentos;

import clasesrmi.Producto;
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
public class ServidorAlimentos extends UnicastRemoteObject implements RMIInterfaceServidor {

    private List<Producto> productosServidor;
    /**
     * @param args the command line arguments
     */
    protected ServidorAlimentos() throws RemoteException {

        super();

    }

    public static void main(String[] args) {
        // TODO code application logic here

        try {
            Registry registry = LocateRegistry.createRegistry(1236);
            registry.rebind("//127.0.0.1/ServidorAlimentos", new ServidorAlimentos());
            System.out.println("Servidor alimentos preparado");

        } catch (Exception e) {

            System.out.println("Error al iniciar el servidor alimentos: " + e.toString());
            e.printStackTrace();
        }

    }

    @Override
    public List<Producto> obtenerProductos() throws RemoteException {
        List<Producto> productos = new ArrayList<Producto>();
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
                Producto producto = new Producto(lines[0], "Alimento" ,Long.parseLong(lines[2]), Integer.parseInt(lines[1]));
                productos.add(producto);
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
        productosServidor = productos;
        return productos;
    }

}
