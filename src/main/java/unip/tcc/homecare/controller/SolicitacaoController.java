package unip.tcc.homecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unip.tcc.homecare.model.Solicitacao;
import unip.tcc.homecare.service.SolicitacaoService;

@RestController
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @GetMapping("/users/{userId}/solicitacoes")
    public ResponseEntity<Solicitacao> getSolicitacoesByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(solicitacaoService.getSolicitacaoByUser(userId));
    }

    @PostMapping("/users/{userId}/solicitacoes")
    public ResponseEntity cadastrarSolicitacao(@PathVariable("userId") Long userId, @RequestBody Solicitacao solicitacao) {
        try {
            solicitacaoService.cadastrarSolicitacao(userId ,solicitacao);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
