package petshop.produtos;
import petshop.animais.*;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;
import java.util.function.Predicate;

public class DadosProduto {
  String nome = null;
  String descricao = null;
  Categoria categoria = null;
  Double precoPorUnidadeCompra = null;
  Double precoPorUnidadeVenda  = null;
  Double pesoPorUnidade = null;
  Double areaPorUnidade = null;
  Criterios criterios = new Criterios();

  // criterios para se aplicar a um animal
  public static class Criterios {
    public Set<Especie> especies = null;
    public Double pesoMinimo = null;
    public Double pesoMaximo = null;
    public Double alturaMinima = null;
    public Double alturaMaxima = null;
    public Double idadeMinima = null;
    public Double idadeMaxima = null;
    public Sexo sexo = null;

    public CriterioDeUso criarPredicado() {
      if (especies == null &&
          pesoMinimo == null && pesoMaximo == null &&
          alturaMinima == null && alturaMaxima == null &&
          idadeMinima == null && idadeMaxima == null && sexo == null) {
        return new CriterioDeUso.Nulo();
      } else if (especies != null &&
          pesoMinimo == null && pesoMaximo == null &&
          alturaMinima == null && alturaMaxima == null &&
          idadeMinima == null && idadeMaxima == null && sexo == null) {
        return new CriterioDeUso.Especies(this.especies);
      } else {
        return new CriterioDeUso.Completo(
          this.especies,
          this.pesoMinimo,
          this.pesoMaximo,
          this.alturaMinima,
          this.alturaMaxima,
          this.idadeMinima,
          this.idadeMaxima,
          this.sexo
        );
      }
    }
  }

  public DadosProduto setNome(String nome) {
    this.nome = nome;
    return this;
  }

  public DadosProduto setDescricao(String descricao) {
    this.descricao = nome;
    return this;
  }

  public DadosProduto setCategoria(Categoria categoria) {
    this.categoria = categoria;
    return this;
  }

  public DadosProduto setPrecoCompra(double preco) {
    this.precoPorUnidadeCompra = preco;
    return this;
  }

  public DadosProduto setPrecoVenda(double preco) {
    this.precoPorUnidadeVenda = preco;
    return this;
  }

  public DadosProduto setPeso(double preco) {
    this.pesoPorUnidade = preco;
    return this;
  }

  public DadosProduto setArea(double area) {
    this.areaPorUnidade = area;
    return this;
  }

  public DadosProduto addCriterioEspecie(Especie especie) {
    if (criterios.especies == null) {
      criterios.especies = new HashSet<Especie>();
    }
    criterios.especies.add(especie);
    return this;
  }

  public DadosProduto setCriterioPesoMinimo(double peso) {
    criterios.pesoMinimo = peso;
    return this;
  }

  public DadosProduto setCriterioPesoMaximo(double peso) {
    criterios.pesoMaximo = peso;
    return this;
  }

  public DadosProduto setCriterioAlturaMinima(double altura) {
    criterios.alturaMinima = altura;
    return this;
  }

  public DadosProduto setCriterioAlturaMaxima(double altura) {
    criterios.alturaMaxima = altura;
    return this;
  }

  public DadosProduto setCriterioIdadeMinima(double idade) {
    criterios.idadeMinima = idade;
    return this;
  }

  public DadosProduto setCriterioIdadeMaxima(double idade) {
    criterios.idadeMaxima = idade;
    return this;
  }

  public DadosProduto setCriterioSexo(Sexo sexo) {
    criterios.sexo = sexo;
    return this;
  }

  public String nome() {
    return nome != null ? nome : "";
  }

  public String descricao() {
    return descricao != null ? descricao : "";
  }

  public double precoPorUnidadeCompra() {
    return precoPorUnidadeCompra != null ? precoPorUnidadeCompra : 0.0;
  }

  public double precoPorUnidadeVenda() {
    return precoPorUnidadeVenda != null ? precoPorUnidadeVenda : 0.0;
  }

  public double pesoPorUnidade() {
    return pesoPorUnidade != null ? pesoPorUnidade : 1.0;
  }

  public double areaPorUnidade() {
    return areaPorUnidade != null ? areaPorUnidade : 1.0;
  }

  public double precoPorQuiloCompra() {
    return precoPorUnidadeCompra() / pesoPorUnidade();
  }

  public double precoPorQuiloVenda() {
    return precoPorUnidadeVenda() / pesoPorUnidade();
  }

  public double areaPorQuilo() {
    return areaPorUnidade() / pesoPorUnidade();
  }

  public CriterioDeUso seAplica() {
    return criterios.criarPredicado();
  }

  public IProduto criar() {
    if (categoria != null) {
      switch (categoria) {
        case Brinquedo:
          return new Brinquedo(this);

        case Remedio:
          return new Remedio(this);

        case Comida:
          return new Comida(this);

        default:
          return null;
      }
    } else {
      return null;
    }
  }
}
