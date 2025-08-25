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

public class pedido {

    private int id_pedido;
    private Date dtPedido;
    private int qtdPeca;
    private int id_cliente;
    private int id_vendedor;
    private int id_transportadora;

    public pedido() {}

    public pedido(int id_pedido, Date dtPedido, int qtdPeca, int id_cliente, int id_vendedor, int id_transportadora) {
        this.id_pedido = id_pedido;
        this.dtPedido = dtPedido;
        this.qtdPeca = qtdPeca;
        this.id_cliente = id_cliente;
        this.id_vendedor = id_vendedor;
        this.id_transportadora = id_transportadora;
    }

    public void cadastrarPedidoCliente(Connection conn, Scanner sc) throws SQLException {
        System.out.println("===============================");
        System.out.println("Cadastrando Pedido para Cliente...");
        System.out.println("===============================");
        System.out.print("Data do Pedido (AAAA-MM-DD): ");
        this.dtPedido = Date.valueOf(sc.nextLine());
        System.out.print("Quantidade de Peças: ");
        this.qtdPeca = sc.nextInt();
        sc.nextLine();
        System.out.print("ID do Cliente: ");
        this.id_cliente = sc.nextInt();
        sc.nextLine();
        System.out.print("ID do Vendedor: ");
        this.id_vendedor = sc.nextInt();
        sc.nextLine();
        System.out.print("ID da Transportadora: ");
        this.id_transportadora = sc.nextInt();
        sc.nextLine();

        // OBS: COD_PEDIDO gerado automaticamente pela SEQUENCE no Oracle
        String sql = "INSERT INTO PEDIDO (COD_PEDIDO, DT_PEDIDO, QTD_PECA, FK_COD_CLIENTE, FK_COD_VENDEDOR, FK_COD_TRANSPORTADORA) " +
                     "VALUES (SEQ_COD_PEDIDO.NEXTVAL, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, this.dtPedido);
            pstmt.setInt(2, this.qtdPeca);
            pstmt.setInt(3, this.id_cliente);
            pstmt.setInt(4, this.id_vendedor);
            pstmt.setInt(5, this.id_transportadora);
            pstmt.executeUpdate();
            System.out.println("===============================");
            System.out.println("Pedido cadastrado com sucesso.");
            System.out.println("===============================");
        }
    }

    public void cancelarPedidoCliente() {
        System.out.println("Cancelando Pedido do Cliente...");
    }

    public void selecionarTransporte() {
        System.out.println("Selecionando Transporte...");
    }

    public void calcularFrete() {
        System.out.println("Calculando Frete...");
    }

    public void aplicarDesconto() {
        System.out.println("Aplicando Desconto...");
    }

    public void emitirNotaFiscal() {
        System.out.println("Emitindo Nota Fiscal...");
    }

    public void verificarEstoque() {
        System.out.println("Verificando Estoque...");
    }

    public void atualizarStatusPedido() {
        System.out.println("Atualizando Status do Pedido...");
    }

    public void listarPedidosCliente() {
        System.out.println("Listando Pedidos do Cliente...");
    }

    public void consultarPedidoPorData() {
        System.out.println("Consultando Pedido por Data...");
    }

    public void gerarRelatorioPedidos() {
        System.out.println("Gerando Relatório de Pedidos...");
    }

    public void validarDadosPedido() {
        System.out.println("Validando Dados do Pedido...");
    }

    // Getters e Setters
    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Date getDtPedido() {
        return dtPedido;
    }

    public void setDtPedido(Date dtPedido) {
        this.dtPedido = dtPedido;
    }

    public int getQtdPeca() {
        return qtdPeca;
    }

    public void setQtdPeca(int qtdPeca) {
        this.qtdPeca = qtdPeca;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public int getId_transportadora() {
        return id_transportadora;
    }

    public void setId_transportadora(int id_transportadora) {
        this.id_transportadora = id_transportadora;
    }
}
