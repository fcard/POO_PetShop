package petshop.gui;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.function.*;
import petshop.produtos.*;

public class Criterios {
  public static Stage stage = null;
  public static Criterios instancia = null;

  @FXML private TextField especie;
  @FXML private TextField sexo;
  @FXML private TextField pesoMin;
  @FXML private TextField pesoMax;
  @FXML private TextField idadeMin;
  @FXML private TextField idadeMax;
  @FXML private TextField alturaMin;
  @FXML private TextField alturaMax;

  @FXML
  private void initialize() {
    instancia = this;
  }

  public void depoisDeMostrado(CriterioDeUso criterios) {
    if (criterios.especies() != null) {
      for (var e : criterios.especies()) {
        especie.setText(e.toString());
      }
    }
    if (criterios.sexo() != null) {
      sexo.setText(criterios.sexo().toString());
    }
    if (criterios.pesoMaximo() != null) {
      pesoMax.setText(String.format("%.2fkg", criterios.pesoMaximo()));
    }
    if (criterios.pesoMinimo() != null) {
      pesoMin.setText(String.format("%.2fkg", criterios.pesoMinimo()));
    }
    if (criterios.idadeMaxima() != null) {
      idadeMax.setText(String.format("%.0f", criterios.idadeMaxima()));
    }
    if (criterios.idadeMinima() != null) {
      idadeMin.setText(String.format("%.0f", criterios.idadeMinima()));
    }
    if (criterios.alturaMaxima() != null) {
      alturaMax.setText(String.format("%.2fm", criterios.alturaMaxima()));
    }
    if (criterios.alturaMinima() != null) {
      alturaMin.setText(String.format("%.2fm", criterios.alturaMinima()));
    }
  }
}
