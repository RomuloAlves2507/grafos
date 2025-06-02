package algoritmos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import lib.Grafo;
import lib.Vertice;
import lib.Aresta;

public class Dijkstra<T> {

    public void executar(Grafo<T> grafo, T dadoOrigem) {
        // Get the source vertex from the graph
        Vertice<T> origem = grafo.getVertice(dadoOrigem);

        if (origem == null) {
            System.out.println("Erro: Vértice de origem '" + dadoOrigem + "' não encontrado no grafo.");
            return;
        }

        // 1. Initialize distances and predecessors for all vertices
        for (Vertice<T> vertice : grafo.getVertices()) {
            vertice.distanciaMinima = Double.POSITIVE_INFINITY;
            vertice.anterior = null;
        }
        // Distance from source to itself is 0
        origem.distanciaMinima = 0.0;

        // 2. Create a priority queue to store vertices to visit
        // The Vertice class's compareTo method ensures ordering by distanciaMinima
        PriorityQueue<Vertice<T>> filaPrioridade = new PriorityQueue<>();
        filaPrioridade.add(origem);

        // 3. Process vertices from the priority queue
        while (!filaPrioridade.isEmpty()) {
            Vertice<T> u = filaPrioridade.poll(); // Get the vertex with the smallest distance

            // Iterate through all outgoing edges (neighbors) of the current vertex 'u'
            for (Aresta<T> aresta : u.getArestasSaida()) {
                Vertice<T> v = aresta.getFim(); // The destination vertex of the edge
                double pesoAresta = aresta.getPeso(); // Weight of the edge

                // Calculate the distance to 'v' if we go through 'u'
                double distanciaAtravesU = u.distanciaMinima + pesoAresta;

                // Relaxation step: If a shorter path to 'v' is found
                if (distanciaAtravesU < v.distanciaMinima) {
                    // Important: remove and re-add to update priority in the queue
                    filaPrioridade.remove(v);
                    v.distanciaMinima = distanciaAtravesU;
                    v.anterior = u; // Set 'u' as the predecessor for path reconstruction
                    filaPrioridade.add(v);
                }
            }
        }
    }

    public List<Vertice<T>> getMenorCaminho(Vertice<T> alvo) {
        List<Vertice<T>> caminho = new ArrayList<>();
        // Traverse back from the target to the source using the 'anterior' (previous) pointers
        for (Vertice<T> vertice = alvo; vertice != null; vertice = vertice.anterior) {
            caminho.add(vertice);
        }
        // The path is built in reverse, so reverse it to get the correct order
        Collections.reverse(caminho);
        return caminho;
    }
}