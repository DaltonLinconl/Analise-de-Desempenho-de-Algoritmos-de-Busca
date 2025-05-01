import pandas as pd
import matplotlib.pyplot as plt
import os

# Caminho para a pasta com os CSVs
pasta_csv = "resultados"
arquivos = [f for f in os.listdir(pasta_csv) if f.endswith(".csv")]

# Gera um gráfico por arquivo CSV
for arquivo in arquivos:
    caminho = os.path.join(pasta_csv, arquivo)
    df = pd.read_csv(caminho, encoding='latin1')

    # Agrupamento: média do tempo por tipo e número de threads
    df_grouped = df.groupby(["Threads", "Tipo" ])["Execução(ms)"].mean().unstack()

    # Criação do gráfico
    ax = df_grouped.plot(kind="bar", figsize=(10, 6))

    # Adiciona rótulos de valor nas barras
    for container in ax.containers:
        ax.bar_label(container, fmt="%.1f", label_type="edge", fontsize=8)

    # Título e eixos
    nome_algoritmo = arquivo.replace("resultados_", "").replace(".csv", "")
    plt.title(f"Desempenho - {nome_algoritmo}")
    plt.xlabel("Tipo de Execução")
    plt.ylabel("Tempo médio (ms)")
    plt.xticks(rotation=0)
    plt.legend(title="Threads")
    plt.tight_layout()

    # Salvar o gráfico
    nome_saida = f"grafico_{nome_algoritmo}.png"
    plt.savefig(os.path.join("resultados_graficos", nome_saida))  
    plt.close()
