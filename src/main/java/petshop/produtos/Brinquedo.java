package petshop.produtos;

public class Brinquedo extends ProdutoPorUnidadeAbstrato {
  private static final long serialVersionUID = 1l;

  public Brinquedo(DadosProduto dados) {
    super(dados);
  }

  @Override
  public Categoria categoria() {
    return Categoria.Brinquedo;
  }
}

