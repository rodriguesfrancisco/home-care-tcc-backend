package unip.tcc.homecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unip.tcc.homecare.model.Solicitacao;
import unip.tcc.homecare.service.SolicitacaoService;

import java.util.List;

@RestController
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService solicitacaoService;

    @GetMapping("/users/{userId}/solicitacoes")
    public ResponseEntity<List<Solicitacao>> listarSolicitacoesByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(solicitacaoService.listSolicitacoesByUser(userId));
    }

    @PostMapping("/users/{userId}/solicitacoes")
    public ResponseEntity cadastrarSolicitacao(@RequestBody Solicitacao solicitacao) {
        solicitacaoService.cadastrarSolicitacao(solicitacao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
