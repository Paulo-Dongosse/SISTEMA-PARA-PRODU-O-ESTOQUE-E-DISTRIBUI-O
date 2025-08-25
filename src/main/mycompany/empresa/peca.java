/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.empresa;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class peca {
    private int id_peca;
    private String nome_peca;
    private double vr_peca;
    private String ds_peca;
    private String cor_peca;
    private String tamanho;
    private Date dt_fabricacao;
    private int prazo_prod_dias;

    public peca() {
    }

    public peca(int id_peca, String nome_peca, double vr_peca, String ds_peca, String cor_peca, String tamanho, Date dt_fabricacao, int prazo_prod_dias) {
        this.id_peca = id_peca;
        this.nome_peca = nome_peca;
        this.vr_peca = vr_peca;
        this.ds_peca = ds_peca;
        this.cor_peca = cor_peca;
        this.tamanho = tamanho;
        this.dt_fabricacao = dt_fabricacao;
        this.prazo_prod_dias = prazo_prod_dias;
    }

    public void coletarDados(Scanner sc) {
        System.out.println("===============================");
        System.out.println("Cadastrar Peca...");
        System.out.println("===============================");
        System.out.print("Nome da Peca: ");
        nome_peca = sc.nextLine();
        System.out.print("Valor da Peca: ");
        vr_peca = sc.nextDouble();
        sc.nextLine(); // limpa o buffer
        System.out.print("Descricao da Peca: ");
        ds_peca = sc.nextLine();
        System.out.print("Cor da Peca: ");
        cor_peca = sc.nextLine();
        System.out.print("Tamanho da Pecas('P','G','M','GG'): ");
        tamanho = sc.nextLine();
        System.out.print("Data de Fabricacao (AAAA-MM-DD): ");
        String dataFabStr = sc.nextLine();
        dt_fabricacao = Date.valueOf(dataFabStr);
        System.out.print("Prazo de Producao (dias): ");
        prazo_prod_dias = sc.nextInt();
        sc.nextLine(); // limpa o buffer
    }

    public void cadastrarPeca(Connection conn) throws SQLException {
    String sql = "INSERT INTO Peca (COD_PECA, NOME_PECA, VLR_PECA, DS_PECA, COR_PECA, TAMANHO, DT_FABRICACAO, PRAZO_PROD_DIAS) VALUES (SEQ_COD_PECA.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, nome_peca);
        pstmt.setDouble(2, vr_peca);
        pstmt.setString(3, ds_peca);
        pstmt.setString(4, cor_peca);
        pstmt.setString(5, tamanho);
        pstmt.setDate(6, dt_fabricacao);
        pstmt.setInt(7, prazo_prod_dias);
        pstmt.executeUpdate();
        System.out.println("============================");
        System.out.println("peca cadastrado com sucesso!");
        System.out.println("=============================");
        // Não precisamos mais tentar recuperar o ID aqui, pois ele é gerado pela sequence
    }
}

    public void consultarPeca() {
        System.out.println("Consultar Peça");
    }

    public void cadastrarTipopeca() {
        System.out.println("cadastrarTipopeca");
    }

    //Getters e Setters
    public int getId_peca() {
        return id_peca;
    }

    public void setId_peca(int id_peca) {
        this.id_peca = id_peca;
    }

    public String getNome_peca() {
        return nome_peca;
    }

    public void setNome_peca(String nome_peca) {
        this.nome_peca = nome_peca;
    }

    public double getVr_peca() {
        return vr_peca;
    }

    public void setVr_peca(double vr_peca) {
        this.vr_peca = vr_peca;
    }

    public String getDs_peca() {
        return ds_peca;
    }

    public void setDs_peca(String ds_peca) {
        this.ds_peca = ds_peca;
    }

    public String getCor_peca() {
        return cor_peca;
    }

    public void setCor_peca(String cor_peca) {
        this.cor_peca = cor_peca;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Date getDt_fabricacao() {
        return dt_fabricacao;
    }

    public void setDt_fabricacao(Date dt_fabricacao) {
        this.dt_fabricacao = dt_fabricacao;
    }

    public int getPrazo_prod_dias() {
        return prazo_prod_dias;
    }

    public void setPrazo_prod_dias(int prazo_prod_dias) {
        this.prazo_prod_dias = prazo_prod_dias;
    }
}