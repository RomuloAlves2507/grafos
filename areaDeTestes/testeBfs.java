package areaDeTestes;
import lib.Grafo;
import algoritmos.Bfs;

public class testeBfs {
    
    public static void main(String[] args) {
        Grafo<String> grafo = new Grafo<String>();
        grafo.adicionarVertice("João");
        grafo.adicionarVertice("Lorenzo");
        grafo.adicionarVertice("Creuza");
        grafo.adicionarVertice("Créber");
        grafo.adicionarVertice("Cráudio");
        
        grafo.adicionarAresta(2.0, "João", "Lorenzo");
        grafo.adicionarAresta(3.0, "Lorenzo", "Créber");
        grafo.adicionarAresta(1.0, "Créber", "Creuza");
        grafo.adicionarAresta(1.0, "João", "Creuza");
        grafo.adicionarAresta(3.0, "Cráudio", "João");
        grafo.adicionarAresta(2.0, "Cráudio", "Lorenzo");
        
        Bfs<String> bfs = new Bfs<>();
        bfs.buscaEmLargura(grafo);
    }
    
}