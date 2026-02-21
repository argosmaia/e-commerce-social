package ammm_tech_labs.e_commerce_mvp.dto;

import java.util.UUID;

import ammm_tech_labs.e_commerce_mvp.models.Produto;

public record ProdutoDTO(
    UUID id,
    String nome,
    Double preco
) {
    public ProdutoDTO(Produto produto) {
        this(
            produto.getId(),
            produto.getNome(),
            produto.getPreco()
        );
    }
}

