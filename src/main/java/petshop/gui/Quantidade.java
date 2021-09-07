package petshop.gui;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.function.*;
import static petshop.gui.GuiPrincipal.limitarSpinnerAInteger;

public class Quantidade {
  public static IntConsumer depois = null;
  public static Stage stage = null;

  @FXML private Spinner<Integer> quantidade;

  @FXML
  private void initialize() {
    limitarSpinnerAInteger(quantidade, 0, 1000000);
  }

  @FXML
  private void confirmar() {
    depois.accept(quantidade.getValue());
    stage.close();
  }

  @FXML
  private void fechar() {
    stage.close();
  }
}
