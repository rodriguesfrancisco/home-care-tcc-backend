package unip.tcc.homecare.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "conclusaoAtendimento")
@Getter
@Setter
public class ConclusaoAtendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer nota;

    private String informacoesAtendimento;

    @OneToOne
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;
}
