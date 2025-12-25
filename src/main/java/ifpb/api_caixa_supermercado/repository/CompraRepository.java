package ifpb.api_caixa_supermercado.repository;

import ifpb.api_caixa_supermercado.entity.Compra;
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
    public Compra postCompra(Compra compra){
        entityManager.persist(compra);
        return compra;
    }

    @Transactional
    public Compra postCompra(){
        Compra compra = new Compra();
        entityManager.persist(compra);
        return compra;
    }

    public Compra getCompra(Integer id){
        return entityManager.find(Compra.class, id);
    }

    @Transactional
    public Compra putCompra(Integer id, Compra compra){
        compra.setId(id);
        return entityManager.merge(compra);
    }

    @Transactional
    public void deleteCompra(Integer id){
        Compra compra = entityManager.find(Compra.class, id);
        if (compra != null) {
            entityManager.remove(compra);
        }
    }

    public List<Compra> getCompras(){
        return entityManager.createQuery("from Compra", Compra.class).getResultList();
    }

}
