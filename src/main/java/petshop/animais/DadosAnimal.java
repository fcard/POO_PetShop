package petshop.animais;
import java.time.LocalDate;
import java.util.ArrayList;

public class DadosAnimal implements IAnimal {
  public String nome = null;
  public Especie especie = null;
  public LocalDate dataDeNascimento = null;
  public Boolean temPelo = null;
  public Boolean temUnhas = null;
  public Double peso = null;
  public Double altura = null;
  public Sexo sexo = null;

  public DadosAnimal setEspecie(Especie esp) {
    this.especie = esp;
    if (temPelo == null) {
      temPelo = (esp == Especie.Cachorro || esp == Especie.Gato);
    }
    if (temUnhas == null) {
      temUnhas = esp == Especie.Cachorro;
    }
    return this;
  }

  public DadosAnimal setDataDeNascimento(LocalDate data) {
    this.dataDeNascimento = data;
    return this;
  }

  public DadosAnimal setTemPelo(boolean pelo) {
    temPelo = pelo;
    return this;
  }

  public DadosAnimal setTemUnhas(boolean unhas) {
    temUnhas = unhas;
    return this;
  }

  public DadosAnimal setNome(String nome) {
    this.nome = nome;
    return this;
  }

  public DadosAnimal setPeso(double peso) {
    this.peso = peso;
    return this;
  }

  public DadosAnimal setAltura(double altura) {
    this.altura = altura;
    return this;
  }

  public DadosAnimal setSexo(Sexo sexo) {
    this.sexo = sexo;
    return this;
  }

  @Override
  public boolean temPelo() {
    if (temPelo == null) {
      return false;
    } else {
      return temPelo;
    }
  }

  @Override
  public boolean temUnhas() {
    if (temUnhas == null) {
      return false;
    } else {
      return temUnhas;
    }
  }

  @Override
  public Especie especie() {
    return especie;
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
    if (peso == null) {
      return 1.0;
    } else {
      return peso;
    }
  }

  @Override
  public double altura() {
    if (altura == null) {
      return 1.0;
    } else {
      return altura;
    }
  }

  @Override
  public Sexo sexo() {
    return sexo;
  }

  public IAnimal criar() {
    if (especie != null) {
      switch (especie) {
        case Cachorro:
          return new Cachorro(this);

        case Gato:
          return new Gato(this);

        case Tartaruga:
          return new Tartaruga(this);

        case Cobra:
          return new Cobra(this);

        case Coelho:
          return new Coelho(this);

        default:
          return null;
      }
    } else {
      return null;
    }
  }
}

