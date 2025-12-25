package ifpb.api_caixa_supermercado.controller;

import ifpb.api_caixa_supermercado.entity.Compra;
import ifpb.api_caixa_supermercado.entity.FormaPagamento;
import ifpb.api_caixa_supermercado.service.CompraService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/mercado/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<Compra> postCompra(@RequestBody(required = false) Compra compra) {
        if (compra != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(compraService.postCompra(compra));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(compraService.postCompra());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> getCompra(@PathVariable Integer id) {
        return ResponseEntity.ok(compraService.getCompra(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> putCompra(@PathVariable Integer id, @RequestBody Compra compra) {
        return ResponseEntity.ok(compraService.putCompra(id, compra));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable Integer id) {
        compraService.deleteCompra(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<Compra>> getCompras() {
        return ResponseEntity.ok(compraService.getCompras());
    }

    @PostMapping("/{compraId}/produtos/{produtoId}")
    public ResponseEntity<Compra> addProdutoToCompra(@PathVariable Integer compraId, @PathVariable Integer produtoId) {
        Compra compra = compraService.addProduto(compraId, produtoId);
        return ResponseEntity.ok(compra);
    }

    @DeleteMapping("/{compraId}/produtos/{produtoId}")
    public ResponseEntity<Void> removeProdutoFromCompra(@PathVariable Integer compraId, @PathVariable Integer produtoId) {
        compraService.removeProduto(compraId, produtoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{compraId}/pagamento/{pagamento}")
    public ResponseEntity<Compra> setPagamentoFromCompra(@PathVariable Integer compraId, @PathVariable String pagamento) {
        Compra compra = compraService.getCompra(compraId);
        Map<String, FormaPagamento> stringToFormaPagamento = Map.of(
                "1", FormaPagamento.A_VISTA,
                "2", FormaPagamento.CREDITO,
                "3", FormaPagamento.DEBITO,
                "4", FormaPagamento.PIX
        );
        compra.setFormaPagamento(stringToFormaPagamento.get(pagamento.toLowerCase()));
        return ResponseEntity.ok(compraService.putCompra(compraId, compra));
    }
}