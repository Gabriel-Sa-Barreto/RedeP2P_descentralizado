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
        Servidor server = new Servidor(1860);
        ControllerCartorio.cadastrar(1880, "127.0.0.1");
    }
    
}
