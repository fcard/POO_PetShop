package petshop.sistema;
import petshop.dados.Empregados;
import petshop.empregados.Empregado;
import petshop.empregados.Privilegios;
import static petshop.ajudante.Io.*;

public class SistemaEmpregados {
  boolean saiu;
  boolean voltou;
  Empregados dados;
  Empregado empregadoAtual;

  public SistemaEmpregados() {
    this.saiu = false;
    this.voltou = false;
    this.dados = new Empregados();
  }

  public void executar() {
  }

  public boolean login() {
    var menuApelido = new MenuString("Login : Nome de Usuario");
    var apelido = menuApelido.executar();
    if (menuApelido.voltou()) {
      return false;
    } else if (menuApelido.saiu()) {
      return true;
    }

    mostrarErro(() -> {
      var menuSenha = new MenuSenha("Login : ", apelido);
      var senha = menuSenha.executar();
      if (senha != null) {
        empregadoAtual = dados.obterEmpregado(apelido);
      }
    });
    return false;
  }

  public boolean registrar() {
    var titulo = "Empregado : Registrar : ";
    var menuApelido = new MenuString(titulo + "Nome de Usuario");
    var menuNome = new MenuString(titulo + "Nome do Funcionario");
    var menuEmail = new MenuString(titulo + "E-Mail");
    var menuPrivs = new MenuItems(titulo + "Privilegios", "Admin", "Normal");
    var menuSenha = new MenuSenha(titulo + "Senha");
    var seq = new Menu.Sequenciador();

    var e = new Empregado.Construtor();
    var s = new Object() {
      String senha;
    };

    seq
      .executar(menuApelido, apelido -> e.setApelido(apelido))
      .executar(menuNome,    nome    -> e.setNome(nome))
      .executar(menuEmail,   email   -> e.setEmail(email))
      .executar(menuPrivs,   privs   -> {
        switch (privs) {
          case 1:
            e.setPrivilegios(Privilegios.Admin);
            break;

          case 2:
            e.setPrivilegios(Privilegios.Normais);
            break;
        }
      })
      .executar(menuSenha,   senha   -> s.senha = senha);

    return seq.saiu();
  }
}
