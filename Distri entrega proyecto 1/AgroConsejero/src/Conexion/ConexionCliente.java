package Conexion;
import java.net.*;

import Cliente.Cliente;

import java.io.*;

public class ConexionCliente extends Thread {
	private Socket  socket   = null;
	   private Cliente client   = null;
	   private DataInputStream  streamIn = null;

	   public ConexionCliente(Cliente _client, Socket _socket)
	   {  client   = _client;
	      socket   = _socket;
	      open();  
	      start();
	   }
	   public void open()
	   {  try
	      {  streamIn  = new DataInputStream(socket.getInputStream());
	      }
	      catch(IOException ioe)
	      {  System.out.println("Error getting input stream: " + ioe);
	         client.stop();
	      }
	   }
	   public void close()
	   {  try
	      {  if (streamIn != null) streamIn.close();
	      }
	      catch(IOException ioe)
	      {  System.out.println("Error closing input stream: " + ioe);
	      }
	   }
	   public void run()
	   {  while (true)
	      {  try
	         {
	    	 
	    	  client.leerInfo(streamIn.readUTF());
	         }
	         catch(IOException ioe)
	         {  System.out.println("Listening error: " + ioe.getMessage());
	            client.stop();
	         }
	      }
	   }
 
}
