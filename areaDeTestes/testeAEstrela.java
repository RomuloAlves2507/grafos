package areaDeTestes;
import java.util.List;
import lib.Aresta;
import algoritmos.AEstrela;
import lib.Grafo;
import lib.Vertice;

public class testeAEstrela {

    public static void main(String[] args) {
        // 1. Criar uma instância do Grafo
        Grafo<String> meuGrafo = new Grafo<>();

        // 2. Adicionar Vértices ao Grafo
        meuGrafo.adicionarVertice("A");
        meuGrafo.adicionarVertice("B");
        meuGrafo.adicionarVertice("C");
        meuGrafo.adicionarVertice("D");
        meuGrafo.adicionarVertice("E");
        meuGrafo.adicionarVertice("F");

        // 3. Adicionar Arestas ao Grafo
        // AdicionarAresta espera peso, dadoInicio, dadoFim.
        // O método adicionaVertice já deve criar os vértices se não existirem
        // mas é mais seguro adicionar os vértices explicitamente antes se você já tem as instâncias.

        // Arestas de exemplo:
        meuGrafo.adicionarAresta(1.0, "A", "B");
        meuGrafo.adicionarAresta(3.0, "A", "C");
        meuGrafo.adicionarAresta(1.0, "B", "D");
        meuGrafo.adicionarAresta(5.0, "B", "E");
        meuGrafo.adicionarAresta(1.0, "C", "D");
        meuGrafo.adicionarAresta(2.0, "D", "E");
        meuGrafo.adicionarAresta(1.0, "E", "F");
        meuGrafo.adicionarAresta(10.0, "A", "F"); // Um caminho mais longo direto

        System.out.println("Grafo criado com sucesso!");

        // 4. Definir os vértices de início e objetivo
        // É importante que esses vértices existam no grafo.
        Vertice<String> inicioBusca = meuGrafo.getVertice("A");
        Vertice<String> objetivoBusca = meuGrafo.getVertice("F");

        if (inicioBusca == null || objetivoBusca == null) {
            System.err.println("Erro: Vértice de início ou objetivo não encontrado no grafo.");
            return;
        }

        // 5. Criar uma instância do algoritmo AEstrela
        AEstrela<String> aEstrela = new AEstrela<>();

        // 6. Encontrar o caminho mais curto
        System.out.println("\nBuscando caminho de " + inicioBusca.getDado() + " para " + objetivoBusca.getDado() + "...");
        List<Vertice<String>> caminho = aEstrela.encontrarCaminhoMaisCurto(meuGrafo, inicioBusca, objetivoBusca);

        // 7. Imprimir o resultado
        if (!caminho.isEmpty()) {
            System.out.println("\nCaminho mais curto encontrado:");
            double custoTotal = 0.0;
            Vertice<String> anterior = null;
            for (int i = 0; i < caminho.size(); i++) {
                Vertice<String> atual = caminho.get(i);
                System.out.print(atual.getDado());
                if (i < caminho.size() - 1) {
                    System.out.print(" -> ");
                }

                // Calcular o custo total do caminho
                if (anterior != null) {
                    // Encontrar a aresta entre anterior e atual
                    for (Aresta<String> aresta : anterior.getArestasSaida()) {
                        if (aresta.getFim().equals(atual)) {
                            custoTotal += aresta.getPeso();
                            break;
                        }
                    }
                }
                anterior = atual;
            }
            System.out.println("\nCusto total do caminho: " + String.format("%.2f", custoTotal));
        } else {
            System.out.println("\nNenhum caminho encontrado de " + inicioBusca.getDado() + " para " + objetivoBusca.getDado() + ".");
        }

        // Teste com outro caminho (ex: um que não existe ou é mais longo)
        System.out.println("\n--- Teste 2: Caminho de A para E ---");
        inicioBusca = meuGrafo.getVertice("A");
        objetivoBusca = meuGrafo.getVertice("E");

        if (inicioBusca != null && objetivoBusca != null) {
            caminho = aEstrela.encontrarCaminhoMaisCurto(meuGrafo, inicioBusca, objetivoBusca);
            if (!caminho.isEmpty()) {
                System.out.println("\nCaminho mais curto encontrado:");
                double custoTotal = 0.0;
                Vertice<String> anterior = null;
                for (int i = 0; i < caminho.size(); i++) {
                    Vertice<String> atual = caminho.get(i);
                    System.out.print(atual.getDado());
                    if (i < caminho.size() - 1) {
                        System.out.print(" -> ");
                    }
                    if (anterior != null) {
                        for (Aresta<String> aresta : anterior.getArestasSaida()) {
                            if (aresta.getFim().equals(atual)) {
                                custoTotal += aresta.getPeso();
                                break;
                            }
                        }
                    }
                    anterior = atual;
                }
                System.out.println("\nCusto total do caminho: " + String.format("%.2f", custoTotal));
            } else {
                System.out.println("\nNenhum caminho encontrado de " + inicioBusca.getDado() + " para " + objetivoBusca.getDado() + ".");
            }
        }
    }
}