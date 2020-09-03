package unip.tcc.homecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unip.tcc.homecare.model.CustomResponse;
import unip.tcc.homecare.model.Mensagem;
import unip.tcc.homecare.service.SocketService;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/api/socket/message")
@CrossOrigin
public class SocketController {

    @Autowired
    private SocketService socketService;

    @PostMapping
    public ResponseEntity<CustomResponse> sendMessage(@RequestBody Mensagem mensagem) {
        return socketService.enviarMensagem(mensagem);
    }

}
