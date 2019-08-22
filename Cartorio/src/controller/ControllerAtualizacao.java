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
     * Metodo que adiciona na lista
     * @param atualizacao 
     */
    public static void add(Atualizacao atualizacao){
        lista.add(atualizacao);
    }
    
    /**
     * Metodo que remove da lista
     * @param indice 
     * @return  
     */
    public static Atualizacao remove(int indice){  
        return lista.remove(indice);
    }
    
    /**
     * Metodo que pega a primeira celula da lista
     * @return 
     */
    public static Atualizacao primeiroDaLista(){
        return lista.get(0);
    }
}
