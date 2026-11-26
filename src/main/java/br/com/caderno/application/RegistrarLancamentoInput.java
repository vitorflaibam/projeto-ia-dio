package br.com.caderno.application;

import br.com.caderno.domain.Categoria;

import java.time.LocalDate;

public record RegistrarLancamentoInput(String descricao, long valorEmCentavos, Categoria categoria, LocalDate ocorridoEm) {
}
