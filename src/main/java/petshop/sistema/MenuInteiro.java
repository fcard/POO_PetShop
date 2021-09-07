package petshop.sistema;
import java.util.Scanner;
import java.util.Set;

public class MenuInteiro extends MenuInteiroAbstrato<Integer> {
  public MenuInteiro(String titulo, Integer minimo, Integer maximo) {
    super(titulo, minimo, maximo);
  }

  public MenuInteiro(String titulo, Set<Integer> conjunto) {
    super(titulo, conjunto);
  }

  @Override
  public Integer obterResultado(String line) {
    return Integer.parseInt(line);
  }
}

