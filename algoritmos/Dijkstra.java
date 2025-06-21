package algoritmos;

import java.util.*;
import lib.Grafo;
import lib.Vertice;
import lib.Aresta;

/**
 * Implementação do algoritmo de Dijkstra que encontra os caminhos mais curtos
 * a partir de uma única origem em um grafo ponderado.
 * Esta classe é desacoplada da estrutura do grafo, mantendo seu próprio estado.
 *
 * @param <T> O tipo de dado armazenado nos vértices.
 */
public class Dijkstra<T> {

    // Sugestão 3: Os resultados são armazenados como atributos da classe
    private Map<Vertice<T>, Double> distancias;
    private Map<Vertice<T>, Vertice<T>> anteriores;
    private Set<Vertice<T>> verticesProcessados;

    /**
     * Executa o algoritmo de Dijkstra a partir de um vértice de origem.
     * Após a execução, os resultados podem ser consultados através de outros métodos.
     *
     * @param grafo O grafo no qual o algoritmo será executado.
     * @param dadoOrigem O dado do vértice de origem.
     */
    public void executar(Grafo<T> grafo, T dadoOrigem) {
        Vertice<T> origem = grafo.getVertice(dadoOrigem);

        if (origem == null) {
            throw new IllegalArgumentException("Erro: Vértice de origem '" + dadoOrigem + "' não encontrado no grafo.");
        }

        // Inicializa as estruturas de dados para armazenar o estado do algoritmo
        distancias = new HashMap<>();
        anteriores = new HashMap<>();
        verticesProcessados = new HashSet<>();

        // 1. Inicialização
        for (Vertice<T> vertice : grafo.getVertices()) {
            distancias.put(vertice, Double.POSITIVE_INFINITY);
            anteriores.put(vertice, null);
        }
        distancias.put(origem, 0.0); // Sugestão 4: Correção do nome da variável

        // 2. Fila de prioridade com o comparador correto (Sugestão 1)
        PriorityQueue<Vertice<T>> filaPrioridade = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));
        filaPrioridade.add(origem);

        // 3. Loop principal do algoritmo
        while (!filaPrioridade.isEmpty()) {
            Vertice<T> u = filaPrioridade.poll();

            // Otimização: se já processamos este nó com um caminho menor, ignoramos.
            if (verticesProcessados.contains(u)) {
                continue;
            }
            verticesProcessados.add(u);

            // 4. Relaxamento das arestas (usando os mapas - Sugestão 2)
            for (Aresta<T> aresta : u.getArestasSaida()) {
                Vertice<T> v = aresta.getFim();
                double pesoAresta = aresta.getPeso();
                double distanciaAtualV = distancias.get(v);
                double distanciaAtravesU = distancias.get(u) + pesoAresta;

                if (distanciaAtravesU < distanciaAtualV) {
                    // Atualiza a distância e o predecessor
                    distancias.put(v, distanciaAtravesU);
                    anteriores.put(v, u);
                    
                    // Adiciona o vizinho à fila para ser processado.
                    // A fila pode conter duplicatas, mas o `poll` sempre pegará a menor distância.
                    filaPrioridade.add(v);
                }
            }
        }
    }

    /**
     * Retorna o caminho mais curto da origem (definida em executar()) até um vértice alvo.
     *
     * @param alvo O vértice de destino.
     * @return Uma lista de vértices representando o caminho, ou uma lista vazia se não houver caminho.
     */
    public List<Vertice<T>> getMenorCaminho(Vertice<T> alvo) {
        if (anteriores == null) {
            throw new IllegalStateException("O algoritmo de Dijkstra precisa ser executado primeiro.");
        }
        
        List<Vertice<T>> caminho = new ArrayList<>();
        // Usa o mapa 'anteriores' para reconstruir o caminho
        for (Vertice<T> vertice = alvo; vertice != null; vertice = anteriores.get(vertice)) {
            caminho.add(vertice);
        }
        Collections.reverse(caminho);
        
        // Se o caminho não começa na origem, significa que o alvo é inalcançável
        if (!caminho.isEmpty() && !caminho.get(0).getDado().equals(getOrigemDoCaminho(alvo).getDado())) {
            return Collections.emptyList();
        }
        
        return caminho;
    }
    
    /**
     * Retorna a distância mínima da origem até um vértice alvo.
     */
    public double getDistancia(Vertice<T> alvo){
        if(distancias == null){
             throw new IllegalStateException("O algoritmo de Dijkstra precisa ser executado primeiro.");
        }
        return distancias.getOrDefault(alvo, Double.POSITIVE_INFINITY);
    }
    
    // Método auxiliar privado para encontrar a origem real do caminho reconstruído
    private Vertice<T> getOrigemDoCaminho(Vertice<T> alvo) {
        Vertice<T> v = alvo;
        while(anteriores.get(v) != null) {
            v = anteriores.get(v);
        }
        return v;
    }
}