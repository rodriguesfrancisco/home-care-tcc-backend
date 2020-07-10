package unip.tcc.homecare.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unip.tcc.homecare.dto.AuthenticationDTO;
import unip.tcc.homecare.dto.TokenDTO;
import unip.tcc.homecare.security.jwt.JwtTokenProvider;
import unip.tcc.homecare.repository.UserRepository;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody AuthenticationDTO authenticationDTO) {
        try {
            String username = authenticationDTO.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationDTO.getPassword()));
            String token = jwtTokenProvider.createToken(username, userRepository.findByUsername(username).orElseThrow(() ->
                    new UsernameNotFoundException("Username: " + username + " not found")).getRoles());

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setUsername(username);
            tokenDTO.setToken(token);

            return ResponseEntity.ok(tokenDTO);
        } catch(AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username/password");
        }
    }
}
