/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiinterface_servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import clasesrmi.Producto;
import clasesrmi.Transaccion;

/**
 *
 * @author green
 */
public interface RMIInterfaceServidor extends Remote{
    
   public List<Producto> obtenerProductos () throws RemoteException;
   public boolean prepararCommit (Transaccion transaccion) throws RemoteException;
   public boolean commit () throws RemoteException;
   public boolean abortar () throws RemoteException;
   
   public boolean existeConexion () throws RemoteException;
    
}
