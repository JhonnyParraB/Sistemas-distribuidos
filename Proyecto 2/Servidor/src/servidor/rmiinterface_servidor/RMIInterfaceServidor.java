/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.rmiinterface_servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author green
 */
public interface RMIInterfaceServidor extends Remote{
    
    public long registrarUsuario(String nombre_usuario, String contrasena) throws RemoteException;
    
}
