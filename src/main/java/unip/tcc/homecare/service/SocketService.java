package unip.tcc.homecare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        return mensagemRepository.findByFromIdIn(Arrays.asList(fromId1, fromId2));
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

}
