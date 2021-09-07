package petshop.gui;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.function.*;
import static petshop.gui.GuiPrincipal.limitarSpinnerADouble;

public class Dinheiro {
  public static DoubleConsumer depois = null;
  public static Stage stage = null;

  @FXML private Spinner<Double> quantidade;

  @FXML
  private void initialize() {
    limitarSpinnerADouble(quantidade, 0, 1000000000);
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
