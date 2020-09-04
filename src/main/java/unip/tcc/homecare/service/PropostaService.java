package unip.tcc.homecare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import unip.tcc.homecare.dto.PropostaDTO;
import unip.tcc.homecare.enums.StatusSolicitacao;
import unip.tcc.homecare.model.Proposta;
import unip.tcc.homecare.model.Solicitacao;
import unip.tcc.homecare.model.User;
import unip.tcc.homecare.repository.PropostaRepository;
import unip.tcc.homecare.repository.SolicitacaoRepository;
import unip.tcc.homecare.repository.UserRepository;

import java.util.Date;
import java.util.List;
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
    public void salvarProposta(PropostaDTO propostaDTO, Long profissionalId) {
        User profissional;
        Optional<User> profissionalOptional = userRepository.findById(profissionalId);
        Proposta proposta = new Proposta().toEntity(propostaDTO);
        if(profissionalOptional.isPresent()) {
            profissional = profissionalOptional.get();
            proposta.setProfissional(profissional);
        } else {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        boolean profissionalTemProposta = propostaRepository
                .findByProfissionalIdAndSolicitacaoId(profissionalId, propostaDTO.getIdSolicitacao())
                .isPresent();
        if (profissionalTemProposta) {
            throw new IllegalArgumentException("Você já possui uma proposta para esta solicitação");
        }

        Solicitacao solicitacao;
        Optional<Solicitacao> optionalSolicitacao = solicitacaoRepository.findById(propostaDTO.getIdSolicitacao());
        if(optionalSolicitacao.isPresent()) {
            solicitacao = optionalSolicitacao.get();
            if(solicitacao.getStatusSolicitacao().equals(StatusSolicitacao.EM_EXECUCAO) ||
                    solicitacao.getStatusSolicitacao().equals(StatusSolicitacao.CONCLUIDA)) {
                throw new IllegalArgumentException("Usuário não pode enviar proposta para esta solicitação");
            }
            proposta.setSolicitacao(solicitacao);
        } else {
            throw new IllegalArgumentException("Solicitação não encontrada");
        }

        proposta.setData(new Date());
        propostaRepository.save(proposta);
    }

    @Secured({"ROLE_USER_PROFISSIONAL"})
    public List<Proposta> listarPropostasProfissional(Long profissionalId) {
        return propostaRepository.findAllByProfissionalId(profissionalId);
    }

    @Secured({"ROLE_USER_PACIENTE", "ROLE_USER_RESPONSAVEL"})
    public List<Proposta> listarPropostasPorSolicitacao(Long solicitacaoId) {
        return propostaRepository.findAllBySolicitacaoId(solicitacaoId);
    }
}
