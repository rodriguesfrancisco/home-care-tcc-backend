package unip.tcc.homecare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import unip.tcc.homecare.dto.AuthenticationDTO;
import unip.tcc.homecare.dto.TokenDTO;
import unip.tcc.homecare.dto.UserRegisterDTO;
import unip.tcc.homecare.model.User;
import unip.tcc.homecare.repository.UserRepository;
import unip.tcc.homecare.security.jwt.JwtTokenProvider;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO) {
        try {
            String email = authenticationDTO.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, authenticationDTO.getPassword()));
            String token = jwtTokenProvider.createToken(email, userRepository.findByEmail(email).orElseThrow(() ->
                    new UsernameNotFoundException("Username: " + email + " not found")).getRoles());

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setEmail(email);
            tokenDTO.setToken(token);
            tokenDTO.setExpireDate(jwtTokenProvider.getExpireDate(token));

            return ResponseEntity.ok(tokenDTO);
        } catch(AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválido");
        }
    }

    public ResponseEntity register(@RequestBody UserRegisterDTO userRegister) {
        Optional<User> userByEmail = userRepository.findByEmail(userRegister.getEmail());
        if(userByEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário com este email");
        }
        User newUser = new User();
        newUser.setEmail(userRegister.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        newUser.setRoles(userRegister.getRoles());

        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
