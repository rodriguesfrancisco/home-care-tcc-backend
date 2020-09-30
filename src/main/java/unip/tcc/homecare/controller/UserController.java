package unip.tcc.homecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unip.tcc.homecare.model.User;
import unip.tcc.homecare.repository.UserRepository;
import unip.tcc.homecare.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    public User findOne(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    @GetMapping("/user/profissionais/{profissionalId}/detalhes")
    public ResponseEntity detalharProfissional(@PathVariable("profissionalId") Long profissionalId) {
        return ResponseEntity.ok(userService.detalharProfissional(profissionalId));
    }
}
