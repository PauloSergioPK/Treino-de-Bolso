package com.karasdecc.treinodebolso.ui.main.classes;

public class Exercicio {
    private String qtd_repeticoes;
    private String nome_exercicio;

    public Exercicio(String qtd_repeticoes,String nome_exercicio) {
        this.qtd_repeticoes = qtd_repeticoes;
        this.nome_exercicio = nome_exercicio;
    }

    public void setNome_exercicio(String nome_exercicio) {
        this.nome_exercicio = nome_exercicio;
    }

    public void setQtd_repeticoes(String qtd_repeticoes) {
        this.qtd_repeticoes = qtd_repeticoes;
    }
}
