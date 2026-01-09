package ifpb.api_caixa_supermercado.modules.caixa.repository;

import ifpb.api_caixa_supermercado.modules.caixa.entity.Compra;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompraRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Compra salvarCompra(Compra compra) {
        entityManager.persist(compra);
        return compra;
    }

    public Compra buscarCompraPorId(Integer id) {
        return entityManager.find(Compra.class, id);
    }

    public List<Compra> listarCompras() {
        return entityManager.createQuery("from Compra", Compra.class).getResultList();
    }

    @Transactional
    public Compra atualizarCompra(Compra compra) {
        return entityManager.merge(compra);
    }

    @Transactional
    public void removerCompra(Integer id) {
        entityManager.remove(entityManager.find(Compra.class, id));
    }
}
