package app.Controller.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;

import app.Views.Menus;
import app.Controller.Querys;
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

    public static void Exibir(int opcao, Class classe, String url, String usuario, String senha) throws Exception {
        
        conexao = DriverManager.getConnection(url, usuario, senha);

        ArrayList<String> cliente = _querys.ConsultarCliente(conexao);
        ArrayList<String> produto = _querys.ConsultarProdutos(conexao);

        HCliente hcliente = new HCliente();
        HProduto hproduto = new HProduto();

        if (opcao == 5 && classe == hcliente.getClass()) {
            HCliente.printaSelect(cliente);
        }
        if (opcao == 5 && classe == hproduto.getClass()) {
            HProduto.printaSelect(produto);
        }
    }

    public static Statement StatementsQuerys(Connection conn) throws SQLException, InterruptedException {
        Statement stmt = conn.createStatement();
        try {
            int max = 22;
            Querys.CriarTabelas(conn);
            System.out.println("Criando tabelas do sistema...");
            for (int i = 0; i <= max; i++) {
                Thread.sleep(100);
                System.out.print(String.format("\r%s", HMenus.progressBar(i, max)));
            }
            Menus.MenuPrincipal();
            System.out.println("\nSucesso tabelas criadas");
        } catch (SQLSyntaxErrorException e) {
            System.out.println(" - Tabelas [OK]");
            Menus.MenuPrincipal();
        }
        return stmt;
    }

    
}
