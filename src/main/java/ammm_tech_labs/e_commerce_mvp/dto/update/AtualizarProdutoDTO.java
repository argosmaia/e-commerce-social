package ammm_tech_labs.e_commerce_mvp.dto.update;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AtualizarProdutoDTO(
    @Size(min = 1, message = "Nome não pode ser vazio") String nome,
    @Positive(message = "Preço deve ser maior que zero") Double preco
) {
    
}
