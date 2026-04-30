package ammm_tech_labs.e_commerce_mvp.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import ammm_tech_labs.e_commerce_mvp.models.Usuario;

public record UsuarioDTO(
    UUID id,
    String primeiroNome,
    String ultimoNome,
    String username,
    String senha,
    String telefone,
    String email,
    LocalDateTime dataNascimento,
    //int idade,
    String cpf
    // List<Desejo> desejos,
    // List<Compra> compras
) {
    public UsuarioDTO(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getPrimeiroNome(),
            usuario.getUltimoNome(),
            usuario.getUsername(),
            usuario.getSenha(),
            usuario.getTelefone(),
            usuario.getEmail(),
            usuario.getDataNascimento(),
            //usuario.getIdade(),
            usuario.getCpf()
            // usuario.getDesejos(),
            // usuario.getCompras()
        );
    }
}
