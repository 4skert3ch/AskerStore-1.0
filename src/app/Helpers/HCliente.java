package app.Helpers;
import java.util.ArrayList;

public class HCliente {
    public static void printaSelect(ArrayList<String> select) throws Exception {
        int tamanho = ((select.size()) / 7);
        System.out.println("================================================CLIENTES===================================================");
        for (int i = 0; i < tamanho; i++) {
            // método temporario
            if (i == 0){
                System.out.println("ID : " + select.get(i) 
                              + " | Nome cliente : " + select.get(i+1) 
                              + " | Email : "+ select.get(i+2) 
                              + " | CPF : "+ select.get(i+3));
            } else {
                System.out.println("ID : "+select.get(i+(6*i)) 
                              + " | Nome cliente : "+ select.get(i+(6*i)+1)
                              + " | Email : "+ select.get(i+(6*i)+2)
                              + " | CPF : "+ select.get(i+(6*i)+3)
                              + " | Telefone : " + select.get(i+(6*i)+4)
                              + " | Endereço : "+ select.get(i+(6*i)+5)
                              + " | Data de nascimento : "+select.get(i+(6*i)+6));
            }
        }
    }
}
