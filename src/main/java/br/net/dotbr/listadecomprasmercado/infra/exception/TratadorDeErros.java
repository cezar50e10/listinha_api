package br.net.dotbr.listadecomprasmercado.infra.exception;

import br.net.dotbr.listadecomprasmercado.domain.ValidacaoException;
import br.net.dotbr.listadecomprasmercado.domain.produtosavulsos.StatusProdutoAvulso;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice

public class TratadorDeErros {
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleEnumValidationException(HttpMessageNotReadableException ex) {
        // Verifique se a mensagem de erro contém a enum específica
        if (ex.getMessage().contains("StatusProdutoAvulso")) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Status fornecido inválido. Os valores permitidos são: " + Arrays.toString(StatusProdutoAvulso.values()));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Erro ao processar a requisição: " + ex.getMessage());
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity tratarTokenInvalido(JWTVerificationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " +ex.getLocalizedMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " +ex.getLocalizedMessage());
    }




    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
