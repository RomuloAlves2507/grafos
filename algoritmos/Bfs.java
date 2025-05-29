package algoritmos;

import lib.Grafo;
import lib.Vertice;
import lib.Aresta;

import java.util.ArrayList;

public class Bfs<T> {
    public void buscaEmLargura(Grafo<T> grafo) {
        ArrayList<Vertice<T>> marcados = new ArrayList<>();
        ArrayList<Vertice<T>> fila = new ArrayList<>();

        if (grafo.getVertices().isEmpty()) return;

        Vertice<T> atual = grafo.getVertices().get(0);
        marcados.add(atual);
        System.out.println(atual.getDado());
        fila.add(atual);

        while (!fila.isEmpty()) {
            Vertice<T> visitado = fila.remove(0);
            for (Aresta<T> aresta : visitado.getArestasSaida()) {
                Vertice<T> proximo = aresta.getFim();
                if (!marcados.contains(proximo)) {
                    marcados.add(proximo);
                    System.out.println(proximo.getDado());
                    fila.add(proximo);
                }
            }
        }
    }
}
