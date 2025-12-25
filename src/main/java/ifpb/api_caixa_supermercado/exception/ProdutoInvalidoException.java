package ifpb.api_caixa_supermercado.exception;

public class ProdutoInvalidoException extends RuntimeException {

    public ProdutoInvalidoException() {
        super("Produto inválido!");
    }

    public ProdutoInvalidoException(String message) {
        super(message);
    }
}
