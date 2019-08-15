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
import model.PessoaJuridica;

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
            
            if(opcao == 2){ //cadastrar pessoa física
                String pacotePessoa = entrada.readUTF();//dados de pessoa física
                PessoaFisica p = ControllerPacotes.repartirPacotePF(pacotePessoa);
                System.out.println(pacotePessoa);
                
                if(controllerPessoa.busca_pessoaAssinatura(p.getAssinatura_digital(), 0) || controllerPessoa.busca_pessoaAssinatura(p.getAssinatura_digital(), 1)){
                    servidor.distribuiMensagem("Ass-Fail"); //cadastro de pessoa física falhou.
                }
                else if(controllerPessoa.busca_pessoaFisica(p.getCPF()) != null)
                    servidor.distribuiMensagem("CPF-Fail");
                else{    
                    controllerPessoa.cadastrarPessoa_fisica(p.getNome(), p.getAssinatura_digital(), p.getCPF(), p.getSenha());
                    ControllerArquivo.escreverPessoaFisica(p);
                    servidor.distribuiMensagem("CadSucesso"); //cadastro de pessoa física com sucesso.
                }
            }
            
            if(opcao == 3) {
                String login = entrada.readUTF();
                String[] dados = login.split(";");
                if( controllerPessoa.existePessoa(dados[0].trim(), dados[1].trim(),0) == 1 || controllerPessoa.existePessoa(dados[0], dados[1],1) == 1 ) {
                    //caso a senha e assinatura seja de uma pessoa física ou jurídica.
                    servidor.distribuiMensagem("Sucesso-Login");
                }     
            }
            
            
            if(opcao == 4){ //cadastrar pessoa jurídica
                String pacotePessoa = entrada.readUTF();//dados de pessoa jurídica
                PessoaJuridica p = ControllerPacotes.repartirPacotePJ(pacotePessoa);
                System.out.println(pacotePessoa);
                if(controllerPessoa.busca_pessoaAssinatura(p.getAssinatura_digital(), 0) || controllerPessoa.busca_pessoaAssinatura(p.getAssinatura_digital(), 1)){
                    servidor.distribuiMensagem("Ass-Fail"); //cadastro de pessoa jurídica falhou.
                }
                else if(controllerPessoa.busca_pessoaJuridica(p.getCNPJ()) != null)
                    servidor.distribuiMensagem("CNPJ-Fail");
                else{
                    controllerPessoa.cadastrarPessoa_juridica(p.getNome(), p.getAssinatura_digital(), p.getCNPJ(), p.getSenha());
                    ControllerArquivo.escreverPessoaJuridica(p);
                    servidor.distribuiMensagem("CadSucesso");//cadastro de pessoa jurídica com sucesso.
                }
            }
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Recebedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
