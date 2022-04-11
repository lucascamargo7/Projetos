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
public class alteracaoResponse {

    private String id;
    private String tipochave;
    private String valorchave;
    private String tipoconta;
    private Integer numagencia;
    private Integer numconta;
    private String nome;
    private String sobrenome;
    private String datainclusao;

}
