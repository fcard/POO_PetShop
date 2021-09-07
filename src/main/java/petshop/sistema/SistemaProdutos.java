package petshop.sistema;
import petshop.produtos.*;
import petshop.animais.*;
import java.io.*;
import java.util.Hashtable;
import java.util.Set;
import petshop.dados.CompraVenda;
import static petshop.ajudante.Io.*;

public class SistemaProdutos {
  CompraVenda dados;
  public boolean saiu;
  public boolean voltou;

  public static final String ARQUIVO_PRODUTOS = "produtos.obj";
  public static final double DINHEIRO_MOV_MAX = 999999999;

  public SistemaProdutos() {
    saiu = false;
    dados = new CompraVenda().ler(ARQUIVO_PRODUTOS);
  }

  public void executar() {
    saiu = false;
    var menuPrincipal = new MenuItems("Produtos",
        new String[]{"Registrar", "Comprar", "Vender",
                     "Descontos", "Dados", "Dinheiro", "Voltar"});

    while (!saiu && !voltou) {
      menuPrincipal.executar(opcao -> {
        switch (opcao) {
          case 1:
            saiu = registrar();
            mostrarErro(() -> dados.salvar(ARQUIVO_PRODUTOS));
            break;

          case 2:
            saiu = comprar();
            mostrarErro(() -> dados.salvar(ARQUIVO_PRODUTOS));
            break;

          case 3:
            saiu = vender();
            mostrarErro(() -> dados.salvar(ARQUIVO_PRODUTOS));
            break;

          case 4:
            saiu = descontos();
            mostrarErro(() -> dados.salvar(ARQUIVO_PRODUTOS));
            break;

          case 5:
            saiu = pesquisarDados();
            break;

          case 6:
            saiu = dinheiro();
            mostrarErro(() -> dados.salvar(ARQUIVO_PRODUTOS));
            break;

          case 7:
            voltou = true;
            return;
        }
      });

      if (menuPrincipal.saiu() || saiu) {
        saiu = true;

      } else if (menuPrincipal.voltou()) {
        voltou = true;
      }
    }
  }

  boolean registrar() {
    var produtoDados = new DadosProduto();

    var menuCategoria = new MenuItems("Registrar Produto : Categoria",
        new String[]{"Comida", "Brinquedo", "Remedio"});

    var menuNome = new MenuString("Registrar Produto : Nome");
    var menuDescricao = new MenuString("Registrar Produto : Descrição");
    var menuPeso = new MenuDecimal("Registrar Produto : Peso", 0, 1000);
    var menuArea = new MenuDecimal("Registrar Produto : Area", 0, 10);
    var menuVenda = new MenuDecimal("Registrar Produto : Preço de Venda", 0, 100000);
    var menuCompra = new MenuDecimal("Registrar Produto : Preço de Compra", 0, 10000);

    var menuCriterios = new MenuCheckbox("Registrar Produto : Criterios de Aplicação",
        new String[]{"Especie", "Peso", "Altura", "Idade", "Sexo"});

    var menuEspecie = new MenuItems("Registrar Produto : Especie para Aplicação",
        new String[]{"Cachorro", "Gato", "Tartaruga", "Coelho", "Cobra"});

    var menuPesoMin = new MenuDecimal("Registrar Produto : Peso Minimo", 0, 1000);
    var menuPesoMax = new MenuDecimal("Registrar Produto : Peso Maximo", 0, 1000);
    var menuAltMin  = new MenuDecimal("Registrar Produto : Altura Minima", 0, 10);
    var menuAltMax  = new MenuDecimal("Registrar Produto : Altura Maxima", 0, 10);
    var menuIdadeMin = new MenuDecimal("Registrar Produto : Idade Minima", 0, 1000);
    var menuIdadeMax = new MenuDecimal("Registrar Produto : Idade Maxima", 0, 1000);

    var menuSexo = new MenuItems("Registrar Animal : Sexo",
        new String[]{"Feminino", "Masculino"});


    var criterios = new Object() {
      Set<Integer> c;
    };

    var seq = new Menu.Sequenciador();

    seq
      .executar(menuCategoria, categoria -> {
        switch (categoria) {
          case 1:
            produtoDados.setCategoria(Categoria.Comida);
            break;

          case 2:
            produtoDados.setCategoria(Categoria.Brinquedo);
            break;

          case 3:
            produtoDados.setCategoria(Categoria.Brinquedo);
            break;
        }
      })
      .executar(menuNome, nome -> produtoDados.setNome(nome))
      .executar(menuDescricao, desc -> produtoDados.setDescricao(desc))
      .executar(menuPeso, peso -> produtoDados.setPeso(peso))
      .executar(menuArea, area -> produtoDados.setArea(area))
      .executar(menuVenda, p -> produtoDados.setPrecoVenda(p))
      .executar(menuCompra, p -> produtoDados.setPrecoCompra(p))
      .executar(menuCriterios, c -> criterios.c = c);


    if (criterios.c.contains(1)) {
      seq.executar(menuEspecie, especie -> {
        switch (especie) {
          case 1:
            produtoDados.addCriterioEspecie(Especie.Cachorro);
            break;

          case 2:
            produtoDados.addCriterioEspecie(Especie.Gato);
            break;

          case 3:
            produtoDados.addCriterioEspecie(Especie.Tartaruga);
            break;

          case 4:
            produtoDados.addCriterioEspecie(Especie.Coelho);
            break;

          case 5:
            produtoDados.addCriterioEspecie(Especie.Cobra);
            break;
        }
      });
    }

    if (criterios.c.contains(2)) {
      seq
        .executar(menuPesoMin, p -> produtoDados.setCriterioPesoMinimo(p))
        .executar(menuPesoMax, p -> produtoDados.setCriterioPesoMaximo(p));
    }

    if (criterios.c.contains(3)) {
      seq
        .executar(menuAltMin, a -> produtoDados.setCriterioAlturaMinima(a))
        .executar(menuAltMax, a -> produtoDados.setCriterioAlturaMaxima(a));
    }

    if (criterios.c.contains(4)) {
      seq
        .executar(menuIdadeMin, i -> produtoDados.setCriterioIdadeMinima(i))
        .executar(menuIdadeMax, i -> produtoDados.setCriterioIdadeMaxima(i));
    }

    if (criterios.c.contains(5)) {
      seq.executar(menuSexo, sexo -> {
        switch (sexo) {
          case 1:
            produtoDados.setCriterioSexo(Sexo.F);
            break;

          case 2:
            produtoDados.setCriterioSexo(Sexo.M);
            break;
        }
      });
    }


    if (!seq.voltou() && !seq.saiu()) {
      mostrarErro(() -> dados.registrar(produtoDados));
      return false;
    } else {
      return seq.saiu();
    }
  }

  boolean comprar() {
    return compra_ou_venda("Comprar Produto", dados::comprarProduto);
  }

  boolean vender() {
    return compra_ou_venda("Vender Produto", dados::venderProduto);
  }

  @FunctionalInterface
  interface Lambda {
    public void chamar(long id, long qtd) throws CompraVenda.Erro;
  }

  boolean compra_ou_venda(String titulo, Lambda f) {
    var menuCV = new MenuProduto(titulo, dados.registro);
    var menuQuantidade = new MenuLong(titulo + " : Quantidade", 0l, 999999l);
    var seq = new Menu.Sequenciador();
    var v = new Object() {
      long id;
      long quantidade;
    };

    seq
      .executar(menuCV, id -> v.id = id)
      .executar(menuQuantidade, qtd -> v.quantidade = qtd);

    if (!seq.voltou() && !seq.saiu()) {
      mostrarErro(() -> f.chamar(v.id, v.quantidade));
      return false;
    } else {
      return seq.saiu();
    }
  }

  boolean descontos() {
    var menuDescontos = new MenuItems("Descontos", "Mudar", "Remover");
    menuDescontos.executar(opcao -> {
      switch (opcao) {

      }
    });
    return false;
  }

  boolean dinheiro() {
    var menuDinheiro = new MenuItems("Dinheiro",
        "Checar Balança", "Adicionar", "Remover",
        "Adicionar com Debito", "Pagar Debito", "Voltar");

    menuDinheiro.executar(opcao -> {
      switch (opcao) {
        case 1:
          double dinheiro = dados.dinheiro();
          System.out.println("##################################################");
          System.out.println(String.format("# Dinheiro da Loja: %8.2f", dinheiro));
          System.out.println(String.format("# Debito: %8.2f", dados.debito()));
          System.out.println("##################################################");
          esperarPressionarEnter();
          break;

        case 2:
          var menuAdicionar = new MenuDecimal("Dinheiro : Adicionar", 0, DINHEIRO_MOV_MAX);
          menuAdicionar.executar(valor -> {
            mostrarErro(() -> dados.adicionarDinheiroSemDebito(valor));
          });
          saiu = menuAdicionar.saiu();
          break;

        case 3:
          var menuRemover = new MenuDecimal("Dinheiro : Remover", 0, dados.dinheiro());
          menuRemover.executar(valor -> {
            mostrarErro(() -> dados.removerDinheiro(valor));
          });
          saiu = menuRemover.saiu();
          break;

        case 4:
          var menuAdcDebito =
            new MenuDecimal("Dinheiro : Adicionar Com Debito", 0, DINHEIRO_MOV_MAX);

          menuAdcDebito.executar(valor -> {
            mostrarErro(() -> dados.adicionarDinheiroComDebito(valor));
          });
          saiu = menuAdcDebito.saiu();
          break;

        case 5:
          double min = Double.min(dados.dinheiro(), dados.debito());
          var menuPagDebito = new MenuDecimal("Dinheiro : Pagar Debito", 0, min);
          menuPagDebito.executar(valor -> {
            mostrarErro(() -> dados.pagarDebito(valor));
          });
          saiu = menuPagDebito.saiu();
          break;

        case 6:
          break;
      }
    });

    return saiu || menuDinheiro.saiu();
  }

  @SuppressWarnings("unchecked")
  boolean pesquisarDados() {
    var produtos = (Hashtable<Long,CompraVenda.Registro>)dados.registro.clone();
    var menuDados = new MenuProduto("Dados", produtos);
    while (!menuDados.voltou() && !menuDados.saiu()) {
      var id = menuDados.executar();
      if (id != null) {
        var produto = produtos.get(id).produto;
        var v = produto.precoPorUnidadeVenda();
        var c = produto.precoPorUnidadeCompra();
        var a = produto.areaPorUnidade();
        var p = produto.pesoPorUnidade();
        System.out.println("##################################################");
        System.out.println(String.format("# Nome:  %-20s %17s  #", produto.nome(), ""));
        System.out.println(String.format("# Descrição:  %-37s #", produto.descricao()));
        System.out.println(String.format("# Preço/Venda: %2.2f %37s #", v, ""));
        System.out.println(String.format("# Preço/Compra: %2.2f %37s #", c, ""));
        System.out.println(String.format("# Area:  %8.2f %30s #", a, ""));
        System.out.println(String.format("# Peso:  %8.2f %30s #", p, ""));
        System.out.println("##################################################");
        esperarPressionarEnter();
      }
    }
    return menuDados.saiu();
  }
}


