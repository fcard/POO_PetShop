package petshop;
import petshop.sistema.*;
import petshop.gui.*;
import javafx.application.Application;

public class App {
  public static void main(String[] args) {
    var principal = new SistemaPrincipal();
    if (args.length > 0 && args[0].equals("--cli")) {
      principal.executar();
    } else {
      Application.launch(GuiPrincipal.class, args);
    }
  }
}

