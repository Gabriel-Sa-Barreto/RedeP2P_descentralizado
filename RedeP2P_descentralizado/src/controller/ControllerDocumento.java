/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import model.Documento;

/**
 *
 * @author Gabriel Sá e Samuel Vitorio
 */
public class ControllerDocumento {
    
    /**
     * Atributo que representa uma lista de documentos armazenados
     */
    private static List<Documento> docs = new ArrayList<>();
    
    /**
     * Atributo que armazena um caminho no qual o usuário deseja salvar algum arquivo quando realizar o download. 
     */
    private static String pathToSaveFile;
    
    /**
     * Atributo que armazena o nome do arquivo que será salvo em um diretório depois de realizado o download.
     */
    private static String nomeDocToSave;
    
    /**
     * Método que envia um arquivo para o cliente ao qual o servidor está conectado.
     * @param caminho
     * @param sock
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void sendFile(String caminho , Socket sock) throws FileNotFoundException, IOException{
        FileInputStream fis = null;
        OutputStream os = null;
        BufferedInputStream bis = null;
        ControllerRede rede = new ControllerRede();
        try{
            File myFile = new File (caminho); //abri arquivo
            //envia nome do arquivo.
            rede.enviarDado(sock,myFile.getName());
            //envia a assinatura digital da pessoa logada para realizar a associação com o arquivo.
            rede.enviarDado(sock,ControllerPessoa.getAssDigitalPessoaLogada());
            //criar um vetor de byte do tamanho do arquivo
            byte [] mybytearray  = new byte [(int)myFile.length()]; 
            //cria um fluxo de saida dos bytes do arquivo
            fis = new FileInputStream(myFile);
             //buffered responsavel pela leitura
            bis = new BufferedInputStream(fis);
            //leitura do arquivo
            bis.read(mybytearray,0,mybytearray.length);
            //conexao com o cliente
            os = sock.getOutputStream();
            System.out.println("Sending " + caminho + "(" + mybytearray.length + " bytes)");
            //armazenamento do arquivo no buffer
            os.write(mybytearray,0,mybytearray.length);
            //limpa buffer
            os.flush();
            System.out.println("Done.");
        }finally {
          if (bis != null) bis.close(); //fecha o buffer
          if (os != null) os.close(); //fecha a conexao
        }
    }
    
    /**
     * Método responsável por receber o arquivo envio por um usuário.
     * @param caminho
     * @param is
     * @throws IOException 
     */
    public static void receiveFile(String caminho , InputStream is) throws IOException{
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null; // fluxo para receber os bytes do caminho
        BufferedOutputStream bos = null;//buffered onde estara salvo o caminho
        try {
            // criacao de um buffered 
            byte [] mybytearray  = new byte [6022386];
            //fluxo para receber o caminho no diretorio especificado
            fos = new FileOutputStream(caminho);
            //variavel para ler o buffer
            bos = new BufferedOutputStream(fos);
            //variavel por ler o buffer
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead; //bytes a serem lido no buffer
            do {
                //leitura do buffer e armazenar no vetor mybytearray
                bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);
            
            //escreve o vetor mybytearray no caminho
            bos.write(mybytearray, 0 , current);
            bos.flush();
            System.out.println("File " + caminho + " downloaded (" + current + " bytes read)");
        }finally {
            if (fos != null) fos.close(); //fecha o fluxo    
            if (bos != null) bos.close(); //fecha conexao
        }
        //reseta o os atributos do nome do arquivo e do caminho onde o arquivo foi salvo.
        ControllerDocumento.setNomeDocToSave("");
        ControllerDocumento.setPathToSaveFile("");
    }
    
    /**
     * Método que retorna a lista de documentos.
     * @return 
     */
    public static List<Documento> getDocs() {
        return docs;
    }

    /**
     * Método que adiciona um documento na lista docs.
     * @param doc
     */
    public static void addDocs( Documento doc) {
        ControllerDocumento.docs.add(doc);
    }

    /**
     * Método que retorna o caminho que o usuário escolheu para salvar um arquivo.
     * @return 
     */
    public static String getPathToSaveFile() {
        return pathToSaveFile;
    }

    /**
     * Método que configura o caminho que o usuário escolheu para salvar um arquivo.
     * @param pathToSaveFile 
     */
    public static void setPathToSaveFile(String pathToSaveFile) {
        ControllerDocumento.pathToSaveFile = pathToSaveFile;
    }

    /**
     * Método que retorna o nome do arquivo que será salvo após realizado o download do mesmo.
     * @return 
     */
    public static String getNomeDocToSave() {
        return nomeDocToSave;
    }

    /**
     * Método que configura o nome do arquivo que será salvo após realizado o download do mesmo.
     * @param nomeDocToSave 
     */
    public static void setNomeDocToSave(String nomeDocToSave) {
        ControllerDocumento.nomeDocToSave = nomeDocToSave;
    }
}
