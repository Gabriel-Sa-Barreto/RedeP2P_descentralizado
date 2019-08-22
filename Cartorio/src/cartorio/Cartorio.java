/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartorio;

import controller.ControllerCartorio;
import java.io.IOException;
import serverAndClient.Servidor;

/**
 *
 * @author gabriel
 */
public class Cartorio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Servidor server = new Servidor(1890); //criacao do servidor cartorio
        //ControllerCartorio.cadastrar(1860,"10.0.0.107");  //cartorio 1
        //ControllerCartorio.cadastrar(1880,"192.168.25.4");  //cartório 2
    }
    
}
