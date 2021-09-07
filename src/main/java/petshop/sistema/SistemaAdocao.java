package petshop.sistema;
import petshop.animais.*;
import java.io.*;
import java.util.Hashtable;
import petshop.dados.Adocao;
import static petshop.ajudante.Io.*;

public class SistemaAdocao {
  Adocao dados;
  public boolean saiu;
  public boolean voltou;

  public static final String ARQUIVO_ADOCAO = "adocao.obj";

  public SistemaAdocao() {
    voltou = false;
    saiu = false;
    dados = new Adocao().ler(ARQUIVO_ADOCAO);
  }

  public void executar() {
    saiu = false;
    voltou = false;
    var menuPrincipal = new MenuItems("Adoção",
        "Registrar", "Reservar", "Terminar Adoção", "Dados", "Voltar");

    while (!voltou && !saiu) {
      menuPrincipal.executar(opcao -> {
        switch (opcao) {
          case 1:
            saiu = registrar();
            mostrarErro(() -> dados.salvar(ARQUIVO_ADOCAO));
            break;

          case 2:
            saiu = reservar();
            mostrarErro(() -> dados.salvar(ARQUIVO_ADOCAO));
            break;

          case 3:
            saiu = terminarAdocao();
            mostrarErro(() -> dados.salvar(ARQUIVO_ADOCAO));
            break;

          case 4:
            saiu = pesquisarDados();
            break;

          case 5:
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
    var animalDados = new DadosAnimal();

    var menuEspecie = new MenuItems("Registrar Animal : Especie",
        "Cachorro", "Gato", "Tartaruga", "Coelho", "Cobra");

    var menuSexo = new MenuItems("Registrar Animal : Sexo", "Feminino", "Masculino");

    var menuNome = new MenuString("Registrar Animal : Nome");
    var menuPeso = new MenuDecimal("Registrar Animal : Peso", 0, 1000);
    var menuAltura = new MenuDecimal("Registrar Animal : Altura", 0, 10);
    var menuNascimento = new MenuData("Registrar Animal : Data de Nascimento");

    var seq = new Menu.Sequenciador();

    seq
      .executar(menuEspecie, especie -> {
        switch (especie) {
          case 1:
            animalDados.setEspecie(Especie.Cachorro);
            break;

          case 2:
            animalDados.setEspecie(Especie.Gato);
            break;

          case 3:
            animalDados.setEspecie(Especie.Tartaruga);
            break;

          case 4:
            animalDados.setEspecie(Especie.Coelho);
            break;

          case 5:
            animalDados.setEspecie(Especie.Cobra);
            break;
        }
      })
      .executar(menuSexo, sexo -> {
        switch (sexo) {
          case 1:
            animalDados.setSexo(Sexo.F);
            break;

          case 2:
            animalDados.setSexo(Sexo.M);
            break;
        }
      })
      .executar(menuNome, nome -> animalDados.setNome(nome))
      .executar(menuPeso, peso -> animalDados.setPeso(peso))
      .executar(menuNascimento, data -> animalDados.setDataDeNascimento(data));

    if (!seq.voltou() && !seq.saiu()) {
      mostrarErro(() -> dados.registrarAnimal(animalDados));
      return false;
    } else {
      return seq.saiu();
    }
  }

  boolean reservar() {
    var menuReservar = new MenuAnimal("Reservar Animal", dados.animaisParaAdocao);
    menuReservar.executar(id -> mostrarErro(()->dados.reservarAnimal(id, "")));
    return menuReservar.saiu();
  }

  boolean terminarAdocao() {
    var menuTerminar = new MenuAnimal("Terminar Adoção", dados.animaisReservados);
    menuTerminar.executar(id -> mostrarErro(()->dados.terminarAdocao(id)));
    return menuTerminar.saiu();
  }

  @SuppressWarnings("unchecked")
  boolean pesquisarDados() {
    var animais = (Hashtable<Long,IAnimal>)dados.animaisParaAdocao.clone();
    animais.putAll(dados.animaisReservados);
    var menuDados = new MenuAnimal("Dados", animais);
    while (!menuDados.voltou() && !menuDados.saiu()) {
      var id = menuDados.executar();
      if (id != null) {
        var animal = animais.get(id);
        System.out.println("##################################################");
        System.out.println(String.format("# Nome:  %-20s %17s  #", animal.nome(), ""));
        System.out.println(String.format("# Sexo:  %s %37s #", animal.sexo(), ""));
        System.out.println(String.format("# Idade: %s %37s #", animal.idade(), ""));
        System.out.println(String.format("# Peso:  %8f %30s #", animal.peso(), ""));
        System.out.println("##################################################");
        esperarPressionarEnter();
      }
    }
    return menuDados.saiu();
  }
}

