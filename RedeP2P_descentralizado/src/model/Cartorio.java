/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author lsjsa
 */
public class Cartorio {
    /**
     * Um atributo int que guarda o id de um cartorio
     */
    private int id;
     /**
     * Um atributo int que guarda a porta do server de um cartorio
     */
    private int porta;
    /**
     * Um atributo int que guarda o ip do server de um cartorio
     */
    private String ip;
    
    /**
     * Metodo construtor da model Cartorio
     * @param porta porta do server
     * @param ip ip do server
     * @param id id do server
     */
    public Cartorio(int id , int porta, String ip) {
        this.porta = porta;
        this.ip = ip;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}