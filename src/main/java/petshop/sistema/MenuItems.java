package petshop.sistema;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuItems extends Menu<Integer> {
  ArrayList<String> items;

  public MenuItems(String titulo, String... items) {
    super(titulo);
    this.items = new ArrayList<String>();
    for (String s : items) {
      this.items.add(s);
    }
  }

  @Override
  public void mostrar() {
    for (int i = 0; i < items.size(); i++) {
      System.out.println(String.format(" %-2d - %s", i+1, items.get(i)));
    }
    System.out.println("###################################################");
    System.out.println("# digite o numero correspondente ao item desejado #");
    System.out.println("###################################################");
  }

  @Override
  public boolean resultadoValido(Integer resultado) {
    return resultado != null && (resultado >= 1 && resultado <= items.size());
  }

  @Override
  public Integer obterResultado(String line) {
    try {
      return Integer.parseInt(line);
    } catch (Exception e) {
      // procurar a string dentre os items
      // permitindo que o usuario digite
      // o nome do item ao invez do id.
      for (int i = 0; i < items.size(); i++) {
        if (items.get(i).toUpperCase().equals(line)) {
          return i+1;
        }
      }
      return null;
    }
  }
}


