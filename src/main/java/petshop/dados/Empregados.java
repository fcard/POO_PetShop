package petshop.dados;
import java.io.Serializable;
import java.util.Hashtable;
import petshop.empregados.Empregado;
import petshop.empregados.Senha;

public class Empregados extends Dados<Empregados> {
  private static final long serialVersionUID = 1l;

  private static class Erro extends Exception {
    private static final long serialVersionUID = 1l;
    public Erro(String erro) {
      super(erro);
    }
  }

  private static class ErroEmpregadoJaExistente extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroEmpregadoJaExistente(String apelido) {
      super(String.format("Empregado com apelido ja existe: %s.", apelido));
    }
  }

  private static class ErroEmpregadoInexistente extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroEmpregadoInexistente(String apelido) {
      super(String.format("Empregado com apelido nao existe: %s.", apelido));
    }
    public ErroEmpregadoInexistente(Long id) {
      super(String.format("Empregado com id nao existe: %d.", id));
    }
  }

  private static class ErroApelidoInvalido extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroApelidoInvalido(String apelido) {
      super(String.format("Esse apelido e invalido: %s.", apelido));
    }
  }

  private static class ErroSenha extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroSenha(Senha.Erro e) {
      super(e.toString());
    }
  }

  public Hashtable<Long, Empregado> empregadosPorId;
  public Hashtable<String, Long> empregadosPorApelido;

  public Empregados() {
    this.empregadosPorId = new Hashtable<>();
    this.empregadosPorApelido = new Hashtable<>();
  }

  @Override
  public Empregados vazio() {
    return new Empregados();
  }

  @Override
  public boolean contemId(long id) {
    return empregadosPorId.containsKey(id);
  }

  @Override
  public long quantidadeDeIds() {
    return empregadosPorId.size();
  }

  public void registrarEmpregado(Empregado e, String senha) throws Erro {
    var apelido = e.apelido();
    if (apelido == null || !Empregado.apelidoValido(apelido)) {
      throw new ErroApelidoInvalido(apelido == null ? "" : apelido);
    }
    if (empregadosPorApelido.containsKey(apelido)) {
      throw new ErroEmpregadoJaExistente(apelido);
    }
    try {
      Senha.registrarSenha(apelido, senha);
    } catch (Senha.Erro err) {
      throw new ErroSenha(err);
    }

    long id = gerarId();
    empregadosPorId.put(id, e);
    empregadosPorApelido.put(apelido, id);
  }

  public void atualizarEmpregado(Empregado e, String senha) throws Erro {
    var id = empregadosPorApelido.get(e.apelido());
    if (id == null) {
      throw new ErroEmpregadoInexistente(e.apelido() == null ? "" : e.apelido());
    }
    try {
      Senha.registrarSenha(e.apelido(), senha);
    } catch (Senha.Erro err) {
      throw new ErroSenha(err);
    }
    empregadosPorId.put(id, e);
  }

  public void removerEmpregado(String apelido) throws Erro {
    var id = empregadosPorApelido.get(apelido);
    if (id == null) {
      throw new ErroEmpregadoInexistente(apelido == null ? "" : apelido);
    }
    empregadosPorId.remove(id);
    empregadosPorApelido.remove(apelido);
  }

  public void removerEmpregado(Long id) throws Erro {
    if (!empregadosPorId.containsKey(id)) {
      throw new ErroEmpregadoInexistente(id == null ? -1 : id);
    }
    var apelido = empregadosPorId.get(id).apelido();
    empregadosPorId.remove(id);
    empregadosPorApelido.remove(apelido);
  }


  public Empregado obterEmpregado(Long id) throws Erro {
    if (!empregadosPorId.containsKey(id)) {
      throw new ErroEmpregadoInexistente(id == null ? -1 : id);
    }
    return empregadosPorId.get(id);
  }

  public Empregado obterEmpregado(String apelido) throws Erro {
    var id = empregadosPorApelido.get(apelido);
    if (id == null) {
      throw new ErroEmpregadoInexistente(apelido == null ? "" : apelido);
    }
    return empregadosPorId.get(id);
  }
}
