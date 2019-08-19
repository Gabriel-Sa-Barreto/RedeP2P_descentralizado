/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Cartorio;

/**
 *
 * @author Samuel Vitorio e Gabriel Sá.
 */
public class ControllerCartorio {
    
    /**
     * Atributo que dar acesso ao sistema de cartórios.
     */
    private static String loginCartorio = new String();
    
    /**
     * Atributo que indica se uma ação de casdastro foi realizada com sucesso pelos cartórios.
     */
    private static String cadSuccessfully = new String();
     
    /**
     * Atributo que armazena a lista de cartórios existentes. 
     */
    private List<Cartorio> cartorios;
    
    /**
     * Construtor da classe ControllerCartorio
     */
    public ControllerCartorio() {
        this.cartorios = new ArrayList<>();
    }
    
    /**
     * Cadastramento de um cartorio
     * @param porta porta do server
     * @param ip ip do server
     */
    public void cadastrar(int porta , String ip){
        Cartorio cartorio;
        if(cartorios.isEmpty())
            cartorio = new Cartorio(0,porta , ip);
        else
            cartorio = new Cartorio(cartorios.size(),porta , ip);
        cartorios.add(cartorio);
    }
    
    /**
     * Busca um cartorio
     * @param indice indice onde esta armazenado o cartorio
     * @return Cartorio dados de um cartorio
     */
    public Cartorio busca(int indice){
        return cartorios.get(indice);
    }
    
    /**
     * Metodo que informa a quantidade de cartorios
     * @return int tamanho da lista
     */
    public int quantCartorio(){
        return cartorios.size();
    }

    /**
     * Método que retorna o estado atual do atributo loginCartorio.
     * Este método utiliza a palavra-chave synchronized, que não permite que mais de uma
     * thread ou classe, acesse esse método ao mesmo tempo.
     * @return 
     */
    public synchronized static String isLoginCartorio() {
        return loginCartorio;
    }

    
    /**
     * Método que seta o estado atual do atributo loginCartorio.
     * Este método utiliza a palavra-chave synchronized, que não permite que mais de uma
     * thread ou classe, acesse esse método ao mesmo tempo.
     * @param loginCartorio 
     */
    public synchronized static void setLoginCartorio(String loginCartorio) {
        ControllerCartorio.loginCartorio = loginCartorio;
    }

    /**
     * Método que verifica o valor atual do atributo cadSuccessfully.
     * Este método utiliza a palavra-chave synchronized, que não permite que mais de uma
     * thread ou classe, acesse esse método ao mesmo tempo.
     * @return 
     */
    public synchronized static String getCadSuccessfully() {
        return cadSuccessfully;
    }

    /**
     * Método que seta o valor atual do atributo cadSuccessfully.
     * Este método utiliza a palavra-chave synchronized, que não permite que mais de uma
     * thread ou classe, acesse esse método ao mesmo tempo.
     * @param cadSuccessfully 
     */
    public synchronized static void setCadSuccessfully(String cadSuccessfully) {
        ControllerCartorio.cadSuccessfully = cadSuccessfully;
    }    
    
    
    /**Método que limpa a tabela na interface
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param table javax.swing.JTable - limpar a tabela passada
    */
    public void limpaTabela(javax.swing.JTable table){
        DefaultTableModel tblRemove = (DefaultTableModel) table.getModel();
        while(tblRemove.getRowCount() != 0){ //remover todas celulas da tabela
            tblRemove.removeRow(0);
        }            
    }
    
    
}
