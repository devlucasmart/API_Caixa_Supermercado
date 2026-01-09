package ifpb.api_caixa_supermercado.modules.caixa.controller;

import ifpb.api_caixa_supermercado.modules.caixa.dto.CompraRequestDTO;
import ifpb.api_caixa_supermercado.modules.caixa.dto.CompraResponseDTO;
import ifpb.api_caixa_supermercado.modules.caixa.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/mercado/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<CompraResponseDTO> salvarCompra(@Valid @RequestBody CompraRequestDTO compraRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compraService.salvarCompra(compraRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> buscarCompraPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(compraService.buscarCompraPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> atualizarCompra(@PathVariable Integer id, @Valid @RequestBody CompraRequestDTO compraRequestDTO) {
        return ResponseEntity.ok(compraService.atualizarCompra(id, compraRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCompra(@PathVariable Integer id) {
        compraService.removerCompra(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>> listarCompras() {
        return ResponseEntity.ok(compraService.listarCompras());
    }
}