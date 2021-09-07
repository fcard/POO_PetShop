package petshop.gui;
import petshop.empregados.*;
import petshop.dados.Empregados;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.stage.*;
import java.util.function.*;
import static petshop.gui.GuiPrincipal.mostrarErro;
import static petshop.gui.GuiPrincipal.limitarSpinnerADouble;

public class RegistrarFuncionario {
  static Stage stage = null;
  static Empregados empregados = null;
  static BiConsumer<Empregado,String> depois = null;
  static boolean adminObrigatorio = false;

  @FXML private TextField apelido;
  @FXML private TextField nome;
  @FXML private TextField email;
  @FXML private PasswordField senha;
  @FXML private PasswordField repetirSenha;
  @FXML private CheckBox admin;

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
  }

  @FXML
  private void registrar() {
    mostrarErro(() -> {
      var empregado = new Empregado.Construtor();
      if (apelido.getText().isEmpty()) {
        throw new Exception("Apelido não pode ser vazio.");
      }
      if (empregados.empregadosPorApelido.containsKey(apelido.getText())) {
        throw new Exception("Apelido já utilizado.");
      }
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
      if (adminObrigatorio && !admin.isSelected()) {
        throw new Exception("Admin não marcado.");
      }
      empregado.setApelido(apelido.getText());
      empregado.setNome(nome.getText());
      empregado.setEmail(email.getText());
      empregado.setPrivilegios(admin.isSelected() ?
          Privilegios.Admin : Privilegios.Normais);
      depois.accept(empregado.paraEmpregado(), senha.getText());
      stage.close();
    });
  }

  @FXML
  private void fechar() {
    stage.close();
  }
}
