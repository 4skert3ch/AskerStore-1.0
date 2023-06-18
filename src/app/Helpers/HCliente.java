package app.Helpers;
import java.util.ArrayList;

import app.App;
import app.Controller.Querys;
import app.Controller.Connection.Conexao;

import java.util.Scanner;

public class HCliente {
    public static void printaSelect(ArrayList<String> select) throws Exception {
        int tamanho = ((select.size()) / 7);
        System.out.println("========================CLIENTES===========================");
        for (int i = 0; i < tamanho; i++) {
            if (i == 0){
                System.out.println(select.get(i) + " | " + select.get(i+1));
            } else {
                System.out.println(select.get(i+(6*i)) + " | "+ select.get(i+(6*i)+1));
            }
        }
    }
    public static void ClienteExibir() throws Exception  {
        Scanner leitor = new Scanner(System.in);

        for (;;) {
        System.out.println("Insira o id do cliente para mostrar suas informações ou digite 0 para voltar: ");
        int idCliente = leitor.nextInt();

         if (idCliente == 0) {
                HMenus.LimparConsole();
                App.main(null);
            }
            else {
                 Querys.exibirInformacoesCliente(idCliente);
        }
    }
}
}
