package unip.tcc.homecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "endereco")
@Data
@Builder
public class Endereco {

    @Id
    @Column(name = "id")
    private Long id;

    private String cep;

    private String endereco;

    private Integer numero;

    private String cidade;

    private String uf;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

}

