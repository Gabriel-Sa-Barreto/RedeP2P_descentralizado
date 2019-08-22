/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverAndClient;

import controller.ControllerCartorio;
import controller.ControllerDocumento;
import controller.ControllerPessoa;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import model.Documento;

/**
 *
 * @author Gabriel Sá e Samuel Vitorio
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
                    case "Sucesso-Login": //mensegm de confirmacao do login
                        //coloca a variavel com valor "Sucesso-Login" para permitir acesso no sistema
                        ControllerCartorio.setLoginCartorio(pacote); 
                        servidor.close(); //fecha o socket
                        setStart(false);
                        break;
                    case "Login-Failed":
                        //coloca a variavel com valor "Login-Failed" para informar o acesso negado ao sistema
                        ControllerCartorio.setLoginCartorio(pacote);     
                        servidor.close();//fecha o socket
                        setStart(false);
                        break;
                    case "Sucesso-Doc": //mensagem de aviso do servidor ao qual informa que um registro de documento foi feito com sucesso.
                        servidor.close();//fecha o socket
                        setStart(false);
                        break;
                    case "CadSucesso": //mensagem de aviso do servidor ao qual informa que um cadastro de pessoa física ou jurídica foi realizado com sucesso.
                        ControllerCartorio.setCadSuccessfully(pacote.trim());
                        servidor.close();//fecha o socket
                        setStart(false);
                        break;
                    case "CNPJ-Fail":
                        //mensagem de aviso do servidor ao qual informa que um cadastro de pessoa jurídica não foi realizado devido ao CNPJ invalido informado.
                        ControllerCartorio.setCadSuccessfully(pacote.trim());
                        servidor.close();//fecha o socket
                        setStart(false);
                        break;
                    case "CPF-Fail":    
                        //mensagem de aviso do servidor ao qual informa que um cadastro de pessoa fisica não foi realizado devido ao CPF invalido informado.
                        ControllerCartorio.setCadSuccessfully(pacote.trim());
                        servidor.close(); //fecha o socket
                        setStart(false);
                        break;
                    case "Ass-Fail":
                        //mensagem de aviso do servidor ao qual informa que um cadastro de pessoa fisica ou juridica não foi realizado devido a assinatura invalido informado.
                        ControllerCartorio.setCadSuccessfully(pacote.trim());
                        servidor.close(); //fecha o scoket
                        setStart(false);
                        break;
                    case "Acabou":
                        //mensagem que informa o fim da transmissao das informacoes dos documentos
                        servidor.close(); //fecha o socket
                        setStart(false);
                        break;
                    default:
                        //código responsável por receber a lista de documentos de uma pessoa que realizou uma solicitação.  
                        String[] docSplit = pacote.split(";");
                        Documento doc = new Documento(docSplit[0],docSplit[1]);
                        ControllerDocumento.addDocs(doc);
                        break;
                }
            }catch(IOException ex){
                System.out.println(ex.toString());
            }
        }
    }
}
