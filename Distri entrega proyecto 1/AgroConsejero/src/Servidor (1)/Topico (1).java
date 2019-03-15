package Servidor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Conexion.ConexionInformacion;
import Conexion.ConexionTopico;



public class Topico implements Runnable {
	   private ConexionTopico clientes[] = new ConexionTopico[50];
	   private ServerSocket topico = null;
	   private Thread       hiloTopico = null;
	   
	   private ConexionInformacion informacion= null;
	   private String hostInfo = "localhost";
	   private Socket socketInfo = null;
	   private int port1 = 1235;
	   
	   private Thread lectura = null;
	   private DataOutputStream streamOut = null;
	   private int clientCount = 0;
	   List< Integer> ids = new ArrayList<Integer>();
	   Map<String, ArrayList<Integer> > mapa = new HashMap<String, ArrayList<Integer>>();
	  

	   public Topico(int port)
	   {  try
	      {  System.out.println("conectando al puerto " + port );
	         topico = new ServerSocket(port);  
	       
	         System.out.println("Servidor  iniciado: " + topico);
	         startTopico(); }
	   
	      catch(IOException ioe)
	      {  System.out.println("No se puede conectar al " + port + ": " + ioe.getMessage()); }
	   	try {
  	         
  	         socketInfo= new Socket(hostInfo, port1);
  	         System.out.println("esperando  informacion: " + socketInfo);
  	         
  		} catch (Exception e) {
  			System.out.println("No se puede conectar al " + port1 + ": " + e.getMessage()); }

	   }
	  
	   public void run()
	   {  while (hiloTopico != null)
	      {  try
	         {  System.out.println("esperando cliente ..."); 
	            addCliente(topico.accept()); 
	     	   		}
	           
	         
	         catch(IOException ioe)
	         {  System.out.println(" error: " + ioe); stop(); }
	      }
	   }
	   
	public void startTopico()  
	   {
		   if (hiloTopico == null)
		      {  hiloTopico = new Thread(this); 
		         hiloTopico.start();
		      }
	   }
	public void startInfor(String input) throws IOException  
	   {
		
		if(streamOut==null)
			streamOut = new DataOutputStream(socketInfo.getOutputStream());
		new DataInputStream(System.in);
	      
	      
		   streamOut.writeUTF(input);
           streamOut.flush();
	      
	      if (lectura == null)
	      {  
	    	  System.out.println("Iniciando lectura");
	    	  informacion = new ConexionInformacion(this, socketInfo);
	         lectura = new Thread(this);                   
	         lectura.start();
	      }
	      
            
	   }
	   @SuppressWarnings("deprecation")
	public void stop()   { 
		   if (hiloTopico != null)
		      {  hiloTopico.stop(); 
		         hiloTopico = null;
		      }
	   }
	   private int findClient(int ID)
	   {  for (int i = 0; i < clientCount; i++)
	         if (clientes[i].getID() == ID)
	            return i;
	      return -1;
	   }
	   
	   public synchronized void leerCliente(int ID, String input) throws IOException 
	   {
		   
		   String[] top=input.split(" ");
		   
		 
		   for(int i=0; i< top.length; i++) {
			
			   ArrayList<Integer> ids = new ArrayList<>();
			 
		
			
				  if(mapa.containsKey(top[i]) ) {
					   ids=mapa.get(top[i]);
					   ids.add(ID);
					   mapa.put(top[i], ids);
					   System.out.println("ya estaba el tpico "+ top[i]);
				  }
				  else {
					   ids.add(ID);
					   mapa.put(top[i],ids );
					   System.out.println("No esta el topico "+ top[i]);
					   
				  }
			  
			  
			}
		   
		   startInfor(input);
		
	   }
	   @SuppressWarnings("deprecation")
	public synchronized void remove(int ID)
	   {  int pos = findClient(ID);
	      if (pos >= 0)
	      {  ConexionTopico toTerminate = clientes[pos];
	         System.out.println("Removiendo cliente " + ID + " en " + pos);
	         if (pos < clientCount-1)
	            for (int i = pos+1; i < clientCount; i++)
	               clientes[i-1] = clientes[i];
	         clientCount--;
	         try
	         {  toTerminate.close(); }
	         catch(IOException ioe)
	         {  System.out.println("Error cerrando cliente: " + ioe); }
	         toTerminate.stop(); }
	   }
	   
	   private void addCliente(Socket socket)
	   {  if (clientCount < clientes.length)
	      {  System.out.println("Cliente acceptado: " + socket);
	         clientes[clientCount] = new ConexionTopico(this, socket);
	         try
	         {  clientes[clientCount].open(); 
	            clientes[clientCount].start();  
	            clientCount++; }
	         catch(IOException ioe)
	         {  System.out.println("Error abriendo conexion: " + ioe); } }
	      else
	         System.out.println("Client rechazado:  " + clientes.length + " rechazado.");
	   }
	   public static void main(String args[]) { 
		    Topico server = new Topico(1234);
	   }

	public synchronized void leerInformacion(String informacion) {
	
		System.out.println("informacion recibida de : "+informacion);
		 String[] top=informacion.split(" ");
			   ArrayList<Integer> ids = new ArrayList<>();
			   if(mapa.containsKey(top[0]) && mapa.containsKey(top[3]) ) {
				   ids.addAll(mapa.get(top[0]));
				   for(int j=0;j<ids.size();j++) {
					   
					   clientes[findClient(ids.get(j))].send(informacion);
					   
					   notifyAll();
					   
					   
				   }

			  }

				   
			   
	}
	
	
}
