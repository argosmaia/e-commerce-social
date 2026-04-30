package ammm_tech_labs.e_commerce_mvp.seeders;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import ammm_tech_labs.e_commerce_mvp.dto.create.CriarProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.models.Produto;
import ammm_tech_labs.e_commerce_mvp.repository.ProdutoRepository;
import ammm_tech_labs.e_commerce_mvp.factory.ProdutoFactory;

@Component
public class ProdutoSeeder {

    private final ProdutoRepository produtoRepository;
    private final ProdutoFactory produtoFactory;

    public ProdutoSeeder(
        ProdutoRepository produtoRepository,
        ProdutoFactory produtoFactory
    ) {
        this.produtoRepository = produtoRepository;
        this.produtoFactory = produtoFactory;
    }

    public void run() {
        List<Produto> produtos = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            CriarProdutoDTO dto = produtoFactory.criarProdutoDTOAleatorio();
            produtos.add(new Produto(dto));
        }

        produtoRepository.saveAll(produtos);

        System.out.println("✅ 100 produtos foram inseridos no banco.");
    }
}