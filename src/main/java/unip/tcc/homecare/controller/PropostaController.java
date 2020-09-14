package unip.tcc.homecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unip.tcc.homecare.dto.PropostaDTO;
import unip.tcc.homecare.model.CustomResponse;
import unip.tcc.homecare.model.Proposta;
import unip.tcc.homecare.service.PropostaService;

@RestController
public class PropostaController {

    @Autowired
    private PropostaService propostaService;

    @PostMapping("/users/{userId}/propostas")
    public ResponseEntity<?> salvarProposta(@PathVariable("userId") Long profissionalId, @RequestBody PropostaDTO proposta) {
        try {
            propostaService.salvarProposta(proposta, profissionalId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new CustomResponse(e.getMessage(), HttpStatus.FORBIDDEN.value())
            );
        }
    }

    @GetMapping("/users/{userId}/propostas")
    public ResponseEntity<?> listarPropostas(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(propostaService.listarPropostasProfissional(userId));
    }

    @GetMapping("/users/{userId}/solicitacoes/{solicitacaoId}/propostas")
    public ResponseEntity<?> listarPropostasPorSolicitacao(@PathVariable("solicitacaoId") Long solicitacaoId) {
        return ResponseEntity.ok(propostaService.listarPropostasPorSolicitacao(solicitacaoId));
    }

    @PutMapping("/users/{userId}/propostas")
    public ResponseEntity<?> editarProposta(@RequestBody Proposta proposta) {
        propostaService.editarProposta(proposta);
        return ResponseEntity.ok(new CustomResponse("Proposta editada com sucesso", HttpStatus.OK.value()));
    }
}
