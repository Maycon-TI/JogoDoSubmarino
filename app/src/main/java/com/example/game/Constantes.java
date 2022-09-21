package com.example.game;

public enum Constantes {
    GAME("Game");

    private String descricao;

    Constantes(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
