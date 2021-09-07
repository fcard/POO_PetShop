package petshop.dados;
import petshop.empregados.*;
import petshop.animais.*;
import petshop.produtos.*;
import java.util.Hashtable;
import java.util.function.*;
import java.time.LocalDate;
import java.io.Serializable;

public class Historico extends Dados<Historico> {
  private static final long serialVersionUID = 1l;

  public static enum RegistroTipo {
    Reserva,
    Adocao,
    Compra,
    Venda
  }

  public static abstract class Registro implements Serializable {
    private static final long serialVersionUID = 1l;
    private LocalDate data;
    private Empregado empregado;

    public Registro(LocalDate data, Empregado empregado) {
      this.data = data;
      this.empregado = empregado;
    }

    public LocalDate data() {
      return data;
    }

    public Empregado empregado() {
      return empregado;
    }

    public abstract RegistroTipo tipo();
  }

  public static abstract class RegistroAnimal extends Registro {
    private static final long serialVersionUID = 1l;
    private IAnimal animal;
    private String dono;

    public RegistroAnimal(
        IAnimal animal, String dono,
        LocalDate data, Empregado empregado) {
      super(data, empregado);
      this.animal = animal;
      this.dono = dono;
    }

    public IAnimal animal() {
      return animal;
    }

    public String dono() {
      return dono;
    }
  }

  public static class RegistroReserva extends RegistroAnimal {
    private static final long serialVersionUID = 1l;

    public RegistroReserva(
        IAnimal animal, String dono,
        LocalDate data, Empregado empregado) {
      super(animal, dono, data, empregado);
    }

    public RegistroTipo tipo() {
      return RegistroTipo.Reserva;
    }

    @Override
    public String toString() {
      return String.format("%s (%s) reservado por (%s) em (%s)",
          animal().nome(), animal().especie().toString(), dono(), data().toString());
    }
  }

  public static class RegistroAdocao extends RegistroAnimal {
    private static final long serialVersionUID = 1l;

    public RegistroAdocao(
        IAnimal animal, String dono,
        LocalDate data, Empregado empregado) {
      super(animal, dono, data, empregado);
    }

    public RegistroTipo tipo() {
      return RegistroTipo.Adocao;
    }

    @Override
    public String toString() {
      return String.format("%s (%s) adotado por (%s) em (%s)",
          animal().nome(), animal().especie().toString(), dono(), data().toString());
    }
  }

  public static abstract class RegistroProduto extends Registro {
    private static final long serialVersionUID = 1l;
    private IProduto produto;
    private long quantidade;
    private double desconto;

    public RegistroProduto(
        IProduto produto, long quantidade, double desconto,
        LocalDate data, Empregado empregado) {
      super(data, empregado);
      this.produto = produto;
      this.quantidade = quantidade;
      this.desconto = desconto;
    }

    public IProduto produto() {
      return produto;
    }

    public long quantidade() {
      return quantidade;
    }

    public double desconto() {
      return desconto;
    }
  }

  public static class RegistroCompra extends RegistroProduto {
    private static final long serialVersionUID = 1l;

    public RegistroCompra(
        IProduto produto, long quantidade, double desconto,
        LocalDate data, Empregado empregado) {
      super(produto, quantidade, desconto, data, empregado);
    }

    public RegistroTipo tipo() {
      return RegistroTipo.Compra;
    }

    @Override
    public String toString() {
      return String.format("%s (%dx) comprado em (%s)",
          produto().nome(), quantidade(), data().toString());
    }
  }

  public static class RegistroVenda extends RegistroProduto {
    private static final long serialVersionUID = 1l;

    public RegistroVenda(
        IProduto produto, long quantidade, double desconto,
        LocalDate data, Empregado empregado) {
      super(produto, quantidade, desconto, data, empregado);
    }

    public RegistroTipo tipo() {
      return RegistroTipo.Venda;
    }

    @Override
    public String toString() {
      return String.format("%s (%dx) vendido em (%s)",
          produto().nome(), quantidade(), data().toString());
    }
  }

  @Override
  public long quantidadeDeIds() {
    return registros.size();
  }

  @Override
  public boolean contemId(long id) {
    return registros.containsKey(id);
  }

  @Override
  public Historico vazio() {
    return new Historico();
  }

  public Hashtable<Long, Registro> registros = new Hashtable<>();

  public <T extends Registro> void registrar(Function<LocalDate,T> f) {
    registros.put(gerarId(), f.apply(LocalDate.now()));
  }

  public void registrarReserva(IAnimal animal, String dono, Empregado e) {
    registrar(hoje -> new RegistroReserva(animal, dono, hoje, e));
  }

  public void registrarAdocao(IAnimal animal, String dono, Empregado e) {
    registrar(hoje -> new RegistroAdocao(animal, dono, hoje, e));
  }

  public void registrarCompra(IProduto produto, double desc, long qtd, Empregado e) {
    registrar(hoje -> new RegistroCompra(produto, qtd, desc, hoje, e));
  }

  public void registrarVenda(IProduto produto,  double desc, long qtd, Empregado e) {
    registrar(hoje -> new RegistroVenda(produto, qtd, desc, hoje, e));
  }

  public void removerRegistro(long id) {
    registros.remove(id);
  }

  public Registro getRegistro(long id) {
    return registros.get(id);
  }

  public RegistroAnimal getRegistroAnimal(long id) {
    return (RegistroAnimal)registros.get(id);
  }

  public RegistroProduto getRegistroProduto(long id) {
    return (RegistroProduto)registros.get(id);
  }
}

