/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmiinterface_banco;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author green
 */
public interface RMIInterfaceBanco extends Remote{
    
    public long registrarUsuario(String nombre_usuario, String contrasena) throws RemoteException;
    public boolean validarUsuario (String nombre_usuario, long numero_tarjeta, String contrasena) throws RemoteException;
    public boolean verificarSaldo (long numTarjeta, long costoCompra) throws RemoteException;
    public boolean disminuirSaldo (long numTarjeta, long costoCompra) throws RemoteException;
    public long consultarSaldo (long numTarjeta) throws RemoteException;
    public boolean aumentarSaldo (long numTarjeta, long monto) throws RemoteException;
    
}
