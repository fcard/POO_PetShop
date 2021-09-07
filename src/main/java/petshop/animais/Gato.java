package petshop.animais;
import java.time.LocalDate;
import java.io.Serializable;

public class Gato extends AnimalAbstrato {
  private static final long serialVersionUID = 1l;
  public boolean temPelo;

  public Gato(DadosAnimal dados) {
    super(dados);
    this.temPelo = dados.temPelo == null ? true : dados.temPelo;
  }

  @Override
  public boolean temPelo() {
    return temPelo;
  }

  @Override
  public boolean temUnhas() {
    return false;
  }

  @Override
  public Especie especie() {
    return Especie.Gato;
  }
}

