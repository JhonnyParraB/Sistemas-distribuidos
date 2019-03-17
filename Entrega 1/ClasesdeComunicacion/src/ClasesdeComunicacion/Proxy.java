/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesdeComunicacion;

import java.io.Serializable;

/**
 * Clase que representa la información para poder conectarse a un proxy
 * @author LENOVO PC
 */
public class Proxy implements Serializable {
    private String IP;
    private int puertoFuentes;
    private int puertoClientes;
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

    public int getPuertoFuentes() {
        return puertoFuentes;
    }

    public void setPuertoFuentes(int puertoFuentes) {
        this.puertoFuentes = puertoFuentes;
    }

    public int getPuertoClientes() {
        return puertoClientes;
    }

    public void setPuertoClientes(int puertoClientes) {
        this.puertoClientes = puertoClientes;
    }

    public int getNumeroClientes() {
        return numeroClientes;
    }

    public void adicionarCliente() {
        this.numeroClientes++;
    }    
}
