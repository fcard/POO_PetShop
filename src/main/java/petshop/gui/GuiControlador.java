package petshop.gui;
import petshop.dados.*;
import petshop.animais.*;
import petshop.produtos.*;
import petshop.empregados.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.fxml.*;
import java.util.function.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.Arrays;
import static petshop.gui.GuiPrincipal.*;
import static petshop.dados.Historico.RegistroTipo;

enum AbaPrincipal {
  Animais,
  Produtos,
  Dinheiro,
  Empregados,
  Historico
}

enum AbaAnimais {
  Livres,
  Reservados
}

enum AbaProdutos {
  Comida,
  Brinquedos,
  Remedios
}

enum AbaHistorico {
  Animais,
  Produtos
}

public class GuiControlador {
  public static GuiControlador instancia = null;

  public static final String ARQUIVO_ADOCAO = "adocao.obj";
  public static final String ARQUIVO_PRODUTOS = "produtos.obj";
  public static final String ARQUIVO_EMPREGADOS = "empregados.obj";
  public static final String ARQUIVO_HISTORICO = "historico.obj";

  private Adocao dadosAdocao = new Adocao().ler(ARQUIVO_ADOCAO);
  private CompraVenda dadosProdutos = new CompraVenda().ler(ARQUIVO_PRODUTOS);
  private Empregados dadosEmpregados = new Empregados().ler(ARQUIVO_EMPREGADOS);
  private Historico dadosHistorico = new Historico().ler(ARQUIVO_HISTORICO);

  private Empregado empregadoLogado = null;

  private Long animalSelecionado = null;
  private Long produtoSelecionado = null;
  private Long empregadoSelecionado = null;
  private Long historicoSelecionado = null;

  private Pesquisa.Animal pesquisaAnimais = new Pesquisa.Animal("");
  private Pesquisa.Produto pesquisaProdutos = new Pesquisa.Produto("");
  private Pesquisa.Empregado pesquisaEmpregados = new Pesquisa.Empregado("");
  private Pesquisa.Historico pesquisaHistorico = new Pesquisa.Historico("");

  private AbaPrincipal abaPrincipal = AbaPrincipal.Animais;
  private AbaAnimais abaAnimais = AbaAnimais.Livres;
  private AbaProdutos abaProdutos = AbaProdutos.Comida;
  private AbaHistorico abaHistorico = AbaHistorico.Animais;

  private ArrayList<Long> idsAnimais = new ArrayList<Long>();
  private ArrayList<Long> idsProdutos = new ArrayList<Long>();
  private ArrayList<Long> idsEmpregados = new ArrayList<Long>();
  private ArrayList<Long> idsHistorico = new ArrayList<Long>();

  // campos definidos no fxml
  @FXML private Button animaisButtonPesquisar;
  @FXML private Button animaisButtonRegistrar;
  @FXML private Button animaisButtonReservar;
  @FXML private Button animaisButtonTerminar;
  @FXML private Button dinheiroButtonAdc;
  @FXML private Button dinheiroButtonAdcDebito;
  @FXML private Button dinheiroButtonRemover;
  @FXML private Button empregadosButtonAtualizar;
  @FXML private Button empregadosButtonLogin;
  @FXML private Button empregadosButtonRemover;
  @FXML private Button produtosButtonComprar;
  @FXML private Button produtosButtonDescontos;
  @FXML private Button produtosButtonRegistrar;
  @FXML private Button produtosButtonVender;
  @FXML private ListView<String> animaisListaLivres;
  @FXML private ListView<String> animaisListaReservados;
  @FXML private ListView<String> empregadosLista;
  @FXML private ListView<String> produtosListaComida;
  @FXML private ListView<String> produtosListaBrinquedos;
  @FXML private ListView<String> produtosListaRemedios;
  @FXML private ListView<String> historicoListaAnimais;
  @FXML private ListView<String> historicoListaProdutos;
  @FXML private Tab animaisAba;
  @FXML private Tab animaisAbaLivres;
  @FXML private Tab animaisAbaReservados;
  @FXML private Tab dinheiroAba;
  @FXML private Tab empregadosAba;
  @FXML private Tab produtosAba;
  @FXML private Tab produtosAbaComida;
  @FXML private Tab produtosAbaBrinquedos;
  @FXML private Tab produtosAbaRemedios;
  @FXML private Tab historicoAba;
  @FXML private Tab historicoAbaAnimais;
  @FXML private Tab historicoAbaProdutos;
  @FXML private TextField animaisInfoAltura;
  @FXML private TextField animaisInfoData;
  @FXML private TextField animaisInfoEspecie;
  @FXML private TextField animaisInfoIdade;
  @FXML private TextField animaisInfoNome;
  @FXML private TextField animaisInfoPeso;
  @FXML private TextField animaisInfoSexo;
  @FXML private TextField animaisPesquisa;
  @FXML private TextField dinheiroInfoDebito;
  @FXML private TextField dinheiroInfoDinheiro;
  @FXML private TextField empregadosInfoApelido;
  @FXML private TextField empregadosInfoEmail;
  @FXML private TextField empregadosInfoNome;
  @FXML private TextField empregadosInfoPrivilegios;
  @FXML private TextField empregadosProdutos;
  @FXML private TextField empregadosPesquisa;
  @FXML private TextField produtosInfoNome;
  @FXML private TextField produtosInfoArea;
  @FXML private TextField produtosInfoPeso;
  @FXML private TextField produtosInfoCategoria;
  @FXML private TextField produtosInfoCompra;
  @FXML private TextField produtosInfoVenda;
  @FXML private TextField produtosInfoEstoque;
  @FXML private TextField produtosPesquisa;
  @FXML private TextField historicoInfoData;
  @FXML private TextField historicoInfoId;
  @FXML private TextField historicoInfoEmpregado;
  @FXML private TextField historicoPesquisa;

  @FXML
  private void initialize() {
    instancia = this;
    inicializarAbaAnimaisLivres();
  }

  public void depoisDeMostrado() {
    if (dadosEmpregados.empregadosPorId.isEmpty()) {
      registrarAdminInicial();
    } else {
      login(false);
    }
  }

  @FunctionalInterface
  public interface Operacao {
    public void chamar();
  }

  public void operacaoAdmin(Operacao op) {
    mostrarErro(() -> {
      if (empregadoLogado.eAdmin()) {
        op.chamar();
      } else {
        throw new Exception("Apenas admins podem fazer essa operação");
      }
    });
  }

  // -- Animais --

  private void inicializarAbaAnimaisLivres() {
    limparInfoAnimais();
    atualizarListaAnimais(dadosAdocao.animaisParaAdocao, animaisListaLivres);
  }

  private void inicializarAbaAnimaisReservados() {
    limparInfoAnimais();
    atualizarListaAnimais(dadosAdocao.animaisReservados, animaisListaReservados);
  }

  private void atualizarListaAnimais(
    Hashtable<Long,IAnimal> animais, ListView<String> lista) {

    animalSelecionado = null;
    idsAnimais.clear();
    lista.getItems().clear();
    for (var id : animais.keySet()) {
      IAnimal animal = animais.get(id);
      if (pesquisaAnimais.seAplica(animal)) {
        idsAnimais.add(id);
        lista.getItems().add(String.format("%s (%s)", animal.nome(), animal.especie()));
      }
    }
  }

  private void limparInfoAnimais() {
    if (animaisInfoEspecie != null) {
      animaisInfoEspecie.clear();
      animaisInfoNome.clear();
      animaisInfoIdade.clear();
      animaisInfoData.clear();
      animaisInfoPeso.clear();
      animaisInfoAltura.clear();
      animaisInfoSexo.clear();
    }
  }

  @FXML
  private void registrarAnimal() {
    var janela = novaJanela("gui/registrarAnimal.fxml");
    if (janela != null) {
      RegistrarAnimal.stage = janela;
      janela.setTitle("Registrar Animal");
      janela.show();
    }
  }

  public void registrarAnimal(DadosAnimal animal) {
    mostrarErro(() -> {
      dadosAdocao.registrarAnimal(animal);
      dadosAdocao.salvar(ARQUIVO_ADOCAO);
      atualizarAbaAnimais();
    });
  }

  @FXML
  private void reservarAnimal() {
    reservarAnimal(() -> {});
  }

  private void reservarAnimal(FuncaoComErro f) {
    if (animaisAbaLivres.isSelected() && animalSelecionado != null) {
      var janela = novaJanela("gui/dono.fxml");
      if (janela != null) {
        Dono.stage = janela;
        Dono.depois = dono -> {
          mostrarErro(() -> {
            var animal = dadosAdocao.getAnimalLivre(animalSelecionado);
            dadosAdocao.reservarAnimal(animalSelecionado, dono);
            dadosHistorico.registrarReserva(animal, dono, empregadoLogado);
            f.chamar();
            dadosAdocao.salvar(ARQUIVO_ADOCAO);
            dadosHistorico.salvar(ARQUIVO_HISTORICO);
            atualizarAbaAnimais();
          });
        };
        janela.setTitle("Novo Dono");
        janela.show();
      }
    }
  }

  @FXML
  private void terminarAdocao() {
    if (animalSelecionado != null) {
      mostrarErro(() -> {
        if (animaisAbaLivres.isSelected()) {
          reservarAnimal(() -> {
            var animal = dadosAdocao.getAnimalReservado(animalSelecionado);
            var dono = dadosAdocao.getDono(animalSelecionado);
            dadosAdocao.terminarAdocao(animalSelecionado);
            dadosHistorico.registrarAdocao(animal, dono, empregadoLogado);
          });
        } else {
          var animal = dadosAdocao.getAnimalReservado(animalSelecionado);
          var dono = dadosAdocao.getDono(animalSelecionado);
          dadosAdocao.terminarAdocao(animalSelecionado);
          dadosAdocao.salvar(ARQUIVO_ADOCAO);
          dadosHistorico.salvar(ARQUIVO_HISTORICO);
        }
      });
    }
  }

  @FXML
  private void animaisPesquisar() {
    pesquisaAnimais = new Pesquisa.Animal(animaisPesquisa.getText());
    atualizarAbaAnimais();
  }

  @FXML
  private void animaisAbaMudada() {
    if (animaisAba.isSelected()) {
      abaPrincipal = AbaPrincipal.Animais;
      atualizarAbaAnimais();
    }
  }

  private void atualizarAbaAnimais() {
    if (abaAnimais == AbaAnimais.Livres) {
      inicializarAbaAnimaisLivres();
    } else {
      inicializarAbaAnimaisReservados();
    }
  }

  @FXML
  private void selecionarAnimal() {
    var lista = abaAnimais == AbaAnimais.Livres ?
                              animaisListaLivres : animaisListaReservados;
    var animais = abaAnimais == AbaAnimais.Livres ?
                                dadosAdocao.animaisParaAdocao :
                                dadosAdocao.animaisReservados;

    int index = lista.getSelectionModel().getSelectedIndex();
    if (index >= 0) {
      animalSelecionado = idsAnimais.get(index);
      IAnimal animal = animais.get(animalSelecionado);
      animaisInfoEspecie.setText(animal.especie().toString());
      animaisInfoNome.setText(animal.nome());
      animaisInfoData.setText(animal.dataDeNascimento().toString());
      animaisInfoPeso.setText(String.format("%.2fkg", animal.peso()));
      animaisInfoAltura.setText(String.format("%.2fm", animal.altura()));
      animaisInfoIdade.setText(animal.idade().toString());
      animaisInfoSexo.setText(animal.sexo().toString());
    }
  }

  @FXML
  private void animaisLivresAbaMudada() {
    if (animaisAbaLivres.isSelected()) {
      abaAnimais = AbaAnimais.Livres;
      inicializarAbaAnimaisLivres();
    }
  }

  @FXML
  private void animaisReservadosAbaMudada() {
    if (animaisAbaReservados.isSelected()) {
      abaAnimais = AbaAnimais.Reservados;
      inicializarAbaAnimaisReservados();
    }
  }

  // -- Produtos --

  @FXML
  private void produtosAbaMudada() {
    if (produtosAba.isSelected()) {
      abaPrincipal = AbaPrincipal.Produtos;
      atualizarAbaProdutos();
    }
  }

  private void atualizarAbaProdutosComSelecao() {
    var lista = listaProduto();
    var index = lista.getSelectionModel().getSelectedIndex();
    atualizarAbaProdutos();
    lista.getSelectionModel().select(index);
    selecionarProduto();
  }

  private void atualizarAbaProdutos() {
    switch (abaProdutos) {
      case Comida:
        inicializarAbaComida();
        break;

      case Brinquedos:
        inicializarAbaBrinquedos();
        break;

      case Remedios:
        inicializarAbaRemedios();
        break;
    }
  }

  private void limparInfoProdutos() {
    if (produtosInfoCategoria != null) {
      produtosInfoCategoria.clear();
      produtosInfoNome.clear();
      produtosInfoArea.clear();
      produtosInfoPeso.clear();
      produtosInfoCompra.clear();
      produtosInfoVenda.clear();
    }
  }

  private void atualizarListaProdutos(Categoria categoria, ListView<String> lista) {
    produtoSelecionado = null;

    var produtos = dadosProdutos.registro;
    idsProdutos.clear();
    lista.getItems().clear();
    for (var id : produtos.keySet()) {
      var registro = produtos.get(id);
      IProduto p = registro.produto;
      if (p.categoria() == categoria && pesquisaProdutos.seAplica(p)) {
        idsProdutos.add(id);
        var preco = p.precoPorUnidadeVenda() * registro.desconto;
        lista.getItems().add(String.format("%s (R$ %.2f)", p.nome(), preco));
      }
    }
  }

  private void inicializarAbaComida() {
    limparInfoProdutos();
    atualizarListaProdutos(Categoria.Comida, produtosListaComida);
  }

  private void inicializarAbaBrinquedos() {
    limparInfoProdutos();
    atualizarListaProdutos(Categoria.Brinquedo, produtosListaBrinquedos);
  }

  private void inicializarAbaRemedios() {
    limparInfoProdutos();
    atualizarListaProdutos(Categoria.Remedio, produtosListaRemedios);
  }

  private ListView<String> listaProduto() {
    switch (abaProdutos) {
      case Comida:
        return produtosListaComida;

      case Brinquedos:
        return produtosListaBrinquedos;

      case Remedios:
        return produtosListaRemedios;

      default:
        return null;
    }
  }

  @FXML
  private void selecionarProduto() {
    var lista = listaProduto();
    int index = lista.getSelectionModel().getSelectedIndex();
    if (index >= 0) {
      produtoSelecionado = idsProdutos.get(index);
      var registro = dadosProdutos.registro.get(produtoSelecionado);
      var p = registro.produto;
      var venda = p.precoPorUnidadeVenda();
      var vendaDesconto = venda * registro.desconto;
      Long estoque = registro.quantidade;
      produtosInfoCategoria.setText(p.categoria().toString());
      produtosInfoNome.setText(p.nome());
      produtosInfoArea.setText(String.format("%.2fm²", p.areaPorUnidade()));
      produtosInfoPeso.setText(String.format("%.2fkg", p.pesoPorUnidade()));
      produtosInfoCompra.setText(String.format("R$ %.2f", p.precoPorUnidadeCompra()));
      produtosInfoVenda.setText(String.format("R$ %.2f (%.2f)", vendaDesconto, venda));
      produtosInfoEstoque.setText(estoque.toString());
    }
  }

  @FXML
  private void produtosPesquisar() {
    pesquisaProdutos = new Pesquisa.Produto(produtosPesquisa.getText());
    atualizarAbaProdutos();
  }

  @FXML
  private void registrarProduto() {
    var janela = novaJanela("gui/registrarProduto.fxml");
    if (janela != null) {
      RegistrarProduto.stage = janela;
      janela.setTitle("Registrar Produto");
      janela.show();
    }
  }

  public void registrarProduto(DadosProduto produto) {
    mostrarErro(() -> {
      dadosProdutos.registrar(produto);
      dadosProdutos.salvar(ARQUIVO_PRODUTOS);
      atualizarAbaProdutosComSelecao();
    });
  }

  @FXML
  private void comprarProduto() {
    if (produtoSelecionado != null) {
      GuiPrincipal.quantidade("Comprar Produto", quantidade -> {
        mostrarErro(() -> {
          var produto = dadosProdutos.getProduto(produtoSelecionado);
          var desconto = dadosProdutos.getDesconto(produtoSelecionado);
          dadosProdutos.comprarProduto(produtoSelecionado, quantidade);
          dadosHistorico.registrarCompra(produto,desconto,quantidade,empregadoLogado);
          dadosProdutos.salvar(ARQUIVO_PRODUTOS);
          dadosHistorico.salvar(ARQUIVO_HISTORICO);
          atualizarAbaProdutosComSelecao();
        });
      });
    }
  }

  @FXML
  private void venderProduto() {
    if (produtoSelecionado != null) {
      GuiPrincipal.quantidade("Vender Produto", quantidade -> {
        mostrarErro(() -> {
          var produto = dadosProdutos.getProduto(produtoSelecionado);
          var desconto = dadosProdutos.getDesconto(produtoSelecionado);
          dadosProdutos.venderProduto(produtoSelecionado, quantidade);
          dadosHistorico.registrarVenda(produto,desconto,quantidade,empregadoLogado);
          dadosProdutos.salvar(ARQUIVO_PRODUTOS);
          dadosHistorico.salvar(ARQUIVO_HISTORICO);
          atualizarAbaProdutosComSelecao();
        });
      });
    }
  }

  @FXML
  private void descontarProduto() {
    if (produtoSelecionado != null) {
      var janela = novaJanela("gui/desconto.fxml");
      if (janela != null) {
        janela.setTitle("Descontar Produto");
        Desconto.stage = janela;
        Desconto.depois = desconto -> {
          mostrarErro(() -> {
            dadosProdutos.setDesconto(produtoSelecionado, (1 - desconto/100));
            dadosProdutos.salvar(ARQUIVO_PRODUTOS);
            atualizarAbaProdutosComSelecao();
          });
        };
        janela.show();
      }
    }
  }

  @FXML
  private void abaComidaMudada() {
    if (produtosAbaComida.isSelected()) {
      abaProdutos = AbaProdutos.Comida;
      inicializarAbaComida();
    }
  }

  @FXML
  private void abaBrinquedosMudada() {
    if (produtosAbaBrinquedos.isSelected()) {
      abaProdutos = AbaProdutos.Brinquedos;
      inicializarAbaBrinquedos();
    }
  }

  @FXML
  private void abaRemediosMudada() {
    if (produtosAbaRemedios.isSelected()) {
      abaProdutos = AbaProdutos.Remedios;
      inicializarAbaRemedios();
    }
  }

  @FXML
  private void checarCriterios() {
    if (produtoSelecionado != null) {
      var janela = novaJanela("gui/criterios.fxml");
      if (janela != null) {
        var criterio = dadosProdutos.getProduto(produtoSelecionado).criteriosDeUso();
        Criterios.stage = janela;
        janela.setOnShown(event -> {
          Criterios.instancia.depoisDeMostrado(criterio);
        });
        janela.setTitle("Criterios de Uso");
        janela.show();
      }
    }
  }

  // -- Dinheiro --

  @FXML
  private void dinheiroAbaMudada() {
    if (dinheiroAba.isSelected()) {
      abaPrincipal = AbaPrincipal.Dinheiro;
      atualizarAbaDinheiro();
    }
  }

  private void atualizarAbaDinheiro() {
    dinheiroInfoDinheiro.setText(String.format("%.2f", dadosProdutos.dinheiro()));
    dinheiroInfoDebito.setText(String.format("%.2f", dadosProdutos.debito()));
  }

  private void quantidadeDinheiro(String titulo, DoubleConsumer depois) {
    var janela = novaJanela("gui/dinheiro.fxml");
    Dinheiro.stage = janela;
    Dinheiro.depois = depois;
    janela.setTitle(titulo);
    janela.show();
  }

  @FXML
  private void adicionarSemDebito() {
    operacaoAdmin(() -> {
      quantidadeDinheiro("Adicionar com Debito", dinheiro -> {
        mostrarErro(() -> {
          dadosProdutos.adicionarDinheiroSemDebito(dinheiro);
          dadosProdutos.salvar(ARQUIVO_PRODUTOS);
          atualizarAbaDinheiro();
        });
      });
    });
  }

  @FXML
  private void adicionarComDebito() {
    operacaoAdmin(() -> {
      quantidadeDinheiro("Adicionar com Debito", dinheiro -> {
        mostrarErro(() -> {
          dadosProdutos.adicionarDinheiroComDebito(dinheiro);
          dadosProdutos.salvar(ARQUIVO_PRODUTOS);
          atualizarAbaDinheiro();
        });
      });
    });
  }

  @FXML
  private void removerDinheiro() {
    operacaoAdmin(() -> {
      quantidadeDinheiro("Remover Dinheiro", dinheiro -> {
        mostrarErro(() -> {
          dadosProdutos.removerDinheiro(dinheiro);
          dadosProdutos.salvar(ARQUIVO_PRODUTOS);
          atualizarAbaDinheiro();
        });
      });
    });
  }

  @FXML
  private void pagarDebito() {
    operacaoAdmin(() -> {
      quantidadeDinheiro("Pagar Debito", dinheiro -> {
        mostrarErro(() -> {
          dadosProdutos.pagarDebito(dinheiro);
          dadosProdutos.salvar(ARQUIVO_PRODUTOS);
          atualizarAbaDinheiro();
        });
      });
    });
  }

  // -- Empregados --

  @FXML
  private void empregadosAbaMudada() {
    if (empregadosAba.isSelected()) {
      abaPrincipal = AbaPrincipal.Empregados;
      atualizarAbaEmpregados();
    }
  }

  private void limparInfoEmpregados() {
    if (empregadosInfoApelido != null) {
      empregadosInfoApelido.clear();
      empregadosInfoNome.clear();
      empregadosInfoEmail.clear();
      empregadosInfoPrivilegios.clear();
    }
  }

  private Empregado empregadoSelecionado() {
    if (empregadoSelecionado == null) {
      return null;
    } else {
      try {
        return dadosEmpregados.obterEmpregado(empregadoSelecionado);
      } catch (Exception e) {
        return null;
      }
    }
  }

  private void atualizarAbaEmpregados() {
    empregadoSelecionado = null;

    var lista = empregadosLista;
    var empregados = dadosEmpregados.empregadosPorId;

    idsEmpregados.clear();
    lista.getItems().clear();
    for (var id : empregados.keySet()) {
      Empregado e = empregados.get(id);
      if (pesquisaEmpregados.seAplica(e)) {
        idsEmpregados.add(id);
        if (e.privilegios() == Privilegios.Admin) {
          lista.getItems().add(String.format("%s (admin)", e.nome()));
        } else {
          lista.getItems().add(String.format("%s", e.nome()));
        }
      }
    }
  }

  private void atualizarAbaEmpregadosComSelecao() {
    var lista = empregadosLista;
    var index = lista.getSelectionModel().getSelectedIndex();
    atualizarAbaEmpregados();
    lista.getSelectionModel().select(index);
    selecionarEmpregado();
  }

  @FXML
  private void empregadosPesquisar() {
    pesquisaEmpregados = new Pesquisa.Empregado(empregadosPesquisa.getText());
    atualizarAbaEmpregados();
  }

  @FXML
  private void registrarEmpregado() {
    operacaoAdmin(() -> {
      var janela = novaJanela("gui/registrarFuncionario.fxml");
      if (janela != null) {
        RegistrarFuncionario.stage = janela;
        RegistrarFuncionario.empregados = dadosEmpregados;
        RegistrarFuncionario.depois = (empregado, senha) -> {
          mostrarErro(() -> {
            dadosEmpregados.registrarEmpregado(empregado, senha);
            dadosEmpregados.salvar(ARQUIVO_EMPREGADOS);
            atualizarAbaEmpregados();
          });
        };
        janela.setTitle("Registrar Empregado");
        janela.show();
      }
    });
  }

  @FXML
  private void selecionarEmpregado() {
    var lista = empregadosLista;
    int index = lista.getSelectionModel().getSelectedIndex();
    if (index >= 0) {
      empregadoSelecionado = idsEmpregados.get(index);
      var e = dadosEmpregados.empregadosPorId.get(empregadoSelecionado);
      empregadosInfoApelido.setText(e.apelido());
      empregadosInfoNome.setText(e.nome());
      empregadosInfoEmail.setText(e.email());
      empregadosInfoPrivilegios.setText(e.privilegios().toString());
    }
  }

  @FXML
  private void registrarAdminInicial() {
    mostrarErro(() -> {
      var janela = novaJanela("gui/registrarFuncionario.fxml");
      if (janela != null) {
        RegistrarFuncionario.stage = janela;
        RegistrarFuncionario.empregados = dadosEmpregados;
        RegistrarFuncionario.adminObrigatorio = true;
        RegistrarFuncionario.depois = (empregado, senha) -> {
          mostrarErro(() -> {
            dadosEmpregados.registrarEmpregado(empregado, senha);
            dadosEmpregados.salvar(ARQUIVO_EMPREGADOS);
            empregadoLogado = empregado;
          });
        };
        janela.setOnCloseRequest(event -> {
          event.consume();
          janela.show();
        });
        janela.setTitle("Registrar Admin");
        janela.show();
      } else {
        throw new Exception("Janela de Sign Up não pode ser criada");
      }
    });
  }

  @FXML
  private void login() {
    login(true);
  }

  private void login(boolean permitirCancelar) {
    var janela = novaJanela("gui/login.fxml");
    if (janela != null) {
      janela.setOnCloseRequest(event -> {
        if (!permitirCancelar) {
          event.consume();
          janela.show();
        }
      });
      Login.stage = janela;
      Login.empregados = dadosEmpregados;
      Login.permitirCancelar = permitirCancelar;
      Login.depois = empregado -> {
        empregadoLogado = empregado;
      };
      janela.setTitle("Login");
      janela.show();
    }
  }

  private boolean empregadoLogadoIgualAoSelecionado() {
    if (empregadoSelecionado == null) {
      return false;
    } else {
      var a1 = empregadoSelecionado().apelido();
      var a2 = empregadoLogado.apelido();
      return a1.equals(a2);
    }
  }

  @FXML
  private void atualizarEmpregado() {
    if (empregadoLogadoIgualAoSelecionado()) {
      _atualizarEmpregado();
    } else {
      operacaoAdmin(() -> {
        _atualizarEmpregado();
      });
    }
  }

  private void _atualizarEmpregado() {
    var janela = novaJanela("gui/atualizarFuncionario.fxml");
    if (janela != null) {
      var es = empregadoSelecionado();
      janela.setOnShown(event -> {
        AtualizarFuncionario.instancia.depoisDeMostrado(es);
      });
      AtualizarFuncionario.stage = janela;
      AtualizarFuncionario.empregados = dadosEmpregados;
      AtualizarFuncionario.apelido = es.apelido();
      AtualizarFuncionario.priv = es.privilegios();
      AtualizarFuncionario.depois = (empregado, senha) -> {
        mostrarErro(() -> {
          dadosEmpregados.atualizarEmpregado(empregado, senha);
          dadosEmpregados.salvar(ARQUIVO_EMPREGADOS);
          atualizarAbaEmpregados();
        });
      };
      janela.setTitle("Registrar Empregado");
      janela.show();
    }
  }

  @FXML
  private void removerEmpregado() {
    operacaoAdmin(() -> {
      if (empregadoSelecionado != null) {
        mostrarErro(() -> {
          if (empregadoLogadoIgualAoSelecionado()) {
            throw new Exception("Funcionari@ não pode remover a si mesm@");
          }
          dadosEmpregados.removerEmpregado(empregadoSelecionado);
          atualizarAbaEmpregados();
        });
      }
    });
  }

  // -- Historico --

  private void atualizarAbaHistorico() {
    switch (abaHistorico) {
      case Animais:
        inicializarAbaHistoricoAnimais();
        break;

      case Produtos:
        inicializarAbaHistoricoProdutos();
        break;
    }
  }

  private void inicializarAbaHistoricoAnimais() {
    limparInfoHistorico();
    atualizarListaHistorico(historicoListaAnimais,
        RegistroTipo.Reserva, RegistroTipo.Adocao);
  }

  private void inicializarAbaHistoricoProdutos() {
    limparInfoHistorico();
    atualizarListaHistorico(historicoListaProdutos,
        RegistroTipo.Compra, RegistroTipo.Venda);
  }

  private void limparInfoHistorico() {
    if (historicoInfoData != null) {
      historicoInfoData.clear();
      historicoInfoId.clear();
      historicoInfoEmpregado.clear();
    }
  }

  private void atualizarListaHistorico(ListView<String> lista, RegistroTipo... tipos) {
    var registros = dadosHistorico.registros;
    var tipoList = new ArrayList<>(Arrays.asList(tipos));
    historicoSelecionado = null;
    idsHistorico.clear();
    lista.getItems().clear();
    for (var id : registros.keySet()) {
      var registro = registros.get(id);
      if (tipoList.contains(registro.tipo()) && pesquisaHistorico.seAplica(registro)) {
        idsHistorico.add(id);
        lista.getItems().add(registro.toString());
      }
    }
  }

  @FXML
  private void historicoAbaMudada() {
    if (historicoAba.isSelected()) {
      abaPrincipal = AbaPrincipal.Historico;
      atualizarAbaHistorico();
    }
  }

  @FXML
  private void abaHistoricoAnimaisMudada() {
    if (historicoAbaAnimais.isSelected()) {
      abaHistorico = AbaHistorico.Animais;
      inicializarAbaHistoricoAnimais();
    }
  }

  @FXML
  private void abaHistoricoProdutosMudada() {
    if (historicoAbaProdutos.isSelected()) {
      abaHistorico = AbaHistorico.Produtos;
      inicializarAbaHistoricoProdutos();
    }
  }

  @FXML
  private void historicoPesquisar() {
    pesquisaHistorico = new Pesquisa.Historico(historicoPesquisa.getText());
    atualizarAbaHistorico();
  }

  @FXML
  private void removerHistorico() {
    if (historicoSelecionado != null) {
      operacaoAdmin(() -> {
        mostrarErro(() -> {
          dadosHistorico.removerRegistro(historicoSelecionado);
          dadosHistorico.salvar(ARQUIVO_HISTORICO);
          atualizarAbaHistorico();
        });
      });
    }
  }

  private ListView<String> listaHistorico() {
    switch (abaHistorico) {
      case Animais:
        return historicoListaAnimais;

      case Produtos:
        return historicoListaProdutos;

      default:
        return null;
    }
  }

  @FXML
  private void selecionarHistorico() {
    var lista = listaHistorico();
    int index = lista.getSelectionModel().getSelectedIndex();
    if (index >= 0) {
      historicoSelecionado = idsHistorico.get(index);
      var registro = dadosHistorico.getRegistro(historicoSelecionado);
      historicoInfoData.setText(registro.data().toString());
      historicoInfoId.setText(historicoSelecionado.toString());
      historicoInfoEmpregado.setText(registro.empregado().nome());
    }
  }

  private void maisInfoAnimal() {
    var registro = dadosHistorico.getRegistroAnimal(historicoSelecionado);
    var janela = novaJanela("gui/infoAnimal.fxml");
    if (janela != null) {
      InfoAnimal.stage = janela;
      janela.setOnShown(event -> {
        InfoAnimal.instancia.depoisDeMostrado(registro);
      });
      janela.setTitle("Informação de Adoção");
      janela.show();
    }
  }

  private void maisInfoProduto() {
    var registro = dadosHistorico.getRegistroProduto(historicoSelecionado);
    var janela = novaJanela("gui/infoProduto.fxml");
    if (janela != null) {
      InfoProduto.stage = janela;
      janela.setOnShown(event -> {
        InfoProduto.instancia.depoisDeMostrado(registro);
      });
      janela.setTitle("Informação de Compra/Venda");
      janela.show();
    }
  }

  @FXML
  private void maisInfo() {
    if (historicoSelecionado != null) {
      switch (abaHistorico) {
        case Animais:
          maisInfoAnimal();
          break;

        case Produtos:
          maisInfoProduto();
          break;
      }
    }
  }
}


