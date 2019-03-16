package Conexion;
import java.net.*;

import Cliente.Cliente;
import Servidor.Topico;

import java.io.*;

public class ConexionInformacion extends Thread {
	private Socket  socket   = null;
	   private Topico topico   = null;
	   private DataInputStream  streamIn = null;

	   public ConexionInformacion(Topico top, Socket _socket)
	   {  topico   = top;
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
	         topico.stop();
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
	    	 
	    	  topico.leerInformacion(streamIn.readUTF());
	         }
	         catch(IOException ioe)
	         {  System.out.println("Listening error conexion info: " + ioe.getMessage());
	            topico.stop();
	         }
	      }
	   }

}
