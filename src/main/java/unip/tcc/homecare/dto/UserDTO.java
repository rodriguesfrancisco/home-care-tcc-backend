package unip.tcc.homecare.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import unip.tcc.homecare.model.User;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private Character sexo;
    private List<String> roles;
    private UserDTO paciente;
}
