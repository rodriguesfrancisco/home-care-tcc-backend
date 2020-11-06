package unip.tcc.homecare.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import unip.tcc.homecare.dto.PropostaDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "proposta")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Proposta.class)
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

    @ManyToOne(fetch = FetchType.EAGER)
    private Solicitacao solicitacao;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profissional_id")
    private User profissional;

    public Proposta toEntity(PropostaDTO propostaDTO) {
        this.preco = propostaDTO.getPreco();
        this.informacoes = propostaDTO.getInformacoes();
        return this;
    }
}
