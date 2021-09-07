package petshop.empregados;
import java.nio.file.Path;
import java.lang.StringBuilder;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.stream.Collectors;
import java.io.*;
import java.util.Scanner;
import petshop.ajudante.Io;

// TODO: usar banco de dados e servidor (fora de escopo do projeto)
public class Senha {
  private String valor;

  private static Integer chave1 = 3;
  private static Integer chave2 = null;
  private static Integer chave = null;
  private static Path database = Paths.get("database");

  public static class Erro extends Exception {
    private static final long serialVersionUID = 1l;
    public Erro(String erro) {
      super(erro);
    }
  }

  public static class ErroChaveNula extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroChaveNula() {
      super("Operações que envolvem senha não podem ser feitas " +
            "pois a chave não foi encontrada. Inicialize a chave " +
            "e tente novamente.");
    }
  }

  public static class ErroIO extends Erro {
    private static final long serialVersionUID = 1l;
    public ErroIO(IOException e) {
      super("Operação com senha falou por causa de um erro de io. Detalhes: " +
            e.toString());
    }
  }

  static {
    Io.mostrarErro(() -> {
      var arquivoChave = new File(database.resolve("chave.data").toString());
      var scanner = new Scanner(arquivoChave);
      chave2 = scanner.nextInt();
      chave = chave1 + chave2;
    });
  }

  public static String encriptar(String s) {
    if (chave == null) {
      return null;
    }
    var resultado = new StringBuilder();
    for (var c : s.toCharArray()) {
      resultado.append((char)(((int)c + chave) % 255));
    }
    return resultado.toString();
  }

  public static String desencriptar(String s) {
    if (chave == null) {
      return null;
    }
    var resultado = new StringBuilder();
    for (var c : s.toCharArray()) {
      int v = ((int)c - chave) % 255;
      v = v < 0 ? 255 - v : v;
      resultado.append((char)v);
    }
    return resultado.toString();
  }

  public static void registrarSenha(String apelido, String senha) throws Erro {
    if (chave == null) {
      throw new ErroChaveNula();
    }
    try {
      var path = database.resolve(apelido + ".senha").toString();
      var arquivo_senha = new FileWriter(path);
      var senha_encriptada = encriptar(senha);
      arquivo_senha.write(senha_encriptada);
      arquivo_senha.flush();
    } catch (IOException e) {
      throw new ErroIO(e);
    }
  }

  private Senha() {
    this.valor = null;
  }

  public Senha(String apelido) throws Erro {
    if (chave == null) {
      throw new ErroChaveNula();
    }
    try {
      var path = database.resolve(apelido + ".senha").toString();
      var senha_encriptada = Files.lines(Paths.get(path), StandardCharsets.UTF_8)
                                  .collect(Collectors.joining(System.lineSeparator()));
      valor = desencriptar(senha_encriptada);
    } catch (IOException e) {
      throw new ErroIO(e);
    }
  }

  public boolean confirmar(String senha) {
    return this.valor.equals(senha);
  }
}
