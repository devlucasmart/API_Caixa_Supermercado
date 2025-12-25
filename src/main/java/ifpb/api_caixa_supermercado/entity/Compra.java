package ifpb.api_caixa_supermercado.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dataCompra;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "compra_produtos",
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtosCompra = new ArrayList<>();
    @Column(precision = 10, scale = 2, nullable = true)
    private BigDecimal valorCompra;
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    public Compra() {
        this.dataCompra = LocalDateTime.now();
        this.valorCompra = BigDecimal.ZERO;
    }

    public Compra(List<Produto> produtos, FormaPagamento formaPagamento) {
        this.dataCompra = LocalDateTime.now();
        this.produtosCompra = produtos;
        this.valorCompra = getValorTotal();
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getValorTotal() {
        List<Produto> produtosCompra = this.produtosCompra;
        BigDecimal total = BigDecimal.ZERO;
        for (Produto produto : produtosCompra) {
            if (produto != null && produto.getPreco() != null) {
                total = total.add(produto.getPreco());
            }
        }
        return total;
    }

    public void addProduto(Produto produto) {
        this.produtosCompra.add(produto);
        this.valorCompra = getValorTotal();
    }

    public void removeProduto(Produto produto) {
        this.produtosCompra.remove(produto);
        this.valorCompra = getValorTotal();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }

    public List<Produto> getProdutosCompra() {
        return produtosCompra;
    }

    public void setProdutosCompra(List<Produto> produtosCompra) {
        this.produtosCompra = produtosCompra;
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", dataCompra=" + dataCompra +
                ", produtosCompra=" + produtosCompra +
                ", valorCompra=" + valorCompra +
                ", formaPagamento=" + formaPagamento +
                '}';
    }
}
