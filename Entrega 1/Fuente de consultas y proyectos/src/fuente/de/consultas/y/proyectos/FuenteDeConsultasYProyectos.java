/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuente.de.consultas.y.proyectos;

/**
 *
 * @author LENOVO PC
 */
public class FuenteDeConsultasYProyectos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LeeFichero funcion = new LeeFichero();
        Fuente fuente = funcion.leer();

        for (Consulta str : fuente.getConsultas()) {
            //imprimimos el objeto pivote
            System.out.println(str.getFecha().toString());
        }
        fuente.solicitarConexión();
        /*while (true){
        fuente.start();
        }*/
    }

}
