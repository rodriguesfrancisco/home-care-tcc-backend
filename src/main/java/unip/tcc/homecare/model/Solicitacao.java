package unip.tcc.homecare.model;

import lombok.Getter;
import lombok.Setter;
import unip.tcc.homecare.enums.StatusSolicitacao;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "solicitacao")
@Getter
@Setter
public class Solicitacao {

    public Solicitacao() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String informacoes;

    private Date dataSolicitacao;

    private StatusSolicitacao statusSolicitacao;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
