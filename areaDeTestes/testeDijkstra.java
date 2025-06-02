package areaDeTestes;
import lib.Aresta;
import algoritmos.Dijkstra; // Import the new Dijkstra class
import lib.Grafo;
import lib.Vertice;
import java.util.List;

public class testeDijkstra {
    public static void main(String[] args) {
        // Create a graph
        Grafo<String> meuGrafo = new Grafo<>();

        // Add vertices
        meuGrafo.adicionarVertice("A");
        meuGrafo.adicionarVertice("B");
        meuGrafo.adicionarVertice("C");
        meuGrafo.adicionarVertice("D");
        meuGrafo.adicionarVertice("E");

        // Add edges
        meuGrafo.adicionarAresta(10.0, "A", "B");
        meuGrafo.adicionarAresta(3.0, "A", "C");
        meuGrafo.adicionarAresta(1.0, "B", "C");
        meuGrafo.adicionarAresta(2.0, "B", "D");
        meuGrafo.adicionarAresta(8.0, "C", "D");
        meuGrafo.adicionarAresta(2.0, "C", "E");
        meuGrafo.adicionarAresta(7.0, "D", "E");

        // Create a Dijkstra instance
        Dijkstra<String> dijkstra = new Dijkstra<>();

        // Execute Dijkstra's algorithm starting from vertex "A"
        System.out.println("Executando o algoritmo de Dijkstra a partir do vértice 'A'...");
        dijkstra.executar(meuGrafo, "A");

        // Get the target vertex
        Vertice<String> verticeE = meuGrafo.getVertice("E");
        Vertice<String> verticeD = meuGrafo.getVertice("D");
        Vertice<String> verticeB = meuGrafo.getVertice("B");

        // Display results for vertex "E"
        System.out.println("\n--- Resultados para o vértice 'E' ---");
        System.out.println("Distância mínima para E: " + verticeE.distanciaMinima);
        List<Vertice<String>> caminhoParaE = dijkstra.getMenorCaminho(verticeE);
        System.out.print("Caminho mais curto para E: ");
        for (int i = 0; i < caminhoParaE.size(); i++) {
            System.out.print(caminhoParaE.get(i).getDado());
            if (i < caminhoParaE.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println(); // New line for clarity

        // Display results for vertex "D"
        System.out.println("\n--- Resultados para o vértice 'D' ---");
        System.out.println("Distância mínima para D: " + verticeD.distanciaMinima);
        List<Vertice<String>> caminhoParaD = dijkstra.getMenorCaminho(verticeD);
        System.out.print("Caminho mais curto para D: ");
        for (int i = 0; i < caminhoParaD.size(); i++) {
            System.out.print(caminhoParaD.get(i).getDado());
            if (i < caminhoParaD.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();

        // Display results for vertex "B"
        System.out.println("\n--- Resultados para o vértice 'B' ---");
        System.out.println("Distância mínima para B: " + verticeB.distanciaMinima);
        List<Vertice<String>> caminhoParaB = dijkstra.getMenorCaminho(verticeB);
        System.out.print("Caminho mais curto para B: ");
        for (int i = 0; i < caminhoParaB.size(); i++) {
            System.out.print(caminhoParaB.get(i).getDado());
            if (i < caminhoParaB.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}