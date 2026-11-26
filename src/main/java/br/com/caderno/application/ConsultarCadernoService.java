package br.com.caderno.application;

import br.com.caderno.domain.Categoria;
import br.com.caderno.domain.LancamentoRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
public class ConsultarCadernoService {
    private final LancamentoRepository repositorio;

    public ConsultarCadernoService(LancamentoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public List<LancamentoOutput> porCategoria(Categoria categoria) {
        return repositorio.buscarPorCategoria(categoria).stream().map(LancamentoOutput::de).toList();
    }

    @Tool(name = "fechar-mes", description = "Informa a quantidade de despesas e o valor total de um mês no formato AAAA-MM.")
    public ResumoMensalOutput fecharMes(@ToolParam(description = "Mês a consultar no formato AAAA-MM") String mes) {
        YearMonth periodo = YearMonth.parse(mes);
        var lancamentos = repositorio.buscarEntre(periodo.atDay(1), periodo.atEndOfMonth());
        long total = lancamentos.stream().mapToLong(lancamento -> lancamento.valorEmCentavos()).sum();
        return new ResumoMensalOutput(periodo.toString(), lancamentos.size(), total);
    }
}
