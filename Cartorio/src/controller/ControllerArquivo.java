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
import model.Pessoa;
import model.PessoaFisica;
import model.PessoaJuridica;


/**
 *
 * @author Samuel Vitorio e Gabriel
 */
public class ControllerArquivo {
    
    
    public static void escreverPessoaFisica(PessoaFisica pessoa) throws IOException{
        BufferedWriter write = new BufferedWriter(new FileWriter("../Banco/fisica.txt",true));
        write.append(pessoa.toString()+ "\n");
        write.close();
    }
    
    
    public static void escreverPessoaJuridica(PessoaJuridica pessoa) throws IOException{
        BufferedWriter write = new BufferedWriter(new FileWriter("../Banco/juridico.txt",true));
        write.append(pessoa.toString()+ "\n");
        write.close();
    }
    
   
    public static void escreverDocumento(String caminho , String assinaturaP , String assinaturaD) throws IOException{
        String caminhoArquivo = "../Banco/arquivosSalvos.txt";
        BufferedWriter write = new BufferedWriter(new FileWriter(caminhoArquivo , true));
        write.append(assinaturaP + ';' + assinaturaD + ";" + caminho + "\n");
        write.close();
    }
    
    
    public static void leitorPessoaJuridica(String caminho, ControllerPessoa gerenciador) throws FileNotFoundException, IOException{
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
            PessoaJuridica pf = salvarPessoaJuridica(linha);
            gerenciador.cadastrarPessoa_juridica(pf.getCNPJ(), pf.getNome(), pf.getAssinatura_digital(), pf.getSenha());
        }
        read.close();
    }
    
    
    public static void leitorPessoaFisica(String caminho, ControllerPessoa gerenciador) throws FileNotFoundException, IOException{
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
            PessoaFisica pf = salvarPessoaFisica(linha);
            gerenciador.cadastrarPessoa_fisica(pf.getNome(), pf.getAssinatura_digital(), pf.getCPF(), pf.getSenha());
        }
        read.close();
    }
    
    public static List<Documento> leitorDocumento(String assP) throws FileNotFoundException, IOException{
        String caminho = "../Banco/arquivosSalvos.txt";
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        List<Documento> documentos = new ArrayList<>();
        while((linha = read.readLine()) != null){
            String[] split = linha.split(";");
            String[] arquivo = split[2].split("/");
            String nome = arquivo[3];
            if(split[0].equals(assP)){
                Documento doc = new Documento(nome , split[1]);
                documentos.add(doc);
            }    
        }
        read.close();
        return documentos;
    }
    
    public static String buscarDoc(String assP , String nome) throws IOException{
        String caminho = "../Banco/arquivosSalvos.txt";
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        List<Documento> documentos = new ArrayList<>();
        while((linha = read.readLine()) != null){
            System.out.println("Entrou2");
            String[] split = linha.split(";");
            String[] arquivo = split[2].split("/");
            String nomeArquivo = arquivo[3];
            if(split[0].equals(assP) && nomeArquivo.equals(nome)){
                System.out.println("Foi");
                System.out.println(split[2]);
                read.close();
                return split[2];
            }    
        }
        read.close();
        return null;
    }
           
    private static PessoaFisica salvarPessoaFisica(String linha){
        String[] split = linha.split(";");
        PessoaFisica pessoa;
        pessoa = new PessoaFisica(split[1],split[0], split[3], split[2] );
        return pessoa;
    }
    
    
    private static PessoaJuridica salvarPessoaJuridica(String linha){
        String[] split = linha.split(";");
        PessoaJuridica pessoa;
        pessoa = new PessoaJuridica(split[1],split[0], split[3], split[2]);
        return pessoa;
    } 
}
