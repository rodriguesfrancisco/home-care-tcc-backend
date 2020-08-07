package unip.tcc.homecare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import unip.tcc.homecare.dto.AuthenticationDTO;
import unip.tcc.homecare.dto.TokenDTO;
import unip.tcc.homecare.dto.UserDTO;
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
            User user = userRepository.findByEmail(email).orElseThrow(() ->
                    new UsernameNotFoundException("Username: " + email + " not found"));
            String token = jwtTokenProvider.createToken(email, user.getRoles());

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setId(user.getId());
            tokenDTO.setEmail(email);
            tokenDTO.setToken(token);
            tokenDTO.setExpireDate(jwtTokenProvider.getExpireDate(token));
            tokenDTO.setRoles(jwtTokenProvider.getRoles(token));

            return ResponseEntity.ok(tokenDTO);
        } catch(AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválido");
        }
    }

    public ResponseEntity register(UserDTO userRegister) {
        Optional<User> userByEmail = userRepository.findByEmail(userRegister.getEmail());
        if(userByEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um usuário com este email");
        }
        User newUser = new User();
        newUser.setNomeCompleto(userRegister.getNomeCompleto());
        newUser.setEmail(userRegister.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        newUser.setDataNascimento(userRegister.getDataNascimento());
        newUser.setSexo(userRegister.getSexo());
        newUser.setRoles(userRegister.getRoles());

        if(userRegister.isResponsavel()) {
            User paciente = new User();
            paciente.setNomeCompleto(userRegister.getPaciente().getNomeCompleto());
            paciente.setDataNascimento(userRegister.getPaciente().getDataNascimento());
            paciente.setSexo(userRegister.getPaciente().getSexo());
            paciente.setRoles(userRegister.getPaciente().getRoles());
            paciente.setEndereco(userRegister.getPaciente().getEndereco());
            paciente.getEndereco().setUser(paciente);
            newUser.setPaciente(paciente);
        } else {
            newUser.setEndereco(userRegister.getEndereco());
            newUser.getEndereco().setUser(newUser);
        }

        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity currentUser(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() ->
            new UsernameNotFoundException("Usuário não encontrado")
        );
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setNomeCompleto(user.getNomeCompleto());
        userDTO.setDataNascimento(user.getDataNascimento());
        userDTO.setSexo(user.getSexo());
        userDTO.setRoles(user.getRoles());

        if(user.getPaciente() != null) {
            UserDTO pacienteDTO = new UserDTO();
            pacienteDTO.setId(user.getPaciente().getId());
            pacienteDTO.setNomeCompleto(user.getPaciente().getNomeCompleto());
            pacienteDTO.setDataNascimento(user.getPaciente().getDataNascimento());
            pacienteDTO.setSexo(user.getPaciente().getSexo());
            pacienteDTO.setRoles(user.getPaciente().getRoles());
            pacienteDTO.setEndereco(user.getPaciente().getEndereco());

            userDTO.setPaciente(pacienteDTO);
        } else {
            userDTO.setEndereco(user.getEndereco());
        }


        return ResponseEntity.ok(userDTO);
    }
}
