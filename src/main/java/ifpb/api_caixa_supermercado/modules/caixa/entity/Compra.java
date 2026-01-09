package ifpb.api_caixa_supermercado.modules.caixa.entity;

import ifpb.api_caixa_supermercado.modules.produto.entity.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compras")
@Getter
@Setter
@NoArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataCompra = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "compra_produtos",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtosCompra = new ArrayList<>();

    @DecimalMin(value = "0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorCompra = BigDecimal.ZERO;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FormaPagamento formaPagamento;

    public Compra(List<Produto> produtos, FormaPagamento formaPagamento) {
        this.dataCompra = LocalDateTime.now();
        this.produtosCompra = produtos;
        this.valorCompra = calcularValorTotal();
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal calcularValorTotal() {
        return produtosCompra.stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
