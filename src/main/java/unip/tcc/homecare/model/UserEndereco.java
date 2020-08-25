package unip.tcc.homecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "endereco")
@Builder
@Getter
@Setter
public class UserEndereco {

    public UserEndereco() {
    }

    public UserEndereco(Long id, String cep, String endereco, Integer numero, String cidade, String uf, User user) {
        this.id = id;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.cidade = cidade;
        this.uf = uf;
        this.user = user;
    }

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

