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

### 📝 Etapa 4 - Confecção do relatório sobre grafos
A seguir, está disponível o link para acesso ao relatório desenvolvido sobre grafos, contendo as conclusões obtidas a partir dos testes realizados:
* 👉 [Relatório Grafos - Allicia Rocha, Romulo Alves, Vitor Nascimento](https://docs.google.com/document/d/1fzEThERj6EUU_T_2Or5LaLBdmrZt5ka-m_gsTPccHQU/edit?usp=sharing)
  
---

### ✒️ Autores
Mencione todos aqueles que ajudaram a levantar o projeto desde o seu início
* 👨🏻‍💻 **Romulo Alves Luciano** - *Implementação e Documentação* - [RomuloAlves2507](https://github.com/RomuloAlves2507)
* 👨🏽‍💻 **Vitor do Nascimento Ramos** - *Implementação e Documentação* - [nascimentoVitor10](https://github.com/nascimentoVitor10)
* 👩🏻‍💻 **Allicia Rocha dos Santos** - *Implementação e Documentaçãoo* - [alliciarocha](https://github.com/alliciarocha)
---
⌨️ com ❤️ por [alliciarocha](https://github.com/alliciarocha) 😊
