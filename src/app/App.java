package app;

import java.util.Scanner;
import app.Database.Querys;
import app.Database.Connection.Conexao;
import app.Helpers.HCliente;
import app.Helpers.HProduto;
import app.Views.Menus;

public class App {
    public static void main(String[] arg) throws Exception {

	String url = "jdbc:mysql://localhost:3306/askerdata";
        String usuario = "root";
        String senha = "";
	
        Querys comandos = new Querys();
        HCliente hcliente = new HCliente();
        HProduto hproduto = new HProduto();

        Menus menu = new Menus();
        Conexao conn = new Conexao();

        conn.main(arg);
        Scanner scan = new Scanner(System.in);
        int opcao = scan.nextInt();

        if (opcao == 1) {
            menu.MenuAcoes();
            int crudCliente = scan.nextInt();
            if (crudCliente == 1) {
                main(arg);
            } else if (crudCliente == 2) {
                comandos.inserirCliente(url, usuario, senha);
            } else if (crudCliente == 3) {
            } else if (crudCliente == 4) {
		       comandos.DeletarCliente(url, usuario, senha);
            } else if (crudCliente == 5) {
                conn.Exibir(crudCliente, hcliente.getClass(), url, usuario, senha);
            } else {
                System.out.println("Essa opção não existe!");
            }
        }
        if (opcao == 2) {
            menu.MenuAcoes();
            int crudProduto = scan.nextInt();
            if (crudProduto == 1) {
                main(arg);
            } else if (crudProduto == 2) {
                comandos.inserirProduto(url, usuario, senha);
            } else if (crudProduto == 3) {
            } else if (crudProduto == 4) {
		        comandos.DeletarProduto(url, usuario, senha);
            } else if (crudProduto == 5) {
                conn.Exibir(crudProduto, hproduto.getClass(), url, usuario, senha);
            } else {
                System.out.println("Essa opção não existe!");
            }
        }

    }
}