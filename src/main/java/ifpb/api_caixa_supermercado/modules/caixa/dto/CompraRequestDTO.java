package ifpb.api_caixa_supermercado.modules.caixa.dto;

import ifpb.api_caixa_supermercado.modules.caixa.entity.FormaPagamento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CompraRequestDTO(
        @NotEmpty
        @Valid
        List<Integer> produtosCompra,
        @NotNull
        FormaPagamento formaPagamento) {
}