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
public class PessoaJuridica extends Pessoa{
    
    /**
     * Atributo que armazena o CNPJ de uma pessoa.
     */
    private String CNPJ;

    /**
     * Método construtor que inicializa uma tipo de pessoa jurídica informando CNPJ, nome e assinatura digital
     * pertencente a ela.
     * @param CNPJ
     * @param nome
     * @param assinatura_digital 
     */
    public PessoaJuridica(String CNPJ, String nome, String assinatura_digital, String senha) {
        super(nome, assinatura_digital,senha);
        this.CNPJ = CNPJ;
    }

    /**
     * Método que retorna o atributo CNPJ da pessoa jurídica.
     * @return CNPJ
     */
    public String getCNPJ() {
        return CNPJ;
    }

    /**
     * Método que configura (troca/armazenar) o CNPJ de uma pessoa física.
     * @param CNPJ 
     */
    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    } 
    
    /**
     * Método que retorna uma string com os atributos associados a uma pessoa juridica.
     * @return 
     */
    @Override
    public String toString() {
        return super.getNome() + ';' + this.CNPJ + ';' + super.getSenha() + ';' + super.getAssinatura_digital() ;
    }
}
