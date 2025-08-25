/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class pagamento {

    private Date dtPagamento;
    private Date dtVencimento;
    private double valorTotal;
    private int fkCodPedido;
    private int fkCodFormaPgto;
    private int fkCodTipoPgto;

    public void coletarDados(Scanner sc) {
        System.out.println("===============================");
        System.out.println("Cadastrando Pagamento...");
        System.out.println("===============================");

        System.out.print("Data do Pagamento (AAAA-MM-DD): ");
        this.dtPagamento = Date.valueOf(sc.nextLine());

        System.out.print("Data de Vencimento (AAAA-MM-DD): ");
        this.dtVencimento = Date.valueOf(sc.nextLine());

        System.out.print("Valor Total: ");
        this.valorTotal = Double.parseDouble(sc.nextLine());

        System.out.print("Codigo do Pedido: ");
        this.fkCodPedido = Integer.parseInt(sc.nextLine());

        System.out.print("Codigo da Forma de Pagamento: ");
        this.fkCodFormaPgto = Integer.parseInt(sc.nextLine());

        System.out.print("Codigo do Tipo de Pagamento: ");
        this.fkCodTipoPgto = Integer.parseInt(sc.nextLine());
    }

    public void registrarPagamento(Connection conn) {
        String sql = "{call PR_CADASTRAR_PAGAMENTO(?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setDate(1, dtPagamento);
            cs.setDate(2, dtVencimento);
            cs.setDouble(3, valorTotal);
            cs.setInt(4, fkCodPedido);
            cs.setInt(5, fkCodFormaPgto);
            cs.setInt(6, fkCodTipoPgto);
            cs.registerOutParameter(7, java.sql.Types.INTEGER);

            cs.execute();

            int codGerado = cs.getInt(7);
            System.out.println("======================================================");
            System.out.println("Pagamento cadastrado com sucesso! Codigo: " + codGerado);
            System.out.println("======================================================");
        } catch (SQLException e) {
            System.err.println("Erro ao executar operação do banco de dados: " + e.getMessage());
        }
    }

    // Métodos adicionais (sem implementação)

    public void acompanharPagamento() {
        // Lógica futura
    }

    public void faturarPedido() {
        // Lógica futura
    }

    public void verificarStatusPagamento() {
        // Lógica futura
    }

    public void verificarLimiteCredito() {
        // Lógica futura
    }

    public void validarPagamento() {
        // Lógica futura
    }

    public void emitirPedido() {
        // Lógica futura
    }

    public void tipoPagamento() {
        // Lógica futura
    }

    public void gerarBoleto() {
        // Lógica futura
    }

    public void consultarHistoricoFaturamento() {
        // Lógica futura
    }

    public void consultarHistoricoVenda() {
        // Lógica futura
    }
}
