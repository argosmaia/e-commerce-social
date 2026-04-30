package ammm_tech_labs.e_commerce_mvp.dto.update;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AtualizarProdutoDTO(
    @NotNull UUID id,
    @NotBlank @Size(min = 2, message = "Nome não pode ser vazio") String nome,
    @NotNull @Positive(message = "Preço deve ser maior que zero") BigDecimal preco,
    @NotBlank String fotoUrl,
    @NotBlank String descricao
) {
    
}
