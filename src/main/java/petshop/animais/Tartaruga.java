package petshop.animais;
import java.time.LocalDate;
import java.io.Serializable;

public class Tartaruga extends AnimalAbstrato {
  private static final long serialVersionUID = 1l;

  public Tartaruga(DadosAnimal dados) {
    super(dados);
  }

  @Override
  public boolean temPelo() {
    return false;
  }

  @Override
  public boolean temUnhas() {
    return false;
  }

  @Override
  public Especie especie() {
    return Especie.Tartaruga;
  }
}

