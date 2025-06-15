import java.util.*;

/**
 * Aplicação de console em Java para simular e calcular rotas de entrega ótimas
 * usando os algoritmos de grafos de Dijkstra e A* (A-Estrela).
 * A aplicação define um mapa com nós (localidades) e arestas (distâncias),
 * e então calcula o caminho mais curto entre um ponto de partida e um destino.
 * Inclui uma suíte de testes que é executada antes da demonstração principal.
 */
public class AppRotaDeEntregaConsole {

    // --- CLASSES DE ESTRUTURA DO GRAFO (Inner classes) ---

    static class No {
        final String nome;
        final int x; // Coordenada X para heurística do A*
        final int y; // Coordenada Y para heurística do A*
        private final List<Aresta> arestasAdjacentes = new ArrayList<>();
        public double gScore = Double.POSITIVE_INFINITY, fScore = Double.POSITIVE_INFINITY;
        public No pai = null;

        public No(String nome, int x, int y) { this.nome = nome; this.x = x; this.y = y; }
        public void adicionarAresta(Aresta aresta) { arestasAdjacentes.add(aresta); }
        public List<Aresta> getArestasAdjacentes() { return arestasAdjacentes; }
        @Override public String toString() { return nome; }
        @Override public int hashCode() { return nome.hashCode(); }
        @Override public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            return nome.equals(((No) obj).nome);
        }
    }

    static class Aresta {
        final No origem;
        final No destino;
        final int peso;
        public Aresta(No origem, No destino, int peso) { this.origem = origem; this.destino = destino; this.peso = peso; }
    }

    static class Grafo {
        private final Set<No> nos = new HashSet<>();
        public void adicionarNo(No no) { nos.add(no); }
        public Set<No> getNos() { return nos; }
    }
    
    static class ResultadoRota {
        final List<No> caminho;
        final double distancia;
        public ResultadoRota(List<No> caminho, double distancia) { this.caminho = caminho; this.distancia = distancia; }
    }

    // --- IMPLEMENTAÇÃO DOS ALGORITMOS ---

    static class Dijkstra {
        public static ResultadoRota encontrarCaminhoMaisCurto(Grafo grafo, No inicio, No fim) {
            Map<No, Double> distancias = new HashMap<>();
            Map<No, No> pais = new HashMap<>();
            PriorityQueue<No> filaPrioridade = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));
            Set<No> visitados = new HashSet<>();
            for (No no : grafo.getNos()) distancias.put(no, Double.POSITIVE_INFINITY);
            distancias.put(inicio, 0.0);
            filaPrioridade.add(inicio);
            while (!filaPrioridade.isEmpty()) {
                No noAtual = filaPrioridade.poll();
                if (noAtual.equals(fim)) return new ResultadoRota(reconstruirCaminho(pais, fim), distancias.get(fim));
                if (visitados.contains(noAtual)) continue;
                visitados.add(noAtual);
                for (Aresta aresta : noAtual.getArestasAdjacentes()) {
                    No vizinho = aresta.destino;
                    if (!visitados.contains(vizinho)) {
                        double novaDistancia = distancias.get(noAtual) + aresta.peso;
                        if (novaDistancia < distancias.get(vizinho)) {
                            distancias.put(vizinho, novaDistancia);
                            pais.put(vizinho, noAtual);
                            filaPrioridade.remove(vizinho);
                            filaPrioridade.add(vizinho);
                        }
                    }
                }
            }
            return new ResultadoRota(Collections.emptyList(), -1);
        }
    }
    
    static class AEstrela {
        private static double heuristica(No no1, No no2) {
            return Math.sqrt(Math.pow(no1.x - no2.x, 2) + Math.pow(no1.y - no2.y, 2));
        }
        public static ResultadoRota encontrarCaminhoMaisCurto(Grafo grafo, No inicio, No fim) {
            for (No no : grafo.getNos()) {
                no.gScore = Double.POSITIVE_INFINITY;
                no.fScore = Double.POSITIVE_INFINITY;
                no.pai = null;
            }
            inicio.gScore = 0;
            inicio.fScore = heuristica(inicio, fim);
            PriorityQueue<No> conjuntoAberto = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fScore));
            conjuntoAberto.add(inicio);
            while (!conjuntoAberto.isEmpty()) {
                No noAtual = conjuntoAberto.poll();
                if (noAtual.equals(fim)) return new ResultadoRota(reconstruirCaminhoAEstrela(fim), noAtual.gScore);
                for (Aresta aresta : noAtual.getArestasAdjacentes()) {
                    No vizinho = aresta.destino;
                    double gScoreTentativo = noAtual.gScore + aresta.peso;
                    if (gScoreTentativo < vizinho.gScore) {
                        vizinho.pai = noAtual;
                        vizinho.gScore = gScoreTentativo;
                        vizinho.fScore = vizinho.gScore + heuristica(vizinho, fim);
                        if (!conjuntoAberto.contains(vizinho)) conjuntoAberto.add(vizinho);
                    }
                }
            }
            return new ResultadoRota(Collections.emptyList(), -1);
        }
    }

    // --- SUÍTE DE TESTES ---

    static class TestRunner {
        public static void runAllTests() {
            System.out.println("--- INICIANDO SUÍTE DE TESTES ---");
            testCaminhoOtimo();
            testCaminhoInexistente();
            testCaminhoSimples();
            System.out.println("\n--- TESTES CONCLUÍDOS ---");
        }

        private static void testCaminhoOtimo() {
            Grafo grafo = criarGrafoParaTeste();
            No inicio = obterNoPeloNome(grafo, "Depósito");
            No fim = obterNoPeloNome(grafo, "Destino Final");
            
            double distanciaEsperada = 10.0;
            List<String> caminhoEsperado = Arrays.asList("Depósito", "Ponto B", "Ponto D", "Destino Final");

            ResultadoRota resultadoDijkstra = Dijkstra.encontrarCaminhoMaisCurto(grafo, inicio, fim);
            assert resultadoDijkstra.distancia == distanciaEsperada : "Teste Dijkstra falhou: Distância incorreta.";
            assert converterParaNomes(resultadoDijkstra.caminho).equals(caminhoEsperado) : "Teste Dijkstra falhou: Caminho incorreto.";

            ResultadoRota resultadoAEstrela = AEstrela.encontrarCaminhoMaisCurto(grafo, inicio, fim);
            assert resultadoAEstrela.distancia == distanciaEsperada : "Teste A* falhou: Distância incorreta.";
            assert converterParaNomes(resultadoAEstrela.caminho).equals(caminhoEsperado) : "Teste A* falhou: Caminho incorreto.";

            System.out.println("OK - Teste de Caminho Ótimo passou.");
        }
        
        private static void testCaminhoInexistente() {
            Grafo grafo = criarGrafoParaTeste();
            No inicio = obterNoPeloNome(grafo, "Depósito");
            No noIsolado = new No("Isolado", 20, 20);
            grafo.adicionarNo(noIsolado);

            ResultadoRota resultado = Dijkstra.encontrarCaminhoMaisCurto(grafo, inicio, noIsolado);
            assert resultado.caminho.isEmpty() : "Teste de Caminho Inexistente falhou: Um caminho foi encontrado.";
            
            System.out.println("OK - Teste de Caminho Inexistente passou.");
        }

        private static void testCaminhoSimples() {
            Grafo grafo = new Grafo();
            No a = new No("A", 0, 0);
            No b = new No("B", 1, 0);
            adicionarArestaBidirecional(a, b, 7);
            grafo.adicionarNo(a);
            grafo.adicionarNo(b);

            ResultadoRota resultado = AEstrela.encontrarCaminhoMaisCurto(grafo, a, b);
            assert resultado.distancia == 7.0 : "Teste de Caminho Simples falhou: Distância incorreta.";
            assert converterParaNomes(resultado.caminho).equals(Arrays.asList("A", "B")) : "Teste de Caminho Simples falhou: Caminho incorreto.";

            System.out.println("OK - Teste de Caminho Simples passou.");
        }
    }
    
    // --- MÉTODOS AUXILIARES ---

    private static List<No> reconstruirCaminho(Map<No, No> pais, No fim) {
        LinkedList<No> caminho = new LinkedList<>();
        for (No passo = fim; passo != null; passo = pais.get(passo)) caminho.addFirst(passo);
        return caminho;
    }
    private static List<No> reconstruirCaminhoAEstrela(No fim) {
        LinkedList<No> caminho = new LinkedList<>();
        for (No passo = fim; passo != null; passo = passo.pai) caminho.addFirst(passo);
        return caminho;
    }
    
    private static void adicionarArestaBidirecional(No u, No v, int p) {
        u.adicionarAresta(new Aresta(u, v, p));
        v.adicionarAresta(new Aresta(v, u, p));
    }
    
    private static No obterNoPeloNome(Grafo g, String n) {
        return g.getNos().stream().filter(no -> no.nome.equals(n)).findFirst().orElse(null);
    }
    
    private static List<String> converterParaNomes(List<No> caminho) {
        List<String> nomes = new ArrayList<>();
        for(No no : caminho) nomes.add(no.nome);
        return nomes;
    }

    private static Grafo criarGrafoParaTeste() {
        Grafo grafo = new Grafo();
        No deposito = new No("Depósito", 1, 5);
        No pontoA = new No("Ponto A", 4, 9);
        No pontoB = new No("Ponto B", 5, 2);
        No pontoC = new No("Ponto C", 9, 8);
        No pontoD = new No("Ponto D", 8, 3);
        No destinoFinal = new No("Destino Final", 13, 6);
        adicionarArestaBidirecional(deposito, pontoA, 5);
        adicionarArestaBidirecional(deposito, pontoB, 4);
        adicionarArestaBidirecional(pontoA, pontoC, 4);
        adicionarArestaBidirecional(pontoA, pontoB, 3);
        adicionarArestaBidirecional(pontoB, pontoD, 2);
        adicionarArestaBidirecional(pontoC, destinoFinal, 3);
        adicionarArestaBidirecional(pontoD, destinoFinal, 4);
        adicionarArestaBidirecional(pontoD, pontoC, 6);
        Collections.addAll(grafo.getNos(), deposito, pontoA, pontoB, pontoC, pontoD, destinoFinal);
        return grafo;
    }
    
    private static void imprimirResultado(String algoritmo, ResultadoRota resultado, long tempoExecucao) {
        if (resultado.caminho.isEmpty()) {
            System.out.println("Não foi possível encontrar um caminho com o algoritmo " + algoritmo + ".");
        } else {
            System.out.println("Rota encontrada com " + algoritmo + ":");
            StringJoiner sj = new StringJoiner(" -> ");
            for (No no : resultado.caminho) {
                sj.add(no.nome);
            }
            System.out.println("Caminho: " + sj.toString());
            System.out.printf("Distância Total: %.0f\n", resultado.distancia);
            System.out.printf("Tempo de Execução: %.4f ms\n", tempoExecucao / 1_000_000.0);
        }
    }

    // --- MÉTODO PRINCIPAL ---
    
    public static void main(String[] args) {
        // 1. Executa os testes primeiro.
        try {
            TestRunner.runAllTests();
            System.out.println("\nTodos os testes passaram com sucesso! Executando a demonstração no console...");
        } catch (AssertionError e) {
            System.err.println("\n!!! UM TESTE FALHOU: " + e.getMessage());
            System.err.println("!!! A demonstração não será executada devido a falha no teste.");
            return;
        }

        // 2. Executa a demonstração principal no console.
        System.out.println("----------------------------------------------------------\n");
        Grafo grafo = criarGrafoParaTeste();
        No deposito = obterNoPeloNome(grafo, "Depósito");
        No destinoFinal = obterNoPeloNome(grafo, "Destino Final");

        System.out.println("Calculando a melhor rota de '" + deposito.nome + "' para '" + destinoFinal.nome + "'...");
        System.out.println("\n----------------------------------------------------------\n");

        // Executar Dijkstra
        System.out.println("--- Executando Algoritmo de Dijkstra ---");
        long inicioDijkstra = System.nanoTime();
        ResultadoRota resultadoDijkstra = Dijkstra.encontrarCaminhoMaisCurto(grafo, deposito, destinoFinal);
        long fimDijkstra = System.nanoTime();
        imprimirResultado("Dijkstra", resultadoDijkstra, (fimDijkstra - inicioDijkstra));
        
        System.out.println("\n----------------------------------------------------------\n");

        // Executar A*
        System.out.println("--- Executando Algoritmo A* (A-Estrela) ---");
        long inicioAEstrela = System.nanoTime();
        ResultadoRota resultadoAEstrela = AEstrela.encontrarCaminhoMaisCurto(grafo, deposito, destinoFinal);
        long fimAEstrela = System.nanoTime();
        imprimirResultado("A*", resultadoAEstrela, (fimAEstrela - inicioAEstrela));

        System.out.println("\n----------------------------------------------------------");
    }
}