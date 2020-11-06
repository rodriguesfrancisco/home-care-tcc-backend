package unip.tcc.homecare.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "atendimento")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Atendimento.class)
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

    @OneToOne(mappedBy = "atendimento")
    private ConclusaoAtendimento conclusaoAtendimento;
}
