/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuente.de.consultas.y.proyectos;

import ClasesdeComunicacion.Consulta;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private static List<ConsultaConteo> conteos = new ArrayList<ConsultaConteo> ();  
    private static int ID;


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LeeFichero funcion=  new LeeFichero();
        solicitarConexión();
        funcion.start();
        
    }

    public static void solicitarConexión (){
        Scanner reader = new Scanner (System.in);
        String ipManejador;
        System.out.println ("Ingrese la IP del manejador de proxies (directorio):");
        ipManejador = reader.nextLine ();
        
        System.out.println("Para acceder al sistema, por favor, ingrese su ID:");
        ID = reader.nextInt();
                
        try{
            socket = new Socket (ipManejador, puertoManejador);      
            
            ObjectOutputStream out = new ObjectOutputStream (socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream (socket.getInputStream());
            
           
            out.writeObject(ID);
                  
            String mensaje = (String)in.readObject();
            if (mensaje.equals("El ID es valido\nBienvenido!")){
                System.out.println (mensaje);  
                directorio = (List <ClasesdeComunicacion.Proxy>)in.readObject();
                socket.close();
                for (ClasesdeComunicacion.Proxy proxy : directorio){
                    System.out.println(proxy.getIP());
                    System.out.println(proxy.getPuertoFuentes());
                    Socket socketp = new Socket (proxy.getIP(), proxy.getPuertoFuentes());
                    sockets.add(socketp);
                }
            }
            else{
                socket.close();
                System.out.println (mensaje);
                System.exit(1);
            }
                    
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.out.println("Es posible que no haya un directorio de proxies en la IP indicada");
            System.exit(1);
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

    
    
    
    
    
    
    
}
