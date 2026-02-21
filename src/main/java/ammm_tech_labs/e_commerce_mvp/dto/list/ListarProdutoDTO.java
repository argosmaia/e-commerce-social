package ammm_tech_labs.e_commerce_mvp.dto.list;

import java.util.UUID;

import ammm_tech_labs.e_commerce_mvp.models.Produto;

public record ListarProdutoDTO(
    UUID id,
    String nome,
    Double preco
) {
    public ListarProdutoDTO(Produto produto) {
        this(
            produto.getId(),
            produto.getNome(),
            produto.getPreco()
        );
    }
}
