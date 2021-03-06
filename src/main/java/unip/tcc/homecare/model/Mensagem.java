package unip.tcc.homecare.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "mensagem")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String message;

    @NotNull
    private Long fromId;

    @NotNull
    private Long toId;

    @NotNull
    private Date date;

    @NotNull
    @OneToOne
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;
}
