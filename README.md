Este projeto implementa um benchmark para comparar o desempenho de algoritmos de ordenação em versões serial e paralela, utilizando diferentes tamanhos de array e números de threads.

# Descrição

O código testa quatro algoritmos de ordenação:

    Bubble Sort

    Insertion Sort

    Merge Sort

    Quick Sort

Cada algoritmo é executado em:

    Versão serial (execução sequencial)

    Versão paralela (usando ForkJoinPool com 2, 4 e 8 threads)

Os resultados são armazenados em arquivos CSV para análise posterior.

# Configuração
Pré-requisitos

    Java 8+

    Maven (opcional, para gerenciamento de dependências)

# Estrutura do Projeto
   

Análise de Desempenho de Algoritmos de Busca
│
├── algoritmos_paralelos/       # Implementações paralelas
│   ├── ParallelBubbleSort.java
│   ├── ParallelInsertionSort.java
│   ├── ParallelMergeSort.java
│   └── ParallelQuickSort.java
│
├── algoritmos_seriais/         # Implementações sequenciais
│   ├── BubbleSort.java
│   ├── InsertionSort.java
│   ├── MergeSort.java
│   └── QuickSort.java
│
├── benchmark/                  # Código do benchmark
│   ├── BenchmarkUtil.java      # Utilitário para medição de tempo
│   └── SortingBenchmark.java   # Classe principal
│
├── class_files/                # Pasta para arquivos compilados (opcional)
└── resultados/                 # Resultados em CSV (gerados automaticamente)


# Métricas Analisadas

Para cada algoritmo, são medidos:

    Tempo de execução (ms) em diferentes tamanhos de array:

        1.000 elementos

        10.000 elementos

        50.000 elementos

        100.000 elementos

    Número de threads (1 para serial, 2/4/8 para paralelo)

    Média de tempo após 5 execuções (SAMPLES = 5)


# Como Executar

    Clone o repositório:
    

Compile e execute:

    


# Resultados:

    Os arquivos CSV são gerados em resultados/.

    Exemplo: resultados_QuickSort_10000.csv
