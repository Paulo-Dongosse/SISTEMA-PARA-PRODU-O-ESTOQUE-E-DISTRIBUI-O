/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.empresa;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

public class Empresa {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;
        boolean conexaoBemSucedida = false;
        boolean continuar = true;

        try {
            // Estabelece a conexão com o banco de dados
            conn = ConexaoOracle.conectar();
            conexaoBemSucedida = true;
            System.out.println("******************************************************");
            System.out.println("Conexao com o banco de dados estabelecida com sucesso!");
            System.out.println("*******************************************************");

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            System.err.println("O programa continuará, mas funcionalidades do banco de dados não estarão disponíveis.");
        }

        while (continuar) {
            System.out.println("====== MENU PRINCIPAL ======");
            System.out.println("");
            System.out.println("1 - Cliente");
            System.out.println("2 - Fornecedor");
            System.out.println("3 - Vendedor");
            System.out.println("4 - Consultar Transportadoria e Regiao de entrega");
            System.out.println("5 - Registrar Regiao de entrega"); // Adicionado Transportadora
            System.out.println("6 - Pedido");
            System.out.println("7 - Pagamento");
            System.out.println("8 - Peca");
            System.out.println("9 - Materia Prima");
            System.out.println("0 - Sair");
            System.out.println("");
            System.out.println("=================");
            System.out.print("Escolha uma opcao: ");
            int opcao = sc.nextInt();
            sc.nextLine(); // Consumir a nova linha

            try {
                if (conexaoBemSucedida) {
                    switch (opcao) {
                        case 1:
                            cliente cli = new cliente();
                            cli.cadastrarCliente(conn, sc);
                            break;
                        case 2:
                            fornecedor forn = new fornecedor();
                            forn.cadastrarFornecedor(conn, sc);
                            break;
                        case 3:
                            vendedor vend = new vendedor();
                            vend.cadastrarVendedor(conn, sc);
                            break;
                        case 4:
                            transportadora tra = new transportadora();
                            tra.consultarTransportadora(conn);
                            break;
                        case 5: // Cadastrar Transportadora
                            transportadora transp = new transportadora();
                            transp.registrarRegioesEntrega(conn, sc);
                            break;
                        case 6:
                            pedido ped = new pedido();
                            ped.cadastrarPedidoCliente(conn, sc);
                            break;
                        case 7:
                            pagamento pag = new pagamento();
                            pag.coletarDados(sc);
                            pag.registrarPagamento(conn);
                            break;
                        case 8:
                            peca pec = new peca();
                            pec.coletarDados(sc);
                            pec.cadastrarPeca(conn);
                            break;
                        case 9:
                            materiaPrima matPri = new materiaPrima();
                            matPri.coletarDados(sc);
                            matPri.cadastrarMateriaPrima(conn);
                            break;
                        case 0:
                            continuar = false; // Opção para sair do loop
                            break;
                        default:
                            System.out.println("Opcao inválida.");
                    }
                } else if (opcao == 0) {
                    continuar = false;
                } else {
                    System.out.println("Funcionalidades do banco de dados não disponíveis (sem conexão com o banco).");
                }

            } catch (SQLException e) {
                System.err.println("Erro ao executar operacao do banco de dados: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
        sc.close();
        System.out.println("===================");
        System.out.println("Programa encerrado.");
        System.out.println("====================");
    }
}
