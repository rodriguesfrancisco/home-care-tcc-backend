package unip.tcc.homecare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConclusaoAtendimentoDTO {
    private Integer nota;
    private String informacoesAtendimento;
}
