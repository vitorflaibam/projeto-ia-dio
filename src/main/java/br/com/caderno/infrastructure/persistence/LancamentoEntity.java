package br.com.caderno.infrastructure.persistence;

import br.com.caderno.domain.Categoria;
import br.com.caderno.domain.Lancamento;
import br.com.caderno.domain.LancamentoId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "lancamentos")
public class LancamentoEntity {
    @Id
    private UUID id;
    private String descricao;
    private long valorEmCentavos;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private LocalDate ocorridoEm;

    protected LancamentoEntity() {
    }

    private LancamentoEntity(UUID id, String descricao, long valorEmCentavos, Categoria categoria, LocalDate ocorridoEm) {
        this.id = id;
        this.descricao = descricao;
        this.valorEmCentavos = valorEmCentavos;
        this.categoria = categoria;
        this.ocorridoEm = ocorridoEm;
    }

    static LancamentoEntity de(Lancamento lancamento) {
        return new LancamentoEntity(lancamento.id().valor(), lancamento.descricao(), lancamento.valorEmCentavos(), lancamento.categoria(), lancamento.ocorridoEm());
    }

    Lancamento paraDominio() {
        return new Lancamento(new LancamentoId(id), descricao, valorEmCentavos, categoria, ocorridoEm);
    }
}
