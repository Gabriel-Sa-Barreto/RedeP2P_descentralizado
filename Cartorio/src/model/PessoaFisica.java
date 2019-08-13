/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;

/**
 *
 * @author gabriel e Samuel
 */
public class PessoaFisica extends Pessoa{
    
    /**
     * Atributo que armazena o CPF de uma pessoa.
     */
    private String CPF; 
        
    /**
     * Método construtor que inicializa uma tipo de pessoa física informando CPF, nome e assinatura digital
     * pertencente a ela.
     * @param CPF
     * @param nome
     * @param assinatura_digital 
     */
    public PessoaFisica(String CPF, String nome, String assinatura_digital,String senha) {
        super(nome, assinatura_digital,senha);
        this.CPF = CPF;
    }

    /**
     * Método que retorna o atributo CPF da pessoa física.
     * @return CPF 
     */
    public String getCPF() {
        return CPF;
    }

    /**
     * Método que configura (troca/armazenar) o CPF de uma pessoa física.
     * @param CPF 
     */
    public void setCPF(String CPF) {
        this.CPF = CPF;
    } 

    /**
     * Método que retorna uma string com os atributos associados a uma pessoa fisica.
     * @return 
     */
    @Override
    public String toString() {
        return super.getNome() + ';' + this.CPF + ';' + super.getSenha() + ';' + super.getAssinatura_digital() ;
    }

     
}
