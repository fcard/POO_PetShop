package petshop.dados;
import java.io.*;

public abstract class Dados<T extends Dados<T>> implements Serializable {
  private static final long serialVersionUID = 1l;

  long ultimoId = 1;

  public abstract T vazio();
  public abstract boolean contemId(long id);
  public abstract long quantidadeDeIds();

  @SuppressWarnings("unchecked")
  public T ler(String nomeArquivo) {
    try {
      var arquivo = new FileInputStream(nomeArquivo);
      var input   = new ObjectInputStream(arquivo);
      return (T)input.readObject();
    } catch (Exception e) {
      return vazio();
    }
  }

  public void salvar(String nomeArquivo) throws Exception {
    var arquivo = new FileOutputStream(nomeArquivo);
    var output  = new ObjectOutputStream(arquivo);
    output.writeObject(this);
  }

  long gerarId() {
    // permitimos a reutilizacao de ids caso a densidade de ids
    // registrados seja baixa o suficiente.
    if (quantidadeDeIds() < 500 && ultimoId > 500) {
      ultimoId = 1;
    }

    while (contemId(ultimoId)) {
      ultimoId += 1;
    }
    return ultimoId;
  }
}

