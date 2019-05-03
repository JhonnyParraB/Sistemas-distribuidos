/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuente.de.consultas.y.proyectos;

import ClasesdeComunicacion.Consulta;

/**
 *
 * @author LENOVO PC
 */
public class ConsultaConteo {
    private int alto;
    private int medio;
    private int bajo;
    private Consulta consulta;

    public ConsultaConteo(Consulta consulta) {
        this.alto = 0;
        this.medio = 0;
        this.bajo = 0;
        this.consulta = consulta;
    } 
    
    public void sumarAlto(){
        alto++;
    }
    public void sumarMedio(){
        medio++;
    }
    public void sumarBajo(){
        bajo++;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getMedio() {
        return medio;
    }

    public void setMedio(int medio) {
        this.medio = medio;
    }

    public int getBajo() {
        return bajo;
    }

    public void setBajo(int bajo) {
        this.bajo = bajo;
    }
    
    
    
    
    
    
}
