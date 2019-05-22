/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor_alimentos;

import clasesrmi.Producto;
import clasesrmi.Transaccion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
                Producto producto = new Producto(lines[0], "Alimento", Long.parseLong(lines[2]), Integer.parseInt(lines[1]));
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
        List<String> productosModificar = new ArrayList<String>();
        
        for (Producto productot : transaccion.getConjuntoEscritura()) {
            if (productot.getCantidad() < 0) {
                return false;
            }
        }

        for (Producto productot : transaccion.getConjuntoEscritura()) {
            productosModificar.add(productot.getNombre());
        }

        generarCopia("Productos.txt", "ProductosCopia.txt", productosModificar);

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

    public void generarCopia(String sourceFile, String destinationFile, List<String> productos) {
        File inputFile = new File(sourceFile);
        File tempFile = new File(destinationFile);
        boolean contiene = false;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                contiene = false;
                String trimmedLine = currentLine.trim();
                for (String producto : productos) {
                    if (trimmedLine.contains(producto)) {
                        contiene = true;
                    }
                }
                if (contiene == true) {
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            System.out.println("LLego hasta la duplicacion del archivo");
        } catch (IOException e) {
            System.err.println("Hubo un error de entrada/salida!!!"+e);
        }
    }

    @Override
    public boolean existeConexion() throws RemoteException {
        return true;
    }

}
