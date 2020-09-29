package unip.tcc.homecare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import unip.tcc.homecare.dto.ConclusaoAtendimentoDTO;
import unip.tcc.homecare.enums.StatusSolicitacao;
import unip.tcc.homecare.model.*;
import unip.tcc.homecare.repository.AtendimentoRepository;
import unip.tcc.homecare.repository.ConclusaoAtendimentoRepository;
import unip.tcc.homecare.repository.PropostaRepository;
import unip.tcc.homecare.repository.SolicitacaoRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private ConclusaoAtendimentoRepository conclusaoAtendimentoRepository;

    @Secured({ "ROLE_USER_PROFISSIONAL" })
    public List<Solicitacao> getSolicitacoesEmAberto() {
        return solicitacaoRepository.findByStatusSolicitacaoIn(Arrays.asList(StatusSolicitacao.EM_ABERTO, StatusSolicitacao.ANALISE));
    }

    @Secured({ "ROLE_USER_PACIENTE", "ROLE_USER_RESPONSAVEL" })
    public Solicitacao getSolicitacaoByUser(Long userId) {
        Optional<Solicitacao> optionalSolicitacao = solicitacaoRepository.findByUserIdAndStatusSolicitacaoNot(userId, StatusSolicitacao.CONCLUIDA);
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
        Optional<Solicitacao> optionalSolicitacao = solicitacaoRepository.findByUserIdAndStatusSolicitacaoNot(userId, StatusSolicitacao.CONCLUIDA);
        if(optionalSolicitacao.isPresent())
            throw new IllegalArgumentException("Esse usuário já tem uma solicitação cadastrada");

        User userSolicitacao = new User();
        userSolicitacao.setId(userId);
        solicitacao.setUser(userSolicitacao);
        solicitacaoRepository.save(solicitacao);
    }

    public void deleteSolicitacao(Long idSolicitacao) {
        Optional<Solicitacao> optionalSolicitacao = solicitacaoRepository.findById(idSolicitacao);
        if(optionalSolicitacao.isPresent()) {
            solicitacaoRepository.delete(optionalSolicitacao.get());
        } else {
            throw new IllegalArgumentException("Solicitação não encontrada");
        }
    }

    public void editarSolicitacao(Solicitacao solicitacao) {
        solicitacaoRepository.save(solicitacao);
    }

    @Secured({ "ROLE_USER_PACIENTE", "ROLE_USER_RESPONSAVEL" })
    public void aceitarProposta(Long solicitacaoId, Long propostaId) {
        Proposta proposta = propostaRepository.findById(propostaId).orElseThrow(
                () -> new IllegalArgumentException("Proposta não encontrada.")
        );

        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId).orElseThrow(
                () -> new IllegalArgumentException("Solicitação não encontrada.")
        );

        if(!proposta.getSolicitacao().getId().equals(solicitacao.getId())) {
            throw new IllegalArgumentException("Você não pode aceitar a proposta de outra solicitação");
        }

        solicitacao.setStatusSolicitacao(StatusSolicitacao.EM_EXECUCAO);

        Atendimento atendimento = new Atendimento();
        atendimento.setProposta(proposta);
        atendimento.setSolicitacao(solicitacao);
        atendimento.setInicioAtendimento(new Date());

        solicitacaoRepository.save(solicitacao);
        atendimentoRepository.save(atendimento);
    }

    @Secured({ "ROLE_USER_PACIENTE", "ROLE_USER_RESPONSAVEL" })
    public void finalizarAtendimento(Long solicitacaoId, Long atendimentoId, ConclusaoAtendimentoDTO conclusaoAtendimentoDTO) {
        Atendimento atendimento = atendimentoRepository.findById(atendimentoId).orElseThrow(
                () -> new IllegalArgumentException("Atendimento não encontrado.")
        );

        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoId).orElseThrow(
                () -> new IllegalArgumentException("Solicitação não encontrada.")
        );

        ConclusaoAtendimento conclusaoAtendimento = new ConclusaoAtendimento();
        conclusaoAtendimento.setAtendimento(atendimento);
        conclusaoAtendimento.setInformacoesAtendimento(conclusaoAtendimentoDTO.getInformacoesAtendimento());
        conclusaoAtendimento.setNota(conclusaoAtendimentoDTO.getNota());
        conclusaoAtendimento.setDataConclusao(new Date());

        solicitacao.setStatusSolicitacao(StatusSolicitacao.CONCLUIDA);

        solicitacaoRepository.save(solicitacao);
        conclusaoAtendimentoRepository.save(conclusaoAtendimento);
    }
}
