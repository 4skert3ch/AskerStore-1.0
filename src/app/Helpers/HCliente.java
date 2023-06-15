package app.Helpers;
import java.util.ArrayList;

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
}
