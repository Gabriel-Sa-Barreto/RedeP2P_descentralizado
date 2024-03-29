/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.PessoaFisica;
import model.PessoaJuridica;

/**
 *
 * @author Samuel Vitorio e Gabriel Sá
 */
public class ControllerPacotes {
    
    /**
     * Metodo que recebe o pacote e cria o objeto
     * @param pacote
     * @return 
     */
    public static PessoaFisica repartirPacotePF(String pacote){
        String split[] = pacote.split(";");
        //split[1] = cpf , split[0] = nome , split[3] = assinatura digital e split[4] = senha
        PessoaFisica fisica = new PessoaFisica( split[1],split[0], split[3], split[2] );
        return fisica;
    }
    
    /**
     * Metodo que recebe o pacote e cria o objeto
     * @param pacote
     * @return 
     */
    public static PessoaJuridica repartirPacotePJ(String pacote){
        String split[] = pacote.split(";");
        //split[1] = cnpj , split[0] = nome , split[3] = assinatura digital e split[4] = senha
        PessoaJuridica juridica = new PessoaJuridica( split[1],split[0], split[3], split[2] );
        return juridica;
    }
    
    /**Método auxiliar na conversao de String para int
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param valor List - receber a String a ser convertida.
    * @param padrao int - caso aconteça uma exceção um valor padrão que possa ser inserido.
    * @return int - retornar o inteiro.
    */    
    public static int strToInt(String valor, int padrao) {
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        } 
        catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return padrao;
        }
    }
}