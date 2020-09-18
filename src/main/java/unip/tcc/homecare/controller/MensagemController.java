package unip.tcc.homecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unip.tcc.homecare.dto.MensagemDTO;
import unip.tcc.homecare.dto.UsersMensagemDTO;
import unip.tcc.homecare.model.Mensagem;
import unip.tcc.homecare.repository.MensagemRepository;
import unip.tcc.homecare.service.SocketService;

import java.util.List;

@RestController
public class MensagemController {

    @Autowired
    private SocketService socketService;

    @GetMapping("/mensagens/{fromId1}/{fromId2}")
    public ResponseEntity<List<Mensagem>> listarMensagens(
            @PathVariable("fromId1") Long fromId1, @PathVariable("fromId2") Long fromId2
    ) {
        return ResponseEntity.ok(socketService.listarMensagens(fromId1, fromId2));
    }

    @GetMapping("/mensagens/user/{fromId}/{toId}")
    public ResponseEntity<UsersMensagemDTO> listarUsersMensagens(
            @PathVariable("fromId") Long fromId, @PathVariable("toId") Long toId
    ) {
        return ResponseEntity.ok(socketService.listarUserMensagens(fromId, toId));
    }

    @GetMapping("/users/{userId}/solicitacoes/{solicitacaoId}/mensagens")
    public ResponseEntity<MensagemDTO> listarMensagensPorProposta(@PathVariable("solicitacaoId") Long solicitacaoId) {
        return ResponseEntity.ok(socketService.listarMensagensPorSolicitacao(solicitacaoId));
    }
}
