package petshop.gui;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.function.*;
import static petshop.gui.GuiPrincipal.limitarSpinnerADouble;

public class Desconto {
  public static DoubleConsumer depois = null;
  public static Stage stage = null;

  @FXML private Spinner<Double> quantidade;
  @FXML private Slider slider;

  @FXML
  private void initialize() {
    limitarSpinnerADouble(quantidade, 0, 100);
    quantidade.valueProperty().addListener((obs, antigo, novo) -> {
      slider.setValue(novo);
    });
    slider.valueProperty().addListener((obs, antigo, novo) -> {
      Double n = Math.round((Double)novo)/1.0;
      quantidade.getValueFactory().setValue(n);
    });
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

