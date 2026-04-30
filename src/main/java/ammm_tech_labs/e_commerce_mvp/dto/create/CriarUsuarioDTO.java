package ammm_tech_labs.e_commerce_mvp.dto.create;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CriarUsuarioDTO(
    @NotBlank String primeiroNome,
    @NotBlank String ultimoNome,
    @NotBlank String username,
    @NotBlank @Size(min = 8, max = 16) String senha,
    @NotBlank @Size(min = 12, max = 13) String telefone,
    //@NotNull int idade,
    @NotBlank @Email String email,
    @NotNull @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDateTime dataNascimento,
    @NotBlank @Pattern(regexp = "\\d{11}") String cpf
) {

}
