package ifpb.api_caixa_supermercado.service;

import ifpb.api_caixa_supermercado.entity.Produto;
import ifpb.api_caixa_supermercado.exception.ProdutoInvalidoException;
import ifpb.api_caixa_supermercado.exception.ProdutoNaoEncontradoException;
import ifpb.api_caixa_supermercado.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto postProduto(Produto produto) {
        if (produto == null) {
            throw new ProdutoInvalidoException();
        }
        return produtoRepository.postProduto(produto);
    }

    public Produto getProduto(Integer id) {
        Produto produto = produtoRepository.getProduto(id);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException();
        }
        return produto;
    }

    public Produto putProduto(Integer id, Produto produto) {
        if (produto == null) {
            throw new ProdutoInvalidoException();
        }
        if (produtoRepository.getProduto(id) == null) {
            throw new ProdutoNaoEncontradoException();
        }
        return produtoRepository.putProduto(id, produto);
    }

    public void deleteProduto(Integer id) {
        Produto produto = produtoRepository.getProduto(id);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException();
        }
        produtoRepository.deleteProduto(id);
    }

    public List<Produto> getProdutos() {
        return produtoRepository.getProdutos();
    }
}
