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
        Vertice<T> origem = grafo.getVertice(dadoOrigem);

        if (origem == null) {
            System.out.println("Erro: Vértice de origem '" + dadoOrigem + "' não encontrado no grafo.");
            return;
        }

        // 1. Definindo Distâncias e Predecessores
        for (Vertice<T> vertice : grafo.getVertices()) {
            vertice.distanciaMinima = Double.POSITIVE_INFINITY;
            vertice.anterior = null;
        }
        origem.distanciaMinima = 0.0;

        // 2. Crie uma fila de prioridade para armazenar vértices a serem visitados
        PriorityQueue<Vertice<T>> filaPrioridade = new PriorityQueue<>();
        filaPrioridade.add(origem);

        // 3. Vértices de processo da fila de prioridade
        while (!filaPrioridade.isEmpty()) {
            Vertice<T> u = filaPrioridade.poll(); 

            for (Aresta<T> aresta : u.getArestasSaida()) {
                Vertice<T> v = aresta.getFim(); 
                double pesoAresta = aresta.getPeso(); 

                double distanciaAtravesU = u.distanciaMinima + pesoAresta;

                if (distanciaAtravesU < v.distanciaMinima) {
                    filaPrioridade.remove(v);
                    v.distanciaMinima = distanciaAtravesU;
                    v.anterior = u; 
                    filaPrioridade.add(v);
                }
            }
        }
    }

    // Encontra o menor caminho
    public List<Vertice<T>> getMenorCaminho(Vertice<T> alvo) {
        List<Vertice<T>> caminho = new ArrayList<>();
        for (Vertice<T> vertice = alvo; vertice != null; vertice = vertice.anterior) {
            caminho.add(vertice);
        }
        Collections.reverse(caminho);
        return caminho;
    }
}