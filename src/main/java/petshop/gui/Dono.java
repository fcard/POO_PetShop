package petshop.gui;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.function.*;
import static petshop.gui.GuiPrincipal.mostrarErro;

public class Dono {
  public static Consumer<String> depois = null;
  public static Stage stage = null;

  @FXML private TextField nome;

  @FXML
  private void initialize() {
  }

  @FXML
  private void confirmar() {
    mostrarErro(() -> {
      if (nome.getText().isEmpty()) {
        throw new Exception("Nome não pode ser vazio");
      }
      depois.accept(nome.getText());
      stage.close();
    });
  }

  @FXML
  private void fechar() {
    stage.close();
  }
}
