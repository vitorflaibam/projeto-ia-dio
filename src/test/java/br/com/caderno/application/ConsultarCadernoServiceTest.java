package br.com.caderno.application;

import br.com.caderno.domain.Categoria;
import br.com.caderno.domain.Lancamento;
import br.com.caderno.domain.LancamentoRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsultarCadernoServiceTest {
    @Test
    void soma_apenas_os_lancamentos_do_mes_solicitado() {
        RepositorioEmMemoria repositorio = new RepositorioEmMemoria();
        repositorio.salvar(new Lancamento("Feira", 12500, Categoria.ALIMENTACAO, LocalDate.of(2026, 11, 3)));
        repositorio.salvar(new Lancamento("Cinema", 3800, Categoria.LAZER, LocalDate.of(2026, 11, 21)));
        repositorio.salvar(new Lancamento("Internet", 9900, Categoria.MORADIA, LocalDate.of(2026, 10, 10)));

        ResumoMensalOutput resumo = new ConsultarCadernoService(repositorio).fecharMes("2026-11");

        assertEquals(2, resumo.quantidadeDeLancamentos());
        assertEquals(16300, resumo.totalEmCentavos());
    }

    private static class RepositorioEmMemoria implements LancamentoRepository {
        private final List<Lancamento> itens = new ArrayList<>();

        @Override
        public Lancamento salvar(Lancamento lancamento) {
            itens.add(lancamento);
            return lancamento;
        }

        @Override
        public List<Lancamento> buscarPorCategoria(Categoria categoria) {
            return itens.stream().filter(item -> item.categoria() == categoria).toList();
        }

        @Override
        public List<Lancamento> buscarEntre(LocalDate inicio, LocalDate fim) {
            return itens.stream().filter(item -> !item.ocorridoEm().isBefore(inicio) && !item.ocorridoEm().isAfter(fim)).toList();
        }
    }
}
