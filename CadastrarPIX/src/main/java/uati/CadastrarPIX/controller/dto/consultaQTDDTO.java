package uati.CadastrarPIX.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uati.CadastrarPIX.projection.consultaQTD;

import java.security.PublicKey;

@AllArgsConstructor @NoArgsConstructor
public class consultaQTDDTO {

    @Getter @Setter
    private Integer quantidade;

    public consultaQTDDTO(consultaQTD projection){
        quantidade = projection.getQuantidade();
    }

}
