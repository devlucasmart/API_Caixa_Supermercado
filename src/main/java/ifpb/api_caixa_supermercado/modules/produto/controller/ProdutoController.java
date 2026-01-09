package ifpb.api_caixa_supermercado.modules.produto.controller;

import ifpb.api_caixa_supermercado.modules.produto.dto.ProdutoRequestDTO;
import ifpb.api_caixa_supermercado.modules.produto.dto.ProdutoResponseDTO;
import ifpb.api_caixa_supermercado.modules.produto.service.ProdutoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ProdutoResponseDTO> salvarProduto(@Valid @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvarProduto(produtoRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(produtoService.buscarProdutoPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Integer id, @Valid @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, produtoRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Integer id) {
        produtoService.removerProduto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
