package Conexion;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.freedesktop.dbus.Tuple;




public class Informacion extends Thread {
    static final int port = 1235;
    Socket skCliente;
    
    

    public Informacion(Socket sCliente) {
        skCliente = sCliente;
    }

    public static void main(String[]args){
        try {
            ServerSocket skServidor = new ServerSocket(port);
            System.out.println("Esperando peticion de informacion en el puerto " + port);

            while(true){
                Socket skCliente = skServidor.accept();
                System.out.println("¡¡¡Cliente Conectado!!!");
                new Informacion(skCliente).start();
            }
        } catch (Exception e) {
            System.out.println("Error de conexión");
        }
    }

    @Override
    public  void run() {
    	while(true) {
        try {  
             OutputStream aux = skCliente.getOutputStream();
             DataOutputStream flujo_salida = new DataOutputStream(aux);

             InputStream auxi = skCliente.getInputStream();
             DataInputStream flujo_entrada = new DataInputStream(auxi);

             flujo_salida.writeUTF("¡¡¡Conexión satisfactoria!!!");//Escribe en el Stream para que lo lea el cliente
             String comando;
             String linea;   
             int cont=0;
             comando=flujo_entrada.readUTF();//Lectura de comando                     
             System.out.println("comando recibido= "+comando);                     
             File archivo = new File ("info.txt");                
             FileReader fr = new FileReader (archivo);            
             BufferedReader br = new BufferedReader(fr);
             String[] cmd = comando.split(" ");
             String[] lineas;
             String get;
             int i,j;
             List<String> tupla = new ArrayList<>();
             while( (linea = br.readLine() ) != null){ 
                 tupla.add(linea);
             }
             Collections.sort(tupla);                
             for( i=0;i<tupla.size();i++){
                
                 get = tupla.get(i);                 
                 lineas = get.split(" ");                               
                 System.out.println(get);                    
                                  
                 if(lineas[0].equals(cmd[0]) && lineas[3].equals(cmd[1])){                                    
                     flujo_salida.writeUTF(get);                     
                     cont++;
                 }        
             }

             if(cont==0){
                 flujo_salida.writeUTF("No existe informacion de ese producto");
             }

             flujo_salida.writeUTF("true");
             //flujo_entrada.close();
             //flujo_salida.close();
             System.out.println("Cliente desconectado");
             //skCliente.close();
             //br.close();
        } catch (Exception e) {
        System.out.println(e.getMessage());
    
        }
    }
	
    }
    
}
