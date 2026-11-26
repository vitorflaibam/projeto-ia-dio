package br.com.caderno.domain;

import java.util.UUID;

public record LancamentoId(UUID valor) {
    public LancamentoId() {
        this(UUID.randomUUID());
    }
}
