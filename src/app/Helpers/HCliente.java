package app.Helpers;
import java.util.ArrayList;

public class HCliente {
    public static void printaSelect(ArrayList<String> select) throws Exception {
        int tamanho = ((select.size()) / 7);
        System.out.println("========================CLIENTES===========================");
        for (int i = 0; i < tamanho; i++) {
            // método temporario
            if (i == 0){
                System.out.println(" ID : " + select.get(i) 
                + " | Nome Cliente : " + select.get(i+1) 
                + " | Email : "+ select.get(i+2) 
                + " | Telefone : "+ select.get(i+3));
            } else {
                System.out.println(select.get(i+(6*i)) + " | "+ select.get(i+(6*i)+1));
            }
        }
    }
}
