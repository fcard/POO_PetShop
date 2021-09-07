package petshop.animais;
import java.time.LocalDate;
import java.io.Serializable;

public abstract class AnimalAbstrato implements IAnimal, Serializable {
  private static final long serialVersionUID = 1l;

  private String nome;
  private LocalDate dataDeNascimento;
  private double peso;
  private double altura;
  private Sexo sexo;

  public AnimalAbstrato(DadosAnimal dados) {
    this.nome = dados.nome();
    this.dataDeNascimento = dados.dataDeNascimento();
    this.peso = dados.peso();
    this.altura = dados.altura();
    this.sexo = dados.sexo();
  }

  @Override
  public String nome() {
    return nome;
  }

  @Override
  public LocalDate dataDeNascimento() {
    return dataDeNascimento;
  }

  @Override
  public double peso() {
    return peso;
  }

  @Override
  public double altura() {
    return altura;
  }

  @Override
  public Sexo sexo() {
    return sexo;
  }
}
