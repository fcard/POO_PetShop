package petshop.produtos;
import petshop.animais.IAnimal;
import petshop.animais.Especie;
import java.time.LocalDate;
import java.util.function.Predicate;

public class Comida extends ProdutoPorQuiloAbstrato {
  private static final long serialVersionUID = 1l;

  public Comida(DadosProduto dados) {
    super(dados);
  }

  @Override
  public Categoria categoria() {
    return Categoria.Comida;
  }
}

