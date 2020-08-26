package unip.tcc.homecare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import unip.tcc.homecare.model.CustomResponse;
import unip.tcc.homecare.model.Mensagem;
import unip.tcc.homecare.repository.MensagemRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class SocketService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ResponseEntity enviarMensagem(Mensagem mensagem) {
        mensagemRepository.save(mensagem);

        messagingTemplate.convertAndSend("/status-processor/" + mensagem.getFromId(), mensagem);
        messagingTemplate.convertAndSend("/status-processor/" + mensagem.getToId(), mensagem);

        return ResponseEntity.ok().body(new CustomResponse(LocalDateTime.now().toString(), 200));
    }

    public List<Mensagem> listarMensagens(Long fromId1, Long fromId2) {
        return mensagemRepository.findByFromIdIn(Arrays.asList(fromId1, fromId2));
    }

}
