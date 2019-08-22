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
import controller.ControllerRede;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Documento;
import model.PessoaFisica;
import model.PessoaJuridica;

/**
 *
 * @author  Samuel Vitorio e Gabriel
 */
public class TrataCliente implements Runnable{
    
    /**
     * Atributo que gerencia as ações referente a pessoa física e jurídica.
     */
    private ControllerPessoa controllerPessoa;
    
    /**
     * Atributo que gerencia as ações referente a pessoa física e jurídica.
     */
    private ControllerRede rede;
        
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
        this.rede = new ControllerRede();
    } 

    @Override
    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(cliente);
            int opcao = entrada.readInt(); //acao a ser realizada com a informacao
            if(opcao == 1){
                int reenvio = entrada.readInt();    //opcao de reenvio
                String arquivo = entrada.readUTF();     //nome do arquivo
                String assDigitalP = entrada.readUTF(); //assinatura da pessoa para associar ao arquivo.
                String caminho ="../Arquivos/" +  assDigitalP.trim();//diretorio do novo arquivo
                File file = new File(caminho);
                //caso for o primeiro document do usuario e nao possuir um diretorio para ele
                if(!file.exists())
                    controllerPessoa.criarDiretorio(caminho);//criacao de um diretorio para o usuario
                caminho = caminho +  "/" + arquivo;
                ControllerDocumento.receiveFile(caminho, cliente);//salvar arquivo
                //verificacao do novo arquivo
                if(file.canRead()){
                    servidor.distribuiMensagem("Sucesso-Doc"); //confirmacao do upload do documento
                    String assDocumento = assDigitalP.trim() + arquivo; // criacao da assinatura digital do documento
                    ControllerArquivo.escreverDocumento(caminho , assDigitalP.trim(), assDocumento);
                }
                if(reenvio == 1) //reenvia para os outros cartorios
                    rede.enviarArquivos(opcao, assDigitalP , caminho);
            }  
            
            if(opcao == 2){ //cadastrar pessoa física
                int reenvio = entrada.readInt(); //opcao de reenvio
                String pacotePessoa = entrada.readUTF();//dados de pessoa física
                PessoaFisica p = ControllerPacotes.repartirPacotePF(pacotePessoa);
                //procura se exite assinatura digital em pessoas fisicas e juridicas
                if(controllerPessoa.busca_pessoaAssinatura(p.getAssinatura_digital(), 0) || controllerPessoa.busca_pessoaAssinatura(p.getAssinatura_digital(), 1)){
                    servidor.distribuiMensagem("Ass-Fail"); //cadastro de pessoa física falhou.
                }
                //procura se ja existe cpf
                else if(controllerPessoa.busca_pessoaFisica(p.getCPF()) != null)
                    servidor.distribuiMensagem("CPF-Fail");//cadastro de pessoa física falhou.
                else{    
                    //cadastra a pessoa fisica
                    controllerPessoa.cadastrarPessoa_fisica(p.getNome(), p.getAssinatura_digital(), p.getCPF(), p.getSenha());
                    ControllerArquivo.escreverPessoaFisica(p);//escrevi no arquivo
                    controllerPessoa.criarDiretorio("../Arquivos/"+ p.getAssinatura_digital());//criacao de um diretorio para o usuario   
                    servidor.distribuiMensagem("CadSucesso"); //cadastro de pessoa física com sucesso.
                    if(reenvio == 1){
                        rede.transmitirDados(opcao,pacotePessoa);
                    } 
                }
            }
            
            if(opcao == 3) {//solicitação de login por um usuário.
                int reenvio = entrada.readInt(); //opcao de reenvio
                String login = entrada.readUTF(); // pacote contendo as informacoes de login
                String[] dados = login.split(";");
                System.out.println(dados[0].trim());
                System.out.println(dados[1].trim());
                //verifica se exite um usuario com a assinatura digital e senha compativeis
                if( controllerPessoa.existePessoa(dados[0].trim(), dados[1].trim(),0) == 1 || controllerPessoa.existePessoa(dados[0].trim(), dados[1].trim(),1) == 1 ) {
                    //caso a senha e assinatura seja de uma pessoa física ou jurídica.
                    servidor.distribuiMensagem("Sucesso-Login");
                }else{
                    //caso nao exista o usuario 
                    servidor.distribuiMensagem("Login-Failed");
                }
            }
            
            
            if(opcao == 4){ //cadastrar pessoa jurídica
                int reenvio = entrada.readInt(); //opcao de reenvio
                String pacotePessoa = entrada.readUTF();//dados de pessoa jurídica
                PessoaJuridica p = ControllerPacotes.repartirPacotePJ(pacotePessoa);
                //procura se exite assinatura digital em pessoas fisicas e juridicas
                if(controllerPessoa.busca_pessoaAssinatura(p.getAssinatura_digital(), 0) || controllerPessoa.busca_pessoaAssinatura(p.getAssinatura_digital(), 1)){
                    servidor.distribuiMensagem("Ass-Fail"); //cadastro de pessoa jurídica falhou.
                }
                //procura se ja existe cnpj
                else if(controllerPessoa.busca_pessoaJuridica(p.getCNPJ()) != null)
                    servidor.distribuiMensagem("CNPJ-Fail");
                else{
                    //cadastro da pessoa juridica
                    controllerPessoa.cadastrarPessoa_juridica(p.getNome(), p.getAssinatura_digital(), p.getCNPJ(), p.getSenha());
                    ControllerArquivo.escreverPessoaJuridica(p);
                    controllerPessoa.criarDiretorio("../Arquivos/"+ p.getAssinatura_digital());//criacao de um diretorio para o usuario
                    servidor.distribuiMensagem("CadSucesso");//cadastro de pessoa jurídica com sucesso.
                }
                if(reenvio == 1){
                    //reenvia a informacao
                    rede.transmitirDados(opcao,pacotePessoa);
                }
            }
            
            if(opcao == 5){//recebe uma solicitação de um usuário para visualização dos seus arquivos submetidos. 
                int reenvio = entrada.readInt(); //opcao de reenvio
                String pacotePessoa = entrada.readUTF();//assinatura digital da pessoa que logou
                //todos os documentos com seus dados
                List<Documento> docs = ControllerArquivo.leitorDocumento(pacotePessoa.trim());
                //envia para o cliente que solicitou
                ControllerDocumento.enviarDoc(docs,servidor);
            }
            
            if(opcao == 6){
                int reenvio = entrada.readInt(); //opcao de reenvio
                String pacoteRede = entrada.readUTF();//dados do cliente da pessoa que logou
                String[] dados = pacoteRede.split(";");
                //informacoes do server do sistema que pediu o download do arquivo
                Client cliente = new Client(dados[1] ,ControllerPacotes.strToInt(dados[0], 1860));
                //diretorio do arquivo
                String file = ControllerArquivo.buscarDoc(dados[2].trim(), dados[3].trim());
                if(file != null)
                    ControllerDocumento.sendFile(file, cliente.getCliente() , null);   
            }      
        } catch (IOException ex) {
            Logger.getLogger(Recebedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
