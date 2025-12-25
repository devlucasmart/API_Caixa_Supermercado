package ifpb.api_caixa_supermercado.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {

    public ProdutoNaoEncontradoException() {
        super("Produto não encontrado!");
    }

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }
}
