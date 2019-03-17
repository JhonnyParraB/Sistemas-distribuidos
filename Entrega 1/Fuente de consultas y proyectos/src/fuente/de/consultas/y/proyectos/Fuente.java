/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuente.de.consultas.y.proyectos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import ClasesdeComunicacion.Proxy;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author green
 */
public class Fuente extends Thread {

    private List<Consulta> consultas;
    private static List <ClasesdeComunicacion.Proxy> directorio;
    private static int puertoManejador = 5998;
    private static Socket socket;
    private static List <Socket> sockets;

    public Fuente(List<Consulta> ejemploLista) {
        this.consultas = ejemploLista;
    }
    
    public Fuente() {
        this.consultas = new ArrayList <Consulta>();
    }

    public void evaluarTiempos() {
        Date actual=java.util.Calendar.getInstance().getTime();
        System.out.println(actual.toString());
        for (Consulta proy : this.consultas) {
            //imprimimos el objeto pivote
            if (proy.getFecha().before(actual)){
                System.out.println("Ya paso"+proy.getNombre());
            }
        }
    }
    
    public void solicitarConexión (){
        Scanner reader = new Scanner (System.in);
        int ID;
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
                    sockets.add( new Socket (proxy.getIP(), proxy.getPuertoFuentes()));
                }
            }
            else{
                System.out.println (mensaje);
                socket.close();
                System.exit(1);
            }
                    
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.out.println("Es posible que no haya un directorio de proxies en la IP indicada");
            System.exit(1);
        }                     
    }

    @Override
    public void run() {
        this.evaluarTiempos();
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    
}
