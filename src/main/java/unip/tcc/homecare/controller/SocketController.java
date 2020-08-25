package unip.tcc.homecare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import unip.tcc.homecare.model.CustomResponse;
import unip.tcc.homecare.model.Mensagem;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/socket/message")
@CrossOrigin
public class SocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<CustomResponse> sendMessage(@RequestBody Mensagem mensagem) {
        messagingTemplate.convertAndSend("/status-processor/" + mensagem.getFromId(), mensagem);
        messagingTemplate.convertAndSend("/status-processor/" + mensagem.getToId(), mensagem);

        return ResponseEntity.ok().body(new CustomResponse(LocalDateTime.now().toString(), 200));
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
