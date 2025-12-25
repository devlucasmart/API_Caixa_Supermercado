package ifpb.api_caixa_supermercado.exception;

public class CompraInvalidaException extends RuntimeException {

    public CompraInvalidaException() {
        super("Compra inválida!");
    }

    public CompraInvalidaException(String message) {
        super(message);
    }
}
