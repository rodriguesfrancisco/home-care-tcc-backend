package unip.tcc.homecare.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "proposta")
@Getter
@Setter
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double preco;

    @NotNull
    private Date data;

    @NotNull
    private String informacoes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitacao_id")
    private Solicitacao solicitacao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private User profissional;
}
