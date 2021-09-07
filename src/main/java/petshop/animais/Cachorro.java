package petshop.animais;
import java.time.LocalDate;
import java.io.Serializable;

public class Cachorro extends AnimalAbstrato {
  private static final long serialVersionUID = 1l;

  public boolean temPelo;
  public boolean temUnhas;

  public Cachorro(DadosAnimal dados) {
    super(dados);
    this.temPelo = dados.temPelo == null ? true : dados.temPelo;
    this.temUnhas = dados.temUnhas == null ? true : dados.temUnhas;
  }

  @Override
  public boolean temPelo() {
    return temPelo;
  }

  @Override
  public boolean temUnhas() {
    return temUnhas;
  }

  @Override
  public Especie especie() {
    return Especie.Cachorro;
  }
}

