package petshop.sistema;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Function;
import petshop.animais.*;

public abstract class MenuId<T,C> extends Menu<Long> {
  Hashtable<Long, T> dados;

  public MenuId(String titulo, Hashtable<Long, T> dados) {
    super(titulo);
    this.dados = dados;
  }

  abstract void ajudaPesquisa();
  abstract Function<String, Boolean> lerOpcaoDePesquisa(String arg, C criterios);
  abstract C criarCriteriosDePesquisa();
  abstract Predicate<T> criarPredicado(C criterios);
  abstract void mostrarDados(long id, T dados);

  @Override
  public void mostrar() {
    System.out.println("# digite um numero id, nome, ou comando          #");
    System.out.println("# comandos:                                      #");
    System.out.println("#   %minimo - mostra o id minimo                 #");
    System.out.println("#   %maximo - mostra o id maximo                 #");
    System.out.println("#   %pesquisa <opções> - pesquisar animais       #");
    System.out.println("#   %ajuda-pesquisa - mostra opções de pesquisa  #");
    System.out.println("##################################################");
  }

  @Override
  public boolean resultadoValido(Long resultado) {
    return dados.containsKey(resultado);
  }

  @Override
  public Long obterResultado(String line) {
    while (true) {
      if (tentar_ler_comando(line)) {
      } else if (line.equals("%MINIMO")) {
        idMinimo();
      } else if (line.equals("%MAXIMO")) {
        idMaximo();
      } else if (line.equals("%AJUDA-PESQUISA")) {
        ajudaPesquisa();
      } else if (line.startsWith("%PESQUISA")) {
        var argumentos = line.substring(9).trim().split(" ");
        pesquisa(argumentos);
      } else {
        try {
          var id = Long.parseLong(line);
          return id;
        } catch (NumberFormatException e) {
          if (!line.isEmpty()) {
            procurarNome(line);
          }
        }
      }
      System.out.print("> ");
      line = scanner.nextLine().trim().toUpperCase();
    }
  }

  void idMinimo() {
    Long minimo = null;
    for (var id : dados.keySet()) {
      if (minimo == null || id < minimo) {
        minimo = id;
      }
    }
    if (minimo != null) {
      System.out.println(String.format("Minimo ID: %d", minimo));
    }
  }

  void idMaximo() {
    Long maximo = null;
    for (var id : dados.keySet()) {
      if (maximo == null || id > maximo) {
        maximo = id;
      }
    }
    if (maximo != null) {
      System.out.println(String.format("Maximo ID: %d", maximo));
    }
  }

  void pesquisa(String[] argumentos) {
    class ConsumidorDeArgumentos {
      int i = 0;
      public void consumir(Function<String, Boolean> f) {
        if (i+1 < argumentos.length) {
          if (f.apply(argumentos[i+1])) {
            i += 2;
          } else {
            i += 1;
          }
        } else {
          i += 1;
        }
      }

      public boolean terminado() {
        return i >= argumentos.length;
      }

      public String comando() {
        return argumentos[i];
      }
    }

    var c = new ConsumidorDeArgumentos();
    var p = criarCriteriosDePesquisa();
    while (!c.terminado()) {
      var arg = c.comando();
      var opc = lerOpcaoDePesquisa(arg, p);
      if (opc != null) {
        c.consumir(opc);
      } else {
        c.i += 1;
      }
    }

    executarPesquisa(criarPredicado(p));
  }


  void procurarNome(String nome) {
    executarPesquisa(dado -> dado.toString().toUpperCase().contains(nome));
  }

  void executarPesquisa(Predicate<T> predicado) {
    var resultado = new ArrayList<Long>();
    dados.forEach((id, dado) -> {
      if (predicado.test(dado)) {
        resultado.add(id);
      }
    });
    if (!resultado.isEmpty()) {
      System.out.println("##################################################");
      for (var id : resultado) {
        var dado = dados.get(id);
        mostrarDados(id, dado);
        System.out.println("##################################################");
      }
    }
  }
}

