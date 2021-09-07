package petshop.sistema;
import petshop.ajudante.Io;

public class SistemaPrincipal {
  boolean saiu;
  SistemaAdocao adocao;
  SistemaProdutos produtos;
  SistemaEmpregados empregados;

  public SistemaPrincipal() {
    adocao = new SistemaAdocao();
    produtos = new SistemaProdutos();
    empregados = new SistemaEmpregados();
  }

  public void executar() {
    var menuPrincipal = new MenuItems("Sistema Pet Shop",
        "Animais", "Produtos", "Menu Especial", "Ajuda", "Sair");

    while (true) {
      menuPrincipal.executar(opcao -> {
        switch (opcao) {
          case 1:
            adocao.executar();
            if (adocao.saiu) {
              saiu = true;
            }
            break;

          case 2:
            produtos.executar();
            if (produtos.saiu) {
              saiu = true;
            }
            break;

          case 3:
            /*
            especial.executar();
            if (especial.saiu) {
              return;
            }
            break;
            */

          case 4:
            System.out.println("# comandos globais:                              #");
            System.out.println("#   %ajuda  - mostrar essa mensagem de ajuda     #");
            System.out.println("#   %voltar - retornar para o menu anterior      #");
            System.out.println("#   %sair   - terminar o programa                #");
            System.out.println("##################################################");
            Io.esperarPressionarEnter();
            break;

          case 5:
            saiu = true;
            break;
        }
      });
      if (saiu || menuPrincipal.voltou() || menuPrincipal.saiu()) {
        return;
      }
    }
  }
}
