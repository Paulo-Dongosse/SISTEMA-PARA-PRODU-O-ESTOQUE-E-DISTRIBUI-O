/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresa;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class pessoaJuridica extends pessoa {

    private String cnpj;
    private String razaoSocial;
    private Date dtAbertura;

    public pessoaJuridica() {
        super();
    }

    public pessoaJuridica(int id_pessoa, String nome, String endereco, String telefone, String email, Date dtCadastro, boolean status, String cnpj, String razaoSocial, Date dtAbertura) {
        super(id_pessoa, nome, endereco, telefone, email, dtCadastro, status);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.dtAbertura = dtAbertura;
    }

    @Override
    public void cadastrarPessoaEspecializacao(Connection conn, Scanner sc) throws SQLException {
        System.out.print("CNPJ: ");
        this.cnpj = sc.nextLine();
        System.out.print("Razão Social: ");
        this.razaoSocial = sc.nextLine();
        System.out.print("Data de Abertura (AAAA-MM-DD): ");
        this.dtAbertura = Date.valueOf(sc.nextLine());

        String sql = "INSERT INTO PessoaJuridica (id_pessoa, cnpj, razao_social, dt_abertura) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, getId_pessoa());
            pstmt.setString(2, this.cnpj);
            pstmt.setString(3, this.razaoSocial);
            pstmt.setDate(4, this.dtAbertura);
            pstmt.executeUpdate();
        }
    }

    public void consultarPessoaJuridica() {
        System.out.println("Consultando Pessoa Jurídica...");
        // Lógica para consultar a pessoa jurídica
    }

    public void cadastrarPessoaJuridica(Connection conn, Scanner sc) throws SQLException {
        super.cadastrarPessoa(conn, sc);
        cadastrarPessoaEspecializacao(conn, sc);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public Date getDtAbertura() {
        return dtAbertura;
    }

    public void setDtAbertura(Date dtAbertura) {
        this.dtAbertura = dtAbertura;
    }
}