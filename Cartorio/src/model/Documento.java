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
     * Atributo que guarda o nome do documento que está armazenado.
     */
    private String documento;
    
    /**
     * Atributo que armazena a assinatura digital de um documento específico.
     */
    private String assinatura_Documento;
       
    /**
     * Método construtor que inicializa um objeto documento com todas as informações relacionadas a ele. 
     * Informações quais são passadas por parâmetro.
     * @param documento
     * @param assinatura_Documento 
     */
    public Documento(String documento, String assinatura_Documento) {
        this.documento = documento;
        this.assinatura_Documento = assinatura_Documento;
    }

    
    /**
     * Método que retorna o nome do que documento está armazenado.
     * @return documento
     */
    public String getDocumento() {
        return documento;
    }

    /**
     * Método que configura (trocar/armazenar) o nome do documento que está/será armazenado.
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
     
}
