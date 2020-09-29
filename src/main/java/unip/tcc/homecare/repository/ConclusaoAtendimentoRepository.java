package unip.tcc.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unip.tcc.homecare.model.ConclusaoAtendimento;

public interface ConclusaoAtendimentoRepository extends JpaRepository<ConclusaoAtendimento, Long> {
}
