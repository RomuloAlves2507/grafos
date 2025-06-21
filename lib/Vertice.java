package lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Representa um vértice (ou nó) em um grafo direcionado.
 * Esta classe é "pura" e contém apenas a estrutura do vértice e suas conexões.
 * O estado de algoritmos (como distâncias) deve ser mantido externamente.
 *
 * @param <T> O tipo de dado armazenado no vértice.
 */
public class Vertice<T> {

    private T dado;
    // Sugestão 4: Usar a interface List
    private final List<Aresta<T>> arestasEntrada;
    private final List<Aresta<T>> arestasSaida;

    public Vertice(T valor) {
        this.dado = Objects.requireNonNull(valor, "O dado do vértice não pode ser nulo.");
        // Inicializa com ArrayList, usando o operador diamante <>
        this.arestasEntrada = new ArrayList<>();
        this.arestasSaida = new ArrayList<>();
    }

    public T getDado() {
        return dado;
    }

    public void setDado(T dado) {
        this.dado = Objects.requireNonNull(dado, "O dado do vértice não pode ser nulo.");
    }

    // Estes métodos são geralmente chamados apenas pela classe Grafo
    public void adicionarArestaEntrada(Aresta<T> aresta) {
        this.arestasEntrada.add(aresta);
    }

    public void adicionarArestaSaida(Aresta<T> aresta) {
        this.arestasSaida.add(aresta);
    }

    // Sugestão 2: Retornar uma lista não modificável para proteger o encapsulamento
    public List<Aresta<T>> getArestasEntrada() {
        return Collections.unmodifiableList(arestasEntrada);
    }


    public List<Aresta<T>> getArestasSaida() {
        return Collections.unmodifiableList(arestasSaida);
    }

    /**
     * Retorna uma lista com os vértices vizinhos diretos (destinos das arestas de saída).
     * @return Uma lista de vértices.
     */
    public List<Vertice<T>> getVizinhos() {
        // Sugestão 3: Método claro que retorna os vértices vizinhos
        // Supondo que a classe Aresta tenha o método getFim()
        return this.arestasSaida.stream()
                                .map(Aresta::getFim)
                                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Vertice{dado=" + dado + '}';
    }
    
    // É uma boa prática implementar equals e hashCode com base no dado único do vértice
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice<?> vertice = (Vertice<?>) o;
        return dado.equals(vertice.dado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dado);
    }
}