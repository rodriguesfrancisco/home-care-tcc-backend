package unip.tcc.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unip.tcc.homecare.enums.StatusSolicitacao;
import unip.tcc.homecare.model.Mensagem;
import unip.tcc.homecare.model.Solicitacao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    Optional<Solicitacao> findByUserId(Long userId);
    Optional<Solicitacao> findByUserIdAndStatusSolicitacaoNot(Long userId, StatusSolicitacao statusSolicitacao);
    List<Solicitacao> findByStatusSolicitacaoIn(Collection<StatusSolicitacao> statusSolicitacao);
}
