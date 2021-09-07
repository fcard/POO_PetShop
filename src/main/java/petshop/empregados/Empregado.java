package petshop.empregados;
import java.io.Serializable;

public class Empregado implements Serializable {
  static final long serialVersionUID = 1l;

  protected String nome;
  protected String apelido;
  protected String email;
  protected Privilegios privilegios;

  public static class Construtor {
    private Empregado e;

    public Construtor() {
      this.e = new Empregado();
    }

    public Construtor setNome(String nome) {
      e.nome = nome;
      return this;
    }

    public Construtor setApelido(String apelido) {
      e.apelido = apelido;
      return this;
    }

    public Construtor setEmail(String email) {
      e.email = email;
      return this;
    }

    public Construtor setPrivilegios(Privilegios p) {
      e.privilegios = p;
      return this;
    }

    public Empregado paraEmpregado() {
      return e;
    }
  }

  public static boolean apelidoValido(String apelido) {
    for (var c : apelido.toCharArray()) {
      if (!Character.isLetterOrDigit(c)) {
        return false;
      }
    }
    return true;
  }

  public String nome() {
    return nome;
  }

  public String apelido() {
    return apelido;
  }

  public String email() {
    return email;
  }

  public Privilegios privilegios() {
    return privilegios;
  }

  public boolean eAdmin() {
    return privilegios == Privilegios.Admin;
  }
}
