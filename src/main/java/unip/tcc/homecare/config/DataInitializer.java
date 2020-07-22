package unip.tcc.homecare.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import unip.tcc.homecare.model.User;
import unip.tcc.homecare.repository.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            log.info("Initializing users");
            userRepository.save(User.builder()
                    .email("admin@admin.com")
                    .nomeCompleto("Eu sou um admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                    .build());


            User responsavel1 = User.builder()
                    .email("responsavel@123.com")
                    .nomeCompleto("Eu sou um responsável")
                    .password(passwordEncoder.encode("responsavel123"))
                    .diaNascimento(LocalDate.of(1999, 5, 24))
                    .roles(Arrays.asList("ROLE_USER_RESPONSAVEL"))
                    .build();

            User paciente1 = User.builder()
                    .nomeCompleto("Eu sou um paciente")
                    .diaNascimento(LocalDate.of(1999, 5, 26))
                    .roles(Arrays.asList("ROLE_USER_PACIENTE"))
                    .build();

            responsavel1.setPaciente(paciente1);
            userRepository.save(responsavel1);

            userRepository.save(User.builder()
                    .email("profissional@123.com")
                    .nomeCompleto("Eu sou um profissional")
                    .diaNascimento(LocalDate.of(1999, 5, 24))
                    .password(passwordEncoder.encode("profissional123"))
                    .roles(Arrays.asList("ROLE_USER_PROFISSIONAL"))
                    .build());

            log.info("Printing users...");
        }

        userRepository.findAll().forEach(u -> log.info(" User: " + u.getUsername()));

    }
}
