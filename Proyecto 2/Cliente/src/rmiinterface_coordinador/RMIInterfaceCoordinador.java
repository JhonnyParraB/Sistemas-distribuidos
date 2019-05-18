/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiinterface_coordinador;

import clasesrmi.Producto;
import clasesrmi.Transaccion;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author green
 */
public interface RMIInterfaceCoordinador extends Remote{
    
    public int sumar(int a, int b) throws RemoteException;
    public long registrarUsuarioBanco(String nombre_usuario, String contrasena) throws RemoteException;
    public boolean validarUsuario (String nombre_usuario, long numero_tarjeta, String contrasena) throws RemoteException;

    public List<Producto> obtenerProductos() throws RemoteException;
    public boolean finalizarTransaccion (Transaccion transaccion) throws RemoteException;
    
}
