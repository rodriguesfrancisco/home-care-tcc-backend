package unip.tcc.homecare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import unip.tcc.homecare.model.Solicitacao;
import unip.tcc.homecare.model.User;
import unip.tcc.homecare.repository.SolicitacaoRepository;

import java.util.Optional;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Secured({ "ROLE_USER_PACIENTE", "ROLE_USER_RESPONSAVEL" })
    public Solicitacao getSolicitacaoByUser(Long userId) {
        Optional<Solicitacao> optionalSolicitacao = solicitacaoRepository.findByUserId(userId);
        if(optionalSolicitacao.isPresent()) {
            Solicitacao solicitacao = optionalSolicitacao.get();
            User userSolicitacao = solicitacao.getUser();
            userSolicitacao.setPassword(null);
            solicitacao.setUser(userSolicitacao);

            return solicitacao;
        }

        return null;
    }

    public void cadastrarSolicitacao(Long userId, Solicitacao solicitacao) {
        Optional<Solicitacao> optionalSolicitacao = solicitacaoRepository.findByUserId(userId);
        if(optionalSolicitacao.isPresent())
            throw new IllegalArgumentException("Esse usuário já tem uma solicitação cadastrada");

        User userSolicitacao = new User();
        userSolicitacao.setId(userId);
        solicitacao.setUser(userSolicitacao);
        solicitacaoRepository.save(solicitacao);
    }
}
