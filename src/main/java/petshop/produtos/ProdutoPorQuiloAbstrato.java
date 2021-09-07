package petshop.produtos;
import petshop.animais.IAnimal;
import petshop.animais.Especie;
import java.time.LocalDate;
import java.util.function.Predicate;
import java.io.Serializable;

public abstract class ProdutoPorQuiloAbstrato extends ProdutoAbstrato {
  private static final long serialVersionUID = 1l;

  double precoPorQuiloCompra;
  double precoPorQuiloVenda;
  double areaPorQuilo;
  double quilosPorUnidade;

  public ProdutoPorQuiloAbstrato(DadosProduto dados) {
    super(dados);
    this.precoPorQuiloCompra = dados.precoPorQuiloCompra();
    this.precoPorQuiloVenda = dados.precoPorQuiloVenda();
    this.areaPorQuilo = dados.areaPorQuilo();
    this.quilosPorUnidade = dados.pesoPorUnidade();
  }

  @Override
  public double precoPorUnidadeCompra() {
    return precoPorQuiloCompra * quilosPorUnidade;
  }

  @Override
  public double precoPorUnidadeVenda() {
    return precoPorQuiloVenda * quilosPorUnidade;
  }

  @Override
  public double pesoPorUnidade() {
    return quilosPorUnidade;
  }

  @Override
  public double areaPorUnidade() {
    return areaPorQuilo * quilosPorUnidade;
  }
}

