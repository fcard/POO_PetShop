package petshop.sistema;
import petshop.empregados.Senha;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.Console;
import petshop.ajudante.Io;

public class MenuSenha extends Menu<String> {
  Senha senha;

  public MenuSenha(String titulo) {
    super(titulo);
  }

  public MenuSenha(String titulo, String apelido) throws Senha.Erro {
    super(titulo);
    this.senha = new Senha(apelido);
  }

  @Override
  public String executar() {
    int tentativas = 3;
    String resultado = null;
    do {
      mostrar_com_titulo();
      resultado = new String(System.console().readPassword("> "));
      if (!resultadoValido(resultado)) {
        System.out.println("Senha errada!");
        Io.esperarPressionarEnter();
        tentativas -= 1;
        if (tentativas == 0) {
          return null;
        }
      }
    } while (!resultadoValido(resultado));
    return resultado;
  }

  @Override
  public void mostrar() {}

  @Override
  public boolean resultadoValido(String resultado) {
    return resultado != null && (senha == null || senha.confirmar(resultado));
  }

  @Override
  public String obterResultado(String line) {
    return line;
  }
}

