package ifpb.api_caixa_supermercado.repository;

import ifpb.api_caixa_supermercado.entity.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Produto postProduto(Produto produto) {
        entityManager.persist(produto);
        return produto;
    }

    public Produto getProduto(Integer id) {
        return entityManager.find(Produto.class, id);
    }

    @Transactional
    public Produto putProduto(Integer id, Produto produto) {
        produto.setId(id);
        return entityManager.merge(produto);
    }

    @Transactional
    public void deleteProduto(Integer id) {
        Produto produto = entityManager.find(Produto.class, id);
        if (produto != null) {
            entityManager.remove(produto);
        }
    }

    public List<Produto> getProdutos() {
        return entityManager.createQuery("from Produto", Produto.class).getResultList();
    }

}
