package unip.tcc.homecare.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import unip.tcc.homecare.enums.StatusSolicitacao;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "solicitacao")
@Getter
@Setter
@AllArgsConstructor
public class Solicitacao {

    public Solicitacao() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String informacoes;

    private Date dataSolicitacao;

    private Character preferenciaSexoProfissional;

    private Double valorAproximadoSolicitacao;

    private StatusSolicitacao statusSolicitacao;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "solicitacao")
    @JsonManagedReference
    private List<Proposta> propostas = new ArrayList<>();
}
