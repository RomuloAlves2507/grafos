package lib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Classe que representa um grafo direcionado genérico.
 * Utiliza um Map para armazenamento e busca eficiente de vértices, garantindo
 * performance e a unicidade dos vértices.
 *
 * @param <T> O tipo de dado a ser armazenado em cada vértice.
 */
public class Grafo<T> {

    // Sugestão 1 e 3: Usar a interface Map para performance O(1) e flexibilidade.
    // O 'final' garante que a coleção em si não será trocada após a criação do objeto.
    private final Map<T, Vertice<T>> vertices;
    private final List<Aresta<T>> arestas;

    public Grafo() {
        this.vertices = new HashMap<>();
        this.arestas = new ArrayList<>();
    }

    /**
     * Adiciona um novo vértice ao grafo, se o dado ainda não existir.
     * @param dado O dado a ser armazenado no novo vértice.
     * @return O vértice recém-criado ou o existente se o dado já estiver no grafo.
     */
    public Vertice<T> adicionarVertice(T dado) {
        // computeIfAbsent é uma forma moderna e segura de adicionar um valor se a chave não existir.
        // O segundo argumento é uma função que cria um novo Vertice se necessário.
        return this.vertices.computeIfAbsent(dado, Vertice::new);
    }

    /**
     * Adiciona uma aresta direcionada entre dois vértices existentes no grafo.
     * @param peso O peso da aresta.
     * @param dadoInicio O dado do vértice de origem.
     * @param dadoFim O dado do vértice de destino.
     * @throws IllegalArgumentException se o vértice de início ou fim não for encontrado.
     */
    public void adicionarAresta(double peso, T dadoInicio, T dadoFim) {
        // A busca aqui é O(1) graças ao Map
        Vertice<T> inicio = getVertice(dadoInicio);
        Vertice<T> fim = getVertice(dadoFim);

        // Sugestão 2: Verificação de nulidade para evitar NullPointerException.
        if (inicio == null) {
            throw new IllegalArgumentException("Vértice de início com dado '" + dadoInicio + "' não encontrado.");
        }
        if (fim == null) {
            throw new IllegalArgumentException("Vértice de fim com dado '" + dadoFim + "' não encontrado.");
        }

        // Supondo que a classe Aresta seja imutável, como recomendado
        Aresta<T> aresta = new Aresta<>(peso, inicio, fim);
        inicio.adicionarArestaSaida(aresta);
        fim.adicionarArestaEntrada(aresta);
        this.arestas.add(aresta);
    }

    /**
     * Retorna um vértice com base no seu dado. A busca é muito rápida (O(1)).
     * @param dado O dado do vértice a ser procurado.
     * @return O objeto Vertice ou null se não for encontrado.
     */
    public Vertice<T> getVertice(T dado) {
        return this.vertices.get(dado);
    }

    /**
     * Retorna uma coleção não modificável de todos os vértices do grafo.
     * Isso protege a lista interna contra modificações externas.
     * @return Uma coleção de vértices.
     */
    public Collection<Vertice<T>> getVertices() {
        return Collections.unmodifiableCollection(this.vertices.values());
    }
    
    /**
     * Retorna uma lista não modificável de todas as arestas do grafo.
     * @return Uma lista de arestas.
     */
    public List<Aresta<T>> getArestas() {
        return Collections.unmodifiableList(this.arestas);
    }
}