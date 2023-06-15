package app.Helpers;

import java.util.ArrayList;
import java.util.Scanner;

import app.App;
import app.Controller.Querys;

public class HProduto {

    public static void printaSelect(ArrayList<String> select) throws Exception {

        int tamanho = ((select.size()) / 4);
        System.out.println("========================PRODUTOS===========================");
        for (int i = 0; i < tamanho; i++) {
            if (i == 0) {
                System.out.println(select.get(i) + " | " + select.get(i + 1));
            } else {
                System.out.println(select.get(i + (6 * i)) + " | " + select.get(i + (6 * i) + 1));
            }
        }
    }

    public static void ValorTotalCompras() throws Exception {
    Scanner leitor = new Scanner(System.in);
        for (;;) {
            System.out
                    .println("Insira o ID do cliente para saber o valor de suas compras (ou digite '0' para voltar):");
            int idCliente = leitor.nextInt();

            if (idCliente == 0) {
                HMenus.LimparConsole();
                App.main(null);
            }

            Querys.MostrarCliente_produto(idCliente);
            double valorTotal = Querys.ValorTotalProdutos(idCliente);
            System.out.println("Valor total dos produtos: R$" + valorTotal + "\n");
        }
    }

}
