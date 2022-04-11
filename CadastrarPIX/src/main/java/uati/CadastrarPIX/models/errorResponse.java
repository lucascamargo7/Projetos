package uati.CadastrarPIX.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@JsonFormat
@Getter
@Setter
public class errorResponse {

    private String code;
    private String message;

}
