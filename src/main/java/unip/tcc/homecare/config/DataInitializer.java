package unip.tcc.homecare.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import unip.tcc.homecare.model.User;
import unip.tcc.homecare.repository.UserRepository;

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

            userRepository.save(User.builder()
                    .email("paciente@123.com")
                    .nomeCompleto("Eu sou um paciente")
                    .password(passwordEncoder.encode("paciente123"))
                    .roles(Arrays.asList("ROLE_USER_PACIENTE"))
                    .build());

            userRepository.save(User.builder()
                    .email("profissional@123.com")
                    .nomeCompleto("Eu sou um profissional")
                    .password(passwordEncoder.encode("profissional123"))
                    .roles(Arrays.asList("ROLE_USER_PROFISSIONAL"))
                    .build());

            log.info("Printing users...");
        }

        userRepository.findAll().forEach(u -> log.info(" User: " + u.getUsername()));

    }
}
