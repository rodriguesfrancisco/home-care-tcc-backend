package unip.tcc.homecare.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRegisterDTO {

    private String email;
    private String password;
    private String nomeCompleto;
    private List<String> roles;
}
