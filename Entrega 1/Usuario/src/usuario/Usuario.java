/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuario;

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
    private static Socket socket;
    private static final String menu = "1. Ver nuevas consultas/proyectos y votarlos\n"
            + "2. Desconectarse";
    private static final String opcionesVotacion = "1: Alto         2: Medio        3: Bajo";
    private static int ID;

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
        String ipManejador;
        System.out.println("Ingrese la IP del manejador de proxies (directorio):");
        ipManejador = reader.nextLine();

        System.out.println("Para acceder al sistema, por favor, ingrese su ID:");
        ID = reader.nextInt();

        try {
            socket = new Socket(ipManejador, puertoManejador);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(ID);

            String mensaje = (String) in.readObject();
            if (mensaje.equals("El ID es valido\nBienvenido!")) {
                System.out.println(mensaje);
                ClasesdeComunicacion.Proxy proxy = (ClasesdeComunicacion.Proxy) in.readObject();
                System.out.println(proxy.getIP());
                System.out.println(proxy.getPuertoClientes());
                socket.close();
                socket = new Socket(proxy.getIP(), proxy.getPuertoClientes());
            } else {
                socket.close();
                System.out.println(mensaje);
                System.exit(1);
            }

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
            if (opcion == 1) {
                solicitarConsultasyProyectosYVotar();
            }

        } while (opcion != 2);
    }

    private static void solicitarConsultasyProyectosYVotar() {

        ObjectOutputStream out;
        ObjectInputStream in;
        try {

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            out.writeObject("1");

            List<ClasesdeComunicacion.Consulta> consultas = (List<ClasesdeComunicacion.Consulta>) in.readObject();
            mostrarConsultas(consultas);
            List<ClasesdeComunicacion.Voto> votos = votarConsultas(consultas);
            
            out.writeObject(votos);

        } catch (Exception ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void mostrarConsultas(List<ClasesdeComunicacion.Consulta> consultas) {
        if (!consultas.isEmpty()) {
            int i = 1;
            System.out.println("Consultas disponibles para votar:");
            for (ClasesdeComunicacion.Consulta consulta : consultas) {
                System.out.println(i + ". " + consulta.getNombre());
                i++;
            }
        } else {
            System.out.println("No hay consultas para votar.");
        }
    }

    private static List<ClasesdeComunicacion.Voto> votarConsultas(List<ClasesdeComunicacion.Consulta> consultas) {
        Scanner reader = new Scanner(System.in);
        int votoAprobacion;
        int i=1;
        List<ClasesdeComunicacion.Voto> votos = new ArrayList <ClasesdeComunicacion.Voto> ();
        ClasesdeComunicacion.Voto voto = null;
        for (ClasesdeComunicacion.Consulta consulta : consultas) {
            System.out.println(i + ". " + consulta.getNombre());
            System.out.println(opcionesVotacion);
            System.out.print("Ingrese su voto: ");
            votoAprobacion = reader.nextInt();
            if (votoAprobacion == 1){
                voto = new Voto (consulta, ID, "Alto");
            }
            if (votoAprobacion == 2){
                voto = new Voto (consulta, ID, "Medio");
            }
            if (votoAprobacion == 3){
                voto = new Voto (consulta, ID, "Bajo");
            }
            i++;
            votos.add(voto);
        }
        return votos;        
    }
}
