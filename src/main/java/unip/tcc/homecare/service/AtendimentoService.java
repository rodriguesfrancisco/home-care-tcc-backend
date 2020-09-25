package unip.tcc.homecare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import unip.tcc.homecare.model.Atendimento;
import unip.tcc.homecare.repository.AtendimentoRepository;

import java.util.List;

@Service
public class AtendimentoService {

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Secured({"ROLE_USER_PROFISSIONAL"})
    public List<Atendimento> listarAtendimentos(Long idProfissional) {
        return atendimentoRepository.findAllByPropostaProfissionalId(idProfissional);
    }
}
