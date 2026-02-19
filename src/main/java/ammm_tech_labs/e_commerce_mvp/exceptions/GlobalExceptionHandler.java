package ammm_tech_labs.e_commerce_mvp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ammm_tech_labs.e_commerce_mvp.response.APIResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
 
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIResponse<?>> naoEncontrado(EntityNotFoundException ex) {
        return ResponseEntity
                .status(404)
                .body(APIResponse
                    .erro(404, ex
                        .getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<?>> violacao(ConstraintViolationException ex) {
        return ResponseEntity
                .status(400)
                .body(APIResponse
                    .erro(400, ex
                        .getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<?>> handleOthers(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIResponse
                    .erro(500, "Erro interno: " + e
                        .getMessage()
                    )
            );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> validacaoErrada(MethodArgumentNotValidException ex) {
        var mensagens = ex.getFieldErrors()
                        .stream()
                        .map(
                            erro -> String
                            .format("O campo %s %s", 
                                erro.getField(), 
                                erro.getDefaultMessage()
                            )
                        )
                        .distinct()
                        .toList();

        return ResponseEntity
                .status(400)
                .body(APIResponse
                    .erroDados(400, "Erro de validação", mensagens)
                );
    }
}