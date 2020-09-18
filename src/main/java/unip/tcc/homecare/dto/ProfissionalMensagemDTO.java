package unip.tcc.homecare.dto;

import lombok.Getter;
import lombok.Setter;
import unip.tcc.homecare.model.Mensagem;
import unip.tcc.homecare.model.User;

import java.util.List;

@Getter
@Setter
public class ProfissionalMensagemDTO {

    public ProfissionalMensagemDTO(UserDTO profissional, List<Mensagem> mensagens) {
        this.profissional = profissional;
        this.mensagens = mensagens;
    }

    private UserDTO profissional;
    private List<Mensagem> mensagens;
}
