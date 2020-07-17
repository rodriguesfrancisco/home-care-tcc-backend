package unip.tcc.homecare.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class UserDTO {

    private String email;
    private String password;
    private String nomeCompleto;
    private List<String> roles;
}
