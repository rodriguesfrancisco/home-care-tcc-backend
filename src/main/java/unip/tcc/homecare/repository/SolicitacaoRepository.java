package unip.tcc.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unip.tcc.homecare.model.Solicitacao;

import java.util.List;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    List<Solicitacao> findAllByUserId(Long userId);
}
