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
        Client cliente;
        for(int i = 0 ; i < ControllerCartorio.quantCartorio(); i++){
            boolean teste = true;
            while(teste){
                try {
                    cliente = new Client(ControllerCartorio.busca(i).getIp(),ControllerCartorio.busca(i).getPorta());
                    cliente.executa();
                    enviarDadoInt(cliente.getCliente(), opcao);
                    enviarDadoInt(cliente.getCliente(), 0);
                    ControllerDocumento.sendFile(caminho, cliente.getCliente() , assP);
                    cliente.fecharConexão();
                    cliente = new Client(ControllerCartorio.busca(i).getIp(),ControllerCartorio.busca(i).getPorta());
                    cliente.executa();
                    teste = false;
                } catch (IOException ex) {
                    ex.getMessage();
                }    
            }
        }
    }
}
