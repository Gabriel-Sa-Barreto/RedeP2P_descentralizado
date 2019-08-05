/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.PessoaFisica;
import model.PessoaJuridica;

/**
 *
 * @author lablenda2
 */
public class ControllerPessoa {
    /**
     * Lista que armazena todas as pessoas físicas já cadastradas neste cartório.
     */
    private static List<PessoaFisica> pessoasFisicas;
    
    /**
     * Lista que armazena todas as pessoas jurídicas já cadastradas neste cartório.
     */
    private static List<PessoaJuridica> pessoasJuridicas;

    /**
     * Construtor que inicializa todos os atributos da classe para possíveis operações.
     */
    public ControllerPessoa() {
        pessoasFisicas = new ArrayList<PessoaFisica>();
        pessoasJuridicas = new ArrayList<PessoaJuridica>();
    }
    
    /**
     * Método que realiza o cadastro de novas pessoas físicas no sistema do cartório.
     * @param nome
     * @param assinatura
     * @param CPF 
     */
    public int cadastrarPessoa_fisica(String nome, String assinatura, String CPF){
        try{
            //tentar cadastrar uma pessoa
            PessoaFisica p = new PessoaFisica(CPF, nome, assinatura);
            pessoasFisicas.add(p);
        }catch(Exception e){
            //caso der algum erro no cadastro
            return -1;
        }
        
        //caso o cadastro seja feito com sucesso
        return 1; 
    }
    
    /**
     * Método que realiza o cadastro de novas pessoas jurídicas no sistema do cartório.
     * @param cnpj
     * @param nome
     * @param assinatura
     * @return 
     */
    public int cadastrarPessoa_juridica(String cnpj, String nome, String assinatura){
        try{
            //tentar cadastrar uma pessoa
            PessoaJuridica p = new PessoaJuridica(cnpj, nome, assinatura);
            pessoasJuridicas.add(p);
        }catch(Exception e){
            //caso der algum erro no cadastro
            return -1;
        }
        //caso o cadastro seja feito com sucesso.
        return 1;
    }    
    
    /**
     * Método que retorna uma pessoa jurídica que está cadastrada no sistema.
     * @param cnpj
     * @return null ou a pessoa encontrada
     */
    public PessoaJuridica busca_pessoaJuridica(String cnpj){
        try{
            PessoaJuridica p = null;
            //realiza busca de pessoa
            for(Iterator<PessoaJuridica> it = pessoasJuridicas.iterator(); it.hasNext();){
                p = it.next();
                if(p.getCNPJ().equals(cnpj)){
                    return p;
                }
            } 
        }catch(Exception e){
            return null;
        }
        return null;
    }   
    
  
    /**
     * Método que busca uma pessoa física que está cadastrada no sistema.
     * @param CPF
     * @return null ou a pessoa encontrada
     */
    public PessoaFisica busca_pessoaFisica(String assinatura){
        try{
            PessoaFisica p = null;
            //realiza busca de pessoa
            for(Iterator<PessoaFisica> it = pessoasFisicas.iterator(); it.hasNext();){
                p = it.next();
                if(p.getAssinatura_digital().equals(assinatura)){
                    return p;
                }
            } 
        }catch(Exception e){
            return null;
        }
        return null;
    }   
    
    
    /**
     * Método que verifica se uma pessoa está cadastrada no sistema.
     * @param assinatura
     * @param opcao
     * @return 1 (sucesso) ou 0 (falha)
     */
    public int existePessoa(String assinatura, int opcao){
        try{
            if(opcao == 0){ //busca de pessoa física
                PessoaFisica p = null;
                for(Iterator<PessoaFisica> it = pessoasFisicas.iterator(); it.hasNext();){
                    p = it.next();
                    if(p.getAssinatura_digital().compareTo(assinatura) == 0){
                        return 1;
                    }
                } 
            }
            
            if(opcao == 1){ //busca de pessoa jurídica
                PessoaJuridica p = null;
                for(Iterator<PessoaJuridica> it = pessoasJuridicas.iterator(); it.hasNext();){
                    p = it.next();
                    if(p.getAssinatura_digital().compareTo(assinatura) == 0){
                        return 1;
                    }
                } 
            }
        }catch(Exception e){
            return 0;
        }
        return 0;
    }
}
