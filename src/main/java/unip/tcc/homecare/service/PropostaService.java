package unip.tcc.homecare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import unip.tcc.homecare.model.Proposta;
import unip.tcc.homecare.model.Solicitacao;
import unip.tcc.homecare.model.User;
import unip.tcc.homecare.repository.PropostaRepository;
import unip.tcc.homecare.repository.SolicitacaoRepository;
import unip.tcc.homecare.repository.UserRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class PropostaService {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Secured({"ROLE_USER_PROFISSIONAL"})
    public void salvarProposta(Proposta proposta, Long profissionalId) {
        User profissional;
        Optional<User> profissionalOptional = userRepository.findById(profissionalId);
        if(profissionalOptional.isPresent()) {
            profissional = profissionalOptional.get();
            proposta.setProfissional(profissional);
        } else {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        boolean profissionalTemProposta = propostaRepository
                .findByProfissionalIdAndSolicitacaoId(profissionalId, proposta.getSolicitacao().getId())
                .isPresent();
        if (profissionalTemProposta) {
            throw new IllegalArgumentException("Este usuário já possui uma proposta para a solicitação");
        }

        Solicitacao solicitacao;
        Optional<Solicitacao> optionalSolicitacao = solicitacaoRepository.findById(proposta.getSolicitacao().getId());
        if(optionalSolicitacao.isPresent()) {
            solicitacao = optionalSolicitacao.get();
            proposta.setSolicitacao(solicitacao);
        } else {
            throw new IllegalArgumentException("Solicitação não encontrada");
        }

        proposta.setData(new Date());
        propostaRepository.save(proposta);
    }
}
