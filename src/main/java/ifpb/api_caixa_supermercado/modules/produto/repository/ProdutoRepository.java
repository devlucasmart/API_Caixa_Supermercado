package ifpb.api_caixa_supermercado.modules.produto.repository;

import ifpb.api_caixa_supermercado.modules.produto.entity.Produto;
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
    public Produto salvarProduto(Produto produto) {
        entityManager.persist(produto);
        return produto;
    }

    public Produto buscarProdutoPorId(Integer id) {
        return entityManager.find(Produto.class, id);
    }

    public List<Produto> buscarProdutoPorNome(String nome) {
        return entityManager
                .createQuery("SELECT p FROM Produto p WHERE p.nome = :nome", Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Produto> listarProdutos() {
        return entityManager.createQuery("from Produto", Produto.class).getResultList();
    }

    @Transactional
    public Produto atualizarProduto(Produto produto) {
        return entityManager.merge(produto);
    }

    @Transactional
    public void removerProduto(Integer id) {
        entityManager.remove(entityManager.find(Produto.class, id));
    }
}
