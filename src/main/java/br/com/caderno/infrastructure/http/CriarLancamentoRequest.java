package br.com.caderno.infrastructure.http;

import br.com.caderno.application.RegistrarLancamentoInput;
import br.com.caderno.domain.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record CriarLancamentoRequest(
        @NotBlank String descricao,
        @Positive long valorEmCentavos,
        @NotNull Categoria categoria,
        @NotNull LocalDate ocorridoEm
) {
    RegistrarLancamentoInput paraInput() {
        return new RegistrarLancamentoInput(descricao, valorEmCentavos, categoria, ocorridoEm);
    }
}
