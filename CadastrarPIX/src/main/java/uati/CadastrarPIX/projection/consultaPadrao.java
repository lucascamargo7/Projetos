package uati.CadastrarPIX.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat
public interface consultaPadrao {

     String getId();
     String getTipochave();
     String getValorchave();
     String getTipoconta();

     Integer getNumagencia();
     Integer getNumconta();

     String getNome();
     String getSobrenome();
     String getDatainclusao();
     String getDatainativacao();

}
