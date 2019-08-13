/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author gabriel
 */
public class Documento {
    
    /**
     * Atributo que guarda o caminho(PATH) para onde o documento está armazenado.
     */
    private String documento;
    
    /**
     * Atributo que armazena a assinatura digital de um documento específico.
     */
    private String assinatura_Documento;
    
    /**
     * Atributo que relaciona a pessoa (física/jurídica) que está relacionada ao documento em questão.
     */
    private Pessoa pessoa;
    
    /**
     * Atributo que informa a qual cartório esse documento pertence.
     */
    private String cartorio;

    /**
     * Método construtor que inicializa um objeto documento com todas as informações relacionadas a ele. 
     * Informações quais são passadas por parâmetro.
     * @param documento
     * @param assinatura_Documento
     * @param pessoa
     * @param cartorio 
     */
    public Documento(String documento, String assinatura_Documento, Pessoa pessoa, String cartorio) {
        this.documento = documento;
        this.assinatura_Documento = assinatura_Documento;
        this.pessoa = pessoa;
        this.cartorio = cartorio;
    }

    
    /**
     * Método que retorna o caminho em que o documento está armazenado.
     * @return documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * Método que configuta (trocar/armazenar) o caminho em que o documento será armazenado.
     * @param documento 
     */
    public void setDocumento(String documento) {
        this.documento = documento;
    }

    
    /**
     * Método que retorna a assinatura digital do documento desejado.
     * @return assinatura_Documento
     */
    public String getAssinatura_Documento() {
        return assinatura_Documento;
    } 

    /**
     * Método que configura (trocar/armazenar) a assinatura digital do documento desejado.
     * @param assinatura_Documento 
     */
    public void setAssinatura_Documento(String assinatura_Documento) {
        this.assinatura_Documento = assinatura_Documento;
    }

    
    /**
     * Método que retorna o objeto pessoa (física/jurídica) que está relacionada com o documento.
     * @return pessoa
     */
    public Pessoa getPessoa() {
        return pessoa;
    }

    /**
     * Método que configura (trocar/armazenar) o objeto pessoa (física/jurídica) que estará relacionada com o documento.
     * @param pessoa 
     */
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    /**
     * Método que informa em qual cartório o documento está vinculado.
     * @return cartorio 
     */
    public String getCartorio() {
        return cartorio;
    }

    /**
     * Método que configura (trocar/armazenar) o cartório em que o documento está vinculado.
     * @param cartorio 
     */
    public void setCartorio(String cartorio) {
        this.cartorio = cartorio;
    }   
}
