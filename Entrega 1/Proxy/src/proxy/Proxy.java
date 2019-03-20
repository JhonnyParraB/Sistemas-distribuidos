/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxy;

import ClasesdeComunicacion.Consulta;
import ClasesdeComunicacion.Voto;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
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
public class Proxy {
    
   
    
    private static int puertoManejador = 6000;
    private static int conexionesActuales = 0;
    private static int puertoClientes;
    private static int puertoFuentes;
    private static List<ClasesdeComunicacion.Consulta> consultas = new ArrayList<ClasesdeComunicacion.Consulta>();
    private static Map<String, List<Integer>> votosConsultas = new HashMap <String, List<Integer>>();
    private static String ipManejador;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        inicializarProxy();
        conectarConManejador();
    }
    
    /*
        Realiza una conexión con el manejador para registrarse como proxy disponible
        Envia la información del puerto y de la IP
        El puerto está quemado
    */
    private static void conectarConManejador(){
        Scanner reader = new Scanner (System.in);       
        
        
        Socket socket;
        String mensaje = "";       
        
        try{
            socket = new Socket (ipManejador, puertoManejador);      
            
            DataOutputStream out = new DataOutputStream (socket.getOutputStream());
            DataInputStream in = new DataInputStream (socket.getInputStream());
            
            
            mensaje = InetAddress.getLocalHost().getHostAddress().toString();
            out.writeUTF(mensaje);
            
            out.writeInt(puertoClientes);
            out.writeInt(puertoFuentes);
            
            mensaje = in.readUTF();
            System.out.println (mensaje);   
            socket.close();
                    
        }catch(Exception e){
            System.out.println("Error: es posible que no haya un directorio de proxys en la IP indicada");
            System.exit(0);
        }
        
               
        
    }
    
    private static void inicializarProxy (){
        Scanner reader = new Scanner (System.in);
        System.out.print ("Ingrese la IP del manejador de proxys (directorio): ");             
        ipManejador = reader.nextLine ();
        System.out.print ("Ingrese el puerto en el que el proxy se comunicara con los clientes: ");
        puertoClientes = reader.nextInt();
        System.out.print ("Ingrese el puerto en el que el proxy se comunicara con las fuentes: ");
        puertoFuentes = reader.nextInt();
        ManejadorClientes manejadorClientes = new ManejadorClientes();
        ManejadorFuentes manejadorFuentes = new ManejadorFuentes();
        manejadorClientes.start();
        manejadorFuentes.start();
    }

    public static int getPuertoClientes() {
        return puertoClientes;
    }

    public static int getPuertoFuentes() {
        return puertoFuentes;
    }   

    public static void setConsultas(List<Consulta> consultas) {
        Proxy.consultas = consultas;
    }

    public static List<Consulta> getConsultas() {
        return consultas;
    }
    
    public static void agregarConsultas (List<ClasesdeComunicacion.Consulta> consultasNuevas){
        consultas.addAll(consultasNuevas);
        for (Consulta consulta: consultasNuevas){
                    votosConsultas.put(consulta.getNombre(), new ArrayList<Integer>());
        }
    }
    
    public static void agregarVotosConsultasReconexion (Map<String, List<Integer>> votosConsultasReconexion){
        votosConsultas.putAll(votosConsultasReconexion);
    }
    
    public static List<Consulta> consultasParaUsuario (int IDUsuario){
        List<Consulta> consultasUsuario = new ArrayList <Consulta>();
        for (Consulta consulta: consultas){
            if (!votosConsultas.get(consulta.getNombre()).contains(IDUsuario)){
                consultasUsuario.add(consulta);
            }
        }
        return consultasUsuario;
    }      
    public static void agregarVoto (Voto voto){
        votosConsultas.get(voto.getConsulta().getNombre()).add(voto.getIDUsuario());
    }
}
