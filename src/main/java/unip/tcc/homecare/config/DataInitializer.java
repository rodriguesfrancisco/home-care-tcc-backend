package unip.tcc.homecare.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import unip.tcc.homecare.model.User;
import unip.tcc.homecare.model.Vehicle;
import unip.tcc.homecare.repository.UserRepository;
import unip.tcc.homecare.repository.VehicleRepository;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        List<Vehicle> vehicles = vehicleRepository.findAll();
        if(vehicles.isEmpty()) {
            log.debug("Initializing vehicle data");
            Arrays.asList("Moto", "Car").forEach(v -> vehicleRepository.save(new Vehicle(v)));
            log.debug("Printing all vehicles...");
            vehicleRepository.findAll().forEach(v -> log.debug(" Vehicle: "+ v.getName()));
        }

        List<User> users = userRepository.findAll();
        if(users.isEmpty()) {
            log.debug("Initializing users");
            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                    .build());

            userRepository.save(User.builder()
                    .username("paciente")
                    .password(passwordEncoder.encode("paciente123"))
                    .roles(Arrays.asList("ROLE_USER_PACIENTE"))
                    .build());

            userRepository.save(User.builder()
                    .username("profissional")
                    .password(passwordEncoder.encode("profissional123"))
                    .roles(Arrays.asList("ROLE_USER_PROFISSIONAL"))
                    .build());

            log.debug("Printing users...");
            userRepository.findAll().forEach(u -> log.debug(" User: " + u.getUsername()));
        }

    }
}
