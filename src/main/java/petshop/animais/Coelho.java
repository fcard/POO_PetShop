package petshop.animais;
import java.time.LocalDate;
import java.io.Serializable;

public class Coelho extends AnimalAbstrato {
  private static final long serialVersionUID = 1l;

  public Coelho(DadosAnimal dados) {
    super(dados);
  }

  @Override
  public boolean temPelo() {
    return true;
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

