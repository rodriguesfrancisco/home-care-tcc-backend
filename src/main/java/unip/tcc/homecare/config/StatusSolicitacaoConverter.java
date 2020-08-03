package unip.tcc.homecare.config;

import unip.tcc.homecare.enums.StatusSolicitacao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusSolicitacaoConverter implements AttributeConverter<StatusSolicitacao, String> {

    @Override
    public String convertToDatabaseColumn(StatusSolicitacao status) {
        if(status == null) {
            return null;
        }
        return status.getCode();
    }

    @Override
    public StatusSolicitacao convertToEntityAttribute(String code) {
        if(code == null) {
            return null;
        }

        return Stream.of(StatusSolicitacao.values())
                .filter(s -> s.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
