package ammm_tech_labs.e_commerce_mvp.dto.update;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizarUsuarioDTO(
    @NotNull UUID id,
    @NotBlank String primeiroNome,
    @NotBlank String ultimoNome,
    @NotBlank String username,
    @NotBlank String telefone,
    @NotBlank @Valid @Email String email,
    @NotNull LocalDateTime dataNascimento
) {
    
}
