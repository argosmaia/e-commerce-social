package ammm_tech_labs.e_commerce_mvp.service;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ammm_tech_labs.e_commerce_mvp.dto.UsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.create.CriarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.list.ListarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.update.AtualizarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.view.VerUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.maps.UsuarioMapper;
import ammm_tech_labs.e_commerce_mvp.models.Usuario;
import ammm_tech_labs.e_commerce_mvp.repository.UsuarioRepository;
import ammm_tech_labs.e_commerce_mvp.response.APIResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarios;
	private final UsuarioMapper mapper;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public APIResponse<UsuarioDTO> cadastrarUsuario(CriarUsuarioDTO dados) {
		if (usuarios.existsByEmail(dados.email())) {
			return APIResponse.conflito("Email já cadastrado");
		}

		var senhaHash = passwordEncoder.encode(dados.senha());

		var usuario = new Usuario(
				dados.primeiroNome(),
				dados.ultimoNome(),
				dados.username(),
				senhaHash,
				dados.telefone(),
				dados.email(),
				dados.dataNascimento(),
				dados.idade(),
				dados.cpf()
		);

		usuarios.save(usuario);

		return APIResponse.criado(
				"Usuário criado com sucesso",
				mapper.toDTO(usuario)
		);
	}

	public APIResponse<Page<ListarUsuarioDTO>> listarUsuarios(Pageable paginacao) {
		var paginas = usuarios.findAll(paginacao)
						.map(usuario -> mapper.toListarDTO(usuario));
		return APIResponse.sucesso("Lista de usuários", paginas);
}


	public APIResponse<VerUsuarioDTO> verUsuario(UUID id) {
		var usuario = usuarios.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

		return APIResponse.sucesso(
				"Usuário encontrado",
				mapper.toVerDTO(usuario)
		);
	}

	@Transactional
	public APIResponse<UsuarioDTO> atualizarUsuario(UUID id, AtualizarUsuarioDTO dados) {
		var usuario = usuarios.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

		usuario.atualizar(
				dados.primeiroNome(),
				dados.ultimoNome(),
				dados.username(),
				dados.telefone(),
				dados.email(),
				dados.dataNascimento()
		);

		return APIResponse.sucesso(
				"Usuário atualizado",
				mapper.toDTO(usuario)
		);
	}

	@Transactional
	public APIResponse<?> deletarUsuario(UUID id) {
		if (!usuarios.existsById(id)) {
				throw new EntityNotFoundException("Usuário não encontrado");
		}

		usuarios.deleteById(id);

		return APIResponse.sucesso("Usuário deletado com sucesso");
	}
}
