package petshop.sistema;
import java.util.Scanner;
import java.util.Set;

public class MenuLong extends MenuInteiroAbstrato<Long> {
  public MenuLong(String titulo, Long minimo, Long maximo) {
    super(titulo, minimo, maximo);
  }

  public MenuLong(String titulo, Set<Long> conjunto) {
    super(titulo, conjunto);
  }

  @Override
  public Long obterResultado(String line) {
    try {
      Long i = Long.parseLong(line);
      return i;
    } catch (Exception e) {
      return null;
    }
  }
}

