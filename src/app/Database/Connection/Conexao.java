package app.Database.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;

import app.Views.Menus;
import app.Database.Querys;
import app.Helpers.HCliente;
import app.Helpers.HMenus;
import app.Helpers.HProduto;


public class Conexao {
    public static Connection conexao = null;
    public static Querys _querys = null;
    static String url = "jdbc:mysql://localhost:3306/askerdata";

    public static void main(String[] args) throws InterruptedException {
        _querys = new Querys();

        try {
            // Carrega o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Cria a conexão
            String url = "jdbc:mysql://localhost:3306/askerdata";
            String usuario = "root";
            String senha = "";

            conexao = DriverManager.getConnection(url, usuario, senha);
            StatementsQuerys(conexao);

        } catch (ClassNotFoundException e) {
            System.out.println("Não foi possível carregar o driver JDBC");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Não foi possível obter uma conexão com o banco de dados");
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.out.println("Não foi possível fechar a conexão com o banco de dados");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void Exibir(int opcao, Class classe, String url, String usuario, String senha) throws SQLException {
        
        conexao = DriverManager.getConnection(url, usuario, senha);

        ArrayList<String> cliente = _querys.Consultas(conexao);
        ArrayList<String> produto = _querys.Consultas(conexao);

        HCliente hcliente = new HCliente();
        HProduto hproduto = new HProduto();

        if (opcao == 5 && classe == hcliente.getClass()) {
            hcliente.printaSelect(cliente);
            for (int i = 0; i < cliente.size(); i++) {
                System.out.println(cliente.get(i));
            }
        }

        if (opcao == 5 && classe == hproduto.getClass()) {
            hproduto.printaSelect(produto);
            for (int i = 0; i < cliente.size(); i++) {
                System.out.println(cliente.get(i));
            }
        }
    }

    public static Statement StatementsQuerys(Connection conn) throws SQLException, InterruptedException {
        Querys _querys = new Querys();
        HMenus hmenus = new HMenus();
        Menus menu = new Menus();
        Statement stmt = conn.createStatement();
        try {
            int max = 22;
            _querys.CriarTabelas(conn);
            System.out.println("Criando tabelas do sistema...");
            for (int i = 0; i <= max; i++) {
                Thread.sleep(100);
                System.out.print(String.format("\r%s", hmenus.progressBar(i, max)));
            }
            menu.MenuPrincipal();
            System.out.println("\nSucesso tabelas criadas");
        } catch (SQLSyntaxErrorException e) {
            System.out.println(" - Tabelas [OK]");
            menu.MenuPrincipal();
        }
        return stmt;
    }

    
}
