package uati.CadastrarPIX.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uati.CadastrarPIX.projection.consultaPadrao;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class consultaPadraoDTO {

    private String id;
    private String tipochave;
    private String valorchave;
    private String tipoconta;
    private Integer numagencia;
    private Integer numconta;
    private String nome;
    private String sobrenome;
    private String datainclusao;
    private String datainativacao;

    public consultaPadraoDTO(consultaPadrao projection) {

        id = projection.getId();
        tipochave = projection.getTipochave();
        valorchave = projection.getValorchave();
        tipoconta = projection.getTipoconta();
        numagencia = projection.getNumagencia();
        numconta = projection.getNumconta();
        nome = projection.getNome();
        sobrenome = projection.getSobrenome();
        datainclusao = projection.getDatainclusao();
        datainativacao = projection.getDatainativacao();

    }

}
