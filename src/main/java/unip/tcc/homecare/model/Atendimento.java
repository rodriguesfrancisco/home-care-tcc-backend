package unip.tcc.homecare.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "atendimento")
@Getter
@Setter
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proposta_id")
    private Proposta proposta;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;

    @NotNull
    private Date inicioAtendimento;
}
