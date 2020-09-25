package unip.tcc.homecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import unip.tcc.homecare.service.AtendimentoService;

@RestController
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @GetMapping("/users/{userId}/atendimentos")
    public ResponseEntity listarAtendimentos(@PathVariable("userId") Long idProfissional) {
        return ResponseEntity.ok(atendimentoService.listarAtendimentos(idProfissional));
    }
}
