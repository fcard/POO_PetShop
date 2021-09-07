package petshop.gui;
import petshop.animais.*;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.stage.*;
import static petshop.gui.GuiPrincipal.mostrarErro;
import static petshop.gui.GuiPrincipal.limitarSpinnerADouble;

public class RegistrarAnimal {
  static Stage stage = null;

  @FXML private ComboBox<String> especie;
  @FXML private TextField nome;
  @FXML private Spinner<Double> peso;
  @FXML private Spinner<Double> altura;
  @FXML private ToggleGroup sexo;
  @FXML private RadioButton sexoM;
  @FXML private RadioButton sexoF;
  @FXML private DatePicker data;

  private boolean valorValido(String s, double min, double max) {
    try {
      if (s.endsWith("d") || s.endsWith("f")) {
        return false;
      }
      var d = Double.parseDouble(s);
      return d >= min && d <= max;
    } catch (Exception e) {
      return false;
    }
  }

  @FXML
  private void initialize() {
    var items = especie.getItems();
    items.add(Especie.Cachorro.toString());
    items.add(Especie.Gato.toString());
    items.add(Especie.Coelho.toString());
    items.add(Especie.Tartaruga.toString());
    items.add(Especie.Cobra.toString());

    limitarSpinnerADouble(peso, 0, 1000);
    limitarSpinnerADouble(altura, 0, 10);
  }

  @FXML
  private void registrar() {
    mostrarErro(() -> {
      var animal = new DadosAnimal();
      var especieIndex = especie.getSelectionModel().getSelectedIndex();
      if (especieIndex < 0) {
        throw new Exception("Especie não selecionada.");
      }
      if (nome.getText().isEmpty()) {
        throw new Exception("Nome não pode ser vazio.");
      }
      if (peso.getValue() == 0.0) {
        throw new Exception("Peso não pode ser 0.");
      }
      if (altura.getValue() == 0.0) {
        throw new Exception("Altura não pode ser 0.");
      }
      if (sexo.getSelectedToggle() == null) {
        throw new Exception("Sexo não selecionado.");
      }
      if (data.getValue() == null) {
        throw new Exception("Data de Nascimento não selecionada.");
      }
      animal.setEspecie(Especie.valueOf(especie.getItems().get(especieIndex)));
      animal.setNome(nome.getText());
      animal.setPeso(peso.getValue());
      animal.setAltura(altura.getValue());
      animal.setSexo(sexoM.isSelected() ? Sexo.M : Sexo.F);
      animal.setDataDeNascimento(data.getValue());
      GuiControlador.instancia.registrarAnimal(animal);
      stage.close();
    });
  }

  @FXML
  private void fechar() {
    stage.close();
  }
}
