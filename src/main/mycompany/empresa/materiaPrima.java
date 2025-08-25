/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package com.mycompany.empresa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class materiaPrima {

    private String dsMateria;
    private double qtdNecessaria;
    private String dsMedida;

    public void coletarDados(Scanner sc) {
        System.out.println("===============================");
        System.out.println("Cadastrando Materia-Prima...");
        System.out.println("===============================");
        System.out.print("Descricao da Materia-Prima: ");
        this.dsMateria = sc.nextLine();

        System.out.print("Quantidade Necesseria: ");
        this.qtdNecessaria = sc.nextDouble();
        sc.nextLine(); // Limpar buffer

        System.out.print("Descricao do Tipo de Medida (ex: KG, L, UN): ");
        this.dsMedida = sc.nextLine();
    }

    public void cadastrarMateriaPrima(Connection conn) {
        String sql = "{call PR_CADASTRAR_MATERIA_TIPO(?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, dsMateria);
            cs.setDouble(2, qtdNecessaria);
            cs.setString(3, dsMedida);

            cs.execute();
            System.out.println("=====================================");
            System.out.println("Materia-prima cadastrada com sucesso!");
            System.out.println("======================================");
        } catch (SQLException e) {
            System.err.println("Erro ao executar operação do banco de dados: " + e.getMessage());
        }
    }
}

