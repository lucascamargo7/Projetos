package uati.CadastrarPIX.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class unprocessableException extends RuntimeException{

        public unprocessableException(){
            super();
        }

}
