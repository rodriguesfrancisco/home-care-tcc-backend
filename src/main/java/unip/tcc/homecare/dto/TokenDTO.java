package unip.tcc.homecare.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TokenDTO {
    private Long id;
    private String email;
    private String token;
    private List roles;
    private Date expireDate;
}
