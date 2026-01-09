package ifpb.api_caixa_supermercado.modules.caixa.exception;

import ifpb.api_caixa_supermercado.modules.caixa.dto.ErroResponseDTO;
import ifpb.api_caixa_supermercado.modules.produto.exception.ProdutoInvalidoException;
import ifpb.api_caixa_supermercado.modules.produto.exception.ProdutoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProdutoInvalidoException.class)
    public ResponseEntity<ErroResponseDTO> produtoInvalido(ProdutoInvalidoException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(construirErroResponseDTO(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> produtoNaoEncontrado(ProdutoNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(construirErroResponseDTO(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(CompraInvalidaException.class)
    public ResponseEntity<ErroResponseDTO> compraInvalida(CompraInvalidaException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(construirErroResponseDTO(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(CompraNaoEncontradaException.class)
    public ResponseEntity<ErroResponseDTO> compraNaoEncontrada(CompraNaoEncontradaException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(construirErroResponseDTO(HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> erroGenerico(Exception e) {
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                List.of("Erro inesperado: " + e.getMessage())
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroResponseDTO);
    }

    private ErroResponseDTO construirErroResponseDTO(HttpStatus status, String mensagem) {
        return new ErroResponseDTO(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                List.of(mensagem)
        );
    }
}
