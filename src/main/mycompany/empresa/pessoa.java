package com.mycompany.empresa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class pessoa {
    protected int id_pessoa;
    protected String nome;
    protected String endereco;
    protected String telefone;
    protected String email;
    protected Date dtCadastro;
    protected boolean status;

    public pessoa() {
    }

    public pessoa(int id_pessoa, String nome, String endereco, String telefone, String email, Date dtCadastro, boolean status) {
        this.id_pessoa = id_pessoa;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.dtCadastro = dtCadastro;
        this.status = status;
    }

    // Método para cadastrar os dados usando a procedure PR_CADASTRAR_CLIENTE_COMPLETO
    public void cadastrarPessoa(Connection conn, Scanner sc) throws SQLException {
        // Coleta todos os dados exigidos pela procedure
        System.out.print("Estado: ");
        String estado = sc.nextLine();

        System.out.print("Cidade: ");
        String cidade = sc.nextLine();

        System.out.print("Bairro: ");
        String bairro = sc.nextLine();

        System.out.print("CEP: ");
        String cep = sc.nextLine();

        System.out.print("Tipo Endereço: ");
        String tipoEndereco = sc.nextLine();

        System.out.print("Logradouro: ");
        String logradouro = sc.nextLine();

        System.out.print("Descrição do Logradouro: ");
        String dsLogradouro = sc.nextLine();

        System.out.print("Número: ");
        int numero = Integer.parseInt(sc.nextLine());

        System.out.print("Complemento: ");
        String complemento = sc.nextLine();

        System.out.print("Tipo Telefone: ");
        String tipoTelefone = sc.nextLine();

        System.out.print("DDD: ");
        int ddd = Integer.parseInt(sc.nextLine());

        System.out.print("Telefone (prefixo e sufixo juntos): ");
        String telefone = sc.nextLine();

        System.out.print("Email: ");
        this.email = sc.nextLine();

        System.out.print("Nome/Razão Social: ");
        this.nome = sc.nextLine();

        System.out.print("CNPJ: ");
        String cnpj = sc.nextLine();

        System.out.print("Limite de Crédito: ");
        double limiteCredito = Double.parseDouble(sc.nextLine());

        // Preenche outros dados internos
        this.dtCadastro = new Date(System.currentTimeMillis());
        this.status = true;

        // Chamada da procedure
        String sql = "{call PR_CADASTRAR_CLIENTE_COMPLETO(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, estado);
            cs.setString(2, cidade);
            cs.setString(3, bairro);
            cs.setString(4, cep);
            cs.setString(5, tipoEndereco);
            cs.setString(6, logradouro);
            cs.setString(7, dsLogradouro);
            cs.setInt(8, numero);
            cs.setString(9, complemento);
            cs.setString(10, tipoTelefone);
            cs.setInt(11, ddd);
            cs.setString(12, telefone);
            cs.setString(13, this.email);
            cs.setString(14, this.nome);
            cs.setString(15, cnpj);
            cs.setDouble(16, limiteCredito);

            cs.execute();
        }
        System.out.println("====================================================");
        System.out.println("Pessoa/Cliente cadastrada com sucesso via procedure!");
        System.out.println("=====================================================");
    }

    public abstract void cadastrarPessoaEspecializacao(Connection conn, Scanner sc) throws SQLException;

    // Getters e Setters
    public int getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(int id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

