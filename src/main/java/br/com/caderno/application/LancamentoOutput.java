package br.com.caderno.application;

import br.com.caderno.domain.Lancamento;

import java.time.LocalDate;
import java.util.UUID;

public record LancamentoOutput(UUID id, String descricao, long valorEmCentavos, String categoria, LocalDate ocorridoEm) {
    public static LancamentoOutput de(Lancamento lancamento) {
        return new LancamentoOutput(
                lancamento.id().valor(),
                lancamento.descricao(),
                lancamento.valorEmCentavos(),
                lancamento.categoria().name(),
                lancamento.ocorridoEm()
        );
    }
}
