/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Samuel Vitorio e Gabriel Sá
 */
public class ControllerPessoa {
    
    /**
     * Atributo que armazena a assDigital da pessoa que está logada no sistema.
     */
    private static String assDigitalPessoaLogada = "";

    /**
     * Método que retornar a assDigital da pessoa que está logada no sistema.
     * @return 
     */
    public static String getAssDigitalPessoaLogada() {
        return assDigitalPessoaLogada;
    }

    /**
     * Método que modifica a assDigital da pessoa que está logada no sistema.
     * @param assDigitalPessoaLogada 
     */
    public static void setAssDigitalPessoaLogada(String assDigitalPessoaLogada) {
        ControllerPessoa.assDigitalPessoaLogada = assDigitalPessoaLogada;
    }
}
