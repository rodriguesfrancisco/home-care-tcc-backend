package unip.tcc.homecare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import unip.tcc.homecare.dto.DetalheProfissionalDTO;
import unip.tcc.homecare.model.Atendimento;
import unip.tcc.homecare.model.User;
import unip.tcc.homecare.repository.AtendimentoRepository;
import unip.tcc.homecare.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    public DetalheProfissionalDTO detalharProfissional(Long profissionalId) {
        User profissional = userRepository.findById(profissionalId)
                .orElseThrow(() -> new IllegalArgumentException("Profissional não encontrado"));

        List<Atendimento> atendimentosDoProfissional = atendimentoRepository.findAllByPropostaProfissionalId(profissionalId);
        List<Integer> notasDoProfissional = new ArrayList<>();
        for(Atendimento atendimento : atendimentosDoProfissional) {
            notasDoProfissional.add(atendimento.getConclusaoAtendimento().getNota());
        }

        Double totalNotas = 0.0;
        for(Integer nota : notasDoProfissional) {
            totalNotas += nota;
        }

        Double notaGeral = totalNotas/notasDoProfissional.size();

        DetalheProfissionalDTO detalheProfissionalDTO = new DetalheProfissionalDTO();
        detalheProfissionalDTO.setNomeCompleto(profissional.getNomeCompleto());
        detalheProfissionalDTO.setEndereco(profissional.getEndereco());
        detalheProfissionalDTO.setNotaGeral(notaGeral);

        return detalheProfissionalDTO;
    }
}
