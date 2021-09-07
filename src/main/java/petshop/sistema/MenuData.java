package petshop.sistema;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MenuData extends Menu<LocalDate> {
  public MenuData(String titulo) {
    super(titulo);
  }

  @Override
  public void mostrar() {
    System.out.println("# digite uma data no formato dd/mm/aaaa           #");
    System.out.println("###################################################");
  }

  @Override
  public boolean resultadoValido(LocalDate resultado) {
    return resultado != null;
  }

  @Override
  public LocalDate obterResultado(String s) {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    try {
      return LocalDate.parse(s, fmt);
    } catch (Exception e) {
      return null;
    }
  }
}

