/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuente.de.consultas.y.proyectos;

import ClasesdeComunicacion.Consulta;
import ClasesdeComunicacion.Voto;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO PC
 */
public class FuenteDeConsultasYProyectos {

    private static List<ClasesdeComunicacion.Consulta> consultas = new ArrayList<Consulta>();
    private static List<ClasesdeComunicacion.Proxy> directorio;
    private static int puertoManejador = 5998;
    private static Socket socket;
    private static List<Socket> sockets = new ArrayList<Socket>();
    private static Map<String, ConsultaConteo> conteos = new HashMap<String, ConsultaConteo>();
    private static Map<String, List<Integer>> votosConsultas = new HashMap<String, List<Integer>>();
    private static int ID;

    private static final String menu = "--Menu principal--\n1. Recuento de votos";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner reader = new Scanner(System.in);
        solicitarConexión();
        System.out.print("Ingrese el nombre del archivo que contiene sus consultas/proyectos: ");
        String nombreArchivo = reader.nextLine();
        InyectorConsultas funcion = new InyectorConsultas(nombreArchivo);
        funcion.start();
        mostrarMenu();

    }

    public static void solicitarConexión() {
        Scanner reader = new Scanner(System.in);
        String ipManejador;
        System.out.print("Ingrese la IP del manejador de proxys (directorio): ");
        ipManejador = reader.nextLine();

        System.out.print("Para acceder al sistema, por favor, ingrese su ID: ");
        ID = reader.nextInt();

        try {
            socket = new Socket(ipManejador, puertoManejador);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(ID);

            String mensaje = (String) in.readObject();
            if (mensaje.equals("El ID es valido\nBienvenido!")) {
                System.out.println(mensaje);
                directorio = (List<ClasesdeComunicacion.Proxy>) in.readObject();
                socket.close();
                for (ClasesdeComunicacion.Proxy proxy : directorio) {
                    Socket socketp = new Socket(proxy.getIP(), proxy.getPuertoFuentes());
                    (new ConexionFuenteAProxy(socketp, "Envio ID")).start();
                    (new ConexionProxyAFuente(socketp)).start();
                    sockets.add(socketp);
                }
            } else {
                socket.close();
                System.out.println(mensaje);
                System.exit(0);
            }

        } catch (Exception e) {
            System.out.println("Error: es posible que no haya un directorio de proxies en la IP indicada");
            System.exit(0);
        }
    }

    public static List<ClasesdeComunicacion.Consulta> getConsultas() {
        return consultas;
    }

    public static void setConsultas(List<ClasesdeComunicacion.Consulta> consultas) {
        FuenteDeConsultasYProyectos.consultas = consultas;
    }

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        FuenteDeConsultasYProyectos.ID = ID;
    }

    public static List<Socket> getSockets() {
        return sockets;
    }
    
    public static void eliminarProxyCaido (Socket socket){
        sockets.remove(socket);
    }

    public static void agregarConteoYVotos(List<ClasesdeComunicacion.Consulta> consultas) {
        for (Consulta consulta : consultas) {
            ConsultaConteo conteo = new ConsultaConteo(consulta);
            conteos.put(consulta.getNombre(), conteo);
            votosConsultas.put(consulta.getNombre(), new ArrayList<Integer>());
        }
    }

    public static void contarVoto(Voto voto) {
        if (voto.getAprobacion().equals("Alto")) {
            conteos.get(voto.getConsulta().getNombre()).sumarAlto();
        }
        if (voto.getAprobacion().equals("Medio")) {
            conteos.get(voto.getConsulta().getNombre()).sumarMedio();
        }
        if (voto.getAprobacion().equals("Bajo")) {
            conteos.get(voto.getConsulta().getNombre()).sumarBajo();
        }
        
        votosConsultas.get(voto.getConsulta().getNombre()).add(voto.getIDUsuario());
    }

    private static void mostrarMenu() {
        int opcion;
        Scanner reader = new Scanner(System.in);
        do {
            
            System.out.println();
            System.out.println();
            System.out.println(menu);
            
            
            System.out.print("Ingrese la opcion: ");
            opcion = reader.nextInt();
            System.out.println();
            System.out.println();
            if (opcion == 1) {
                int i = 1;
                for (ConsultaConteo conteo : conteos.values()) {
                    System.out.println(i+". "+conteo.getConsulta().getNombre());
                    System.out.println("Alto: " + conteo.getAlto() + "   Medio: " + conteo.getMedio()
                            + "   Bajo: " + conteo.getBajo());
                    i++;
                }
            }
        } while (opcion != 2);
    }

    public static Map<String, List<Integer>> getVotosConsultas() {
        return votosConsultas;
    }

    public static void setVotosConsultas(Map<String, List<Integer>> votosConsultas) {
        FuenteDeConsultasYProyectos.votosConsultas = votosConsultas;
    }
    
    

}
