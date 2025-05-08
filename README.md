# 📊 Análise de Desempenho de Algoritmos de Ordenação em Java

##  👥 Autores

- Dalton Linconl
- Marcos Antonio Félix

## 📌 Descrição

Este projeto compara o desempenho de algoritmos de ordenação em versões **seriais e paralelas**, utilizando **Java**. Avaliamos como diferentes tamanhos de dados e número de **threads** influenciam o tempo de execução.

---

## 🧠 Algoritmos Implementados

- Bubble Sort  
- Insertion Sort  
- Merge Sort  
- Quick Sort  

Cada um com duas versões:  
✔️ **Serial** (sequencial)  
⚡ **Paralela** (usando `ForkJoinPool` com 2, 4 e 8 threads)

---

## 🧪 Metodologia

- Execuções com diferentes tamanhos de entrada  
- Variação no número de threads  
- 5 amostras por execução  
- Resultados salvos em `.csv`  
- Geração de gráficos com base nos dados

---

## 📁 Estrutura do Projeto

algoritmos_paralelos/ → Implementações paralelas

algoritmos_seriais/ → Implementações seriais

benchmark/ → Execução dos testes

resultados/ → Arquivos CSV com os tempos

resultados_graficos/ → Gráficos gerados

---

## ⚙️ Requisitos

- Java 8+  
- (Opcional) Maven  

---

## 🚀 Como Executar

1. Compile os arquivos Java:
 javac -d bin src/**/*.java

2. Execute os testes:


3. Verifique os resultados na pasta `/resultados/`

---

## 📊 Visualização dos Resultados

Use os arquivos `.csv` para gerar gráficos no **Excel**, **Google Sheets** ou com bibliotecas Java como **JFreeChart**.

---

Feito com ☕ e 🔁 em Java.
