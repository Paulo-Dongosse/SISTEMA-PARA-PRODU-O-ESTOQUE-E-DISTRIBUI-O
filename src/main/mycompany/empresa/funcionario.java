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

public class funcionario extends pessoa {

    private String cpf;
    private String sexo;
    private Date dtNasc;
    private String estadoCivil;
    private String cargo;
    private double salario;
    private String departamento;

    public funcionario() {
        super();
    }

    public funcionario(int id_pessoa, String nome, String endereco, String telefone, String email, Date dtCadastro, boolean status, String cpf, String sexo, Date dtNasc, String estadoCivil, String cargo, double salario, String departamento) {
        super(id_pessoa, nome, endereco, telefone, email, dtCadastro, status);
        this.cpf = cpf;
        this.sexo = sexo;
        this.dtNasc = dtNasc;
        this.estadoCivil = estadoCivil;
        this.cargo = cargo;
        this.salario = salario;
        this.departamento = departamento;
    }

    @Override
    public void cadastrarPessoaEspecializacao(Connection conn, Scanner sc) throws SQLException {
        System.out.print("CPF: ");
        this.cpf = sc.nextLine();
        System.out.print("Sexo: ");
        this.sexo = sc.nextLine();
        System.out.print("Data de Nascimento (AAAA-MM-DD): ");
        this.dtNasc = Date.valueOf(sc.nextLine());
        System.out.print("Estado Civil: ");
        this.estadoCivil = sc.nextLine();

        String sql = "INSERT INTO Funcionario (id_pessoa, cpf, sexo, dt_nasc, estado_civil) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, getId_pessoa());
            pstmt.setString(2, this.cpf);
            pstmt.setString(3, this.sexo);
            pstmt.setDate(4, this.dtNasc);
            pstmt.setString(5, this.estadoCivil);
            pstmt.executeUpdate();
        }
    }

    public void consultarFuncionario() {
        System.out.println("Consultando Funcionário...");
        // Lógica para consultar o funcionário
    }

    public void cadastrarFuncionario(Connection conn, Scanner sc) throws SQLException {
        super.cadastrarPessoa(conn, sc);
        cadastrarPessoaEspecializacao(conn, sc);
        System.out.print("Cargo: ");
        this.cargo = sc.nextLine();
        System.out.print("Salário: ");
        this.salario = sc.nextDouble();
        sc.nextLine(); // Consumir a nova linha
        System.out.print("Departamento: ");
        this.departamento = sc.nextLine();

        String sql = "UPDATE Funcionario SET cargo = ?, salario = ?, departamento = ? WHERE id_pessoa = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.cargo);
            pstmt.setDouble(2, this.salario);
            pstmt.setString(3, this.departamento);
            pstmt.setInt(4, getId_pessoa());
            pstmt.executeUpdate();
        }
    }

    public void editarFuncionario() {
        System.out.println("Editando Funcionário...");
        // Lógica para editar o funcionário
    }

    public void inativarFuncionario() {
        System.out.println("Inativando Funcionário...");
        // Lógica para inativar o funcionário
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}