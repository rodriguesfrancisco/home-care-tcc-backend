package unip.tcc.homecare.dto;

import lombok.Getter;
import lombok.Setter;
import unip.tcc.homecare.model.UserEndereco;

@Getter
@Setter
public class DetalheProfissionalDTO {

    private String nomeCompleto;
    private UserEndereco endereco;
    private Double notaGeral;
}
