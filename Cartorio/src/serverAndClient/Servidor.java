/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverAndClient;

import controller.ControllerArquivo;
import controller.ControllerPessoa;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author lsjsa
 */
public class Servidor implements Runnable{
    /**Atributo que guarda o servidor.
     */
    private ServerSocket servidor;
    
    /**Atributo que guarda a porta do servidor.
     */
    private int porta;
       
    /**Atributo que guarda o PrintStream do cliente conectado com o servidor.
     */
    private PrintStream cartorio;
    
    /**Construtor que inicializa os atributos da classe
     * @param porta  -parâmetro que informa a porta de conexão do servidor
     * @throws java.io.IOException     
    */
    public Servidor (int porta) throws IOException{
        this.porta = porta; //Porta ao qual o servidor irá se associar.
        criarConexão();
        ControllerArquivo.leitorPessoaFisica("../Banco/fisica.txt", new ControllerPessoa());
        ControllerArquivo.leitorPessoaJuridica("../Banco/juridico.txt", new ControllerPessoa());
        //ControllerPessoa.getPessoasFisicas().forEach(u -> System.out.println(u.toString()));
        new Thread(this).start(); //executa a thread do servidor 
    }
    
    /***Método que starta o servidor.
     * @author Gabriel Sá Barreto Alves e Samuel Vitorio Lima
     */
    private void criarConexão() throws IOException{
        servidor = new ServerSocket(this.porta);
        System.out.println("Porta 12345 aberta!");    
    }
    
    @Override
    public void run() {
        while (true) {
            //aceita um cliente
            Socket cliente = null;
            try {
                cliente = servidor.accept();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
            
            // adiciona saida do cliente à lista
            PrintStream ps = null;
            try {
                ps = new PrintStream(cliente.getOutputStream());
            } catch (IOException ex) {
               ex.printStackTrace();
            }
            cartorio = ps;
            // cria tratador de cliente numa nova thread
            TrataCliente tc = null;
            try {
               tc = new TrataCliente(cliente.getInputStream(), this);
            } catch (IOException ex) {
               ex.printStackTrace();
            }
            //nova thread que irá tratar um novo cliente que foi conectado.
            new Thread(tc).start();
        }
    }
    
    /**Método que envia a todos os cliente conectados pacotes específicos para armazenamento de informações e outros dados.
     *@param msg String a ser enviada para o cliente
     */
    public synchronized void distribuiMensagem(String msg) throws IOException {
        // envia msg a todos os clientes conectados
            DataOutputStream saida = new DataOutputStream(cartorio);
            saida.writeUTF(msg);
            System.out.println("Entrou");
            //saida.close();
    }
}
