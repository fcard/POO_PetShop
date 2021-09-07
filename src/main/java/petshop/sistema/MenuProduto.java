package petshop.sistema;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Function;
import petshop.produtos.*;
import petshop.dados.*;

class PesquisaProduto {
  Set<Categoria> categorias = new HashSet<Categoria>();
  Double precoVendaMin = 0.0;
  Double precoCompraMin = 0.0;
  Double areaMin = 0.0;
  Double pesoMin = 0.0;
}

public class MenuProduto extends MenuId<CompraVenda.Registro, PesquisaProduto> {
  public MenuProduto(String titulo, Hashtable<Long, CompraVenda.Registro> produtos) {
    super(titulo, produtos);
  }

  @Override
  void ajudaPesquisa() {
    System.out.println("###################################################");
    System.out.println("# Opções de pesquisa:                             #");
    System.out.println("#   -categoria <cat> - pesquisa por categoria     #");
    System.out.println("#   -peso-min <n> - peso minimo em quilos         #");
    System.out.println("#   -area-min <n> - area minima em metros qd      #");
    System.out.println("#   -preco-venda-min <n> - preco minimo de venda  #");
    System.out.println("#   -preco-venda-max <n> - preco minimo de compra #");
    System.out.println("###################################################");
  }

  @Override
  Function<String, Boolean> lerOpcaoDePesquisa(String arg, PesquisaProduto c) {
    switch (arg) {
      case "-CATEGORIA":
        return categoria -> {
          switch (categoria) {
            case "COMIDA":
              c.categorias.add(Categoria.Comida);
              break;
            case "BRINQUEDO":
              c.categorias.add(Categoria.Brinquedo);
              break;
            case "REMEDIO":
            case "REMÉDIO":
              c.categorias.add(Categoria.Remedio);
              break;
            default:
              return false;
          }
          return true;
        };

      case "-PESO-MIN":
      case "-AREA-MIN":
      case "-PRECO-VENDA-MIN":
      case "-PRECO-VENDA-MAX":
        return valor -> {
          Double valorNumerico = null;
          try {
            valorNumerico = Double.parseDouble(valor);
            switch(arg) {
              case "-PESO-MIN":
                c.pesoMin = valorNumerico;
                break;
              case "-AREA-MIN":
                c.areaMin = valorNumerico;
                break;
              case "-PRECO-VENDA-MIN":
                c.precoVendaMin = valorNumerico;
                break;
              case "-PRECO-COMPRA-MIN":
                c.precoCompraMin = valorNumerico;
                break;
            }
            return true;
          } catch (Exception e) {
            return false;
          }
        };

      default:
        return null;
    }
  }

  @Override
  void procurarNome(String nome) {
    executarPesquisa(registro -> registro.produto.nome().toUpperCase().contains(nome));
  }

  @Override
  PesquisaProduto criarCriteriosDePesquisa() {
    return new PesquisaProduto();
  }

  @Override
  Predicate<CompraVenda.Registro> criarPredicado(PesquisaProduto c) {
    return registro -> {
      var produto = registro.produto;
      return (c.categorias.isEmpty() || c.categorias.contains(produto.categoria())) &&
       (produto.pesoPorUnidade() >= c.pesoMin) &&
       (produto.areaPorUnidade() >= c.areaMin) &&
       (produto.precoPorUnidadeVenda() >= c.precoVendaMin) &&
       (produto.precoPorUnidadeCompra() >= c.precoCompraMin);
    };
  }

  @Override
  void mostrarDados(long id, CompraVenda.Registro registro) {
    var produto = registro.produto;
    System.out.println(String.format("# %-3d -- %-22s (%s) R$%.2f       #",
          id, produto.nome(), produto.categoria(), produto.precoPorUnidadeVenda()));
  }
}

