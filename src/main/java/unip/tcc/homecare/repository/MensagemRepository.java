package unip.tcc.homecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unip.tcc.homecare.model.Mensagem;

import java.util.Collection;
import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByFromIdOrFromId(Collection<Long> fromIds);
    List<Mensagem> findBySolicitacaoId(Long solicitacaoId);
}
