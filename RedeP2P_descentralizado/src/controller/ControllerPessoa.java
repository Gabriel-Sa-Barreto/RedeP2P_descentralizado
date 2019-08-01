/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Pessoa;
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
    private static List<Pessoa> pessoasFisicas;
    
    /**
     * Lista que armazena todas as pessoas jurídicas já cadastradas neste cartório.
     */
    private static List<Pessoa> pessoasJuridicas;

    /**
     * Construtor que inicializa todos os atributos da classe para possíveis operações.
     */
    public ControllerPessoa() {
        pessoasFisicas = new ArrayList<Pessoa>();
        pessoasJuridicas = new ArrayList<Pessoa>();
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
}
