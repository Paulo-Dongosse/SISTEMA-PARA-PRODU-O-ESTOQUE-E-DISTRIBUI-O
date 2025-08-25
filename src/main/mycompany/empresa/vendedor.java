/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresa;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Date;

public class vendedor extends funcionario {

    private String matricula;
    private double comissao;

    public vendedor() {
        super();
    }

    public vendedor(int id_pessoa, String nome, String endereco, String telefone, String email, Date dtCadastro, boolean status, String cpf, String sexo, Date dtNasc, String estadoCivil, String cargo, double salario, String departamento, String matricula, double comissao) {
        super(id_pessoa, nome, endereco, telefone, email, dtCadastro, status, cpf, sexo, dtNasc, estadoCivil, cargo, salario, departamento);
        this.matricula = matricula;
        this.comissao = comissao;
    }

    // Método para cadastrar vendedor chamando a procedure no banco
    public void cadastrarVendedor(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("CPF (14 caracteres): ");
        String cpf = sc.nextLine();

        System.out.print("Sexo (M/F): ");
        String sexo = sc.nextLine();

        System.out.print("Data de Nascimento (AAAA-MM-DD): ");
        String dataNascStr = sc.nextLine();
        Date dtNasc = Date.valueOf(dataNascStr);

        System.out.print("Cargo: ");
        String cargo = sc.nextLine();

        System.out.print("Salário: ");
        double salario = sc.nextDouble();
        sc.nextLine();

        System.out.print("Departamento: ");
        String departamento = sc.nextLine();

        System.out.print("Matrícula: ");
        String matricula = sc.nextLine();

        System.out.print("Comissão: ");
        double comissao = sc.nextDouble();
        sc.nextLine();

        // Chamar procedure
        String sql = "{call pr_cadastrar_vendedor_pf(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, nome);
            cs.setString(2, cpf);
            cs.setString(3, sexo);
            cs.setDate(4, dtNasc);
            cs.setString(5, cargo);
            cs.setDouble(6, salario);
            cs.setString(7, departamento);
            cs.setString(8, matricula);
            cs.setDouble(9, comissao);

            cs.execute();
            System.out.println("==================================");
            System.out.println("Vendedor cadastrado com sucesso!");
            System.out.println("==================================");
        } catch (SQLException e) {
            System.out.println("Erro ao executar operação do banco de dados: " + e.getMessage());
        }
    }

    // Getters e setters
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    public double getComissao() {
        return comissao;
    }
    public void setComissao(double comissao) {
        this.comissao = comissao;
    }
}
