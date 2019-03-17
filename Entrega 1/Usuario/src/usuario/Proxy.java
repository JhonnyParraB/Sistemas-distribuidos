/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuario;

import java.io.Serializable;

/**
 * Clase que representa la información para poder conectarse a un proxy
 * @author LENOVO PC
 */
public class Proxy implements Serializable{
    private String IP;
    private int puerto;
    private int numeroClientes;

    public Proxy() {
        numeroClientes = 0;
    }
    
    

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public int getNumeroClientes() {
        return numeroClientes;
    }
    
    
    
}
