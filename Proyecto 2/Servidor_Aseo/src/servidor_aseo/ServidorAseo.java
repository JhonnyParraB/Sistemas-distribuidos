/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_aseo;

import clasesrmi.Producto;
import clasesrmi.Transaccion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
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

    private List<Producto> productosServidor;

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

    public List<Producto> obtenerProductosYPrecios() {
        return null;
    }

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
                Producto producto = new Producto(lines[0], "Aseo", Long.parseLong(lines[2]), Integer.parseInt(lines[1]));
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

    @Override
    public boolean prepararCommit(Transaccion transaccion) throws RemoteException {
        for (Producto productot : transaccion.getConjuntoEscritura()) {
            if (productot.getCantidad() < 0) {
                return false;
            }
        }

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("ProductosCopia.txt", true);
            pw = new PrintWriter(fichero);

            for (Producto productot : transaccion.getConjuntoEscritura()) {
                pw.println(productot.getNombre() + " " + productot.getCantidad() + " " + productot.getPrecio());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean commit() throws RemoteException {
        File fichero = new File("Productos.txt");
        fichero.delete();

        File f1 = new File("ProductosCopia.txt");
        File f2 = new File("Productos.txt");
        f1.renameTo(f2);

        return true;
    }

    @Override
    public boolean abortar() throws RemoteException {
        File fichero = new File("ProductosCopia.txt");
        fichero.delete();
        return true;
    }

}
