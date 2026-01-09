package ifpb.api_caixa_supermercado.modules.caixa.mapper;

import ifpb.api_caixa_supermercado.modules.caixa.dto.CompraResponseDTO;
import ifpb.api_caixa_supermercado.modules.produto.dto.ProdutoResponseDTO;
import ifpb.api_caixa_supermercado.modules.caixa.entity.Compra;
import ifpb.api_caixa_supermercado.modules.produto.mapper.ProdutoMapper;

import java.util.List;

public class CompraMapper {

    public static CompraResponseDTO toCompraResponseDTO(Compra compra) {
        List<ProdutoResponseDTO> produtosDTO = compra.getProdutosCompra().stream()
                .map(ProdutoMapper::toProdutoResponseDTO)
                .toList();
        return new CompraResponseDTO(
                compra.getId(),
                compra.getDataCompra(),
                produtosDTO,
                compra.getValorCompra(),
                compra.getFormaPagamento()
        );
    }
}
