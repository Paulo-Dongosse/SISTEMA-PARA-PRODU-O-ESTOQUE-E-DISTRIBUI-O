/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresa;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class fornecedor extends pessoaJuridica {

    private String categoria;
    private String avaliacao;

    public fornecedor() {
        super();
    }

    @Override
    public void cadastrarPessoaEspecializacao(Connection conn, Scanner sc) throws SQLException {
        // Aqui só coleta categoria e avaliação, pois a inserção é via procedure
        System.out.print("Categoria (não será salva no banco por enquanto): ");
        this.categoria = sc.nextLine();
        System.out.print("Avaliação (não será salva no banco por enquanto): ");
        this.avaliacao = sc.nextLine();
    }

    public void cadastrarFornecedor(Connection conn, Scanner sc) {
    try {
        System.out.print("Nome/Razao Social: ");
        String nome = sc.nextLine();

        System.out.print("CNPJ (18 caracteres): ");
        String cnpj = sc.nextLine();

        CallableStatement cs = conn.prepareCall("{ call pr_cadastrar_fornecedor(?, ?) }");
        cs.setString(1, nome);
        cs.setString(2, cnpj);
        cs.execute();
        System.out.println("==================================");
        System.out.println("Fornecedor cadastrado com sucesso!");
        System.out.println("==================================");

    } catch (SQLException e) {
        System.err.println("Erro ao executar operação do banco de dados: " + e.getMessage());
    }
}


    // Getters e setters de categoria e avaliação
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }
}
