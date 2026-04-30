package ammm_tech_labs.e_commerce_mvp.dto.list;

import java.math.BigDecimal;
import java.util.UUID;

import ammm_tech_labs.e_commerce_mvp.models.Produto;

public record ListarProdutoDTO(
    UUID id,
    String nome,
    BigDecimal preco
) {
    public ListarProdutoDTO(Produto produto) {
        this(
            produto.getId(),
            produto.getNome(),
            produto.getPreco()
        );
    }
}
