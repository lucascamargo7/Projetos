package uati.CadastrarPIX.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uati.CadastrarPIX.models.errorResponse;

@Slf4j
@ControllerAdvice
public class applicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(notFoundException.class)
    public ResponseEntity notFoundException(Exception e){
        log.info("Caiu na excessão 404");
        errorResponse error = new errorResponse();
        error.setCode(HttpStatus.NOT_FOUND.toString());
        error.setMessage("Dados não encontrados");
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(unprocessableException.class)
    public ResponseEntity unprocessableException(Exception e){
        log.info("Entrou na exceção 422");
        errorResponse error = new errorResponse();
        error.setCode(HttpStatus.UNPROCESSABLE_ENTITY.toString());
        error.setMessage("Não foi possivel validar os dados");
        return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
