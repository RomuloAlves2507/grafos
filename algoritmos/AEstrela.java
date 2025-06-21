package algoritmos;


import lib.Aresta;
import lib.Grafo;
import lib.Vertice;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.BiFunction;

public class AEstrela<T> {
    private BiFunction<Vertice<T>, Vertice<T>, Double> heuristica;

    public AEstrela(BiFunction<Vertice<T>, Vertice<T>, Double> heuristica) {
        this.heuristica = heuristica;
    }

    public List<Vertice<T>> encontrarCaminhoMaisCurto(Grafo<T> grafo, Vertice<T> inicio, Vertice<T> objetivo) {
        // gScore: Custo do inicio até o nó atual
        Map<Vertice<T>, Double> gScore = new HashMap<>();
        gScore.put(inicio, 0.0);

        // fScore: gScore + heuristica. É o custo estimado do inicio ao objetivo através do nó atual.
        Map<Vertice<T>, Double> fScore = new HashMap<>();
        // Para o nó inicial, fScore é a heurística (gScore é 0)
        fScore.put(inicio, heuristica(inicio, objetivo));

        // cameFrom: Para reconstruir o caminho
        Map<Vertice<T>, Vertice<T>> cameFrom = new HashMap<>();

        // openSet: Conjunto de nós a serem avaliados, ordenados pelo fScore
        PriorityQueue<Vertice<T>> openSet = new PriorityQueue<>((v1, v2) -> {
            // Retorna 0 se fScore não existe para qualquer um dos vértices (deve ser tratado)
            double score1 = fScore.getOrDefault(v1, Double.POSITIVE_INFINITY);
            double score2 = fScore.getOrDefault(v2, Double.POSITIVE_INFINITY);
            return Double.compare(score1, score2);
        });
        openSet.add(inicio);

        // closedSet: Conjunto de nós já avaliados
        Set<Vertice<T>> closedSet = new HashSet<>();

        while (!openSet.isEmpty()) {
            Vertice<T> atual = openSet.poll();

            if (atual.equals(objetivo)) {
                return reconstruirCaminho(cameFrom, atual);
            }

            closedSet.add(atual);

            // Percorre os vizinhos do nó atual
            // No seu Vertice.java, getArestasSaida() retorna as arestas que saem do vértice
            for (Aresta<T> arestaSaida : atual.getArestasSaida()) {
                Vertice<T> vizinho = arestaSaida.getFim(); // O fim da aresta é o vizinho

                if (closedSet.contains(vizinho)) {
                    continue; // Já avaliado
                }

                // Custo provisório do inicio ao vizinho
                double tentative_gScore = gScore.getOrDefault(atual, Double.POSITIVE_INFINITY) + arestaSaida.getPeso();

                // Se o novo caminho para o vizinho é melhor
                if (tentative_gScore < gScore.getOrDefault(vizinho, Double.POSITIVE_INFINITY)) {
                    cameFrom.put(vizinho, atual);
                    gScore.put(vizinho, tentative_gScore);
                    fScore.put(vizinho, tentative_gScore + heuristica(vizinho, objetivo));

                    if (!openSet.contains(vizinho)) {
                        openSet.add(vizinho);
                    }
                }
            }
        }

        // Se o objetivo não foi alcançado
        return Collections.emptyList();
    }

    private double heuristica(Vertice<T> atual, Vertice<T> objetivo) {
        // Exemplo de uma heurística mais complexa se 'T' contiver coordenadas:
        // if (atual.getDado() instanceof Ponto && objetivo.getDado() instanceof Ponto) {
        //     Ponto p1 = (Ponto) atual.getDado();
        //     Ponto p2 = (Ponto) objetivo.getDado();
        //     return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
        // }
        return 0.0; // Heurística trivial
    }

    private List<Vertice<T>> reconstruirCaminho(Map<Vertice<T>, Vertice<T>> cameFrom, Vertice<T> atual) {
        List<Vertice<T>> caminho = new ArrayList<>();
        while (cameFrom.containsKey(atual)) {
            caminho.add(atual);
            atual = cameFrom.get(atual);
        }
        caminho.add(atual); // Adiciona o nó inicial
        Collections.reverse(caminho); // Inverte para obter o caminho do inicio ao fim
        return caminho;
    }
}