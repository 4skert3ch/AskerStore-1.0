package app.Controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import app.App;
import app.Controller.Connection.Conexao;
import app.Helpers.HCliente;
import app.Helpers.HMenus;

public class Querys {
    HMenus hmenus = new HMenus();
    App app = new App();

    public static Statement CriarTabelas(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql;

        sql = "create table clientes(" +
                "id int primary key auto_increment," +
                "nome varchar(25) not null," +
                "email varchar(35)," +
                "cpf varchar(11)," +
                "telefone varchar(20)," +
                "endereco varchar(20)," +
                "DataDeNascimento date);";
        stmt.executeUpdate(sql);

        sql = "create table produtos (" +
                "id int primary key auto_increment," +
                "id_cliente int not null," +
                "nome varchar(50) not null," +
                "preco decimal(10, 2) not null default 0," +
                "foreign key (id_cliente) references clientes(id));";
        stmt.executeUpdate(sql);

        return stmt;
    }

    public void inserirCliente(String url, String usuario, String senha) {
        String sql_insert_clientes = "insert into clientes (nome, email, cpf, telefone, endereco, DataDeNascimento) values (?, ?, ?, ?, ?, ?);";

        String nome;
        String email;
        String cpf;
        String telefone;
        String endereco;
        Date DataDeNascimento;

        try {
            Scanner scan = new Scanner(System.in);
            Connection conn = DriverManager.getConnection(url, usuario, senha);

            PreparedStatement stmt = conn.prepareStatement(sql_insert_clientes);
            System.out.println("Caso não queira inserir um valor, digite null.");
            System.out.println("Qual o nome do cliente? (obrigatório; máximo: 25 caracteres)");
            nome = scan.nextLine();
            stmt.setString(1, nome);

            System.out.println("Qual o email do cliente? (máximo: 35 caracteres)");
            email = scan.nextLine();
            stmt.setString(2, email);

            System.out.println("Qual o cpf do cliente? (máximo: 11 caracteres)");
            cpf = scan.nextLine();
            stmt.setString(3, cpf);

            System.out.println("Qual o telefone do cliente? (máximo: 20 caracteres)");
            telefone = scan.nextLine();
            stmt.setString(4, telefone);

            System.out.println("Qual o endereço do cliente? (máximo: 20 caracteres)");
            endereco = scan.nextLine();
            stmt.setString(5, endereco);

            System.out.println("Qual a data de nascimento do cliente?\nFormato: ano-mês-dia\nUse números!");
            DataDeNascimento = Date.valueOf(scan.nextLine());
            stmt.setDate(6, DataDeNascimento);

            stmt.executeUpdate();
            conn.close();

            HMenus.LimparConsole();
            App.main(null);

        } catch (Exception e) {
            System.out.println("INSERIDO COM SUCESSO !");
        }
    }

    public void inserirProduto(String url, String usuario, String senha) {
        String sql_insert_produtos = "insert into produtos (id_cliente, nome, preco) values (?, ?, ?);";

        int id_cliente;
        String nome;
        double preco;

        try {
            Scanner scan = new Scanner(System.in);
            Connection conn = DriverManager.getConnection(url, usuario, senha);
            PreparedStatement stmt = conn.prepareStatement(sql_insert_produtos);

            System.out.println("Insira o id do cliente:");
            id_cliente = Integer.parseInt(scan.nextLine());
            stmt.setInt(1, id_cliente);

            System.out.println("Insira o nome do produto (até 50 caracteres):");
            nome = scan.nextLine();
            stmt.setString(2, nome);

            System.out.println("Insira o preço do produto (até 11 caracteres):");
            preco = scan.nextDouble();
            stmt.setDouble(3, preco);

            HMenus.LimparConsole();

            stmt.executeUpdate();
            conn.close();

            HMenus.LimparConsole();
            App.main(null);

        } catch (Exception e) {
            System.out.println("INSERIDO COM SUCESSO !");
        }

    }

    public ArrayList<String> ConsultarCliente(Connection conn) throws SQLException {
        ArrayList<String> cliente = new ArrayList<String>();
        try {
            PreparedStatement pstm = conn.prepareStatement(
                    "select id, nome, email, cpf, telefone, endereco, datadeNascimento from clientes;");
            ResultSet rs = null;
            rs = pstm.executeQuery();
            while (rs.next()) {
                cliente.add(rs.getString("id"));
                cliente.add(rs.getString("nome"));
                cliente.add(rs.getString("email"));
                cliente.add(rs.getString("cpf"));
                cliente.add(rs.getString("telefone"));
                cliente.add(rs.getString("endereco"));
                cliente.add(rs.getString("datadeNascimento"));
            }
        } catch (Exception e) {
            System.out.println(e + "| erro select cliente");
        }

        return cliente;
    }

    public ArrayList<String> ConsultarProdutos(Connection conn) throws SQLException {
        ArrayList<String> cliente = new ArrayList<String>();
        try {
            PreparedStatement pstm = conn.prepareStatement(
                    "select id, id_cliente , nome, preco from produtos;");
            ResultSet rs = null;
            rs = pstm.executeQuery();
            while (rs.next()) {
                cliente.add(rs.getString("id"));
                cliente.add(rs.getString("id_cliente"));
                cliente.add(rs.getString("nome"));
                cliente.add(rs.getString("preco"));
            }
        } catch (Exception e) {
            System.out.println(e + "| erro select cliente");
        }

        return cliente;
    }

    public void DeletarCliente(String url, String usuario, String senha) throws Exception {

        Scanner entrada = new Scanner(System.in);
        HCliente hcliente = new HCliente();
        Conexao.Exibir(5, hcliente.getClass(), url, usuario, senha);

        System.out.println("Id do cliente");
        int idCliente = entrada.nextInt();

        System.out.println("Id do produto");
        int idProduto = entrada.nextInt();

        Statement sqlmgr = null;
        Connection conn2 = DriverManager.getConnection(url, usuario, senha);
        sqlmgr = conn2.createStatement();

        String sql_delete_cliente = "DELETE FROM produtos WHERE id = " + idProduto;
        sqlmgr.executeUpdate(sql_delete_cliente);
        String sql_delete_produto = "DELETE FROM clientes WHERE id = " + idCliente;
        sqlmgr.executeUpdate(sql_delete_produto);

        HMenus.LimparConsole();
        App.main(null);

    }

    public void AtualizarCliente(String url, String usuario, String senha) throws Exception {

        Scanner entrada = new Scanner(System.in);
        HCliente hcliente = new HCliente();
        Conexao.Exibir(5, hcliente.getClass(), url, usuario, senha);

        System.out.println("Id do cliente que deseja atualizar");
        int idCliente = entrada.nextInt();

        Statement sqlmgr = null;
        Connection conn2 = DriverManager.getConnection(url, usuario, senha);
        sqlmgr = conn2.createStatement();

        int v_contador = 0;
        String campos = "";

        do {// nome, email, cpf, telefone, endereco, datadenascimento

            // nome
            System.out.println("Você deseja atualizar o nome? (1) Sim | (2) Não");
            int opcaoNome = entrada.nextInt();

            switch (opcaoNome) {
                case 1:
                    System.out.println("Digite o novo nome:");
                    String nome = entrada.next();
                    nome = "'" + nome + "'";
                    campos = "nome = " + nome;
                    v_contador++;
                    break;
                default:
                    break;
            }

            // EMAIL
            System.out.println("Você deseja atualizar o email? (1) Sim | (2) Não");
            int opcaoEmail = entrada.nextInt();
            switch (opcaoEmail) {
                case 1:
                    String teste = v_contador > 0 ? "," : "";
                    campos = campos + teste;

                    System.out.println("Digite o novo email:");
                    String email = entrada.next();
                    email = "'" + email + "'";
                    campos = campos + "email = " + email;
                    v_contador++;
                    break;
                default:
                    break;
            }

            // CPF
            System.out.println("Você deseja atualizar o CPF? (1) Sim | (2) Não");
            int opcaoCPF = entrada.nextInt();
            switch (opcaoCPF) {
                case 1:
                    String teste = v_contador > 0 ? "," : "";
                    campos = campos + teste;

                    System.out.println("Digite o novo CPF:");
                    String CPF = entrada.next();
                    CPF = "'" + CPF + "'";
                    campos = campos + "cpf = " + CPF;
                    v_contador++;
                    break;
                default:
                    break;
            }

            // telefone
            System.out.println("Você deseja atualizar o telefone? (1) Sim | (2) Não");
            int opcaoTelefone = entrada.nextInt();
            switch (opcaoTelefone) {
                case 1:
                    String teste = v_contador > 0 ? "," : "";
                    campos = campos + teste;

                    System.out.println("Digite o novo telefone:");
                    String telefone = entrada.next();
                    telefone = "'" + telefone + "'";
                    campos = campos + "telefone = " + telefone;
                    v_contador++;
                    break;
                default:
                    break;
            }

            // ENDERECO
            System.out.println("Você deseja atualizar o endereço? (1) Sim | (2) Não");
            int opcaoEndereco = entrada.nextInt();
            switch (opcaoEndereco) {
                case 1:
                    String teste = v_contador > 0 ? "," : "";
                    campos = campos + teste;

                    System.out.println("Digite o novo endereço:");
                    String endereco = entrada.next();
                    endereco = "'" + endereco + "'";
                    campos = campos + "endereco = " + endereco;
                    v_contador++;
                    break;
                default:
                    break;
            }

            // data de nascimento
            System.out.println("Você deseja atualizar a data de nascimento? (1) Sim | (2) Não");
            int opcaoDatadeNascimento = entrada.nextInt();
            switch (opcaoDatadeNascimento) {
                case 1:
                    String teste = v_contador > 0 ? "," : "";
                    campos = campos + teste;

                    System.out.println("Digite o novo data de nascimento: formato AAAA-MM-DD");
                    String datadenascimento = entrada.next();
                    datadenascimento = "'" + datadenascimento + "'";
                    campos = campos + "DataDeNascimento = " + datadenascimento;
                    v_contador++;
                    break;
                default:
                    break;
            }

            if (v_contador == 0) {
                App.main(null);
            }

        } while (v_contador == 0);
        System.out.println("---------");
        String sql_update_cliente = "update clientes " +
                "set " + campos +
                " where id = " + idCliente;

        sqlmgr.executeUpdate(sql_update_cliente);
        // HMenus.LimparConsole();
        App.main(null);

        entrada.close();

    }

    public void AtualizarProduto(String url, String usuario, String senha) throws Exception {

        Scanner entrada = new Scanner(System.in);
        HCliente hcliente = new HCliente();
        Conexao.Exibir(5, hcliente.getClass(), url, usuario, senha);

        System.out.println("Id do cliente que deseja atualizar");
        int idCliente = entrada.nextInt();

        Statement sqlmgr = null;
        Connection conn2 = DriverManager.getConnection(url, usuario, senha);
        sqlmgr = conn2.createStatement();

        int v_contador = 0;
        String campos = "";

        do {// nome, email, cpf, telefone, endereco, datadenascimento

            // nome
            System.out.println("Você deseja atualizar o nome? (1) Sim | (2) Não");
            int opcaoNome = entrada.nextInt();

            switch (opcaoNome) {
                case 1:
                    System.out.println("Digite o novo nome:");
                    String nome = entrada.next();
                    nome = "'" + nome + "'";
                    campos = "nome = " + nome;
                    v_contador++;
                    break;
                default:
                    break;
            }

            // EMAIL
            System.out.println("Você deseja atualizar o email? (1) Sim | (2) Não");
            int opcaoEmail = entrada.nextInt();
            switch (opcaoEmail) {
                case 1:
                    String teste = v_contador > 0 ? "," : "";
                    campos = campos + teste;

                    System.out.println("Digite o novo email:");
                    String email = entrada.next();
                    email = "'" + email + "'";
                    campos = campos + "email = " + email;
                    v_contador++;
                    break;
                default:
                    break;
            }

            // CPF
            System.out.println("Você deseja atualizar o CPF? (1) Sim | (2) Não");
            int opcaoCPF = entrada.nextInt();
            switch (opcaoCPF) {
                case 1:
                    String teste = v_contador > 0 ? "," : "";
                    campos = campos + teste;

                    System.out.println("Digite o novo CPF:");
                    String CPF = entrada.next();
                    CPF = "'" + CPF + "'";
                    campos = campos + "cpf = " + CPF;
                    v_contador++;
                    break;
                default:
                    break;
            }

            // telefone
            System.out.println("Você deseja atualizar o telefone? (1) Sim | (2) Não");
            int opcaoTelefone = entrada.nextInt();
            switch (opcaoTelefone) {
                case 1:
                    String teste = v_contador > 0 ? "," : "";
                    campos = campos + teste;

                    System.out.println("Digite o novo telefone:");
                    String telefone = entrada.next();
                    telefone = "'" + telefone + "'";
                    campos = campos + "telefone = " + telefone;
                    v_contador++;
                    break;
                default:
                    break;
            }

            // ENDERECO
            System.out.println("Você deseja atualizar o endereço? (1) Sim | (2) Não");
            int opcaoEndereco = entrada.nextInt();
            switch (opcaoEndereco) {
                case 1:
                    String teste = v_contador > 0 ? "," : "";
                    campos = campos + teste;

                    System.out.println("Digite o novo endereço:");
                    String endereco = entrada.next();
                    endereco = "'" + endereco + "'";
                    campos = campos + "endereco = " + endereco;
                    v_contador++;
                    break;
                default:
                    break;
            }

            // data de nascimento
            System.out.println("Você deseja atualizar a data de nascimento? (1) Sim | (2) Não");
            int opcaoDatadeNascimento = entrada.nextInt();
            switch (opcaoDatadeNascimento) {
                case 1:
                    String teste = v_contador > 0 ? "," : "";
                    campos = campos + teste;

                    System.out.println("Digite o novo data de nascimento: formato AAAA-MM-DD");
                    String datadenascimento = entrada.next();
                    datadenascimento = "'" + datadenascimento + "'";
                    campos = campos + "DataDeNascimento = " + datadenascimento;
                    v_contador++;
                    break;
                default:
                    break;
            }

            if (v_contador == 0) {
                App.main(null);
            }

        } while (v_contador == 0);
        System.out.println("---------");
        String sql_update_cliente = "update clientes " +
                "set " + campos +
                " where id = " + idCliente;

        sqlmgr.executeUpdate(sql_update_cliente);
        // HMenus.LimparConsole();
        App.main(null);

        entrada.close();

    }

    public void DeletarProduto(String url, String usuario, String senha) throws Exception {

        Scanner entrada = new Scanner(System.in);
        HCliente hcliente = new HCliente();

        Conexao.Exibir(5, hcliente.getClass(), url, usuario, senha);

        System.out.println("Id do cliente");
        int id = entrada.nextInt();

        Statement sqlmgr = null;
        Connection conn2 = DriverManager.getConnection(url, usuario, senha);
        sqlmgr = conn2.createStatement();

        String sql_delete_cliente = "DELETE FROM produtos WHERE id = " + id;
        sqlmgr.executeUpdate(sql_delete_cliente);

        HMenus.LimparConsole();
        App.main(null);

    }

    public static ResultSet MostrarCliente_produto(int idCliente) {
        String url = "jdbc:mysql://localhost:3306/askerdata";
        String usuario = "root";
        String senha = "";
        String sql_Produtos_cliente = "SELECT p.Nome AS NomeProduto, p.preco, c.Nome AS NomeCliente FROM produtos p " +
                "JOIN clientes c ON c.ID = p.ID_cliente " +
                "WHERE c.ID = ?";

        try {
            Connection conn = DriverManager.getConnection(url, usuario, senha);
            PreparedStatement stmt = conn.prepareStatement(sql_Produtos_cliente);
            stmt.setInt(1, idCliente);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String NomeCliente = rs.getString("NomeCliente");
                String nomeProduto = rs.getString("NomeProduto");
                double preco = rs.getDouble("preco");
                System.out.println("Nome do cliente: " + NomeCliente);
                System.out.println("Nome do Produto: " + nomeProduto);
                System.out.println("Preço: R$" + preco);
                System.out.println("\r");
            }

            conn.close();
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static double ValorTotalProdutos(int idCliente) {
        String url = "jdbc:mysql://localhost:3306/askerdata";
        String usuario = "root";
        String senha = "";
        String sqlProdutosCliente = "SELECT SUM(p.preco) AS total FROM produtos p WHERE p.id_cliente = ?";

        try {
            Connection conn = DriverManager.getConnection(url, usuario, senha);
            PreparedStatement stmt = conn.prepareStatement(sqlProdutosCliente);
            stmt.setInt(1, idCliente);

            ResultSet rs = stmt.executeQuery();
            double total = 0.0;

            if (rs.next()) {
                total = rs.getDouble("total");
            }

            stmt.close();
            conn.close();

            return total;
        } catch (SQLException e) {
            System.out.println(e);
            return 0.0;
        }
    }
}
