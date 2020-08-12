package unip.tcc.homecare.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomResponse {

    private String message;
    private Integer status;
}
