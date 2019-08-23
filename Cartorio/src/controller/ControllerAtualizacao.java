/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import model.Atualizacao;

/**
 *
 * @author lsjsa
 */
public class ControllerAtualizacao {
    
    /**
     * Atributo que armazena todos os pacotes nao enviados
     */
    private static List<Atualizacao> lista = new ArrayList<>();
    
    /**
     * Atributo que permite realizar atualizações para outros cartórios ou não.
     */
    private static int permiteAtualizacao = 0;
    
    
    /**
     * Metodo que adiciona na lista
     * @param atualizacao 
     */
    public synchronized static void add(Atualizacao atualizacao){
        lista.add(atualizacao);
    }
    
    /**
     * Metodo que remove da lista
     * @param indice 
     * @return  
     */
    public synchronized static Atualizacao remove(int indice){  
        return lista.remove(indice);
    }
    
    /**
     * Metodo que pega a primeira celula da lista
     * @return 
     */
    public synchronized static Atualizacao primeiroDaLista(){
        return lista.get(0);
    }
    
    /**
     * Método que retorna se a lista está vazia ou não.
     * @return 
     */
    public synchronized static boolean isEmpty(){
        return lista.isEmpty();
    }

    /**
     * Método que retorna o status da permissão de atualização.
     * @return 
     */
    public synchronized static int getPermiteAtualizacao() {
        return permiteAtualizacao;
    }

    /**
     * Método que configura o status de permissão das atualizações.
     * @param permiteAtualizacao 
     */
    public synchronized static void setPermiteAtualizacao(int permiteAtualizacao) {
        ControllerAtualizacao.permiteAtualizacao = permiteAtualizacao;
    }    
}
