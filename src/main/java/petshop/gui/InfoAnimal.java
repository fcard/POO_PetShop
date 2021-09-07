package petshop.gui;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import java.util.function.*;
import static petshop.dados.Historico.*;

public class InfoAnimal {
  public static Stage stage = null;
  public static InfoAnimal instancia = null;

  @FXML private TextField especie;
  @FXML private TextField nome;
  @FXML private TextField idade;
  @FXML private TextField peso;
  @FXML private TextField altura;
  @FXML private TextField nascimento;
  @FXML private TextField sexo;
  @FXML private TextField dono;
  @FXML private TextField data;
  @FXML private TextField funcionario;

  @FXML
  private void initialize() {
    instancia = this;
  }

  public void depoisDeMostrado(RegistroAnimal r) {
    var animal = r.animal();
    especie.setText(animal.especie().toString());
    nome.setText(animal.nome());
    idade.setText(animal.idade().toString());
    peso.setText(String.format("%.2fkg", animal.peso()));
    altura.setText(String.format("%.2fm", animal.altura()));
    nascimento.setText(animal.dataDeNascimento().toString());
    sexo.setText(animal.sexo().toString());

    data.setText(r.data().toString());
    dono.setText(r.dono().toString());
    funcionario.setText(r.empregado().nome());
  }
}
