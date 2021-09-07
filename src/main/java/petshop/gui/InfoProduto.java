package petshop.gui;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.function.*;
import static petshop.dados.Historico.*;

public class InfoProduto {
  public static Stage stage = null;
  public static InfoProduto instancia = null;

  @FXML private TextField categoria;
  @FXML private TextField nome;
  @FXML private TextField peso;
  @FXML private TextField area;
  @FXML private TextField venda;
  @FXML private TextField compra;
  @FXML private TextField desconto;
  @FXML private TextField data;
  @FXML private TextField quantidade;
  @FXML private TextField funcionario;

  @FXML
  private void initialize() {
    instancia = this;
  }

  public void depoisDeMostrado(RegistroProduto r) {
    var produto = r.produto();
    categoria.setText(produto.categoria().toString());
    nome.setText(produto.nome());
    peso.setText(String.format("%.2fkg", produto.pesoPorUnidade()));
    area.setText(String.format("%.2fm²", produto.areaPorUnidade()));
    venda.setText(String.format("R$ %.2f", produto.precoPorUnidadeVenda()));
    compra.setText(String.format("R$ %.2f", produto.precoPorUnidadeCompra()));
    desconto.setText(String.format("%.2f%%", 100*(1 - r.desconto())));

    data.setText(r.data().toString());
    quantidade.setText(((Long)r.quantidade()).toString());
    funcionario.setText(r.empregado().nome());
  }
}
