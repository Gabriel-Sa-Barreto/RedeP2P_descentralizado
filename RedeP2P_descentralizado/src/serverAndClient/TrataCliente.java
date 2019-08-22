/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverAndClient;

import controller.ControllerDocumento;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lsjsa
 */
public class TrataCliente implements Runnable{
    /**
     * InputStream do cliente.
     */
    private InputStream cliente;

    /**
     * Objeto do Servidor para realizar ações de envio de mensagem a todos os cliente conectados.
     */
    private Servidor servidor;
    
    /**
     * Construtor que inicializa todos os atributos da classe.
     * @param cliente  - InputStream do cliente conectado.
     * @param servidor - Objeto servidor
     */
    public TrataCliente(InputStream cliente, Servidor servidor){
        this.cliente  = cliente;
        this.servidor = servidor;
    } 

    @Override
    public void run() {
        try {
            //receber o arquivo do cartorio
            ControllerDocumento.receiveFile(ControllerDocumento.getPathToSaveFile() + "/" + ControllerDocumento.getNomeDocToSave(), cliente);//salvar arquivo
        } catch (IOException ex) {
            Logger.getLogger(Recebedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
