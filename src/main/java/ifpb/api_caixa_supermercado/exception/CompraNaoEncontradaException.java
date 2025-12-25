package ifpb.api_caixa_supermercado.exception;

public class CompraNaoEncontradaException extends RuntimeException {

    public CompraNaoEncontradaException() {
        super("Compra não encontrada!");
    }

    public CompraNaoEncontradaException(String message) {
        super(message);
    }
}
