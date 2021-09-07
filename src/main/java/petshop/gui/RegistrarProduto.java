package petshop.gui;
import petshop.produtos.*;
import petshop.animais.Sexo;
import petshop.animais.Especie;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.stage.*;
import static petshop.gui.GuiPrincipal.mostrarErro;
import static petshop.gui.GuiPrincipal.limitarSpinnerADouble;
import static petshop.gui.GuiPrincipal.limitarSpinnerAInteger;

public class RegistrarProduto {
  static Stage stage = null;

  @FXML private ComboBox<String> categoria;
  @FXML private Spinner<Double> area;
  @FXML private Spinner<Double> peso;
  @FXML private Spinner<Double> compra;
  @FXML private Spinner<Double> venda;
  @FXML private TextField nome;

  @FXML private ComboBox<String> critEspecie;
  @FXML private Spinner<Double> critAlturaMax;
  @FXML private Spinner<Double> critAlturaMin;
  @FXML private Spinner<Double> critPesoMax;
  @FXML private Spinner<Double> critPesoMin;
  @FXML private Spinner<Integer> critIdadeMax;
  @FXML private Spinner<Integer> critIdadeMin;
  @FXML private RadioButton critSexoM;
  @FXML private RadioButton critSexoF;
  @FXML private ToggleGroup sexo;

  @FXML
  private void initialize() {
    var cs = categoria.getItems();
    cs.add(Categoria.Comida.toString());
    cs.add(Categoria.Brinquedo.toString());
    cs.add(Categoria.Remedio.toString());

    var es = categoria.getItems();
    es.add(Especie.Cachorro.toString());
    es.add(Especie.Gato.toString());
    es.add(Especie.Coelho.toString());
    es.add(Especie.Tartaruga.toString());
    es.add(Especie.Cobra.toString());

    limitarSpinnerADouble(peso, 0, 1000);
    limitarSpinnerADouble(area, 0, 1000);
    limitarSpinnerADouble(compra, 0, 999999999);
    limitarSpinnerADouble(venda, 0, 999999999);
    limitarSpinnerADouble(critAlturaMax, 0, 10);
    limitarSpinnerADouble(critAlturaMin, 0, 10);
    limitarSpinnerADouble(critPesoMax, 0, 1000);
    limitarSpinnerADouble(critPesoMin, 0, 1000);
    limitarSpinnerADouble(critPesoMax, 0, 1000);
    limitarSpinnerAInteger(critIdadeMin, 0, 1000);
    limitarSpinnerAInteger(critIdadeMax, 0, 1000);
  }

  @FXML
  private void registrar() {
    mostrarErro(() -> {
      var produto = new DadosProduto();
      var catIndex = categoria.getSelectionModel().getSelectedIndex();
      if (catIndex < 0) {
        throw new Exception("Categoria não selecionada.");
      }
      if (nome.getText().isEmpty()) {
        throw new Exception("Nome não pode ser vazio.");
      }
      if (peso.getValue() == 0) {
        throw new Exception("Peso não pode ser 0.");
      }
      if (area.getValue() == 0) {
        throw new Exception("Area não pode ser 0.");
      }
      produto.setCategoria(Categoria.valueOf(categoria.getItems().get(catIndex)));
      produto.setNome(nome.getText());
      produto.setPeso(peso.getValue());
      produto.setArea(area.getValue());
      produto.setPrecoCompra(compra.getValue());
      produto.setPrecoVenda(venda.getValue());
      if (critAlturaMax.getValue() != 0) {
        produto.setCriterioAlturaMaxima(critAlturaMax.getValue());
      }
      if (critAlturaMin.getValue() != 0) {
        produto.setCriterioAlturaMinima(critAlturaMin.getValue());
      }
      if (critPesoMax.getValue() != 0) {
        produto.setCriterioPesoMaximo(critPesoMax.getValue());
      }
      if (critPesoMin.getValue() != 0) {
        produto.setCriterioPesoMinimo(critPesoMin.getValue());
      }
      if (critIdadeMax.getValue() != 0) {
        produto.setCriterioIdadeMaxima(critIdadeMax.getValue());
      }
      if (critIdadeMin.getValue() != 0) {
        produto.setCriterioIdadeMinima(critIdadeMin.getValue());
      }
      if (sexo.getSelectedToggle() != null) {
        produto.setCriterioSexo(critSexoM.isSelected() ? Sexo.M : Sexo.F);
      }
      GuiControlador.instancia.registrarProduto(produto);
      stage.close();
    });
  }

  @FXML
  private void fechar() {
    stage.close();
  }
}
