package unip.tcc.homecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unip.tcc.homecare.model.Mensagem;
import unip.tcc.homecare.repository.MensagemRepository;
import unip.tcc.homecare.service.SocketService;

import java.util.List;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private SocketService socketService;

    @GetMapping("/{fromId1}/{fromId2}")
    private ResponseEntity<List<Mensagem>> listarMensagens(
            @PathVariable("fromId1") Long fromId1, @PathVariable("fromId2") Long fromId2
    ) {
        return ResponseEntity.ok(socketService.listarMensagens(fromId1, fromId2));
    }
}
