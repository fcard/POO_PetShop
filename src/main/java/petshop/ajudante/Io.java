package petshop.ajudante;
import java.nio.file.FileSystems;

public class Io {
  private static final boolean isPosix =
    FileSystems.getDefault().supportedFileAttributeViews().contains("posix");

  private static final boolean isWindows =
    System.getProperty("os.name").contains("Windows");


  public static interface FuncaoComErro {
    public void chamar() throws Exception;
  }

  public static void mostrarErro(FuncaoComErro f) {
    try {
      f.chamar();
    } catch (Exception e) {
      System.out.println("###########################################");
      System.out.println("# ~~~~~~~~~~~~~~~~~ ERRO ~~~~~~~~~~~~~~~~~#");
      System.out.println("###########################################");
      System.out.println(e.toString());
      System.out.println("###########################################");
      esperarPressionarEnter();
    }
  }

  public static void esperarPressionarEnter() {
    try {
      System.in.read();
    } catch (Exception e) {
    }
  }

  public static void limparTela() {
    try {
      if (isPosix) {
        new ProcessBuilder("sh", "-c", "clear").inheritIO().start().waitFor();
      } else if (isWindows) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
      }
    } catch (Exception e) {
    }
  }
}
