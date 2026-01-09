package ifpb.api_caixa_supermercado.modules.caixa.dto;

import ifpb.api_caixa_supermercado.modules.caixa.entity.FormaPagamento;
import ifpb.api_caixa_supermercado.modules.produto.dto.ProdutoResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CompraResponseDTO (
    Integer id,
    LocalDateTime dataCompra,
    List<ProdutoResponseDTO> produtosCompra,
    BigDecimal valorCompra,
    FormaPagamento formaPagamento) {
}
