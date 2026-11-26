package br.com.caderno.infrastructure.persistence;

import br.com.caderno.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

interface SpringDataLancamentoRepository extends JpaRepository<LancamentoEntity, UUID> {
    List<LancamentoEntity> findByCategoriaOrderByOcorridoEmDesc(Categoria categoria);

    List<LancamentoEntity> findByOcorridoEmBetweenOrderByOcorridoEmAsc(LocalDate inicio, LocalDate fim);
}
