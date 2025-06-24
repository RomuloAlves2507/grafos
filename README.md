# 🚀 Trabalho Prático — Técnicas de Programação Avançada

## 🏗️ Etapa 1 · Biblioteca de Grafos Genérica

Neste projeto desenvolvemos **duas peças principais**:

1. **📚 Biblioteca genérica de grafos** — estruturas de dados e algoritmos clássicos implementados do zero em Java.
2. **🛵 Aplicativo de entregas** — demonstra o uso da biblioteca para calcular rotas de entrega ótimas.

---

### 📖 Algoritmos Implementados

| Algoritmo          | 📜 Descrição Rápida                                                             | 🛠️ Complexidade |
| ------------------ | ------------------------------------------------------------------------------- | ---------------- |
| **Dijkstra**       | Caminho mínimo em grafos ponderados (distância, tempo, custo)                   | `O(E log V)`     |
| **A★ (A Estrela)** | Variante do Dijkstra com heurística (distância euclidiana) para acelerar buscas | `O(E)` (médio)   |
| **BFS**            | Caminho mais curto em grafos **não‑ponderados**                                 | `O(V + E)`       |

---

### 📂 O que o Código Faz

* **Define** vértices e arestas via classes genéricas.
* **Carrega** mapas de ruas (ou qualquer grafo) a partir de arquivos `.csv` ou **JSON**.
* **Calcula** a rota mais rápida entre dois pontos escolhidos pelo usuário.
* **Exibe** o caminho e o seu custo total no terminal.

---

### ⚙️ Pré‑requisitos

* ☕ **Java 8** ou superior (JDK)
* Nenhuma dependência externa — somente a biblioteca padrão do Java.

---

### 💾 Instalação

```bash
# 1. Clone o repositório
git clone https://github.com/RomuloAlves2507/grafos.git
cd grafos

# 2. Compile todas as classes
javac -d out src/**/*.java
```

---

### ▶️ Como Executar

```bash
# Execute o programa principal
java -cp out Main
```

---

### 🔍 Estrutura de Pastas

```
grafos/
├── algoritmos/             # Algoritmos 
├── app/            # Implementação do Aplicativo de Entregas
├── lib/             # Biblioteca
└── README.md
```

---
