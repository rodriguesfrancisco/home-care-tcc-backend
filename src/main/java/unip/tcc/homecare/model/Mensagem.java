package unip.tcc.homecare.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mensagem {

    private String message;
    private Integer fromId;
    private Integer toId;
}
