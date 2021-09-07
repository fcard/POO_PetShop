package petshop.produtos;

public class Remedio extends ProdutoPorUnidadeAbstrato {
  private static final long serialVersionUID = 1l;

  public Remedio(DadosProduto dados) {
    super(dados);
  }

  @Override
  public Categoria categoria() {
    return Categoria.Remedio;
  }
}

