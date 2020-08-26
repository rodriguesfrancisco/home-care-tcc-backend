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
//    @PostMapping
//    public ResponseEntity<?> useSimpleRest(@RequestBody Map<String, String> message) {
//        if(message.containsKey("message")) {
//            if(message.containsKey("toId") &&
//                message.get("toId") != null &&
//                !message.get("toId").equals("")) {
//
//                messagingTemplate.convertAndSend("/socket-publisher" + message.get("toId"), message);
//                messagingTemplate.convertAndSend("/socket-publisher" + message.get("fromId"), message);
//            }
//
//            return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }
//
//    @MessageMapping("/send/message")
//    public Map<String, String> useSocketCommunication(String message) {
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, String> messageConverted = null;
//
//        try {
//            messageConverted = mapper.readValue(message, Map.class);
//        } catch (IOException e) {
//            messageConverted = null;
//        }
//
//        if(messageConverted != null) {
//            if(messageConverted.containsKey("toId") &&
//                messageConverted.get("toId") != null &&
//                !messageConverted.get("toId").equals("")) {
//
//                messagingTemplate.convertAndSend("/socket-publisher" + messageConverted.get("toId"), messageConverted);
//                messagingTemplate.convertAndSend("/socket-publisher" + messageConverted.get("fromId"), messageConverted);
//            }
//        }
//
//        return messageConverted;
//    }
}
