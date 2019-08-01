/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author gabriel e Samuel
 */
public class Pessoa {
    /**
     * Atributo para identificação do nome da pessoa.
     */
    private String nome;
    
    /**
     * Atributo para guardar a assinatura digital da pessoa (jurídica ou física).
     */
    private String assinatura_digital;
    
    /**
     * Método construtor que inicializa a instância de uma pessoa com nome e assinatura digital.
     * @param nome
     * @param assinatura_digital 
     */
    public Pessoa(String nome, String assinatura_digital) {
        this.nome = nome;
        this.assinatura_digital = assinatura_digital;
    }

    /**
     * Método que retorna o atributo nome que pertence à pessoa.
     * @return nome 
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método que configura (trocar/armazenar) o atributo nome que pertence à pessoa.
     * @param nome 
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método que retorna a assinatura digital cadastrada de uma pessoa.
     * @return assinatura_digital 
     */
    public String getAssinatura_digital() {
        return assinatura_digital;
    }

    /**
     * Método que configura (trocar/armazenar) a assinatura digital de uma pessoa. 
     * @param assinatura_digital 
     */
    public void setAssinatura_digital(String assinatura_digital) {
        this.assinatura_digital = assinatura_digital;
    }
    
}
