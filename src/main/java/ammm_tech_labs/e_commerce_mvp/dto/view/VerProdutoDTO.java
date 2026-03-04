package ammm_tech_labs.e_commerce_mvp.dto.view;

import java.math.BigDecimal;
import java.util.UUID;

public record VerProdutoDTO(
    UUID id,
    String nome,
    BigDecimal preco,
    String fotoUrl,
    String descricao,
    Double avaliacaoMedia
) {
    
}
   
