package petshop.gui;
import petshop.animais.*;
import petshop.produtos.*;
import petshop.empregados.*;
import java.util.function.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static petshop.dados.Historico.RegistroTipo;
import static petshop.dados.Historico.Registro;

public class Pesquisa {
  public String nome = null;

  private static class Aux {
    int i = 0;
    String[] args;

    public Aux(String s) {
      args = s.split(" ");
    }

    public String arg() {
      return args[i];
    }

    public <T> T getArg(Function<String,T> f) {
      if (i+1 < args.length) {
        try {
          T resultado = f.apply(args[i+1]);
          i += 1;
          return resultado;
        } catch (Exception e) {
          return null;
        }
      } else {
        return null;
      }
    }

    public Double argDouble() {
      return getArg(arg -> Double.parseDouble(arg));
    }

    public Integer argInteger() {
      return getArg(arg -> Integer.parseInt(arg));
    }

    public String argString() {
      return getArg(arg -> arg);
    }

    public Boolean argBoolean() {
      return getArg(arg -> Boolean.parseBoolean(arg));
    }

    public LocalDate argDate() {
      return getArg(arg -> {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(arg, fmt);
      });
    }
  }

  public static class Animal extends Pesquisa {
    Double pesoMinimo = null;
    Double pesoMaximo = null;
    Integer idadeMinima = null;
    Integer idadeMaxima = null;
    Double alturaMinima = null;
    Double alturaMaxima = null;

    public Animal(String s) {
      var a = new Aux(s);
      for (a.i = 0; a.i < a.args.length; a.i++) {
        switch (a.arg().toLowerCase()) {
          case "-peso-min":
          case "-peso-minimo":
          case "-pmin":
            pesoMinimo = a.argDouble();
            break;

          case "-peso-max":
          case "-peso-maximo":
          case "-pmax":
            pesoMaximo = a.argDouble();
            break;

          case "-idade-min":
          case "-idade-minima":
          case "-imin":
            idadeMinima = a.argInteger();
            break;

          case "-idade-max":
          case "-idade-maxima":
          case "-imax":
            idadeMaxima = a.argInteger();
            break;

          case "-altura-min":
          case "-altura-minima":
          case "-amin":
            alturaMinima = a.argDouble();
            break;

          case "-altura-max":
          case "-altura-maxima":
          case "-amax":
            alturaMinima = a.argDouble();
            break;

          default:
            if (!a.arg().isEmpty()) {
              nome = a.arg();
            }
            break;
        }
      }
    }
    public boolean seAplica(IAnimal animal) {
      return (nome == null || animal.nome().contains(nome)) &&
             (pesoMinimo == null || animal.peso() >= pesoMinimo) &&
             (pesoMaximo == null || animal.peso() <= pesoMaximo) &&
             (idadeMinima == null || animal.idade() >= idadeMinima) &&
             (idadeMaxima == null || animal.idade() <= idadeMaxima) &&
             (alturaMinima == null || animal.altura() >= alturaMinima) &&
             (alturaMaxima == null || animal.altura() <= alturaMaxima);
    }
  }

  public static class Produto extends Pesquisa {
    Double pesoMinimo = null;
    Double pesoMaximo = null;
    Double areaMinima = null;
    Double areaMaxima = null;
    Double compraMinima = null;
    Double compraMaxima = null;
    Double vendaMinima = null;
    Double vendaMaxima = null;

    public Produto(String s) {
      var a = new Aux(s);
      for (a.i = 0; a.i < a.args.length; a.i++) {
        switch (a.arg().toLowerCase()) {
          case "-peso-min":
          case "-peso-minimo":
          case "-pmin":
            pesoMinimo = a.argDouble();
            break;

          case "-peso-max":
          case "-peso-maximo":
          case "-pmax":
            pesoMaximo = a.argDouble();
            break;

          case "-area-min":
          case "-area-minima":
          case "-amin":
            areaMinima = a.argDouble();
            break;

          case "-area-max":
          case "-area-maxima":
          case "-amax":
            areaMaxima = a.argDouble();
            break;

          case "-compra-min":
          case "-compra-minima":
          case "-cmin":
            compraMinima = a.argDouble();
            break;

          case "-compra-max":
          case "-compra-maxima":
          case "-cmax":
            compraMaxima = a.argDouble();
            break;

          case "-venda-min":
          case "-venda-minima":
          case "-vmin":
            vendaMinima = a.argDouble();
            break;

          case "-venda-max":
          case "-venda-maxima":
          case "-vmax":
            vendaMaxima = a.argDouble();
            break;

          default:
            if (!a.arg().isEmpty()) {
              nome = a.arg();
            }
            break;
        }
      }
    }
    public boolean seAplica(IProduto p) {
      return (nome == null || p.nome().contains(nome)) &&
             (pesoMinimo == null || p.pesoPorUnidade() >= pesoMinimo) &&
             (pesoMaximo == null || p.pesoPorUnidade() <= pesoMaximo) &&
             (areaMinima == null || p.areaPorUnidade() >= areaMinima) &&
             (areaMaxima == null || p.areaPorUnidade() <= areaMaxima) &&
             (compraMinima == null || p.precoPorUnidadeCompra() >= compraMinima) &&
             (compraMaxima == null || p.precoPorUnidadeCompra() <= compraMaxima) &&
             (vendaMinima == null || p.precoPorUnidadeVenda() >= vendaMinima) &&
             (vendaMaxima == null || p.precoPorUnidadeVenda() <= vendaMaxima);
    }
  }
  public static class Empregado extends Pesquisa {
    String apelido = null;
    String email = null;
    Privilegios privilegios = null;

    public Empregado(String s) {
      var a = new Aux(s);
      for (a.i = 0; a.i < a.args.length; a.i++) {
        switch (a.arg().toLowerCase()) {
          case "-apelido":
          case "-a":
            apelido = a.argString();
            break;

          case "-email":
          case "-e":
            email = a.argString();
            break;

          case "-privilegios":
          case "-p":
            switch (a.argString().toLowerCase()) {
              case "normais":
              case "normal":
              case "n":
                privilegios = Privilegios.Normais;
                break;

              case "administrador":
              case "admin":
              case "a":
                privilegios = Privilegios.Admin;
                break;
            }
            break;

          default:
            if (!a.arg().isEmpty()) {
              nome = a.arg();
            }
            break;
        }
      }
    }

    public boolean seAplica(petshop.empregados.Empregado e) {
      return (nome == null || e.nome().contains(nome)) &&
             (apelido == null || e.apelido().contains(apelido)) &&
             (email == null || e.email().contains(email)) &&
             (privilegios == null || e.privilegios() == privilegios);
    }
  }

  public static class Historico extends Pesquisa {
    RegistroTipo tipo = null;
    String empregado = null;
    LocalDate dataMinima = null;
    LocalDate dataMaxima = null;

    public Historico(String s) {
      var a = new Aux(s);
      for (a.i = 0; a.i < a.args.length; a.i++) {
        switch (a.arg().toLowerCase()) {
          case "-tipo":
          case "-t":
            switch (a.argString().toLowerCase()) {
              case "reserva":
              case "r":
                tipo = RegistroTipo.Reserva;
                break;

              case "adoção":
              case "adocao":
              case "a":
                tipo = RegistroTipo.Adocao;
                break;

              case "compra":
              case "c":
                tipo = RegistroTipo.Compra;
                break;

              case "venda":
              case "v":
                tipo = RegistroTipo.Venda;
                break;
            }
            break;

          case "-empregado":
          case "-e":
            empregado = a.argString();
            break;

          case "-data-minima":
          case "-data-min":
          case "-dmin":
            dataMinima = a.argDate();
            break;

          case "-data-maxima":
          case "-data-max":
          case "-dmax":
            dataMaxima = a.argDate();
            break;

          default:
            if (!a.arg().isEmpty()) {
              nome = a.arg();
            }
            break;
        }
      }
    }
    public boolean seAplica(Registro registro) {
      return (nome == null || registro.toString().contains(nome)) &&
             (empregado == null || registro.empregado().nome().contains(empregado)) &&
             (dataMinima == null || registro.data().isAfter(dataMinima)) &&
             (dataMaxima == null || registro.data().isBefore(dataMaxima));
    }
  }
}
