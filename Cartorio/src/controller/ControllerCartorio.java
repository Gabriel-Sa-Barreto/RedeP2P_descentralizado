/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import model.Cartorio;

/**
 *
 * @author lsjsa
 */
public class ControllerCartorio {
    
    /**
     * Lista que armazena a porta e ip dos cartorios vizinhos
     */
    private static List<Cartorio> cartorios  = new ArrayList<>();;
   
    /**
     * Cadastramento de um cartorio
     * @param porta porta do server
     * @param ip ip do server
     */
    public static void cadastrar(int porta , String ip){
        Cartorio cartorio;
        if(cartorios.isEmpty())
            cartorio = new Cartorio(0,porta , ip); //criacao do objeto com o id
        else
            cartorio = new Cartorio(cartorios.size() - 1,porta , ip); //criacao do objeto com o id
        cartorios.add(cartorio); //adiciona na lista
    }
    
    /**
     * Busca um cartorio por um indice determinado
     * @param indice indice onde esta armazenado o cartorio
     * @return Cartorio dados de um cartorio
     */
    public static Cartorio busca(int indice){
        return cartorios.get(indice);
    }
    
    /**
     * Metodo que informa a quantidade de cartorios
     * @return int tamanho da lista
     */
    public static int quantCartorio(){
        return cartorios.size();
    }
}