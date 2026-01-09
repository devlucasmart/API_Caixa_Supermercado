package ifpb.api_caixa_supermercado.modules.produto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(nullable = false, length = 20)
    @NotBlank
    @Size(max = 20)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal preco;

    @Column(nullable = false, length = 5)
    @NotBlank
    @Size(max = 5)
    private String unidade;

    public Produto(String nome, BigDecimal preco, String unidade) {
        this.nome = nome;
        this.preco = preco;
        this.unidade = unidade;
    }
}
