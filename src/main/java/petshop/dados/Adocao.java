package petshop.dados;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map.Entry;
import petshop.animais.IAnimal;
import petshop.animais.DadosAnimal;

public class Adocao extends Dados<Adocao> {
  private static final long serialVersionUID = 1l;

  public static class Erro extends Exception {
    private static final long serialVersionUID = 1l;
    public Erro(String erro) {
      super(erro);
    }
  }

  public static class ErroAnimalNaoCriado extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroAnimalNaoCriado() {
      super("Os dados do animal não puderam ser criados");
    }
  }

  public static class ErroAnimalNaoParaAdocao extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroAnimalNaoParaAdocao(long id) {
      super(String.format("O animal com id %d não está para adoção", id));
    }
  }

  public static class ErroAnimalNaoReservado extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroAnimalNaoReservado(long id) {
      super(String.format("O animal com id %d não está reservado", id));
    }
  }

  public Hashtable<Long, IAnimal> animaisParaAdocao;
  public Hashtable<Long, IAnimal> animaisReservados;
  public Hashtable<Long, String> donos;

  public Adocao() {
    this.animaisParaAdocao = new Hashtable<>();
    this.animaisReservados = new Hashtable<>();
    this.donos = new Hashtable<>();
  }

  @Override
  public Adocao vazio() {
    return new Adocao();
  }

  @Override
  public boolean contemId(long id) {
    return animaisParaAdocao.containsKey(id) ||
           animaisReservados.containsKey(id);
  }

  @Override
  public long quantidadeDeIds() {
    return animaisParaAdocao.size() + animaisReservados.size();
  }

  public void registrarAnimal(DadosAnimal dados) throws Erro {
    IAnimal animal = dados.criar();
    if (animal == null) {
      throw new ErroAnimalNaoCriado();
    }
    animaisParaAdocao.put(gerarId(), animal);
  }

  public void reservarAnimal(long id, String dono) throws Erro {
    if (!animaisParaAdocao.containsKey(id)) {
      throw new ErroAnimalNaoParaAdocao(id);
    }
    IAnimal animal = animaisParaAdocao.remove(id);
    animaisReservados.put(id, animal);
    donos.put(id, dono);
  }

  public void terminarAdocao(long id) throws Erro {
    if (!animaisReservados.containsKey(id)) {
      throw new ErroAnimalNaoReservado(id);
    }
    animaisReservados.remove(id);
    donos.remove(id);
  }

  public Set<Long> idsAnimaisParaAdocao() {
    return animaisParaAdocao.keySet();
  }

  public Set<Long> idsAnimaisReservados() {
    return animaisReservados.keySet();
  }

  public ArrayList<Long> pesquisarAnimalPorNome(String nome) {
    var resultado = new ArrayList<Long>();
    animaisParaAdocao.forEach((id, animal) -> {
      if (animal.nome().equals(nome)) {
        resultado.add(id);
      }
    });

    animaisReservados.forEach((id, animal) -> {
      if (animal.nome().equals(nome)) {
        resultado.add(id);
      }
    });
    return resultado;
  }

  public IAnimal getAnimalLivre(long id) {
    return animaisParaAdocao.get(id);
  }

  public IAnimal getAnimalReservado(long id) {
    return animaisReservados.get(id);
  }

  public String getDono(long id) {
    return donos.get(id);
  }
}

