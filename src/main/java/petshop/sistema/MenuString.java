package petshop.sistema;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuString extends Menu<String> {
  public MenuString(String titulo) {
    super(titulo);
  }

  @Override
  public void mostrar() {}

  @Override
  public boolean resultadoValido(String resultado) {
    return resultado != null && resultado.length() != 0;
  }

  @Override
  public String obterResultado(String line) {
    return line;
  }
}

