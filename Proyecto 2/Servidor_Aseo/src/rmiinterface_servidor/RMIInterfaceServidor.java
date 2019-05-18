/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiinterface_servidor;

import clasesrmi.Producto;
import clasesrmi.Transaccion;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author green
 */
public interface RMIInterfaceServidor extends Remote{
    
   public List<Producto> obtenerProductos () throws RemoteException;
   public boolean prepararCommit (Transaccion transaccion) throws RemoteException;
   public boolean commit () throws RemoteException;
   public boolean abortar () throws RemoteException;
    
}
