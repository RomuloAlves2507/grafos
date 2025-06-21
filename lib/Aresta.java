package lib;

import java.util.Objects;

/**
 * Representa uma aresta direcionada e ponderada em um grafo.
 * Esta classe é imutável, o que significa que seu estado (origem, fim, peso)
 * não pode ser alterado após a criação, tornando a estrutura do grafo mais segura e previsível.
 *
 * @param <T> O tipo de dado armazenado nos vértices.
 */
public final class Aresta<T> { // A classe pode ser 'final' para impedir herança

    // Sugestão 1 e 4: Atributos 'final' para imutabilidade e 'double' primitivo
    private final double peso;
    private final Vertice<T> inicio;
    private final Vertice<T> fim;

    /**
     * Construtor para uma aresta com peso definido.
     * @param peso O peso da aresta.
     * @param inicio O vértice de origem.
     * @param fim O vértice de destino.
     */
    public Aresta(double peso, Vertice<T> inicio, Vertice<T> fim) {
        // É uma boa prática verificar se os parâmetros não são nulos
        this.inicio = Objects.requireNonNull(inicio, "O vértice de início não pode ser nulo.");
        this.fim = Objects.requireNonNull(fim, "O vértice de fim não pode ser nulo.");
        this.peso = peso;
    }

    /**
     * Construtor para uma aresta não ponderada (ou com peso padrão 1.0).
     * @param inicio O vértice de origem.
     * @param fim O vértice de destino.
     */
    public Aresta(Vertice<T> inicio, Vertice<T> fim) {
        // Chama o outro construtor, evitando repetição de código
        this(1.0, inicio, fim);
    }

    // Apenas métodos 'get', sem métodos 'set'
    public double getPeso() {
        return peso;
    }

    public Vertice<T> getInicio() {
        return inicio;
    }

    public Vertice<T> getFim() {
        return fim;
    }

    // Sugestão 3: Um método toString() útil para depuração
    @Override
    public String toString() {
        return String.format("%s -> %s (Peso: %.2f)", inicio.getDado(), fim.getDado(), peso);
    }

    // Sugestão 2: Implementação correta de equals() e hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aresta<?> aresta = (Aresta<?>) o;
        return Double.compare(aresta.peso, peso) == 0 &&
               Objects.equals(inicio, aresta.inicio) &&
               Objects.equals(fim, aresta.fim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(peso, inicio, fim);
    }
}