package unip.tcc.homecare.dto;

import lombok.Getter;
import lombok.Setter;
import unip.tcc.homecare.model.Solicitacao;
import unip.tcc.homecare.model.User;

import java.util.List;

@Getter
@Setter
public class MensagemDTO {

    public MensagemDTO(List<ProfissionalMensagemDTO> mensagensProfissional, Solicitacao solicitacao) {
        this.mensagensProfissional = mensagensProfissional;
        this.solicitacao = solicitacao;
    }

    private List<ProfissionalMensagemDTO> mensagensProfissional;
    private Solicitacao solicitacao;
}
