package unip.tcc.homecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import unip.tcc.homecare.dto.AuthenticationDTO;
import unip.tcc.homecare.dto.UserDTO;
import unip.tcc.homecare.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO) {
        return authService.login(authenticationDTO);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserDTO userRegister) {
        return authService.register(userRegister);
    }

    @GetMapping("/me")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return authService.currentUser(userDetails);
    }
}
