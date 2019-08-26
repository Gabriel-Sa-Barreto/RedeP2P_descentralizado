/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author lsjsa
 */
public class Atualizacao {
    /**
     * Atributo que armazena o ip do server que nao recebeu o pacote
     */
    private String ip;
    
    /**
     * Atributo que armazena a porta do server que nao recebeu o pacote
     */
    private int porta;
    
    /**
     * Atributo que armazena o pacote
     */
    private String pacote;
    
    /**
     * Atributo que pega a opcao de processamento do pacote
     */
    private int opcao;
    
    /**
     * Atributo que guarda a assinatura digital da pessoa que tentou enviar o pacote.
     */
    private String ass;
    
    
    /**
     * Metodo construtor da model Atualizacao
     * @param ip
     * @param porta
     * @param pacote 
     */
    public Atualizacao(String ip, int porta, String pacote, int opcao) {
        this.ip = ip;
        this.porta = porta;
        this.pacote = pacote;
        this.opcao = opcao;
    }
    
    /**
     * Metodo construtor da model Atualizacao
     * @param ip
     * @param porta
     * @param pacote 
     */
    public Atualizacao(String ip, int porta, String pacote, int opcao, String ass) {
        this.ip = ip;
        this.porta = porta;
        this.pacote = pacote;
        this.opcao = opcao;
        this.ass = ass;
    }
    
    
    
    /**
     * Metodo que retorna o ip
     * @return 
     */
    public String getIp() {
        return ip;
    }
    
    /**
     * Metodo que retorna a porta
     * @return 
     */
    public int getPorta() {
        return porta;
    }
    
    /**
     * Metodo que retorna o pacote
     * @return 
     */
    public String getPacote() {
        return pacote;
    }
    
    /**
     * Metodo que retorna a opcao de processamento
     * @return 
     */
    public int getOpcao() {
        return opcao;
    }

    /**
     * Método que retorna a assinatura digital da pessoa que tentou enviar o pacote.
     * @return 
     */
    public String getAss() {
        return ass;
    }    
}
