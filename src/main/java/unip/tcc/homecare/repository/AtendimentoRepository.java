package unip.tcc.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unip.tcc.homecare.model.Atendimento;

import java.util.List;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    List<Atendimento> findAllByPropostaProfissionalId(Long profissionalId);
}
