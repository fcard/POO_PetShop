package petshop.produtos;
import java.time.LocalDate;
import petshop.animais.IAnimal;

public interface IProduto {
  public String nome();
  public String descricao();
  public Categoria categoria();
  public double precoPorUnidadeCompra();
  public double precoPorUnidadeVenda();
  public double pesoPorUnidade();
  public double areaPorUnidade();
  public CriterioDeUso criteriosDeUso();

  public default boolean seAplica(IAnimal animal) {
    return criteriosDeUso().seAplica(animal);
  }
}

