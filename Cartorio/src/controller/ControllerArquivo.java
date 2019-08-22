/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Documento;
import model.PessoaFisica;
import model.PessoaJuridica;


/**
 *
 * @author Samuel Vitorio e Gabriel
 */
public class ControllerArquivo {
    
    /**
     * Método que registra uma pessoa fisica no arquivo
     * @param pessoa Pessoa Fisica a ser registrada
     * @throws IOException 
     */
    public static void escreverPessoaFisica(PessoaFisica pessoa) throws IOException{
        //objeto responsavel por escrever as linhas do arquivo
        BufferedWriter write = new BufferedWriter(new FileWriter("../Banco/fisica.txt",true));
        //padrao definido para o armazenamento da informacao no arquivo
        write.append(pessoa.toString()+ "\n");
        write.close();
    }
    
    /**
     * Método que registra uma pessoa juridica no arquivo
     * @param pessoa Pessoa Juridica a ser registrada
     * @throws IOException 
     */
    public static void escreverPessoaJuridica(PessoaJuridica pessoa) throws IOException{
        //objeto responsavel por escrever as linhas do arquivo
        BufferedWriter write = new BufferedWriter(new FileWriter("../Banco/juridico.txt",true));
        //padrao definido para o armazenamento da informacao no arquivo
        write.append(pessoa.toString()+ "\n");
        write.close();
    }
    
   /**
    * Método que registra um documento no arquivo.
    * @param caminho diretorio onde foi armazenado no servidor
    * @param assinaturaP //assinatura da pessoa
    * @param assinaturaD //assinatura do documento
    * @throws IOException 
    */
    public static void escreverDocumento(String caminho , String assinaturaP , String assinaturaD) throws IOException{
        String caminhoArquivo = "../Banco/arquivosSalvos.txt";
        //objeto responsavel por escrever as linhas do arquivo
        BufferedWriter write = new BufferedWriter(new FileWriter(caminhoArquivo , true));
        //padrao definido para o armazenamento da informacao no arquivo
        write.append(assinaturaP + ';' + assinaturaD + ";" + caminho + "\n");
        write.close();
    }
    
    /**
     * Método que levanta do arquivo todos as pessoas juridicas cadastradas
     * @param caminho caminho do arquivo que armazena as pessoas juridicas
     * @param gerenciador controller que é responsavel por armazenar os dados em um lista
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void leitorPessoaJuridica(String caminho, ControllerPessoa gerenciador) throws FileNotFoundException, IOException{
        //objeto responsavel por ler as linhas do arquivo
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
             //cria o objeto com os dados da linha 
            PessoaJuridica pf = salvarPessoaJuridica(linha);
            //metodo do controllerPessoa para armazenar na lista
            gerenciador.cadastrarPessoa_juridica(pf.getNome(), pf.getAssinatura_digital(),pf.getCNPJ(),pf.getSenha());
        }
        read.close();
    }
    
    /**
     * Método que levanta do arquivo todos as pessoas fisicas cadastradas
     * @param caminho caminho do arquivo que armazena as pessoas fisicas
     * @param gerenciador controller que é responsavel por armazenar os dados em um lista
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void leitorPessoaFisica(String caminho, ControllerPessoa gerenciador) throws FileNotFoundException, IOException{
        //objeto responsavel por ler as linhas do arquivo
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
            //cria o objeto com os dados da linha 
            PessoaFisica pf = salvarPessoaFisica(linha);
            //metodo do controllerPessoa para armazenar na lista
            gerenciador.cadastrarPessoa_fisica(pf.getNome(), pf.getAssinatura_digital(), pf.getCPF(), pf.getSenha());
        }
        read.close();
    }
    
    /**
     * Método que pega todos os documentos armazenados por uma pessoa
     * @param assP assinatura digital da pessoa
     * @return Lista contendo os nomes e assintaturas digitais dos documentos
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static List<Documento> leitorDocumento(String assP) throws FileNotFoundException, IOException{
        //diretorio onde fica o arquivo que armazena informacoes dos arquivos
        String caminho = "../Banco/arquivosSalvos.txt";
        //objeto responsavel por ler as linhas do arquivo
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        //lista onde sera armazenado o nome e assinatura do documento
        List<Documento> documentos = new ArrayList<>();
        while((linha = read.readLine()) != null){
            //realiza o split
            String[] split = linha.split(";");
            //split[2] onde fica a informação do diretorio do arquivo
            String[] arquivo = split[2].split("/");
            //arquivo[3] informacao contendo o nome
            String nome = arquivo[3];
            //split[0] contem a informacao da assinatura digital
            if(split[0].equals(assP)){
                Documento doc = new Documento(nome , split[1]);
                documentos.add(doc);
            }    
        }
        read.close();
        return documentos;
    }
    
    /**
     * Metodo que retorna o diretorio de um arquivo requisitado pelo sistema
     * @param assP assinatura da pessoa
     * @param nome nome do arquivo desejado
     * @return
     * @throws IOException 
     */
    public static String buscarDoc(String assP , String nome) throws IOException{
        //diretorio onde fica o arquivo que armazena informacoes dos arquivos
        String caminho = "../Banco/arquivosSalvos.txt";
        //objeto responsavel por ler as linhas do arquivo
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        //le ate o final do arquivo
        while((linha = read.readLine()) != null){
            //realiza o split
            String[] split = linha.split(";");
            //split[2] onde fica a informação do diretorio do arquivo
            String[] arquivo = split[2].split("/");
            //arquivo[3] informacao contendo o nome
            String nomeArquivo = arquivo[3];
            //se achar um igual retorna o diretorio do arquivo
            if(split[0].equals(assP) && nomeArquivo.equals(nome)){
                read.close(); //fecha a leitura
                return split[2];
            }    
        }
        read.close(); //fecha a leitura
        return null;
    }
    
    /**
     * Método que auxilia na criação do objeto pessoa fisica
     * @param linha contem os dados da pessoa fisica cadastradas
     * @return 
     */
    private static PessoaFisica salvarPessoaFisica(String linha){
        //realiza o split para separar as informações
        String[] split = linha.split(";");
        PessoaFisica pessoa;
        //split[1] = cpf , split[0] = nome , split[3] = assinatura digital e split[4] = senha
        pessoa = new PessoaFisica(split[1],split[0], split[3], split[2] );
        return pessoa;
    }
    
    /**
     * Método que auxilia na criação do objeto pessoa juridica
     * @param linha contem os dados da pessoa juridica cadastradas
     * @return 
     */
    private static PessoaJuridica salvarPessoaJuridica(String linha){
        //realiza o split para separar as informações
        String[] split = linha.split(";");
        //split[1] = cnpj , split[0] = nome , split[3] = assinatura digital e split[4] = senha 
        PessoaJuridica pessoa;
        pessoa = new PessoaJuridica(split[1],split[0], split[3], split[2]);
        return pessoa;
    } 
}
