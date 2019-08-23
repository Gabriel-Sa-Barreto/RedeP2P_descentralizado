/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import model.AtualizaCartorio;
import model.Atualizacao;
import serverAndClient.Client;

/**
 *
 * @author lsjsa
 */
public class ControllerRede {
    
    /**Método para enviar os pacotes desejados ao servidor.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param conexao Socket - obter a conexao com o server para enviar os pacotes para ele via TCP
    * @param dado String - o dado a ser enviado
    */
    public void enviarDado(Socket conexao , String dado){
        try{
            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            saida.writeUTF(dado);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    } 
    
    /**
     * Método que envia ao servidor do cartório a ação que será realizada. 
     * @param conexao
     * @param opcao 
     */
    public void enviarDadoInt(Socket conexao , int opcao){
        try{
            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            saida.writeInt(opcao);
            //saida.flush();
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    /**
     * Método que reenvia os arquivos para outros cartorios
     * @param opcao 
     * @param assP 
     * @param caminho 
     */
    public void enviarArquivos(int opcao , String assP , String caminho){
        //retira permissão de atualização dos cartórios.
        ControllerAtualizacao.setPermiteAtualizacao(0);
        
        Client cliente;
        for(int i = 0 ; i < ControllerCartorio.quantCartorio(); i++){
            boolean teste = true;
            while(teste){
                try {
                    cliente = new Client(ControllerCartorio.busca(i).getIp(),ControllerCartorio.busca(i).getPorta());
                    cliente.executa();
                    //opcao da acao a ser processado
                    enviarDadoInt(cliente.getCliente(), opcao);
                    //envia se essa informacao a ser mandada deve ser reenviada para os outros sockets
                    enviarDadoInt(cliente.getCliente(), 0);
                    //envia o arquivo
                    ControllerDocumento.sendFile(caminho, cliente.getCliente() , assP);
                    cliente.fecharConexão();
                    //devido ao fechamento do socket no metodo sendFile para receber a confirmacao do salvamento deve se abrir o socket novamente
                    cliente = new Client(ControllerCartorio.busca(i).getIp(),ControllerCartorio.busca(i).getPorta());
                    cliente.executa();
                    teste = false;
                } catch (IOException ex) {
                    if(ControllerAtualizacao.isEmpty()){ 
                        //caso der falha em um envio de pacote e a lista de reenvio estiver vazia, inicia a thread de atualização.
                        AtualizaCartorio atualizacoes = new AtualizaCartorio();
                    }                 
                    //caso ocorra a excecao de conection refused guarda o pacote que nao foi enviado para quando tiver online possa receber
                    Atualizacao inserir = new Atualizacao(ControllerCartorio.busca(i).getIp() , ControllerCartorio.busca(i).getPorta() , caminho , opcao, assP);
                    ControllerAtualizacao.add(inserir);
                    ex.getMessage();
                }    
            }
        }
        //depois de enviado (realizado uma tentativa) o arquivo para todos os cartórios,
        //é dado permissão para a thread de atualização poder verificar se algum cartório deu falha
        //caso sim, a thread tenta reenvia-lo periodicamente quando dado permissão.
        ControllerAtualizacao.setPermiteAtualizacao(1);
        
        
    }
    
    /**
     * Método para transmissão de dados ao respectivos cartórios cadastrados.
     * @param opcao
     * @param dados 
     */
    public void transmitirDados(int opcao, String dados){
        //retira permissão de atualização dos cartórios.
        ControllerAtualizacao.setPermiteAtualizacao(0);
        Client cliente;
        for(int i = 0 ; i < ControllerCartorio.quantCartorio(); i++){
            boolean teste = true;
            while(teste){
                try {
                    cliente = new Client(ControllerCartorio.busca(i).getIp(),ControllerCartorio.busca(i).getPorta());
                    cliente.executa();
                    enviarDadoInt(cliente.getCliente(), opcao); //opcao do processamento da informacao
                    enviarDadoInt(cliente.getCliente(), 0); //opcao de reenvio dos cartorios que estao abertos
                    enviarDado(cliente.getCliente(),dados); // o dado a ser processado
                    teste = false;
                } catch (IOException ex) {
                    if(ControllerAtualizacao.isEmpty()){ 
                        //caso der falha em um envio de pacote e a lista de reenvio estiver vazia, inicia a thread de atualização.
                        AtualizaCartorio atualizacoes = new AtualizaCartorio();
                    }
                    //caso ocorra a excecao de conection refused guarda o pacote que nao foi enviado para quando tiver online possa receber
                    Atualizacao inserir = new Atualizacao(ControllerCartorio.busca(i).getIp() , ControllerCartorio.busca(i).getPorta() , dados , opcao);
                    ControllerAtualizacao.add(inserir);
                    teste = false;
                    ex.getMessage();
                }    
            }
        } 
        //depois de enviados(realizado uma tentativa) os pacotes para todos os cartórios,
        //é dado permissão para a thread de atualização poder verificar se algum pacote deu falha
        //caso sim, a thread tenta reenvia-los periodicamente quando dado permissão.
        ControllerAtualizacao.setPermiteAtualizacao(1);
    }   
}
