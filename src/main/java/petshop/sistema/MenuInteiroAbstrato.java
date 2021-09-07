package petshop.sistema;
import java.util.Scanner;
import java.util.Set;

abstract class ConjuntoAbstratoInteiros<T> {
  public abstract boolean contem(T valor);
}

class MinMaxInteiros<T extends Comparable<T>> extends ConjuntoAbstratoInteiros<T> {
  T minimo;
  T maximo;

  MinMaxInteiros(T minimo, T maximo) {
    this.minimo = minimo;
    this.maximo = maximo;
  }

  @Override
  public boolean contem(T valor) {
    return valor.compareTo(minimo) >= 0 && valor.compareTo(maximo) <= 0;
  }
}

class ConjuntoInteiros<T> extends ConjuntoAbstratoInteiros<T> {
  Set<T> conjunto;

  ConjuntoInteiros(Set<T> conjunto) {
    this.conjunto = conjunto;
  }

  @Override
  public boolean contem(T valor) {
    return conjunto.contains(valor);
  }
}

public abstract class MenuInteiroAbstrato<T extends Number & Comparable<T>> extends Menu<T> {
  ConjuntoAbstratoInteiros<T> conjunto;

  public MenuInteiroAbstrato(String titulo, T minimo, T maximo) {
    super(titulo);
    this.conjunto = new MinMaxInteiros<T>(minimo, maximo);
  }

  public MenuInteiroAbstrato(String titulo, Set<T> conjunto) {
    super(titulo);
    this.conjunto = new ConjuntoInteiros<T>(conjunto);
  }

  @Override
  public void mostrar() {
    System.out.println("# digite um numero inteiro                       #");
    System.out.println("##################################################");
  }

  @Override
  public boolean resultadoValido(T resultado) {
    return conjunto.contem(resultado);
  }
}

