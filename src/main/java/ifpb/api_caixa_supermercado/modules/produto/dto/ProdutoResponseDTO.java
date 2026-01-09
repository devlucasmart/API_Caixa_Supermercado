package ifpb.api_caixa_supermercado.modules.produto.dto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Integer id,
        String nome,
        BigDecimal preco,
        String unidade
) {
}
