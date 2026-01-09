package ifpb.api_caixa_supermercado.modules.produto.mapper;

import ifpb.api_caixa_supermercado.modules.produto.dto.ProdutoRequestDTO;
import ifpb.api_caixa_supermercado.modules.produto.dto.ProdutoResponseDTO;
import ifpb.api_caixa_supermercado.modules.produto.entity.Produto;

public class ProdutoMapper {

    public static Produto toProduto(ProdutoRequestDTO produtoRequestDTO) {
        return new Produto(
                produtoRequestDTO.nome(),
                produtoRequestDTO.preco(),
                produtoRequestDTO.unidade()
        );
    }

    public static ProdutoResponseDTO toProdutoResponseDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getPreco(),
                produto.getUnidade()
        );
    }
}
