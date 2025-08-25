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

public class cliente extends pessoaJuridica {

    private String limiteCredito;

    public cliente() {
        super();
    }

    public cliente(int id_pessoa, String nome, String endereco, String telefone, String email, Date dtCadastro, boolean status, String cnpj, String razaoSocial, Date dtAbertura, String limiteCredito) {
        super(id_pessoa, nome, endereco, telefone, email, dtCadastro, status, cnpj, razaoSocial, dtAbertura);
        this.limiteCredito = limiteCredito;
    }

    @Override
    public void cadastrarPessoaEspecializacao(Connection conn, Scanner sc) throws SQLException {
        super.cadastrarPessoaEspecializacao(conn, sc); // Chama o cadastro de Pessoa Jurídica

        System.out.print("Limite de Crédito: ");
        this.limiteCredito = sc.nextLine();

        String sql = "INSERT INTO Cliente (id_pessoa, limite_credito) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, getId_pessoa()); // Usa o ID da pessoa cadastrada
            pstmt.setString(2, this.limiteCredito);
            pstmt.executeUpdate();
        }
    }

    public void consultarCliente() {
        System.out.println("Consultando Cliente...");
        // Lógica para consultar o cliente
    }

    public void cadastrarCliente(Connection conn, Scanner sc) throws SQLException {
        super.cadastrarPessoa(conn, sc); // Chama o método da superclasse (Pessoa) para cadastrar os dados básicos
        cadastrarPessoaEspecializacao(conn, sc); // Chama o método para cadastrar os dados específicos de cliente (e Pessoa Jurídica)
    }

    public void editarCliente() {
        System.out.println("Editando Cliente...");
        // Lógica para editar o cliente
    }

    public void consultarHistoricoCliente() {
        System.out.println("Consultando Histórico do Cliente...");
        // Lógica para consultar o histórico do cliente
    }

    public String getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(String limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
}