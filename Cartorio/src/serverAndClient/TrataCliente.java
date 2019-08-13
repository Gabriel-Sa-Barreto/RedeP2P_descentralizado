/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverAndClient;

import controller.ControllerArquivo;
import controller.ControllerDocumento;
import controller.ControllerPacotes;
import controller.ControllerPessoa;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PessoaFisica;

/**
 *
 * @author lsjsa
 */
public class TrataCliente implements Runnable{
    
    /**
     * Atributo que gerencia as ações referente a pessoa física e jurídica.
     */
    private ControllerPessoa controllerPessoa;
        
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
        this.controllerPessoa = new ControllerPessoa();
        
    } 

    @Override
    public void run() {
        //ControllerPessoa.getPessoasFisicas().forEach(u -> System.out.println(u.toString()));
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
                    servidor.distribuiMensagem("Sucesso-Doc");
                } 
            }  
            
            if(opcao == 2){
                String pacotePessoa = entrada.readUTF();//dados de pessoa física
                PessoaFisica p = ControllerPacotes.repartirPacotePF(pacotePessoa);
                System.out.println(pacotePessoa);
                controllerPessoa.cadastrarPessoa_fisica(p.getNome(), p.getAssinatura_digital(), p.getCPF(), p.getSenha());
                ControllerArquivo.escreverPessoaFisica(p);
            }
            
            if(opcao == 3) {
                String login = entrada.readUTF();
                String[] dados = login.split(";");
                if( controllerPessoa.existePessoa(dados[0].trim(), dados[1].trim(),0) == 1 || controllerPessoa.existePessoa(dados[0], dados[1],1) == 1 ) {
                    //caso a senha e assinatura seja de uma pessoa física ou jurídica.
                    servidor.distribuiMensagem("Sucesso-Login");
                }     
            }
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Recebedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
