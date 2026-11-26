package br.com.caderno.domain;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepository {
    Lancamento salvar(Lancamento lancamento);

    List<Lancamento> buscarPorCategoria(Categoria categoria);

    List<Lancamento> buscarEntre(LocalDate inicio, LocalDate fim);
}
