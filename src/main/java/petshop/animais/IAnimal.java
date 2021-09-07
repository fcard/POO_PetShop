package petshop.animais;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public interface IAnimal {
  public Especie especie();
  public String nome();
  public LocalDate dataDeNascimento();
  public double peso();
  public double altura();
  public Sexo sexo();

  public boolean temUnhas();
  public boolean temPelo();

  public default Long idade() {
    var agora = LocalDate.now();
    return ChronoUnit.YEARS.between(dataDeNascimento(), agora);
  }
}

