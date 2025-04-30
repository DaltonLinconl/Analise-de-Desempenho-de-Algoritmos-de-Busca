# plot_resultados.py
import pandas as pd
import matplotlib.pyplot as plt
import os

pasta = 'resultados'
arquivos = [f for f in os.listdir(pasta) if f.endswith('.csv')]

for arquivo in arquivos:
    caminho = os.path.join(pasta, arquivo)
    df = pd.read_csv(caminho)

    nome = arquivo.replace('.csv', '')
    plt.figure()
    for tipo in df['Tipo'].unique():
        for thread in df[df['Tipo'] == tipo]['Threads'].unique():
            dados = df[(df['Tipo'] == tipo) & (df['Threads'] == int(thread))]
            plt.plot(dados['Tamanho'], dados['Execução(ms)'], label=f"{tipo} - {thread} threads")
    
    plt.title(nome)
    plt.xlabel("Tamanho")
    plt.ylabel("Execução (ms)")
    plt.legend()
    plt.grid(True)
    plt.savefig(f"{pasta}/{nome}.png")
    plt.close()
