/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ControllerAtualizacao;
import controller.ControllerCartorio;
import controller.ControllerDocumento;
import controller.ControllerRede;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverAndClient.Client;

/**
 *
 * @author lablenda2
 */
public class AtualizaCartorio implements Runnable {
    
    /**
     * Atributo de conexão com um cliente.
     */
    private Client cliente;
    
    /**
     * Atributo responsável por disponibilizar os métodos para envio das informações.
     */
    private ControllerRede rede = new ControllerRede();
    
    /**
     * Construtor da classe: responsável por chamar o método que inicia a thread.
     */
    public AtualizaCartorio(){
        this.startThread();
    }
    
    
    /**
     * Método que inicia a thread de verificação de reenvio de dados.
     */
    private void startThread(){
        new Thread(this).start();
    }
    
    
    /**
     * Método que envia pacote de dados para outro cartório que necessita de atualização.
     * @param aux 
     */
    public void sendDados(Atualizacao aux){
        try {
            cliente = new Client(aux.getIp(), aux.getPorta()); //tenta conectar o cartório a outro cartório que deseja enviar as informações.
            cliente.executa();
            rede.enviarDadoInt(cliente.getCliente(),aux.getOpcao()); //opcao do processamento da informacao.
            rede.enviarDadoInt(cliente.getCliente(),0); //opcao de reenvio dos cartorios que estao abertos.
            rede.enviarDado(cliente.getCliente(),aux.getPacote());
            cliente.fecharConexão(); //depois de enviado o dados fecha a conexão com o cartório.
        } catch (IOException ex) {
            ControllerAtualizacao.add(aux); //caso ocorra falha no envio, adiciona novamente na lista para tentar reenviar depois.
        }    
    }  
    
    /**
     * Método que envia arquivo para outro cartório que necessita de atualização.
     * @param aux 
     */
    public void sendFile(Atualizacao aux){
        try {
            cliente = new Client(aux.getIp(), aux.getPorta()); //tenta conectar o cartório a outro cartório que deseja enviar as informações.
            cliente.executa();
            //opcao da acao a ser processado
            rede.enviarDadoInt(cliente.getCliente(), aux.getOpcao()); //opcao do processamento da informacao.
            //envia se essa informacao a ser mandada deve ser reenviada para os outros sockets
            rede.enviarDadoInt(cliente.getCliente(), 0); //opcao de reenvio dos cartorios que estao abertos.
            //envia o arquivo
            ControllerDocumento.sendFile(aux.getPacote(), cliente.getCliente() , aux.getAss());
            cliente.fecharConexão();
        } catch (IOException ex) {
            //caso ocorra a excecao de conection refused guarda o pacote que nao foi enviado para quando tiver online possa receber 
            ControllerAtualizacao.add(aux);
            ex.getMessage();
        }      
    }
    
    /**
     * Método que fica verificando se existe pacotes que necessitam ser reenviados para os devidos cartórios que antes
     * estavam indisponíveis.
     */
    @Override
    public void run() {
        while(true){
            if(!ControllerAtualizacao.isEmpty()){
                //caso tenha informações a serem enviadas.
                Atualizacao aux = ControllerAtualizacao.remove(0);
                if(aux.getOpcao() == 1){ //pacote de envio de arquivo
                    sendFile(aux);
                }else{
                    System.out.println("teste OK");
                    sendDados(aux); //envia outros pacotes (ex:cadastro)
                }
            }else{
                //quando não existir nenhuma informação.
                break; //encerra a thread.
            }
        }
    }
    
}
