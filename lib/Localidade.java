package lib;

import java.util.Objects;

public class Localidade {
    private final String nome;
    private final int x;
    private final int y;

    public Localidade(String nome, int x, int y) {
        this.nome = nome;
        this.x = x;
        this.y = y;
    }

    public String getNome() {
        return nome;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return nome;
    }

    // Essencial para o Map no Grafo funcionar corretamente
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localidade that = (Localidade) o;
        return nome.equals(that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}