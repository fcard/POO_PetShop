package petshop.sistema;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuDecimal extends Menu<Double> {
  double minimo;
  double maximo;

  public MenuDecimal(String titulo, double minimo, double maximo) {
    super(titulo);
    this.minimo = minimo;
    this.maximo = maximo;
  }

  @Override
  public void mostrar() {
    System.out.println(
        String.format("# digite um numero entre %-7f e %-7f     #", minimo, maximo));
    System.out.println("##################################################");
  }

  @Override
  public boolean resultadoValido(Double resultado) {
    return resultado != null && (resultado >= minimo && resultado <= maximo);
  }

  @Override
  public Double obterResultado(String line) {
    try {
      return Double.parseDouble(line);
    } catch (Exception e) {
      return null;
    }
  }
}

