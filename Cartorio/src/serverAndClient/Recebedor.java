/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverAndClient;

import controller.ControllerCartorio;
import controller.ControllerDocumento;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import model.Documento;

/**
 *
 * @author lsjsa
 */
public class Recebedor implements Runnable{
    /**
     * Atributo que receberá o Inputstream do servidor conectado.
     */
    private InputStream servidor;
    
    /**
     * Atributo que configura se as atividades da serão executadas.
     */
    private boolean start;
    
    /**Construtor da classe Recebedor que inicia todos os atributos. 
     * @author Samuel Vitorio Lima e Gabriel Sá Barreto 
     * @param servidor - Atributo responsável por recebdor o objeto InputStream do servidor
     */
    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
        this.start    = true;
    }

    /**Método responsável por configurar se as atividades que a classe executa serão implementadas pelo
     * método run da interace Runnable.
     * @author Samuel Vitorio Lima e Gabriel Sá Barreto 
     * @param start 
     */
    public void setStart(boolean start) {
        this.start = start;
    }
    
    /**Método da interface Runnable responsável por executar todas as atividades da thread quando for iniciada.
     * @author Samuel Vitorio Lima e Gabriel Sá Barreto 
     */
    @Override
    public void run() {
        while(start){
            // recebe msgs do servidor e imprime na tela    
            String pacote;
            DataInputStream entrada = new DataInputStream(this.servidor);
            try{
                pacote = entrada.readUTF();
                switch(pacote) {
                    case "Sucesso-Login":
                        servidor.close();
                        setStart(false);
                        break;
                    case "Login-Failed":
                        servidor.close();
                        setStart(false);
                        break;
                    case "Sucesso-Doc": //mensagem de aviso do servidor ao qual informa que um registro de documento foi feito com sucesso.
                        servidor.close();
                        setStart(false);
                        break;
                    case "CadSucesso": //mensagem de aviso do servidor ao qual informa que um cadastro de pessoa física ou jurídica foi realizado com sucesso.
                        servidor.close();
                        setStart(false);
                        break;
                    case "CNPJ-Fail":    
                        servidor.close();
                        setStart(false);
                        break;
                    case "CPF-Fail":    
                        servidor.close();
                        setStart(false);
                        break;
                    case "Ass-Fail":
                        servidor.close();
                        setStart(false);
                        break;
                    case "Acabou":
                        servidor.close();
                        setStart(false);
                        break;
                }
            }catch(IOException ex){
                System.out.println(ex.toString());
            }
        }
    }
}
