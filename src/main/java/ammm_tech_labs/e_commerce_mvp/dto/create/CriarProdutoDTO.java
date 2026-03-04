package ammm_tech_labs.e_commerce_mvp.dto.create;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CriarProdutoDTO(
    @NotBlank String nome,
    @NotNull @Positive BigDecimal preco,
    @NotBlank String fotoUrl,
    @NotBlank String descricao,
    @NotNull @Positive Double avaliacaoMedia
) {

}