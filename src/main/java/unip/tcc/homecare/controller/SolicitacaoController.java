package unip.tcc.homecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import unip.tcc.homecare.model.CustomResponse;
import unip.tcc.homecare.model.Solicitacao;
import unip.tcc.homecare.service.SolicitacaoService;

@RestController
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @GetMapping("/users/{userId}/solicitacoes")
    public ResponseEntity getSolicitacoesByUser(@PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(solicitacaoService.getSolicitacaoByUser(userId));
        } catch(AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/users/{userId}/solicitacoes")
    public ResponseEntity cadastrarSolicitacao(@PathVariable("userId") Long userId, @RequestBody Solicitacao solicitacao) {
        try {
            solicitacaoService.cadastrarSolicitacao(userId, solicitacao);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new CustomResponse("Solicitação Criada", HttpStatus.CREATED.value())
        );
    }

    @DeleteMapping("/users/{userId}/solicitacoes/{idSolicitacao}")
    public ResponseEntity deletarSolicitacao(@PathVariable("idSolicitacao") Long idSolicitacao) {
        try {
            solicitacaoService.deleteSolicitacao(idSolicitacao);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok().body(new CustomResponse("Solicitação deletada", HttpStatus.OK.value()));
    }
}
