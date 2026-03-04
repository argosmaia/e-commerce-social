package ammm_tech_labs.e_commerce_mvp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ammm_tech_labs.e_commerce_mvp.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    
    boolean existsByNome(String nome);

}

