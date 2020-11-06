package unip.tcc.homecare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import unip.tcc.homecare.dto.MensagemDTO;
import unip.tcc.homecare.dto.ProfissionalMensagemDTO;
import unip.tcc.homecare.dto.UserDTO;
import unip.tcc.homecare.dto.UsersMensagemDTO;
import unip.tcc.homecare.model.CustomResponse;
import unip.tcc.homecare.model.Mensagem;
import unip.tcc.homecare.model.Solicitacao;
import unip.tcc.homecare.model.User;
import unip.tcc.homecare.repository.MensagemRepository;
import unip.tcc.homecare.repository.SolicitacaoRepository;
import unip.tcc.homecare.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SocketService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ResponseEntity enviarMensagem(Mensagem mensagem) {
        Solicitacao solicitacao;
        Optional<Solicitacao> optionalSolicitacao = solicitacaoRepository.findById(mensagem.getSolicitacao().getId());
        if(optionalSolicitacao.isPresent()) {
            solicitacao = optionalSolicitacao.get();
        } else {
            throw new IllegalArgumentException("Solicitação não encontrada");
        }

        mensagem.setSolicitacao(solicitacao);
        mensagem.setDate(new Date());
        mensagemRepository.save(mensagem);

        messagingTemplate.convertAndSend("/status-processor/" + mensagem.getFromId(), mensagem);
        messagingTemplate.convertAndSend("/status-processor/" + mensagem.getToId(), mensagem);

        return ResponseEntity.ok().body(new CustomResponse(LocalDateTime.now().toString(), 200));
    }

    public List<Mensagem> listarMensagens(Long fromId1, Long fromId2) {
        List<Mensagem> mensagens = new ArrayList<>();
        List<Mensagem> messagesFromId1ToId2 = mensagemRepository.findByFromIdAndToId(fromId1, fromId2);
        mensagens.addAll(messagesFromId1ToId2);
        List<Mensagem> messagesFromId2ToId1 = mensagemRepository.findByFromIdAndToId(fromId2, fromId1);
        mensagens.addAll(messagesFromId2ToId1);
        mensagens.sort(Comparator.comparing(Mensagem::getId));
        return mensagens;
    }

    public UsersMensagemDTO listarUserMensagens(Long fromId, Long toId) {
        Optional<User> fromUserOptional = userRepository.findById(fromId);
        User fromUser;
        if(fromUserOptional.isPresent()) {
            fromUser = fromUserOptional.get();
        } else {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        Optional<User> toUserOptional = userRepository.findById(toId);
        User toUser;
        if(toUserOptional.isPresent()) {
            toUser = toUserOptional.get();
        } else {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        UserDTO fromUserDto = new UserDTO();
        fromUserDto.setNomeCompleto(fromUser.getNomeCompleto());

        UserDTO toUserDto = new UserDTO();
        toUserDto.setNomeCompleto(toUser.getNomeCompleto());

        UsersMensagemDTO usersMensagemDTO = new UsersMensagemDTO();
        usersMensagemDTO.setFromUser(fromUserDto);
        usersMensagemDTO.setToUser(toUserDto);

        return usersMensagemDTO;
    }

    public MensagemDTO listarMensagensPorSolicitacao(Long idSolicitacao) {
        List<Mensagem> mensagens = mensagemRepository.findBySolicitacaoId(idSolicitacao);

        List<Mensagem> mensagensDosProfissionais = new ArrayList<>();
        for(Mensagem mensagem : mensagens) {
            if(!mensagem.getFromId().equals(mensagem.getSolicitacao().getUser().getId())) {
                mensagensDosProfissionais.add(mensagem);
            }
        }

        if(!mensagensDosProfissionais.isEmpty()) {
            Map<UserDTO, List<Mensagem>> mensagensPorProfissional = new HashMap<>();
            for(Mensagem mensagemDoProfissional : mensagensDosProfissionais) {
                User profissional = userRepository.findById(mensagemDoProfissional.getFromId()).get();
                if(!mensagensPorProfissional.containsKey(profissional)) {
                    List<Mensagem> novasMensagens = new ArrayList<>();
                    novasMensagens.add(mensagemDoProfissional);
                    mensagensPorProfissional.put(profissional.toDto(), novasMensagens);
                } else {
                    List<Mensagem> mensagensJaExistentes = mensagensPorProfissional.get(profissional);
                    mensagensJaExistentes.add(mensagemDoProfissional);
                    mensagensPorProfissional.put(profissional.toDto(), mensagensJaExistentes);
                }
            }

            List<ProfissionalMensagemDTO> profissionalMensagemDTOS = new ArrayList<>();
            mensagensPorProfissional.forEach((profissional, mensagensInMap) -> {
                ProfissionalMensagemDTO profissionalMensagemDTO = new ProfissionalMensagemDTO(profissional, mensagensInMap);
                profissionalMensagemDTOS.add(profissionalMensagemDTO);
            });

            return new MensagemDTO(profissionalMensagemDTOS, solicitacaoRepository.getOne(idSolicitacao));
        }



        return null;
    }

}
