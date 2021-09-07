package petshop.gui;
import java.util.Comparator;
import java.util.function.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.fxml.*;


public class GuiPrincipal extends Application {
  public static GuiPrincipal instancia = null;
  public static Stage stage = null;

  public static Stage novaJanela(String fxml) {
    try {
      Stage stage = new Stage();
      var resource = instancia.getClass().getClassLoader().getResource(fxml);
      var loader = new FXMLLoader(resource);
      Scene scene = loader.load();
      stage.setScene(scene);
      stage.initModality(Modality.WINDOW_MODAL);
      stage.initOwner(GuiPrincipal.stage);
      return stage;
    } catch (Exception e) {
      erro(e);
      return null;
    }
  }

  public static void quantidade(String titulo, IntConsumer f) {
    Stage janela = novaJanela("gui/quantidade.fxml");
    Quantidade.stage = janela;
    Quantidade.depois = f;
    janela.setTitle(titulo);
    janela.show();
  }

  public static void porJanelaNoCentro(Stage janela) {
    var dx = GuiPrincipal.stage.getWidth()/2  - stage.getWidth()/2;
    var dy = GuiPrincipal.stage.getHeight()/2 - stage.getHeight()/2;
    stage.setX(GuiPrincipal.stage.getX() + dx);
    stage.setY(GuiPrincipal.stage.getY() + dy);
  }

  @Override
  public void start(Stage stage) throws Exception {
    instancia = this;
    GuiPrincipal.stage = stage;
    var resource = getClass().getClassLoader().getResource("gui/gui.fxml");
    var loader = new FXMLLoader(resource);
    Scene scene = loader.load();

    stage.setOnShown(event -> {
      GuiControlador.instancia.depoisDeMostrado();
    });

    stage.setTitle("PetShop");
    stage.setScene(scene);
    stage.show();
  }

  // -- Metodos de Auxilio --

  // Erro

  @FunctionalInterface
  public static interface FuncaoComErro {
    public void chamar() throws Exception;
  }

  public static void mostrarErro(FuncaoComErro f) {
    try {
      f.chamar();
    } catch (Exception e) {
      erro(e);
    }
  }

  public static void erro(Exception e) {
    var mensagem = e.toString();
    var alert = new Alert(Alert.AlertType.ERROR, e.toString());
    e.printStackTrace();
    alert.showAndWait();
  }

  // Control

  private static <T extends Comparable<T>> boolean valorValido(
      String s, T min, T max, Function<String,T> gerador) {
    try {
      T d = gerador.apply(s);
      return d.compareTo(min) >= 0 && d.compareTo(max) <= 0;
    } catch (Exception e) {
      return false;
    }
  }

  public static void limitarSpinnerADouble(Spinner<Double> sp, double min, double max) {
    sp.setValueFactory(
        new SpinnerValueFactory.DoubleSpinnerValueFactory(0, max, min, 1));

    sp.getEditor().textProperty().addListener((observable, antigo, novo) -> {
      novo = novo.replace(",", ".");
      if (!valorValido(novo, min, max, s->Double.parseDouble(s))) {
        sp.getEditor().setText(antigo);
      }
    });
  }

  public static void limitarSpinnerAInteger(Spinner<Integer> sp, int min, int max) {
    sp.setValueFactory(
        new SpinnerValueFactory.IntegerSpinnerValueFactory(0, max, min, 1));

    sp.getEditor().textProperty().addListener((observable, antigo, novo) -> {
      if (!valorValido(novo, min, max, s->Integer.parseInt(s))) {
        sp.getEditor().setText(antigo);
      }
    });
  }
}


