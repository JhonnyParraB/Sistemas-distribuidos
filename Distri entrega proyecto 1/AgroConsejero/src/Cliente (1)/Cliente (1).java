package Cliente;
import java.net.*;

import javax.swing.JFrame;

import Conexion.ConexionCliente;
import Conexion.ConexionTopico;

import java.io.*;

public class Cliente implements Runnable{
	private Socket socket              = null;
	   private Thread thread              = null;
	   private DataInputStream  console   = null;
	   private DataOutputStream flujoSalida = null;
	   private ConexionCliente client    = null;
	   private String ubicacion = null;
	   private String tipoProducto = null;
	   private int tam = 0;
           
	   public Cliente(String serverName, int serverPort, String ubicacion, String tipoProducto, int tam) 
	   {  System.out.println("Establishing connection. Please wait ...");
	   		this.ubicacion=ubicacion;
	   		this.tipoProducto=tipoProducto;
	   		this.tam=tam;
	      try
	      {  socket = new Socket(serverName, serverPort);
	         System.out.println("Connected: " + socket);
	         start();
                 //Socket s = openSocket(serverName, serverPort);
                 
	      }
	      catch(UnknownHostException uhe)
	      {  System.out.println("Host unknown: " + uhe.getMessage()); }
	      catch(IOException ioe)
	      {  System.out.println("Unexpected exception: " + ioe.getMessage()); }
	   }
	   public void run()
	   {  if (thread != null)
	      {  try
	         {  flujoSalida.writeUTF(ubicacion+" "+tipoProducto+" "+String.valueOf(tam));
	            flujoSalida.flush();
	         }
	         catch(IOException ioe)
	         {  System.out.println("Sending error: " + ioe.getMessage());
	            stop();
	         }
	      }
	   }
	   public synchronized void leerInfo(String msg)
	   {  if (msg.equals("true"))
	      {  System.out.println("Good bye. Press RETURN to exit ...");
	      try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	         stop();
	      }
	      else {
	    	  if(msg.equalsIgnoreCase("No existe informacion de ese producto"))
	    		  System.out.println(msg);
	    	  else
	    		  System.out.println(msg);
	      }
	         
	   }
	   public void start() throws IOException
	   {  console   = new DataInputStream(System.in);//sobra
	      flujoSalida = new DataOutputStream(socket.getOutputStream());
	      if (thread == null)
	      {  client = new ConexionCliente(this, socket);
	         thread = new Thread(this);                   
	         thread.start();
	      }

	   }
	   public void stop()
	   {  if (thread != null)
	      {  thread.stop();  
	         thread = null;
	      }
	      try
	      {  if (console   != null)  console.close();
	         if (flujoSalida != null)  flujoSalida.close();
	         if (socket    != null)  socket.close();
	      }
	      catch(IOException ioe)
	      {  System.out.println("Error closing ..."); }
	      client.close();  
	      client.stop();
	   }
}

