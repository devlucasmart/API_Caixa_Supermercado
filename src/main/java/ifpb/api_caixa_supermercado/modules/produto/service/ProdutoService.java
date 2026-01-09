package ifpb.api_caixa_supermercado.modules.produto.service;

import ifpb.api_caixa_supermercado.modules.produto.dto.ProdutoRequestDTO;
import ifpb.api_caixa_supermercado.modules.produto.dto.ProdutoResponseDTO;
import ifpb.api_caixa_supermercado.modules.produto.entity.Produto;
import ifpb.api_caixa_supermercado.modules.produto.exception.ProdutoInvalidoException;
import ifpb.api_caixa_supermercado.modules.produto.exception.ProdutoNaoEncontradoException;
import ifpb.api_caixa_supermercado.modules.produto.mapper.ProdutoMapper;
import ifpb.api_caixa_supermercado.modules.produto.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static ifpb.api_caixa_supermercado.modules.produto.mapper.ProdutoMapper.toProduto;
import static ifpb.api_caixa_supermercado.modules.produto.mapper.ProdutoMapper.toProdutoResponseDTO;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponseDTO salvarProduto(ProdutoRequestDTO produtoRequestDTO) {
        if (!produtoRepository.buscarProdutoPorNome(produtoRequestDTO.nome()).isEmpty()) {
            throw new ProdutoInvalidoException("Produto já existe com esse nome");
        }
        Produto produto = toProduto(produtoRequestDTO);
        return toProdutoResponseDTO(produtoRepository.salvarProduto(produto));
    }

    public ProdutoResponseDTO buscarProdutoPorId(Integer id) {
        Produto produto = produtoRepository.buscarProdutoPorId(id);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado");
        }
        return toProdutoResponseDTO(produto);
    }

    public List<ProdutoResponseDTO> listarProdutos() {
        List<Produto> produtos = produtoRepository.listarProdutos();
        return produtos.stream()
                .map(ProdutoMapper::toProdutoResponseDTO)
                .toList();
    }

    public ProdutoResponseDTO atualizarProduto(Integer id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoRepository.buscarProdutoPorId(id);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado");
        }
        produto.setNome(produtoRequestDTO.nome());
        produto.setPreco(produtoRequestDTO.preco());
        produto.setUnidade(produtoRequestDTO.unidade());
        return toProdutoResponseDTO(produtoRepository.atualizarProduto(produto));
    }

    public void removerProduto(Integer id) {
        Produto produto = produtoRepository.buscarProdutoPorId(id);
        if (produto == null) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado");
        }
        produtoRepository.removerProduto(id);
    }
}
