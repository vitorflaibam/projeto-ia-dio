package br.com.caderno.application;

import br.com.caderno.domain.Lancamento;
import br.com.caderno.domain.LancamentoRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RegistrarLancamentoService {
    private final LancamentoRepository repositorio;

    public RegistrarLancamentoService(LancamentoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Tool(name = "registrar-lancamento", description = "Registra uma despesa no caderno. O valor deve estar em centavos.")
    public LancamentoOutput executar(RegistrarLancamentoInput input) {
        LocalDate data = input.ocorridoEm() == null ? LocalDate.now() : input.ocorridoEm();
        Lancamento lancamento = new Lancamento(input.descricao(), input.valorEmCentavos(), input.categoria(), data);
        return LancamentoOutput.de(repositorio.salvar(lancamento));
    }
}
