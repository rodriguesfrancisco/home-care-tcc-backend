package unip.tcc.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unip.tcc.homecare.enums.StatusSolicitacao;
import unip.tcc.homecare.model.Proposta;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    Optional<Proposta> findByProfissionalIdAndSolicitacaoId(Long profissionalId, Long solicitacaoId);
    List<Proposta> findAllBySolicitacaoId(Long solicitacaoId);
    List<Proposta> findAllByProfissionalIdAndSolicitacaoStatusSolicitacaoNotIn(Long profissionalId, Collection<StatusSolicitacao> statusSolicitacao);
}
