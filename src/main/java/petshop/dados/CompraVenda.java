package petshop.dados;
import petshop.produtos.*;
import java.util.Hashtable;
import java.io.Serializable;

public class CompraVenda extends Dados<CompraVenda> {
  private static final long serialVersionUID = 1l;

  public static class Erro extends Exception {
    private static final long serialVersionUID = 1l;
    public Erro(String erro) {
      super(erro);
    }
  }

  public static class ErroProdutoNaoCriado extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroProdutoNaoCriado() {
      super("O produto não pôde ser criado");
    }
  }

  public static class ErroProdutoInexistente extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroProdutoInexistente(long id) {
      super(String.format("O produto com id %d não existe", id));
    }
  }

  public static class ErroDinheiroNegativo extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroDinheiroNegativo(double d) {
      super(String.format("A quantidade de dinheiro colocada (%.2f) foi negativa.", d));
    }
  }

  public static class ErroQuantidadeInsuficiente extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroQuantidadeInsuficiente(long estoque, long requerido) {
      super(String.format("A quantidade em estoque (%d) é menor que a requerida (%d)",
                          estoque, requerido));
    }
  }

  public static class ErroDescontoInvalido extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroDescontoInvalido(double desconto) {
      super(String.format("O desconto (%f) esta acima de 1 ou abaixo de 0", desconto));
    }
  }

  public static class ErroDinheiroInsuficiente extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroDinheiroInsuficiente(double dinheiro, double preco) {
      super(
        String.format("O dinheiro em estoque (%.2f) é menor que o valor retirado (%.2f)",
                      dinheiro, preco));
    }
  }

  public static class ErroDebitoInsuficiente extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroDebitoInsuficiente(double debito, double pagamento) {
      super(String.format("O pagamento (%.2f) colocaria o debito (%.2f) no negativo",
                          pagamento, debito));
    }
  }

  public static class Registro implements Serializable {
    private static final long serialVersionUID = 1l;
    public IProduto produto;
    public long quantidade = 0;
    public double desconto = 1.0;

    Registro(IProduto produto) {
      this.produto = produto;
    }
  }

  public Hashtable<Long, Registro> registro;
  private double dinheiro = 0.0;
  private double debito = 0.0;

  public CompraVenda() {
    this.registro = new Hashtable<Long, Registro>();
  }

  @Override
  public CompraVenda vazio() {
    return new CompraVenda();
  }

  @Override
  public boolean contemId(long id) {
    return registro.containsKey(id);
  }

  @Override
  public long quantidadeDeIds() {
    return registro.size();
  }

  public void registrar(DadosProduto dados) throws Erro {
    IProduto produto = dados.criar();
    if (produto == null) {
      throw new ErroProdutoNaoCriado();
    }
    registro.put(gerarId(), new Registro(produto));
  }

  public void removerRegistro(long id) throws Erro {
    if (!registro.containsKey(id)) {
      throw new ErroProdutoInexistente(id);
    }
    registro.remove(id);
  }

  public void adicionarDinheiroSemDebito(double dinheiro) throws Erro {
    if (dinheiro < 0) {
      throw new ErroDinheiroNegativo(dinheiro);
    }
    this.dinheiro += dinheiro;
  }

  public void removerDinheiro(double dinheiro) throws Erro {
    if (dinheiro < 0) {
      throw new ErroDinheiroNegativo(dinheiro);
    } else if (this.dinheiro < dinheiro) {
      throw new ErroDinheiroInsuficiente(this.dinheiro, dinheiro);
    }
    this.dinheiro -= dinheiro;
  }

  public void adicionarDinheiroComDebito(double dinheiro) throws Erro {
    if (dinheiro < 0) {
      throw new ErroDinheiroNegativo(dinheiro);
    }
    this.dinheiro += dinheiro;
    this.debito += dinheiro;
  }

  public void pagarDebito(double dinheiro) throws Erro {
    if (dinheiro < 0) {
      throw new ErroDinheiroNegativo(dinheiro);
    } else if (dinheiro > this.dinheiro) {
      throw new ErroDinheiroInsuficiente(this.dinheiro, dinheiro);
    } else if (dinheiro > this.debito) {
      throw new ErroDebitoInsuficiente(this.debito, dinheiro);
    }
    this.dinheiro -= dinheiro;
    this.debito -= dinheiro;
  }

  private double produtoPrecoVenda(long id, long quantidade) throws Erro {
    if (!registro.containsKey(id)) {
      throw new ErroProdutoInexistente(id);
    }
    var registro = this.registro.get(id);
    return quantidade * registro.desconto * registro.produto.precoPorUnidadeVenda();
  }

  public void venderProduto(long id, long quantidade) throws Erro {
    var preco = produtoPrecoVenda(id, quantidade);
    var registro = this.registro.get(id);
    if (registro.quantidade <= quantidade) {
      throw new ErroQuantidadeInsuficiente(registro.quantidade, quantidade);
    }
    dinheiro += preco;
    registro.quantidade -= quantidade;
  }

  private double produtoPrecoCompra(long id, long quantidade) throws Erro {
    if (!registro.containsKey(id)) {
      throw new ErroProdutoInexistente(id);
    }
    var registro = this.registro.get(id);
    return quantidade * registro.produto.precoPorUnidadeCompra();
  }

  public void comprarProduto(long id, long quantidade) throws Erro {
    var preco = produtoPrecoVenda(id, quantidade);
    if (dinheiro < preco) {
      throw new ErroDinheiroInsuficiente(dinheiro, preco);
    }
    var registro = this.registro.get(id);
    dinheiro -= preco;
    registro.quantidade += quantidade;
  }

  public void setDesconto(long id, double desconto) throws Erro {
    if (!registro.containsKey(id)) {
      throw new ErroProdutoInexistente(id);
    }
    if (desconto < 0.0 || desconto > 1.0) {
      throw new ErroDescontoInvalido(desconto);
    }
    this.registro.get(id).desconto = desconto;
  }

  public double dinheiro() {
    return dinheiro;
  }

  public double debito() {
    return debito;
  }

  public IProduto getProduto(long id) {
    var r = registro.get(id);
    if (r == null) {
      return null;
    } else {
      return r.produto;
    }
  }

  public Double getDesconto(long id) {
    var r = registro.get(id);
    if (r == null) {
      return null;
    } else {
      return r.desconto;
    }
  }
}
