/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import serverAndClient.Client;

/**
 *
 * @author Samuel Vitorio e Gabriel Sá
 */
public class ControllerRede {
    
    /**
    * Método para enviar os pacotes desejados ao servidor.
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
     * Método para transmissão de dados ao respectivos cartórios cadastrados.
     * @param opcao
     * @param control
     * @param dados 
     */
    public void transmitirDadosCartorio(int opcao, ControllerCartorio control, String dados){
        Client cliente;
        for(int i = 0 ; i < control.quantCartorio(); i++){
            boolean teste = true;
            while(teste){
                try {
                    cliente = new Client(control.busca(i).getIp(),control.busca(i).getPorta());
                    cliente.executa();
                    enviarDadoInt(cliente.getCliente(), opcao); //opcao do processamento da informacao
                    enviarDadoInt(cliente.getCliente(), 1); //opcao de reenvio dos cartorios que estao abertos
                    enviarDado(cliente.getCliente(),dados);
                    teste = false;
                } catch (IOException ex) {
                    teste = false;
                    ex.getMessage();
                }    
            }
        } 
    }   
}
