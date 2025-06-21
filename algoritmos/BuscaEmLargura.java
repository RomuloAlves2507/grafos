package algoritmos;

import lib.Grafo;
import lib.Vertice;
import lib.Aresta;

import java.util.*;

/**
 * Implementação do algoritmo de Busca em Largura (BFS).
 * Utiliza estruturas de dados eficientes (Queue e Set) para garantir
 * a complexidade de tempo O(V + E).
 *
 * @param <T> O tipo de dado armazenado nos vértices.
 */
public class BuscaEmLargura<T> {

    /**
     * Encontra o caminho mais curto (em número de arestas) entre um vértice de origem e um de destino.
     *
     * @param grafo O grafo a ser percorrido.
     * @param dadoOrigem O dado do vértice de início da busca.
     * @param dadoDestino O dado do vértice de destino.
     * @return Uma lista de vértices representando o caminho. Retorna uma lista vazia se não houver caminho.
     */
    public List<Vertice<T>> encontrarCaminho(Grafo<T> grafo, T dadoOrigem, T dadoDestino) {
        Vertice<T> origem = grafo.getVertice(dadoOrigem);
        Vertice<T> destino = grafo.getVertice(dadoDestino);

        if (origem == null || destino == null) {
            throw new IllegalArgumentException("Vértice de origem ou destino não encontrado no grafo.");
        }

        // Estruturas de dados eficientes
        Queue<Vertice<T>> fila = new LinkedList<>();
        Set<Vertice<T>> visitados = new HashSet<>();
        Map<Vertice<T>, Vertice<T>> predecessores = new HashMap<>();

        // Configuração inicial
        fila.add(origem);
        visitados.add(origem);
        predecessores.put(origem, null); // A origem não tem predecessor

        while (!fila.isEmpty()) {
            Vertice<T> atual = fila.poll(); // O(1) para remover da fila

            // Se encontramos o destino, podemos parar e reconstruir o caminho
            if (atual.equals(destino)) {
                return reconstruirCaminho(predecessores, destino);
            }

            for (Aresta<T> aresta : atual.getArestasSaida()) {
                Vertice<T> vizinho = aresta.getFim();
                
                // O(1) para verificar se já foi visitado
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    predecessores.put(vizinho, atual);
                    fila.add(vizinho); // O(1) para adicionar na fila
                }
            }
        }
        
        // Se o loop terminar e não encontrarmos o destino, não há caminho
        return Collections.emptyList();
    }

    /**
     * Método auxiliar para reconstruir o caminho a partir do mapa de predecessores.
     */
    private List<Vertice<T>> reconstruirCaminho(Map<Vertice<T>, Vertice<T>> predecessores, Vertice<T> destino) {
        LinkedList<Vertice<T>> caminho = new LinkedList<>();
        for (Vertice<T> v = destino; v != null; v = predecessores.get(v)) {
            caminho.addFirst(v);
        }
        return caminho;
    }

    /**
     * Realiza um percurso em largura a partir de um nó de origem e retorna a ordem de visitação.
     *
     * @param grafo O grafo a ser percorrido.
     * @param dadoOrigem O dado do vértice de início.
     * @return Uma lista de vértices na ordem em que foram visitados.
     */
    public List<Vertice<T>> percurso(Grafo<T> grafo, T dadoOrigem) {
        Vertice<T> origem = grafo.getVertice(dadoOrigem);
        if (origem == null) {
            throw new IllegalArgumentException("Vértice de origem não encontrado.");
        }

        Queue<Vertice<T>> fila = new LinkedList<>();
        Set<Vertice<T>> visitados = new HashSet<>();
        List<Vertice<T>> ordemDeVisita = new ArrayList<>();

        fila.add(origem);
        visitados.add(origem);

        while (!fila.isEmpty()) {
            Vertice<T> atual = fila.poll();
            ordemDeVisita.add(atual); // Adiciona à lista de resultado

            for (Aresta<T> aresta : atual.getArestasSaida()) {
                Vertice<T> vizinho = aresta.getFim();
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    fila.add(vizinho);
                }
            }
        }
        return ordemDeVisita;
    }
}