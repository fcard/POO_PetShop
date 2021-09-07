package petshop.produtos;
import java.util.Set;
import java.util.HashSet;
import java.io.Serializable;
import petshop.animais.*;


public abstract class CriterioDeUso implements Serializable {
  private static final long serialVersionUID = 1l;
  public abstract boolean seAplica(IAnimal animal);
  public abstract Set<Especie> especies();
  public abstract Double pesoMinimo();
  public abstract Double pesoMaximo();
  public abstract Double alturaMinima();
  public abstract Double alturaMaxima();
  public abstract Double idadeMinima();
  public abstract Double idadeMaxima();
  public abstract Sexo sexo();

  public static class Nulo extends CriterioDeUso {
    private static final long serialVersionUID = 1l;
    @Override
    public boolean seAplica(IAnimal animal) {
      return true;
    }

    @Override public Set<Especie> especies() { return null; }
    @Override public Double pesoMinimo() { return null; }
    @Override public Double pesoMaximo() { return null; }
    @Override public Double alturaMinima() { return null; }
    @Override public Double alturaMaxima() { return null; }
    @Override public Double idadeMinima() { return null; }
    @Override public Double idadeMaxima() { return null; }
    @Override public Sexo sexo() { return null; }
  }

  public static class Especies extends CriterioDeUso {
    private static final long serialVersionUID = 1l;
    private Set<Especie> especies;

    public Especies(Set<Especie> especies) {
      this.especies = especies;
    }

    @Override
    public boolean seAplica(IAnimal animal) {
      return especies.contains(animal.especie());
    }

    @Override public Set<Especie> especies() { return especies; }
    @Override public Double pesoMinimo() { return null; }
    @Override public Double pesoMaximo() { return null; }
    @Override public Double alturaMinima() { return null; }
    @Override public Double alturaMaxima() { return null; }
    @Override public Double idadeMinima() { return null; }
    @Override public Double idadeMaxima() { return null; }
    @Override public Sexo sexo() { return null; }
  }

  public static class Completo extends CriterioDeUso {
    private static final long serialVersionUID = 1l;
    private Set<Especie> especies = null;
    private Double pesoMinimo = null;
    private Double pesoMaximo = null;
    private Double alturaMinima = null;
    private Double alturaMaxima = null;
    private Double idadeMinima = null;
    private Double idadeMaxima = null;
    private Sexo sexo = null;

    public Completo(
        Set<Especie> especies,
        Double pesoMinimo,
        Double pesoMaximo,
        Double alturaMinima,
        Double alturaMaxima,
        Double idadeMinima,
        Double idadeMaxima,
        Sexo sexo
      ) {
      this.especies = especies;
      this.pesoMinimo = pesoMinimo;
      this.pesoMaximo = pesoMaximo;
      this.alturaMinima = alturaMinima;
      this.alturaMaxima = alturaMaxima;
      this.idadeMinima = idadeMinima;
      this.idadeMaxima = idadeMaxima;
      this.sexo = sexo;
    }

    @Override
    public boolean seAplica(IAnimal animal) {
      return (especies != null && especies.contains(animal)) &&
             (pesoMinimo != null && animal.peso() >= pesoMinimo) &&
             (pesoMaximo != null && animal.peso() >= pesoMaximo) &&
             (alturaMinima != null && animal.altura() >= alturaMinima) &&
             (alturaMaxima != null && animal.altura() >= alturaMaxima) &&
             (idadeMinima != null && animal.idade() >= idadeMinima) &&
             (idadeMaxima != null && animal.idade() >= idadeMaxima) &&
             (sexo != null && animal.sexo() == sexo);
    }

    @Override public Set<Especie> especies() { return especies; }
    @Override public Double pesoMinimo() { return pesoMinimo; }
    @Override public Double pesoMaximo() { return pesoMaximo; }
    @Override public Double alturaMinima() { return alturaMinima; }
    @Override public Double alturaMaxima() { return alturaMaxima; }
    @Override public Double idadeMinima() { return idadeMinima; }
    @Override public Double idadeMaxima() { return idadeMaxima; }
    @Override public Sexo sexo() { return sexo; }
  }
}
