package ifpb.api_caixa_supermercado.modules.produto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank @Size(max = 20)
        String nome,
        @NotNull @DecimalMin("0.01")
        BigDecimal preco,
        @NotBlank @Size(max = 5)
        String unidade
) {
}
