package uati.CadastrarPIX.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uati.CadastrarPIX.models.alteracaoResponse;
import uati.CadastrarPIX.models.chaveModel;
import uati.CadastrarPIX.models.chaveResponse;
import uati.CadastrarPIX.models.errorResponse;
import uati.CadastrarPIX.projection.consultaPadrao;
import uati.CadastrarPIX.services.chaveService;
import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@AllArgsConstructor
public class chaveController {

    @Autowired
    chaveService chaveService;

    //Criado teste
    @PostMapping(path = "/api/cadastroChave")
    public chaveResponse cadastroChave (@RequestBody @Valid chaveModel chave ){
        chaveResponse chaveResponse = new chaveResponse();
        chaveResponse.setId(chaveService.incluirChave(chave).getId());
        return chaveResponse;
    }

    @PutMapping(path = "/api/alterarChave/{id}")
    public alteracaoResponse alterarChave(@PathVariable String id, @RequestBody @Valid chaveModel chave) {
        alteracaoResponse alteracaoResponse = new alteracaoResponse();
        alteracaoResponse = chaveService.alterarChave(id, chave);
        return alteracaoResponse;
    }

    @PutMapping(path = "/api/deletarChave/{id}")
    public errorResponse deletarChave(@PathVariable String id) {
        errorResponse errorResponse = new errorResponse();
        if(chaveService.deletarChave(id)==false){
            errorResponse.setCode(HttpStatus.UNPROCESSABLE_ENTITY.toString());
            errorResponse.setMessage("Chave já esta inátivada");
        }
        else
        {
            errorResponse.setCode(HttpStatus.OK.toString());
            errorResponse.setMessage("Chave inativada  " +id );
            return errorResponse;
        }

        return errorResponse;
    }

    @GetMapping(path = "/api/listarChavesID/{id}")
    public chaveModel listarPorID(@PathVariable String id){
        return chaveService.listarPorID(id);
    }

    @GetMapping(path = "/api/listarChavesTipo/{tipo}")
    public List<consultaPadrao> listarPorTipo(@PathVariable String tipo){return chaveService.listarPorTipoChave(tipo);    }

    @GetMapping(path = "/api/listarAgenciaConta/{agencia}/{conta}")
    public List<consultaPadrao> listarPorAgenciaConta(@PathVariable String agencia, @PathVariable String conta){
        return chaveService.listarPorAgenciaConta(agencia, conta);
    }
    @GetMapping(path = "/api/listarNome/{nome}")
    public List<consultaPadrao> listarPorNome(@PathVariable String nome){
        return chaveService.listarPorNome(nome);
    }

}
