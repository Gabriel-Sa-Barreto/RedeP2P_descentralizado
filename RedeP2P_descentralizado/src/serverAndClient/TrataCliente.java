/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverAndClient;

import controller.ControllerDocumento;
import java.io.DataInputStream;
import java.io.File;
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
            DataInputStream entrada = new DataInputStream(cliente);
            int opcao = entrada.readInt(); //acao a ser realizada com a informacao
            if(opcao == 1){
                String arquivo = entrada.readUTF();//nome do arquivo
                String caminho ="../Arquivos/" + arquivo;//diretorio do novo arquivo
                ControllerDocumento.receiveFile(caminho, cliente);//salvar arquivo
                //verificacao do novo arquivo
                File file = new File(caminho);
                if(file.canRead()){
                    servidor.distribuiMensagem("Sucesso");
                    System.out.println("Sucesso1");
                } 
            }    
        } catch (IOException ex) {
            Logger.getLogger(Recebedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
