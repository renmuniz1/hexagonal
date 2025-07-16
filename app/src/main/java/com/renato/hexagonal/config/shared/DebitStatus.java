package com.renato.hexagonal.config.shared;

public enum DebitStatus {

    DEBITO_CRIADO("Débito criado com sucesso"),
    DEBITO_CANCELADO("Débito cancelado");

    private final String descricao;

    DebitStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
