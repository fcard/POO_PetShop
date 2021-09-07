package petshop.produtos;
import petshop.animais.IAnimal;
import petshop.animais.Especie;
import java.time.LocalDate;
import java.util.function.Predicate;
import java.io.Serializable;

public abstract class ProdutoPorUnidadeAbstrato extends ProdutoAbstrato {
  private static final long serialVersionUID = 1l;

  double precoPorUnidadeCompra;
  double precoPorUnidadeVenda;
  double pesoPorUnidade;
  double areaPorUnidade;

  public ProdutoPorUnidadeAbstrato(DadosProduto dados) {
    super(dados);
    this.precoPorUnidadeCompra = dados.precoPorUnidadeCompra();
    this.precoPorUnidadeVenda = dados.precoPorUnidadeVenda();
    this.areaPorUnidade = dados.areaPorUnidade();
    this.pesoPorUnidade = dados.pesoPorUnidade();
  }


  @Override
  public double precoPorUnidadeCompra() {
    return precoPorUnidadeCompra;
  }

  @Override
  public double precoPorUnidadeVenda() {
    return precoPorUnidadeVenda;
  }

  @Override
  public double pesoPorUnidade() {
    return pesoPorUnidade;
  }

  @Override
  public double areaPorUnidade() {
    return areaPorUnidade;
  }
}

