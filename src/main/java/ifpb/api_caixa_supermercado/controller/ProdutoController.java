package ifpb.api_caixa_supermercado.controller;

import ifpb.api_caixa_supermercado.entity.Produto;
import ifpb.api_caixa_supermercado.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/mercado/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Produto> postProduto(@RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.postProduto(produto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProduto(@PathVariable Integer id) {
        return ResponseEntity.ok(produtoService.getProduto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> putProduto(@PathVariable Integer id, @RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.putProduto(id, produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Integer id) {
        produtoService.deleteProduto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<Produto>> getProdutos() {
        return ResponseEntity.ok(produtoService.getProdutos());
    }
}
