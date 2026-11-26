package br.com.caderno.infrastructure.persistence;

import br.com.caderno.domain.Categoria;
import br.com.caderno.domain.Lancamento;
import br.com.caderno.domain.LancamentoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class JpaLancamentoRepository implements LancamentoRepository {
    private final SpringDataLancamentoRepository repositorio;

    public JpaLancamentoRepository(SpringDataLancamentoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Lancamento salvar(Lancamento lancamento) {
        return repositorio.save(LancamentoEntity.de(lancamento)).paraDominio();
    }

    @Override
    public List<Lancamento> buscarPorCategoria(Categoria categoria) {
        return repositorio.findByCategoriaOrderByOcorridoEmDesc(categoria).stream().map(LancamentoEntity::paraDominio).toList();
    }

    @Override
    public List<Lancamento> buscarEntre(LocalDate inicio, LocalDate fim) {
        return repositorio.findByOcorridoEmBetweenOrderByOcorridoEmAsc(inicio, fim).stream().map(LancamentoEntity::paraDominio).toList();
    }
}
