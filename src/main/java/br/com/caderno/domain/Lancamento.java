package br.com.caderno.domain;

import java.time.LocalDate;
import java.util.Objects;

public record Lancamento(LancamentoId id, String descricao, long valorEmCentavos, Categoria categoria, LocalDate ocorridoEm) {
    public Lancamento {
        Objects.requireNonNull(id);
        Objects.requireNonNull(descricao);
        Objects.requireNonNull(categoria);
        Objects.requireNonNull(ocorridoEm);
        if (descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição é obrigatória");
        }
        if (valorEmCentavos <= 0) {
            throw new IllegalArgumentException("O valor precisa ser maior que zero");
        }
    }

    public Lancamento(String descricao, long valorEmCentavos, Categoria categoria, LocalDate ocorridoEm) {
        this(new LancamentoId(), descricao, valorEmCentavos, categoria, ocorridoEm);
    }
}
