package petshop.sistema;
import petshop.ajudante.Io;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

public abstract class Menu<T> {
  String titulo;
  Scanner scanner;
  private boolean saiu;
  private boolean voltou;
  protected boolean mostrarMenu = false;

  public Menu(String titulo) {
    this.titulo = titulo;
    this.scanner = new Scanner(System.in);
  }

  public void executar(Consumer<T> consumidor) {
    T valor = executar();
    if (valor != null) {
      consumidor.accept(valor);
    }
  }

  public T executar() {
    mostrar_com_titulo();
    int iteracoes = 0;
    T resultado = null;
    do {
      if (iteracoes == 8 || mostrarMenu) {
        mostrar_com_titulo();
        mostrarMenu = false;
        iteracoes = 0;
      }
      String line = null;
      boolean comando = true;
      while (comando) {
        System.out.print("> ");
        line = scanner.nextLine().trim().toUpperCase();
        comando = tentar_ler_comando(line);
        if (voltou || saiu) {
          return null;
        }
      }
      resultado = obterResultado(line);
      iteracoes += 1;
    } while (!resultadoValido(resultado));
    return resultado;
  }

  boolean tentar_ler_comando(String line) {
    switch (line) {
      case "%AJUDA":
        System.out.println("##################################################");
        System.out.println("# comandos globais:                              #");
        System.out.println("#   %ajuda  - mostrar essa mensagem de ajuda     #");
        System.out.println("#   %voltar - retornar para o menu anterior      #");
        System.out.println("#   %sair   - terminar o programa                #");
        System.out.println("##################################################");
        return true;

      case "%MEOW":
        System.out.println("##################################################");
        System.out.println("# Meow Meow Meow Meow Meow Meow Meow Meow Meow ~ #");
        System.out.println("# Meow Meow Meow Meow Meow Meow Meow Meow Meow ~ #");
        System.out.println("# Meow Meow Meow Meow Meow Meow Meow Meow Meow ~ #");
        System.out.println("##################################################");
        return true;

      case "%VOLTAR":
        voltou = true;
        return true;

      case "%SAIR":
        saiu = true;
        return true;

      default:
        return false;
    }
  }

  void mostrar_com_titulo() {
    Io.limparTela();
    System.out.println("##################################################");
    System.out.println(String.format("# %-46s #", titulo));
    System.out.println("##################################################");

    mostrar();
  }

  public abstract void mostrar();
  public abstract boolean resultadoValido(T resultado);
  public abstract T obterResultado(String linha);

  public final boolean voltou() {
    return voltou;
  }

  public final boolean saiu() {
    return saiu;
  }

  public static class Sequenciador {
    private boolean voltou;
    private boolean saiu;

    public <T> Sequenciador executar(Menu<T> menu, Consumer<T> consumidor) {
      if (!voltou && !saiu) {
        menu.executar(consumidor);
        voltou = menu.voltou();
        saiu = menu.saiu();
      }
      return this;
    }

    public final boolean voltou() {
      return voltou;
    }

    public final boolean saiu() {
      return saiu;
    }
  }
}


