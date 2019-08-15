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
import java.util.List;
import model.Documento;

/**
 *
 * @author Gabriel Sá e Samuel Vitorio
 */
public class ControllerDocumento {
    
    
    public static void sendFile(String caminho , Socket sock) throws FileNotFoundException, IOException{
        FileInputStream fis = null;
        OutputStream os = null;
        BufferedInputStream bis = null;
        ControllerRede rede = new ControllerRede();
        //String[] split = caminho.split("/");
        try{
            File myFile = new File (caminho); //abri arquivo
            rede.enviarDado(sock,myFile.getName());
            //criar um vetor de byte do tamanho do arquivo
            byte [] mybytearray  = new byte [(int)myFile.length()]; 
            //cria um fluxo de saida dos bytes do arquivo
            fis = new FileInputStream(myFile);
            //buffered onde será guardado o arquivo
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
    
    public static void receiveFile(String caminho , InputStream is) throws IOException{
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null; // fluxo para receber os bytes do caminho
        BufferedOutputStream bos = null;//buffered onde estara salvo o caminho
        try {
            // criacao de um buffered 
            byte [] mybytearray  = new byte [6022386];
            //InputStream is = sock.getInputStream();
            //fluxo para receber o caminho no diretorio especificado
            fos = new FileOutputStream(caminho);
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
    }
    
    
    public String buscarDocumento(){
    
    
        return null;
    }
    
    
    
}