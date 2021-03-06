package Conexion;
import java.net.*;

import Servidor.Topico;

import java.io.*;

public class ConexionTopico extends Thread {
	   private Topico       server    = null;
	   private Socket           socket    = null;
	   private int              ID        = -1;
	   private DataInputStream  streamIn  =  null;
	   private DataOutputStream streamOut = null;

	   public ConexionTopico(Topico _server, Socket _socket)
	   {  super();
	      server = _server;
	      socket = _socket;
	      ID     = socket.getPort();
	   }
	   public void send(String msg)
	   {  // System.out.println("send cse"+msg);
		   try
	       {  streamOut.writeUTF(msg);
	          streamOut.flush();
	       }
	       catch(IOException ioe)
	       {  System.out.println(ID + " ERROR sending: " + ioe.getMessage());
	          server.remove(ID);
	          stop();
	       }
	   }
	   public int getID()
	   {  return ID;
	   }
	   public void run()
	   {  System.out.println("Conexion de servidor " + ID + " Corriendo.");
	      while (true)
	      {  try
	         {  
	    	  
	    	  server.leerCliente(ID, streamIn.readUTF());
	         }
	         catch(IOException ioe)
	         {  System.out.println(ID + " ERROR leyendo ConexionTopico: " + ioe.getMessage());
	
	            server.remove(ID);
	            stop();
	         }
	      }
	   }
	   public void open() throws IOException
	   {  streamIn = new DataInputStream(new 
	                        BufferedInputStream(socket.getInputStream()));
	      streamOut = new DataOutputStream(new
	                        BufferedOutputStream(socket.getOutputStream()));
	   }
	   public void close() throws IOException
	   {  if (socket != null)    socket.close();
	      if (streamIn != null)  streamIn.close();
	      if (streamOut != null) streamOut.close();
	   }

}
