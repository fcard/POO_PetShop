package petshop.sistema;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Function;
import petshop.animais.*;

class PesquisaAnimal {
  Set<Especie> especies = new HashSet<Especie>();
  Double pesoMinimo = 0.0;
  Double alturaMinima = 0.0;
  Double idadeMinima = 0.0;
  Sexo sexo = null;
}

public class MenuAnimal extends MenuId<IAnimal, PesquisaAnimal> {
  public MenuAnimal(String titulo, Hashtable<Long, IAnimal> animais) {
    super(titulo, animais);
  }

  @Override
  void ajudaPesquisa() {
    System.out.println("##################################################");
    System.out.println("# Opções de pesquisa:                            #");
    System.out.println("#   -especie <especie> - pesquisa por especie    #");
    System.out.println("#   -peso-minimo <n>   - peso minimo em quilos   #");
    System.out.println("#   -altura-minima <n> - altura minima em metros #");
    System.out.println("#   -idade-minima <n>  - idade minima em anos    #");
    System.out.println("#   -sexo F/M          - pesquisar por sexo      #");
    System.out.println("##################################################");
  }

  @Override
  Function<String, Boolean> lerOpcaoDePesquisa(String arg, PesquisaAnimal c) {
    switch (arg) {
      case "-ESPECIE":
        return esp -> {
          switch (esp) {
            case "CACHORRO":
              c.especies.add(Especie.Cachorro);
              break;
            case "GATO":
              c.especies.add(Especie.Gato);
              break;
            case "TARTARUGA":
              c.especies.add(Especie.Tartaruga);
              break;
            case "COELHO":
              c.especies.add(Especie.Coelho);
              break;
            case "COBRA":
              c.especies.add(Especie.Cobra);
              break;
            default:
              return false;
          }
          return true;
        };

      case "-SEXO":
        return sexo -> {
          switch (sexo) {
            case "F":
              c.sexo = Sexo.F;
              break;

            case "M":
              c.sexo = Sexo.M;
              break;

            default:
              return false;
          }
          return true;
        };

      case "-PESO-MINIMO":
      case "-ALTURA-MINIMA":
      case "-IDADE-MINIMA":
        return valor -> {
          Double valorNumerico = null;
          try {
            valorNumerico = Double.parseDouble(valor);
            switch(arg) {
              case "-PESO-MINIMO":
                c.pesoMinimo = valorNumerico;
                break;
              case "-ALTURA-MINIMA":
                c.alturaMinima = valorNumerico;
                break;
              case "-IDADE-MINIMA":
                c.idadeMinima = valorNumerico;
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
    executarPesquisa(animal -> animal.nome().toUpperCase().contains(nome));
  }

  @Override
  PesquisaAnimal criarCriteriosDePesquisa() {
    return new PesquisaAnimal();
  }

  @Override
  Predicate<IAnimal> criarPredicado(PesquisaAnimal c) {
    return animal ->
      (c.especies.isEmpty() || c.especies.contains(animal.especie())) &&
        (animal.peso() >= c.pesoMinimo) &&
        (animal.altura() >= c.alturaMinima) &&
        (animal.idade() >= c.idadeMinima) &&
        (c.sexo == null || animal.sexo() == c.sexo);
  }

  @Override
  void mostrarDados(long id, IAnimal animal) {
      System.out.println(String.format("# %-3d -- %-22s (%s)   (%2d)       #",
          id, animal.nome(), animal.sexo(), animal.idade()));
  }
}

