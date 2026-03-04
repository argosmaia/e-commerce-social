package ammm_tech_labs.e_commerce_mvp.dto;

import java.math.BigDecimal;
import java.util.UUID;

import ammm_tech_labs.e_commerce_mvp.models.Produto;


public record ProdutoDTO(
    UUID id,
    String nome,
    BigDecimal preco,
    String fotoUrl,
    String descricao,
    Double avaliacaoMedia
) {
    public ProdutoDTO(Produto produto) {
        this(
            produto.getId(),
            produto.getNome(),
            produto.getPreco(),
            produto.getFotoUrl(),
            produto.getDescricao(),
            produto.getAvaliacaoMedia()
        );
    }
}

