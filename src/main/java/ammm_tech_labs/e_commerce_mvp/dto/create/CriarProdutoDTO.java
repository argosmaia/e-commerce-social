package ammm_tech_labs.e_commerce_mvp.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CriarProdutoDTO(
    @NotBlank String nome,
    @NotNull @Positive Double preco
) {

}