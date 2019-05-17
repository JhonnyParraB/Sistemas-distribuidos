/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import cliente.Cliente;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;
import rmiinterface_coordinador.Producto;
import rmiinterface_coordinador.RMIInterfaceCoordinador;

/**
 *
 * @author green
 */
public class Cliente {

    private static RMIInterfaceCoordinador look_up_coordinador;
    private static Scanner in = new Scanner(System.in);
    
    private static List<Producto> productos;

    private static final int REGISTRO = 1;
    private static final int INICIAR_COMPRA = 2;
    private static final int CONSULTAR_SALDO = 3;
    private static final int INGRESAR_DINERO = 4;
    private static final int SALIR = 5;

    private static final int AGREGAR_PRODUCTO = 1;
    private static final int MODIFICAR_CANTIDADES = 2;
    private static final int ELIMINAR_PRODUCTO = 3;
    private static final int CONFIRMAR_COMPRA = 4;
    private static final int CANCELAR_COMPRA = 5;

    private static final long SALDO_INICIAL = 5000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        // TODO code application logic here

        System.setProperty("java.security.policy", "./client.policy");

        Registry registry = LocateRegistry.getRegistry(1234);
        look_up_coordinador = (RMIInterfaceCoordinador) registry.lookup("//127.0.0.1/Coordinador");

        boolean mostrarOpciones = true;
        int opcion = -1;
        while (opcion != 5) {
            if (mostrarOpciones) {
                System.out.println("<<< Menú principal >>>");
                System.out.println("1. Registrarse");
                System.out.println("2. Iniciar compra");
                System.out.println("3. Consultar saldo");
                System.out.println("4. Ingresar dinero");
                System.out.println("5. Salir");
            }

            System.out.print("Opción: ");
            opcion = in.nextInt();
            mostrarOpciones = true;

            switch (opcion) {
                case REGISTRO:
                    registrar();
                    break;
                case INICIAR_COMPRA:
                    comprar();
                    break;
                case CONSULTAR_SALDO:
                    break;
                case INGRESAR_DINERO:
                    break;
                case SALIR:
                    break;
                default:
                    System.out.println("Opcion incorrecta, inténtelo nuevamente.");
                    mostrarOpciones = false;
            }
        }

    }

    private static void registrar() throws RemoteException {
        String contrasena;
        String nombre_usuario;

        System.out.println("-------------Registro-------------\n");
        System.out.println("Ingrese el nombre de usuario:");
        nombre_usuario = in.next();
        System.out.println("Ingrese la contraseña:");
        contrasena = in.next();

        long numeroTarjeta = look_up_coordinador.registrarUsuarioBanco(nombre_usuario, contrasena);
        System.out.println("Bienvenido, su nombre de usuario y contraseña ha sido registrado.");
        System.out.println("El número de tarjeta es: " + numeroTarjeta);

        System.out.println("Recuerde que inicia con un saldo inicial de " + SALDO_INICIAL);

    }

    private static void comprar() throws RemoteException {

        String nombre_usuario;
        String contrasena;
        long numero_tarjeta;

        System.out.println("-------------Compra-------------\n");
        System.out.println("Ingrese el nombre de usuario:");
        nombre_usuario = in.next();
        System.out.println("Ingrese el numero de tarjeta:");
        numero_tarjeta = in.nextLong();
        System.out.println("Ingrese la clave:");
        contrasena = in.next();

        boolean usuarioRegistrado = look_up_coordinador.validarUsuario(nombre_usuario, numero_tarjeta, contrasena);
        if (usuarioRegistrado) {
            productos = look_up_coordinador.obtenerProductos();
            System.out.println("<< Bienvenido a la tienda virtual >>");
            System.out.println("<<     Productos de la tienda     >>");
            if (productos != null) {
                System.out.println("<< Alimentos >>");
                int i = 1 ;
                for (Producto producto : productos) {
                    if (producto.getTipo().equals("Alimento")) {
                        System.out.println(i+". "+producto.getNombre() + "   " + producto.getPrecio());
                        i++;
                    }
                }
                
                System.out.println("<< Aseo >>");
                for (Producto producto : productos) {
                    if (producto.getTipo().equals("Aseo")) {
                        System.out.println(producto.getNombre() + "   " + producto.getPrecio());
                        i++;
                    }
                }
                
                System.out.println("<< Ropa >>");
                for (Producto producto : productos) {
                    if (producto.getTipo().equals("Ropa")) {
                        System.out.println(i+". "+producto.getNombre() + "   " + producto.getPrecio());
                        i++;
                    }
                }
                
                System.out.println("<< Salud >>");
                for (Producto producto : productos) {
                    if (producto.getTipo().equals("Salud")) {
                        System.out.println(i+". "+producto.getNombre() + "   " + producto.getPrecio());
                        i++;
                    }
                }
                
                System.out.println("<< Utiles escolares >>");
                for (Producto producto : productos) {
                    if (producto.getTipo().equals("Utiles escolares")) {
                        System.out.println(i+". "+producto.getNombre() + "   " + producto.getPrecio());
                        i++;
                    }
                }
            }

            int opcion = -1;
            boolean mostrarOpciones = true;
            while (opcion != 5) {
                if (mostrarOpciones) {
                    System.out.println ("================================");
                    System.out.println("<<       MENU DE COMPRA      >>");
                    System.out.println("1. Agregar producto al carrito de compras");
                    System.out.println("2. Modificar cantidades de un producto del carrito de compras");
                    System.out.println("3. Eliminar producto del carrito de compras");
                    System.out.println("4. << CONFIRMAR COMPRA >>");
                    System.out.println("5. << CANCELAR COMPRA >>");
                    System.out.println ("================================");
                }
                System.out.print("Opción: ");
                opcion = in.nextInt();
                mostrarOpciones = true;

                switch (opcion) {
                    case AGREGAR_PRODUCTO:
                        agregarProducto();
                        break;
                    case MODIFICAR_CANTIDADES:
                        break;
                    case ELIMINAR_PRODUCTO:
                        break;
                    case CONFIRMAR_COMPRA:
                        break;
                    case CANCELAR_COMPRA:
                        break;
                    default:
                        System.out.println("Opcion incorrecta, inténtelo nuevamente.");
                        mostrarOpciones = false;
                }
            }

        } else {
            System.out.println("El usuario no está registrado, no se pudo iniciar la transacción.");
        }
    }
    
    
    private static void agregarProducto (){
        System.out.println ("Ingrese el número del producto que desea agregar al carrito de compras:");
        
        System.out.println ("Ingrese la cantidad: ");
        //Lectura
        //Escritura
    }
}
