package petshop.produtos;
import petshop.animais.IAnimal;
import petshop.animais.Especie;
import java.time.LocalDate;
import java.util.function.Predicate;
import java.io.Serializable;

public abstract class ProdutoAbstrato implements Serializable, IProduto {
  private static final long serialVersionUID = 1l;

  String nome;
  String descricao;
  CriterioDeUso seAplica;

  public ProdutoAbstrato(DadosProduto dados) {
    this.nome = dados.nome();
    this.descricao = dados.descricao();
    this.seAplica = dados.seAplica();
  }

  @Override
  public String nome() {
    return nome;
  }

  @Override
  public String descricao() {
    return descricao;
  }

  @Override
  public CriterioDeUso criteriosDeUso() {
    return seAplica;
  }
}


