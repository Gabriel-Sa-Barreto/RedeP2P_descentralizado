/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
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
    private static List<PessoaFisica> pessoasFisicas = new ArrayList<>();;
    
    /**
     * Lista que armazena todas as pessoas jurídicas já cadastradas neste cartório.
     */
    private static List<PessoaJuridica> pessoasJuridicas = new ArrayList<>();;

    /**
     * Construtor que inicializa todos os atributos da classe para possíveis operações.
     */
    
    public ControllerPessoa(){}
    
    /**
     * Método que realiza o cadastro de novas pessoas físicas no sistema do cartório.
     * @param nome
     * @param assinatura
     * @param CPF 
     * @param senha 
     * @return  
     */
    public int cadastrarPessoa_fisica(String nome, String assinatura, String CPF, String senha){
        //cria um objeto do tipo PessoaFisica
        PessoaFisica p = new PessoaFisica(CPF, nome, assinatura, senha);
        //adiciona na lista
        pessoasFisicas.add(p);
        //caso o cadastro seja feito com sucesso
        return 1; 
    }
    
    /**
     * Método que realiza o cadastro de novas pessoas jurídicas no sistema do cartório.
     * @param cnpj
     * @param nome
     * @param assinatura
     * @param senha
     * @return 
     */
    public int cadastrarPessoa_juridica(String nome, String assinatura, String cnpj, String senha){
        //cria um objeto do tipo PessoaFisica
        PessoaJuridica p = new PessoaJuridica(cnpj, nome, assinatura,senha);
        //adiciona na lista
        pessoasJuridicas.add(p);
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
                //compara o cnpj da atual celula com a passada como argumento
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
     * Método que busca uma pessoa física pelo CPF que está cadastrada no sistema.
     * @param CPF
     * @return null ou a pessoa encontrada
     */
    public PessoaFisica busca_pessoaFisica(String CPF){
        //se a lista estiver vazia.
        if(pessoasFisicas.isEmpty()){
            return null;
        }else {
            //realiza busca de pessoa
            Iterator<PessoaFisica> it = pessoasFisicas.iterator(); 
            while ( it.hasNext() ){
                PessoaFisica p = it.next();
                //compara o cpf da atual celula com a passada como argumento
                if(p.getCPF().equals(CPF)){
                   return p;
                }
            } 
        }
        return null;
    }   
    
    /**
     * Método que busca uma pessoa pela sua assinatura digital que está cadastrada no sistema.
     * @param assinatura
     * @param opcao
     * @return null ou a pessoa encontrada
     */
    public boolean busca_pessoaAssinatura(String assinatura, int opcao){
        //se a lista estiver vazia.
        if(pessoasFisicas.isEmpty()){
            return false;
        }else {
            PessoaFisica pf = null;
            if(opcao == 0){ //busca assinatura na lista de pessoas físicas.
                Iterator<PessoaFisica> it = pessoasFisicas.iterator(); 
                while ( it.hasNext() ){
                    pf = it.next();
                    //compara a assinatura da atual celula com a passada como argumento
                    if(pf.getAssinatura_digital().equals(assinatura)){
                       return true;
                    }
                } 
            }
            
            PessoaJuridica pj = null;
            if(opcao == 1){ //busca assinatura na lista de pessoas jurídicas.
                Iterator<PessoaJuridica> it = pessoasJuridicas.iterator(); 
                while ( it.hasNext() ){
                    pj = it.next();
                    //compara a assinatura da atual celula com a passada como argumento
                    if(pj.getAssinatura_digital().equals(assinatura)){
                       return true;
                    }
                } 
            }
        }
        return false;
    }   
    
    /**
     * Método que verifica se uma pessoa está cadastrada no sistema.
     * @param assinatura
     * @param senha
     * @param opcao
     * @return 1 (sucesso) ou 0 (falha)
     */
    public int existePessoa(String assinatura, String senha, int opcao){
        try{
            if(opcao == 0){ //busca de pessoa física
                PessoaFisica p = null;
                for(Iterator<PessoaFisica> it = pessoasFisicas.iterator(); it.hasNext();){
                    p = it.next();
                    //compara a atual celula com os dados passados como argumento
                    if(p.getAssinatura_digital().equals(assinatura) && p.getSenha().equals(senha) ){
                        return 1;
                    }
                } 
            }
            
            if(opcao == 1){ //busca de pessoa jurídica
                PessoaJuridica p = null;
                for(Iterator<PessoaJuridica> it = pessoasJuridicas.iterator(); it.hasNext();){
                    p = it.next();
                    //compara a atual celula com os dados passados como argumento
                    if(p.getAssinatura_digital().equals(assinatura) && p.getSenha().equals(senha)){
                        return 1;
                    }
                } 
            }
        }catch(Exception e){
            return 0;
        }
        return 0;
    }
    
    /**
     * Método que retorna a lista contendo as pessoa fisicas cadastradas
     * @return 
     */
    public static List<PessoaFisica> getPessoasFisicas() {
        return pessoasFisicas;
    }
    
    /**
     * Cria o diretorio para ser armazenado os arquivos da pessoa com base na sua assinatura digital
     * @param diretorio 
     */
    public void criarDiretorio(String diretorio){
        new File(diretorio).mkdir();
    }
    
}
