package ammm_tech_labs.e_commerce_mvp.dto.list;

import java.util.UUID;

import ammm_tech_labs.e_commerce_mvp.models.Usuario;

public record ListarUsuarioDTO(
    UUID id,
    String primeiroNome,
    String ultimoNome,
    String username,
    int idade
) {
    public ListarUsuarioDTO(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getPrimeiroNome(),
            usuario.getUltimoNome(),
            usuario.getUsername(),
            usuario.getIdade()
        );
    }
}
