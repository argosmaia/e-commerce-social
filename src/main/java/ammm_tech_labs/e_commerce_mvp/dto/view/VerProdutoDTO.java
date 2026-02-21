package ammm_tech_labs.e_commerce_mvp.dto.view;

import java.util.UUID;

public record VerProdutoDTO(
    UUID id,
    String nome,
    Double preco
) {
    
}
   
