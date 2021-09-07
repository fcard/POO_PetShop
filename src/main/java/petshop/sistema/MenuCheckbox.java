package petshop.sistema;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class MenuCheckbox extends Menu<Set<Integer>> {
  ArrayList<String> items;
  Set<Integer> selecao;

  public MenuCheckbox(String titulo, String[] items) {
    super(titulo);
    this.items = new ArrayList<String>();
    this.selecao = new HashSet<Integer>();
    for (String s : items) {
      this.items.add(s);
    }
  }

  @Override
  public void mostrar() {
    for (int i = 0; i < items.size(); i++) {
      String check = "[ ]";
      if (selecao.contains(i+1)) {
        check = "[x]";
      }
      System.out.println(String.format(" %-2d - %s %s", i+1, check, items.get(i)));
    }
    System.out.println("###################################################");
    System.out.println("# digite um numero, nome, ou comando              #");
    System.out.println("# comandos:                                       #");
    System.out.println("#   %terminar - retornar sua seleção              #");
    System.out.println("#   %voltar   - cancelar sua seleção              #");
    System.out.println("#   %limpar   - desmarcar todas as seleções       #");
    System.out.println("#   %todos    - marcar todas as seleções          #");
    System.out.println("###################################################");
  }

  @Override
  public boolean resultadoValido(Set<Integer> resultado) {
    return resultado != null;
  }

  @Override
  public Set<Integer> obterResultado(String line) {
    switch (line) {
      case "%TERMINAR":
        return selecao;

      case "%LIMPAR":
        mostrarMenu = true;
        selecao.clear();
        break;

      case "%TODOS":
        mostrarMenu = true;
        for (int i = 1; i <= items.size(); i++) {
          selecao.add(i);
        }
        break;

      default:
        var i = lerInteiro(line);
        if (i != null) {
          mostrarMenu = true;
          if (selecao.contains(i)) {
            selecao.remove(i);
          } else {
            selecao.add(i);
          }
        }
    }
    return null;
  }

  public Integer lerInteiro(String line) {
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


