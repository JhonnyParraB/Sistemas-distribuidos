/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuario;

import ClasesdeComunicacion.Consulta;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import ClasesdeComunicacion.Proxy;
import ClasesdeComunicacion.Voto;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO PC
 */
public class Usuario {

    private static int puertoManejador = 5999;
    private static String ipManejador;
    private static Socket socket;
    private static final String menu = "1. Ver nuevas consultas/proyectos\n"
            + "2. Votar una consulta/proyecto\n"
            + "3. Desconectarse";
    private static final String opcionesVotacion = "1: Alto         2: Medio        3: Bajo";
    private static int ID;
    private static String pass;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        solicitarConexión();
        mostrarMenu();
    }

    /**
     * Se valida que la ID sea valida y no este repetida ya
     */
    private static void solicitarConexión() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Ingrese la IP del manejador de proxies (directorio):");
        ipManejador = reader.nextLine();

        System.out.println("Para acceder al sistema, por favor, ingrese su ID:");
        ID = Integer.parseInt(reader.nextLine());

        System.out.println("Para acceder al sistema, por favor, ingrese su contraseña:");
        pass = reader.nextLine();

        try {
            socket = new Socket(ipManejador, puertoManejador);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject("Conexion");
            out.writeObject(ID);
            out.writeObject(sha1(pass));

            String mensaje = (String) in.readObject();
            if (mensaje.equals("El ID es valido\nBienvenido!")) {
                System.out.println(mensaje);
                ClasesdeComunicacion.Proxy proxy = (ClasesdeComunicacion.Proxy) in.readObject();
                socket.close();
                socket = new Socket(proxy.getIP(), proxy.getPuertoClientes());
                System.out.println("Binding a proxy con IP: "
                        + proxy.getIP()
                        + " y Puerto clientes: "
                        + proxy.getPuertoClientes());
            } else {
                socket.close();
                System.out.println(mensaje);
                System.exit(1);
            }

        } catch (Exception ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    private static void solicitarReconexión() {

        try {
            socket = new Socket(ipManejador, puertoManejador);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject("Reconexion");

            ClasesdeComunicacion.Proxy proxy = (ClasesdeComunicacion.Proxy) in.readObject();
            socket.close();
            socket = new Socket(proxy.getIP(), proxy.getPuertoClientes());
            System.out.println("Binding a proxy con IP: "
                    + proxy.getIP()
                    + " y Puerto clientes: "
                    + proxy.getPuertoClientes());

        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.out.println("Es posible que no haya un directorio de proxies en la IP indicada");
            System.exit(1);
        }
    }

    private static void mostrarMenu() {
        int opcion;
        Scanner reader = new Scanner(System.in);
        do {
            System.out.println(menu);
            System.out.print("Ingrese la opcion: ");
            opcion = reader.nextInt();
            if (opcion == 2) {
                solicitarConsultasyProyectosYVotar();
            }

        } while (opcion != 3);
    }

    private static void solicitarConsultasyProyectosYVotar() {

        ObjectOutputStream out;
        ObjectInputStream in;
        try {

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            out.writeObject("1");
            out.writeObject(ID);

            List<ClasesdeComunicacion.Consulta> consultas = (List<ClasesdeComunicacion.Consulta>) in.readObject();
            mostrarConsultas(consultas);
            Voto voto = votarConsulta(consultas);
            out.writeObject(voto);
            String mensaje = (String) in.readObject();
            if (mensaje.equals("El voto se ha enviado correctamente")) {
                System.out.println(mensaje);
            }

        } catch (Exception ex) {
            System.out.println("Se cayó el proxy...Intentando reconectar con otro proxy");
            solicitarReconexión();
            System.out.println("Reconectado!");
        }
    }

    private static void mostrarConsultas(List<ClasesdeComunicacion.Consulta> consultas) {
        if (!consultas.isEmpty()) {
            int i = 1;
            System.out.println("Consultas disponibles para ser votadas:");
            for (ClasesdeComunicacion.Consulta consulta : consultas) {
                System.out.println(i + ". " + consulta.getNombre());
                i++;
            }
        } else {
            System.out.println("No hay consultas para votar.");
        }
    }

    private static Voto votarConsulta(List<ClasesdeComunicacion.Consulta> consultas) {
        Scanner reader = new Scanner(System.in);
        int votoAprobacion;
        int i = 1;
        ClasesdeComunicacion.Voto voto = null;
        System.out.println("Seleccione la consulta que desea votar: ");
        int consultaElegida = reader.nextInt();
        Consulta consulta = consultas.get(consultaElegida - 1);
        System.out.println("Votando consulta/proyecto \"" + consulta.getNombre() + "\"" );

        System.out.println(opcionesVotacion);
        System.out.print("Ingrese su voto: ");
        votoAprobacion = reader.nextInt();
        if (votoAprobacion == 1) {
            voto = new Voto(consulta, ID, "Alto");
        }
        if (votoAprobacion == 2) {
            voto = new Voto(consulta, ID, "Medio");
        }
        if (votoAprobacion == 3) {
            voto = new Voto(consulta, ID, "Bajo");
        }
        return voto;
    }

    static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
