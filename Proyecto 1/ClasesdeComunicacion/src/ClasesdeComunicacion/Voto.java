/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClasesdeComunicacion;

import java.io.Serializable;

/**
 *
 * @author LENOVO PC
 */
public class Voto implements Serializable {
    private Consulta consulta;
    private int IDUsuario;
    private String aprobacion;

    public Voto(Consulta consulta, int IDUsuario, String aprobacion) {
        this.consulta = consulta;
        this.IDUsuario = IDUsuario;
        this.aprobacion = aprobacion;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    

    public int getIDUsuario() {
        return IDUsuario;
    }

    public void setIDUsuario(int IDUsuario) {
        this.IDUsuario = IDUsuario;
    }

    public String getAprobacion() {
        return aprobacion;
    }

    public void setAprobacion(String aprobacion) {
        this.aprobacion = aprobacion;
    }
    
    
    
    
}
