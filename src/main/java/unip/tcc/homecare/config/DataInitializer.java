package unip.tcc.homecare.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import unip.tcc.homecare.enums.StatusSolicitacao;
import unip.tcc.homecare.model.Endereco;
import unip.tcc.homecare.model.Solicitacao;
import unip.tcc.homecare.model.User;
import unip.tcc.homecare.repository.SolicitacaoRepository;
import unip.tcc.homecare.repository.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

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
                    .dataNascimento(LocalDate.of(1999, 5, 24))
                    .sexo('F')
                    .roles(Arrays.asList("ROLE_USER_RESPONSAVEL"))
                    .build();

            User paciente1 = User.builder()
                    .nomeCompleto("Eu sou um paciente")
                    .dataNascimento(LocalDate.of(1999, 5, 26))
                    .sexo('M')
                    .roles(Arrays.asList("ROLE_USER_PACIENTE"))
                    .build();

            responsavel1.setPaciente(paciente1);
            userRepository.save(responsavel1);

            Endereco enderecoProfissional = Endereco.builder()
                    .cep("72222222")
                    .numero(22)
                    .cidade("São Paulo")
                    .uf("SP")
                    .endereco("Quadra 2 rua 123")
                    .build();

            User profissional = User.builder()
                    .email("profissional@123.com")
                    .nomeCompleto("Eu sou um profissional")
                    .dataNascimento(LocalDate.of(1999, 5, 24))
                    .sexo('M')
                    .endereco(enderecoProfissional)
                    .password(passwordEncoder.encode("profissional123"))
                    .roles(Arrays.asList("ROLE_USER_PROFISSIONAL"))
                    .build();

            enderecoProfissional.setUser(profissional);

            userRepository.save(profissional);

            log.info("Printing users...");

            userRepository.findAll().forEach(u -> log.info(" User: " + u.getUsername()));

            List<Solicitacao> solicitacoes = solicitacaoRepository.findAll();
            if(solicitacoes.isEmpty()) {
                Solicitacao solicitacao = new Solicitacao();
                solicitacao.setDataSolicitacao(new Date());
                solicitacao.setInformacoes("Uma solicitação");
                solicitacao.setStatusSolicitacao(StatusSolicitacao.EM_ABERTO);
                solicitacao.setUser(paciente1);

                solicitacaoRepository.save(solicitacao);
            }
        }
    }
}
