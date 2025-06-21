import algoritmos.AEstrela;
import algoritmos.BuscaEmLargura;
import algoritmos.Dijkstra;
import lib.Grafo;
import lib.Localidade;
import lib.Vertice;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class AppMenuInterativo {

    // --- MÉTODOS DE CONFIGURAÇÃO DO GRAFO E EXIBIÇÃO ---

    private static Grafo<Localidade> criarMapaDeEntregas() {
        // Agora usamos Generics para um grafo de Localidades
        Grafo<Localidade> grafo = new Grafo<>();

        // Os dados dos vértices agora são objetos 'Localidade'
        Localidade deposito = new Localidade("Depósito", 1, 5);
        Localidade pontoA = new Localidade("Ponto A", 4, 9);
        Localidade pontoB = new Localidade("Ponto B", 5, 2);
        Localidade pontoC = new Localidade("Ponto C", 9, 8);
        Localidade pontoD = new Localidade("Ponto D", 8, 3);
        Localidade destinoFinal = new Localidade("Destino Final", 13, 6);

        // O grafo agora gerencia a adição de vértices internamente
        grafo.adicionarVertice(deposito);
        grafo.adicionarVertice(pontoA);
        grafo.adicionarVertice(pontoB);
        grafo.adicionarVertice(pontoC);
        grafo.adicionarVertice(pontoD);
        grafo.adicionarVertice(destinoFinal);

        // Adicionando arestas bidirecionais
        adicionarArestaBidirecional(grafo, deposito, pontoA, 5);
        adicionarArestaBidirecional(grafo, deposito, pontoB, 4);
        adicionarArestaBidirecional(grafo, pontoA, pontoC, 4);
        adicionarArestaBidirecional(grafo, pontoA, pontoB, 3);
        adicionarArestaBidirecional(grafo, pontoB, pontoD, 2);
        adicionarArestaBidirecional(grafo, pontoC, destinoFinal, 3);
        adicionarArestaBidirecional(grafo, pontoD, destinoFinal, 4);
        adicionarArestaBidirecional(grafo, pontoD, pontoC, 6);
        
        return grafo;
    }

    // Adaptado para usar a nova estrutura
    private static void adicionarArestaBidirecional(Grafo<Localidade> grafo, Localidade u, Localidade v, double peso) {
        grafo.adicionarAresta(peso, u, v);
        grafo.adicionarAresta(peso, v, u);
    }

    // Adaptado para receber a nova estrutura de resultados
    private static void imprimirResultado(String algoritmo, List<Vertice<Localidade>> caminho, double distancia, long tempoExecucao) {
        System.out.println("\n--- RESULTADO ---");
        if (caminho == null || caminho.isEmpty()) {
            System.out.println("Não foi possível encontrar um caminho com o algoritmo " + algoritmo + ".");
        } else {
            System.out.println("Rota encontrada com " + algoritmo + ":");
            StringJoiner sj = new StringJoiner(" -> ");
            caminho.forEach(vertice -> sj.add(vertice.getDado().getNome()));
            
            System.out.println("  Caminho: " + sj.toString());
            System.out.printf("  Distância Total: %.1f\n", distancia);
            System.out.printf("  Tempo de Execução: %.4f ms\n", tempoExecucao / 1_000_000.0);
        }
        System.out.println("-----------------");
    }
    
    // Função auxiliar para calcular a distância de um caminho retornado (A* e BFS)
    private static double calcularDistanciaCaminho(List<Vertice<Localidade>> caminho) {
        double distancia = 0.0;
        if (caminho == null || caminho.size() < 2) {
            return 0.0;
        }
        for (int i = 0; i < caminho.size() - 1; i++) {
            Vertice<Localidade> atual = caminho.get(i);
            Vertice<Localidade> proximo = caminho.get(i + 1);
            // Encontra a aresta que liga 'atual' a 'proximo' para somar o peso
            distancia += atual.getArestasSaida().stream()
                .filter(aresta -> aresta.getFim().equals(proximo))
                .mapToDouble(aresta -> aresta.getPeso())
                .findFirst()
                .orElse(0.0);
        }
        return distancia;
    }

    // --- LÓGICA DO MENU INTERATIVO ---

    private static void mostrarMenu() {
        System.out.println("\n==============================================");
        System.out.println("   CALCULADORA DE ROTAS DE ENTREGA");
        System.out.println("==============================================");
        System.out.println("Escolha o algoritmo para encontrar o caminho:");
        System.out.println("  1. Dijkstra (Garante o caminho mais curto por peso)");
        System.out.println("  2. A* (A-Estrela) (Otimizado com heurística)");
        System.out.println("  3. Busca em Largura (BFS) (Caminho mais curto por paradas)");
        System.out.println("  4. Sair");
        System.out.println("----------------------------------------------");
    }

    private static Vertice<Localidade> obterPontoDoUsuario(Grafo<Localidade> grafo, Scanner scanner, String tipoPonto) {
        Vertice<Localidade> vertice = null;
        while (vertice == null) {
            System.out.print("Digite o nome do ponto de " + tipoPonto + ": ");
            String nomePonto = scanner.nextLine();
            // Busca o vértice cujo dado (Localidade) tem o nome correspondente
            vertice = grafo.getVertices().stream()
                .filter(v -> v.getDado().getNome().equalsIgnoreCase(nomePonto))
                .findFirst()
                .orElse(null);
            
            if (vertice == null) {
                System.out.println("Ponto '" + nomePonto + "' não encontrado. Tente novamente.");
            }
        }
        return vertice;
    }

    public static void main(String[] args) {
        Grafo<Localidade> mapa = criarMapaDeEntregas();
        Scanner scanner = new Scanner(System.in);

        // --- Definindo a heurística para o A* ---
        BiFunction<Vertice<Localidade>, Vertice<Localidade>, Double> heuristica = (v1, v2) -> {
            Localidade p1 = v1.getDado();
            Localidade p2 = v2.getDado();
            return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
        };
        // Instanciando os algoritmos
        Dijkstra<Localidade> dijkstra = new Dijkstra<>();
        AEstrela<Localidade> aEstrela = new AEstrela<>();
        BuscaEmLargura<Localidade> bfs = new BuscaEmLargura<>();

        while (true) {
            mostrarMenu();
            System.out.print("Digite sua opção: ");
            String escolha = scanner.nextLine();
            
            if (escolha.equals("4")) {
                System.out.println("Saindo do programa. Até mais!");
                break;
            }

            if (!Arrays.asList("1", "2", "3").contains(escolha)) {
                System.out.println("Opção inválida. Por favor, escolha de 1 a 4.");
                continue;
            }
            
            System.out.println("\nPontos disponíveis no mapa:");
            String localidadesDisponiveis = mapa.getVertices().stream()
                .map(v -> v.getDado().getNome())
                .collect(Collectors.joining(" | "));
            System.out.println(localidadesDisponiveis + "\n");
            
            Vertice<Localidade> inicio = obterPontoDoUsuario(mapa, scanner, "PARTIDA");
            Vertice<Localidade> fim = obterPontoDoUsuario(mapa, scanner, "DESTINO");

            List<Vertice<Localidade>> caminho = null;
            double distancia = 0.0;
            String nomeAlgoritmo = "";
            
            long tempoInicio = System.nanoTime();

            switch (escolha) {
                case "1":
                    nomeAlgoritmo = "Dijkstra";
                    // Nova forma de chamar o Dijkstra
                    dijkstra.executar(mapa, inicio.getDado());
                    caminho = dijkstra.getMenorCaminho(fim);
                    distancia = dijkstra.getDistancia(fim);
                    break;
                case "2":
                    nomeAlgoritmo = "A*";
                    // Nova forma de chamar o A*
                    caminho = aEstrela.encontrarCaminhoMaisCurto(mapa, inicio, fim);
                    distancia = calcularDistanciaCaminho(caminho);
                    break;
                case "3":
                    nomeAlgoritmo = "Busca em Largura (BFS)";
                    // Nova forma de chamar o BFS
                    caminho = bfs.encontrarCaminho(mapa, inicio.getDado(), fim.getDado());
                    distancia = calcularDistanciaCaminho(caminho);
                    break;
            }
            
            long tempoFim = System.nanoTime();
            imprimirResultado(nomeAlgoritmo, caminho, distancia, (tempoFim - tempoInicio));

            System.out.print("\nPressione Enter para voltar ao menu...");
            scanner.nextLine();
        }

        scanner.close();
    }
}