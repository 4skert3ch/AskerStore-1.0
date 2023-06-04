package app.Helpers;

import java.util.ArrayList;

public class HProduto {
    public static void printaSelect(ArrayList<String> select) {
        int tamanho = ((select.size()) / 7);
        System.out.println(tamanho);
    }
}
