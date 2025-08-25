/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.empresa;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class transportadora extends pessoaJuridica {

    private String nome_regiao;

    public transportadora() {
        super();
    }

    // Método público visível no diagrama
    public void consultarTransportadora(Connection conn) {
    String sql = """
        SELECT 
            t.COD_TRANSPORTADORA,
            z.COD_ZN_ENTREGA,
            z.NOME_REGIAO
        FROM 
            Transportadora t
        JOIN 
            Transportadora_Zona_Entrega tz 
            ON t.COD_TRANSPORTADORA = tz.FK_COD_TRANSPORTADORA
        JOIN 
            Zona_Entrega z 
            ON tz.FK_COD_ZN_ENTREGA = z.COD_ZN_ENTREGA
        """;

    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        System.out.println("\n--- Transportadoras e Regioes de Entrega ---");
        while (rs.next()) {
            int codTransportadora = rs.getInt("COD_TRANSPORTADORA");
            int codZona = rs.getInt("COD_ZN_ENTREGA");
            String nomeRegiao = rs.getString("NOME_REGIAO");

            System.out.printf("Transportadora: %d | Zona: %d | Regiao: %s%n", codTransportadora, codZona, nomeRegiao);
            
        }
    } catch (SQLException e) {
        System.out.println("Erro ao consultar transportadoras: " + e.getMessage());
    }
}



    // Método para coletar o nome da região
    private void entrarnome_regiao(Scanner sc) {
        System.out.print("Digite o nome da região de entrega: ");
        this.nome_regiao = sc.nextLine();
    }

    // Cadastra todos os dados da transportadora
   public void registrarRegioesEntrega(Connection conn, Scanner sc) throws SQLException {
    System.out.print("Digite o codigo da transportadora: ");
    int codTransportadora = Integer.parseInt(sc.nextLine());

    System.out.print("Digite o codigo da zona de entrega: ");
    int codZona = Integer.parseInt(sc.nextLine());

    String sql = "{ call TRANSPORTADORA_ZONA(?, ?) }";

    try (CallableStatement cs = conn.prepareCall(sql)) {
        cs.setInt(1, codTransportadora);
        cs.setInt(2, codZona);
        cs.execute();
        System.out.println("==============================");
        System.out.println("Regiao registrada com sucesso!");
        System.out.println("==============================");
    } catch (SQLException e) {
        System.out.println("Erro ao registrar região: " + e.getMessage());
        throw e;
    }
    
}


    // Atualiza os dados da transportadora
    private void editarTransportadora(Scanner sc) {
        System.out.println("Editar Transportadora:");
        entrarnome_regiao(sc);
        // Adicione aqui lógica para edição no banco se necessário
    }
    
    public void cadastrarTransportadora(Connection conn, Scanner scanner) {
    try {
        System.out.print("Digite o código da nova transportadora: ");
        int codTransportadora = scanner.nextInt();

        System.out.print("Digite o código da pessoa jurídica (já cadastrada): ");
        int fkCodPj = scanner.nextInt();

        String sql = "INSERT INTO Transportadora (COD_TRANSPORTADORA, FK_COD_PJ) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codTransportadora);
            ps.setInt(2, fkCodPj);
            ps.executeUpdate();
            System.out.println("Transportadora cadastrada com sucesso!");
        }

    } catch (SQLException e) {
        System.out.println("Erro ao cadastrar transportadora: " + e.getMessage());
    }
}



    // Inativa a transportadora (status = false)
    private void inativarTransportadora(Connection conn) {
        try {
            String sql = "UPDATE pessoa SET status = 0 WHERE id_pessoa = ?";
            var pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, getId_pessoa());
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Transportadora inativada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao inativar: " + e.getMessage());
        }
    }

    // Método auxiliar para buscar o código da zona pelo nome
    private int buscarCodigoZonaPorNome(Connection conn, String nome) {
        try {
            var stmt = conn.prepareStatement("SELECT cod_zona FROM zona_entrega WHERE nome_zona = ?");
            stmt.setString(1, nome);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cod_zona");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar zona: " + e.getMessage());
        }
        return -1; // Não encontrado
    }

    // Getter e Setter
    public String getNome_regiao() {
        return nome_regiao;
    }

    public void setNome_regiao(String nome_regiao) {
        this.nome_regiao = nome_regiao;
    }
}
