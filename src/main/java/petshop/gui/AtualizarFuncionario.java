package petshop.gui;
import petshop.empregados.*;
import petshop.dados.Empregados;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.stage.*;
import java.util.function.*;
import static petshop.gui.GuiPrincipal.mostrarErro;
import static petshop.gui.GuiPrincipal.limitarSpinnerADouble;

public class AtualizarFuncionario {
  static Stage stage = null;
  static AtualizarFuncionario instancia = null;
  static Empregados empregados = null;
  static String apelido = null;
  static Privilegios priv = null;
  static BiConsumer<Empregado,String> depois = null;

  @FXML private TextField nome;
  @FXML private TextField email;
  @FXML private PasswordField senha;
  @FXML private PasswordField repetirSenha;

  private boolean valorValido(String s, double min, double max) {
    try {
      if (s.endsWith("d") || s.endsWith("f")) {
        return false;
      }
      var d = Double.parseDouble(s);
      return d >= min && d <= max;
    } catch (Exception e) {
      return false;
    }
  }

  @FXML
  private void initialize() {
    instancia = this;
  }

  public void depoisDeMostrado(Empregado e) {
    nome.setText(e.nome());
    email.setText(e.email());
  }

  @FXML
  private void atualizar() {
    mostrarErro(() -> {
      var empregado = new Empregado.Construtor();
      if (nome.getText().isEmpty()) {
        throw new Exception("Nome não pode ser vazio.");
      }
      if (email.getText().isEmpty()) {
        throw new Exception("E-mail não pode ser vazio.");
      }
      if (senha.getText().isEmpty()) {
        throw new Exception("Senha não pode ser vazia.");
      }
      if (!senha.getText().equals(repetirSenha.getText())) {
        throw new Exception("Senha não repetida corretamente.");
      }
      empregado.setApelido(apelido);
      empregado.setNome(nome.getText());
      empregado.setEmail(email.getText());
      empregado.setPrivilegios(priv);
      depois.accept(empregado.paraEmpregado(), senha.getText());
      stage.close();
    });
  }

  @FXML
  private void fechar() {
    stage.close();
  }
}
