package unip.tcc.homecare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unip.tcc.homecare.model.Solicitacao;
import unip.tcc.homecare.repository.SolicitacaoRepository;

import java.util.List;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    public List<Solicitacao> listSolicitacoesByUser(Long userId) {
        return solicitacaoRepository.findAllByUserId(userId);
    }
}
