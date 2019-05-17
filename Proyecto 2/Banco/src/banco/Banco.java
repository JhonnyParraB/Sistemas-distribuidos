/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import rmiinterface_banco.RMIInterfaceBanco;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author green
 */
public class Banco extends UnicastRemoteObject implements RMIInterfaceBanco {

    private static long numeroTarjetas = recuperarNumerotarjetas();
    private static String key = "92AE3RA79WEEB2A3";
    private static String iv = "0123456789ABCDEF";

    private static String alg = "AES";
    private static String cI = "AES/CBC/PKCS5Padding";

    /**
     * @param args the command line arguments
     */
    protected Banco() throws RemoteException {

        super();

    }

    public static void main(String[] args) {
        // TODO code application logic here
        
        System.out.println(numeroTarjetas);
        
        try {

            Registry registry = LocateRegistry.createRegistry(1235);
            registry.rebind("//127.0.0.1/Banco", new Banco());
            System.out.println("Banco preparado");

        } catch (Exception e) {

            System.out.println("Error al iniciar el banco: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public long registrarUsuario(String nombre_usuario, String contrasena) throws RemoteException {

        System.out.println("Asignando número de tarjeta a un nuevo usuario");
        long saldo = 50000;
        numeroTarjetas++;
        long numTarjeta = numeroTarjetas;
        escribirArchivo(nombre_usuario, contrasena, numTarjeta, saldo);
        return numTarjeta;

        //escribir archivo
    }

    public void escribirArchivo(String nombre_usuario, String contrasena, long numTarjeta, long saldo) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("RegistroBanco.txt", true);
            pw = new PrintWriter(fichero);
            pw.println(encriptar(nombre_usuario) + " " + encriptar(contrasena) + " " + numTarjeta + " " + saldo);

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
    }

    private static String encriptar(String informacion) {

        try {
            Cipher cipher = Cipher.getInstance(cI);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(informacion.getBytes());
            return new String(Base64.getEncoder().encode(encrypted));

        } catch (Exception e) {
            System.out.println("Error en la encriptación.");
        }
        return null;
    }

    private static String desencriptar(String informacion_encriptada) {
        try {
            Cipher cipher = Cipher.getInstance(cI);
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), alg);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());

            byte[] enc = Base64.getDecoder().decode(informacion_encriptada);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
            byte[] decrypted = cipher.doFinal(enc);
            return new String(decrypted);

        } catch (Exception e) {
            System.out.println("Error en la encriptación.");
        }
        return null;
    }

    //Esta función debe leer el archivo y validar que el usuario existe y es valido (se debe encriptar)
    @Override
    public boolean validarUsuario(String nombre_usuario, long numero_tarjeta, String contrasena) throws RemoteException {

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        String nombre_usuario_encriptado, contrasena_encriptado;
        nombre_usuario_encriptado = encriptar(nombre_usuario);
        contrasena_encriptado = encriptar(contrasena);
        boolean valido = false;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("RegistroBanco.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                String lines[] = linea.split(" ");
                if (lines[0].equals(nombre_usuario_encriptado) && lines[1].equals(contrasena_encriptado) && lines[2].equals(numero_tarjeta+"")) {
                    valido = true;
                }
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
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return valido;
    }

    public static long recuperarNumerotarjetas() {

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        long numeroTarjeta = 10000000;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("RegistroBanco.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                String lines[] = linea.split(" ");
                numeroTarjeta = Long.parseLong(lines[2]);
            }
        } catch (Exception e) {

            FileWriter fichero = null;
            try {
                fichero = new FileWriter("RegistroBanco.txt", true);

            } catch (IOException er) {
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

        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return numeroTarjeta;

    }

}
