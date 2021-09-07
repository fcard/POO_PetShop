package petshop.gui;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.function.*;
import petshop.empregados.*;
import petshop.dados.Empregados;
import static petshop.gui.GuiPrincipal.mostrarErro;

public class Login {
  public static Consumer<Empregado> depois = null;
  public static Empregados empregados = null;
  public static boolean permitirCancelar = true;
  public static Stage stage = null;

  @FXML private TextField apelido;
  @FXML private PasswordField senha;

  @FXML
  private void initialize() {
  }

  @FXML
  private void login() {
    mostrarErro(() -> {
      Empregado e = empregados.obterEmpregado(apelido.getText());
      Senha s = new Senha(apelido.getText());
      if (!s.confirmar(senha.getText())) {
        throw new Exception("Senha incorreta");
      }
      depois.accept(e);
      stage.close();
    });
  }

  @FXML
  private void fechar() {
    if (permitirCancelar) {
      stage.close();
    }
  }
}
