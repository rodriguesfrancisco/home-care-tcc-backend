package unip.tcc.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unip.tcc.homecare.model.Atendimento;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
}
