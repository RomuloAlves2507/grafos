package lib;

import java.util.ArrayList;

public class Vertice<T> implements Comparable<Vertice<T>> {
    private T dado;
    private ArrayList<Aresta<T>> arestasEntrada;
    private ArrayList<Aresta<T>> arestasSaida;
    public double distanciaMinima = Double.POSITIVE_INFINITY;
    public Vertice<T> anterior; 

    public Vertice(T valor){
        this.dado = valor;
        this.arestasEntrada = new ArrayList<Aresta<T>>();
        this.arestasSaida = new ArrayList<Aresta<T>>();
        this.anterior = null;
    }

    public T getDado() {
        return dado;
    }

    public void setDado(T dado) {
        this.dado = dado;
    }
    
    public void adicionarArestaEntrada(Aresta<T> aresta){
        this.arestasEntrada.add(aresta);
    }
    
    public void adicionarArestaSaida(Aresta<T> aresta){
        this.arestasSaida.add(aresta);
    }

    public ArrayList<Aresta<T>> getArestasEntrada() {
        return arestasEntrada;
    }

    public ArrayList<Aresta<T>> getArestasSaida() {
        return arestasSaida;
    }

    @Override
    public int compareTo(Vertice<T> outroVertice) { 
        return Double.compare(this.distanciaMinima, outroVertice.distanciaMinima);
    }

    public ArrayList<Aresta<T>> getVizinhos() {
        return arestasSaida; 
    }
}